package net.insi8.coconut.ui.list

import net.insi8.coconut.api.data.Item

sealed class GroceriesCartState {
    object Loading : GroceriesCartState()
    data class Done(val items: List<CartListItem>) : GroceriesCartState()
}

sealed class CartListItem {
    data class ProductItem(val item: Item) : CartListItem()
    data class ExtraDataItem(
        val money: ShowMeTheMoney
    ) : CartListItem()
}

data class ShowMeTheMoney(
    val totalNumberOfItems: Long,
    val grandTotalPrice: String
)