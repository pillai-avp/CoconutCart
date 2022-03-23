package net.insi8.coconut.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import net.insi8.coconut.cart.GroceriesCartUseCase

class GroceriesCartViewModel(private val useCase: GroceriesCartUseCase) : ViewModel() {

    val viewState = useCase.cartState.map { cart ->
        if (cart == null) {
            GroceriesCartState.Loading
        } else {
            GroceriesCartState.Done(cart = cart)
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