package net.insi8.coconut.cart

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.insi8.coconut.api.data.Cart
import net.insi8.coconut.api.datasource.GroceriesCartDataSource
import net.insi8.coconut.api.db.CartDao
import net.insi8.coconut.api.db.CartEntity

class GroceriesCartUseCase(
    private val dataSource: GroceriesCartDataSource,
    private val cartDao: CartDao
) {

    private val _cartState: MutableStateFlow<Cart?> =
        MutableStateFlow(null)
    val cartState = _cartState.asStateFlow()

    suspend fun getGroceryCart() {
        val cart = dataSource.getGroceriesCart().asSuccessOrNull()
        _cartState.update {
            cart
        }
        cart?.let {
            syncLocalData(it)
        }
    }

    private suspend fun syncLocalData(cart: Cart) {
        val networkCartItems =
            cart.items.map { CartEntity(product_id = it.product.id, quantity = it.quantity) }
        val localCartItems = cartDao.gelAllCartItems()
        val syncedLocalCartItems = localCartItems.filter { it.synced }
        val notSyncedLocalCartItems = localCartItems.filterNot { it.synced }

        // new from server
        networkCartItems.filterNot { networkItems ->
            localCartItems.map { it.product_id }.contains(networkItems.product_id)
        }.map {
            cartDao.insertItem(item = it)
        }
        // update synced from network
        networkCartItems.filter { networkItems ->
            syncedLocalCartItems.map { it.product_id }.contains(networkItems.product_id)
        }.map {
            cartDao.updateItem(cart = it)
        }
        // Update the remote cart
        updateRemoteCart(cartItems = mutableMapOf<Long, Long>().apply {
            notSyncedLocalCartItems.forEach {
                this[it.product_id] = it.quantity
            }
        })
    }

    suspend fun updateCartItem(productId: Long, updatedQuantity: Long) {
        updateLocalStorage(productId, updatedQuantity)
        updateRemoteCart(mutableMapOf((productId to updatedQuantity)))
    }

    private suspend fun updateLocalStorage(productId: Long, updatedQuantity: Long) {
        cartDao.updateItem(
            CartEntity(
                product_id = productId,
                quantity = updatedQuantity,
                synced = false
            )
        )
        updateCartState(mutableMapOf((productId to updatedQuantity)), false)
    }

    private suspend fun updateRemoteCart(cartItems: Map<Long, Long>) {
        coroutineScope {
            launch {
                val result = dataSource.updateCart(cartItems = cartItems)
                result.asSuccessOrNull()?.forEach { it ->
                    cartDao.updateItem(
                        CartEntity(
                            product_id = it.product.id,
                            quantity = it.quantity,
                            synced = true
                        )
                    )
                }
                updateCartState(cartItems, true)
            }
        }
    }

    private fun updateCartState(cartItems: Map<Long, Long>, synced: Boolean) {
        _cartState.update { state ->
            val updatedCartItemIds = cartItems.keys
            state?.let { cart ->
                cart.copy(items = cart.items.mapNotNull { item ->
                    if (updatedCartItemIds.contains(item.product.id)) {
                        if (synced && cartItems[item.product.id] == 0L) {
                            null
                        } else {
                            cartItems[item.product.id]?.let {
                                item.copy(quantity = it)
                            }
                        }
                    } else {
                        item
                    }
                })
            }
        }
    }
}