package com.vyshas.newsapp.core.data

/**
 * A generic class that holds a value or an exception
 */
sealed class DataState<T> {
    data class Success<T>(val data: T) : DataState<T>()
    data class Error<T>(val message: String) : DataState<T>()
}
