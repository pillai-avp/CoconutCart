package net.insi8.coconut.ui.list

import androidx.annotation.StringRes
import net.insi8.coconut.api.data.Cart

sealed class GroceriesCartState {
    object Loading : GroceriesCartState()
    data class Done(val cart: Cart) : GroceriesCartState()
}