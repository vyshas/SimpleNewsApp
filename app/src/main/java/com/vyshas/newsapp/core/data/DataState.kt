package com.vyshas.newsapp.core.data

import com.vyshas.newsapp.core.domain.exceptions.ExceptionEntity

/**
 * A generic class that holds a value or an exception
 */
sealed class DataState<T> {
    data class Success<T>(val data: T) : DataState<T>()
    data class Error<T>(val message: ExceptionEntity) : DataState<T>()
}
