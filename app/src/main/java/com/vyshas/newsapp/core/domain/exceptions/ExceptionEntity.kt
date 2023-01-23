package com.vyshas.newsapp.core.domain.exceptions

sealed class ExceptionEntity {

    object NoConnectivityErrorEntity : ExceptionEntity()

    data class ApiErrorEntity(
        val errorMsg: String?
    ) : ExceptionEntity()
}
