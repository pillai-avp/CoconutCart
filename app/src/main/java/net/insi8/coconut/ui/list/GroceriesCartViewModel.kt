package net.insi8.coconut.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.insi8.coconut.R
import net.insi8.coconut.api.data.common.Result
import net.insi8.coconut.api.datasource.GroceriesCartDataSource

class GroceriesCartViewModel(private val cartDataSource: GroceriesCartDataSource) : ViewModel() {


    private val _viewState: MutableStateFlow<GroceriesCartState> =
        MutableStateFlow(GroceriesCartState.Loading)
    val viewState = _viewState.asStateFlow()

    init {
        //getGroceriesCart()
    }

    private fun getGroceriesCart() {
        viewModelScope.launch {
            when (val result = cartDataSource.getGroceriesCart()) {
                is Result.Error -> {
                    // Propagate error messaage to the view
                }
                is Result.Success -> {
                    _viewState.update {
                        GroceriesCartState.Done(result.data)
                    }
                }
            }
        }
    }
}