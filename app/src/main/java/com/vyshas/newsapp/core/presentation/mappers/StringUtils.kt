package com.vyshas.newsapp.core.presentation.mappers

import android.app.Application
import com.vyshas.newsapp.R

class StringUtils(private val appContext: Application) {
    fun noNetworkErrorMessage() = appContext.getString(R.string.error_message_no_network_connected)
    fun somethingWentWrong() = appContext.getString(R.string.error_generic)
}
