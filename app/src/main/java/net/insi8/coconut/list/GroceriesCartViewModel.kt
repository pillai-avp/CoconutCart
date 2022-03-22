package net.insi8.coconut.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.insi8.coconut.api.data.common.Result
import net.insi8.coconut.api.datasource.GroceriesCartDataSource
import timber.log.Timber

class GroceriesCartViewModel(private val cartDataSource: GroceriesCartDataSource) : ViewModel() {

    fun initialize() {
        getGroceriesCart()
    }

    private fun getGroceriesCart() {
        viewModelScope.launch {
            when(val result = cartDataSource.getGroceriesCart()){
                is Result.Error -> {
                    Timber.e("result is ${result.errorMessage}")
                }
                is Result.Success -> {
                    Timber.d("result is ${result.data}")
                }
            }
        }
    }
}