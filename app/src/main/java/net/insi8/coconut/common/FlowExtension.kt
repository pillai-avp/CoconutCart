package net.insi8.coconut.common

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalCoroutinesApi::class)
fun <T> MutableStateFlow<T>.update(updateFunction: (currentValue: T) -> T) {
    val currentValue = this.value
    if (currentValue != null) {
        val newValue = updateFunction(currentValue)
        this.value = newValue
    }
}