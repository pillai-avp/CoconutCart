package net.insi8.coconut.common

/**
 * A wrapper class that allows providing a strongly typed placeholder
 * for a value that is going to be loaded. This means we don't have to use nullable variables
 * and assume that if the value is null its not yet loaded.
 */
/*
sealed class Loadable<out T> {
    */
/**
 * The value has not yet been loaded
 *//*

    object Loading : Loadable<Nothing>()

    */
/**
 * The value is loaded and should be access via the data member
 *//*

    data class Done<T>(val data: T) : Loadable<T>()

    fun asDoneOrNull(): T? = (this as? Done)?.data

    fun isLoaded() = this is Done
}*/
