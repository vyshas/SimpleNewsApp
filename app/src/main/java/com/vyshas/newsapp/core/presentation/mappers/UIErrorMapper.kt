package com.vyshas.newsapp.core.presentation.mappers

import com.vyshas.newsapp.core.domain.exceptions.ExceptionEntity
import timber.log.Timber
import javax.inject.Inject

class UIErrorMapper @Inject constructor(
    private val stringUtils: StringUtils
) {
    fun mapToUiMsg(exceptionEntity: ExceptionEntity): String {
        return when (exceptionEntity) {
            is ExceptionEntity.ApiErrorEntity -> {
                Timber.d(exceptionEntity.errorMsg)
                stringUtils.noNetworkErrorMessage()
            }
            ExceptionEntity.NoConnectivityErrorEntity -> {
                stringUtils.somethingWentWrong()
            }
        }
    }
}