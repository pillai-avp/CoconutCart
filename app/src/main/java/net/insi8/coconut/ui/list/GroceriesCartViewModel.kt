package net.insi8.coconut.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import net.insi8.coconut.api.data.Cart
import net.insi8.coconut.cart.GroceriesCartUseCase

class GroceriesCartViewModel(private val useCase: GroceriesCartUseCase) : ViewModel() {

    val viewState = useCase.cartState.map { cart ->
        if (cart == null) {
            GroceriesCartState.Loading
        } else {
            GroceriesCartState.Done(transformData(cart = cart))
        }
    }

    private fun transformData(cart: Cart): List<CartListItem> {
        return mutableListOf<CartListItem>().apply {
            addAll(cart.items.map {
                CartListItem.ProductItem(it)
            })
            val pairOfTotalQuantityAndPrice =
                cart.items.map { Pair(it.quantity, it.product.grossPrice.toBigDecimal()) }
                    .reduce { acc, pair ->
                        Pair(
                            acc.first + pair.first,
                            acc.second + (pair.second * pair.first.toBigDecimal())
                        )
                    }
            add(
                CartListItem.ExtraDataItem(
                    money = ShowMeTheMoney(
                        totalNumberOfItems = pairOfTotalQuantityAndPrice.first,
                        grandTotalPrice = pairOfTotalQuantityAndPrice.second.toString()
                    )
                )
            )
        }

    }

    init {
        getGroceriesCart()
    }

    private fun getGroceriesCart() {
        viewModelScope.launch {
            useCase.getGroceryCart()
        }
    }

    fun updateCartItem(productId: Long, updatedQuantity: Long) {
        viewModelScope.launch {
            useCase.updateCartItem(productId, updatedQuantity)
        }
    }
}