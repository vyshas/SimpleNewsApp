package com.vyshas.newsapp.core.domain.entity

import com.vyshas.newsapp.core.domain.exceptions.ExceptionEntity
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.net.UnknownHostException

fun Throwable.mapErrorOrException(): ExceptionEntity {
    return when (this) {
        is UnknownHostException,
        is InterruptedIOException -> ExceptionEntity.NoConnectivityErrorEntity
        is HttpException -> {
            ExceptionEntity.ApiErrorEntity(message())
        }
        else -> ExceptionEntity.ApiErrorEntity(message)
    }
}
