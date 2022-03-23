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
            saveLocalStorage(it)
        }
    }

    private suspend fun saveLocalStorage(cart: Cart) {
        val carts =
            cart.items.map { CartEntity(product_id = it.product.id, quantity = it.quantity) }
        carts.forEach {
            cartDao.insertItem(item = it)
        }
    }

    suspend fun updateCartItem(productId: Long, updatedQuantity: Long) {
        updateLocalStorage(productId, updatedQuantity)
        syncUpdatedCart(productId, updatedQuantity)
    }

    private suspend fun updateLocalStorage(productId: Long, updatedQuantity: Long) {
        cartDao.updateItem(
            CartEntity(
                product_id = productId,
                quantity = updatedQuantity,
                synced = false
            )
        )
        updateCartState(productId, updatedQuantity, false)
    }

    private suspend fun syncUpdatedCart(productId: Long, updatedQuantity: Long) {
        coroutineScope {
            launch {
                val result = dataSource.updateCart(productId, updatedQuantity)
                result.asSuccessOrNull()?.let {
                    cartDao.updateItem(
                        CartEntity(
                            product_id = productId,
                            quantity = updatedQuantity,
                            synced = true
                        )
                    )
                }
                updateCartState(productId, updatedQuantity, true)
            }
        }
    }

    private fun updateCartState(productId: Long, updatedQuantity: Long, synced: Boolean) {
        _cartState.update { state ->
            state?.let { cart ->
                cart.copy(items = cart.items.mapNotNull { item ->
                    if (item.product.id == productId) {
                        if (synced && updatedQuantity == 0L) {
                            null
                        } else {
                            item.copy(quantity = updatedQuantity)
                        }
                    } else {
                        item
                    }
                })
            }
        }
    }
}