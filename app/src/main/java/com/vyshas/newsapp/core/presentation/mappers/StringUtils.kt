package com.vyshas.newsapp.core.presentation.mappers

import android.app.Application
import com.vyshas.newsapp.R
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.toJavaDuration

class StringUtils(private val appContext: Application) {
    fun noNetworkErrorMessage() = appContext.getString(R.string.error_message_no_network_connected)
    fun somethingWentWrong() = appContext.getString(R.string.error_generic)

    /**
     * TODO: add text from string resources.
     */
    fun instantToAgoString(instant: Instant): String {
        val now: Instant = Clock.System.now()
        val duration = (now - instant).toJavaDuration()
        val s = duration.seconds
        return when {
            s > 31536000 -> "${s / 31536000}y ago"
            s > 2592000 -> "${s / 2592000}m ago"
            s > 86400 -> "${s / 86400}d ago"
            s > 3600 -> "${s / 3600}h ago"
            s > 60 -> "${s / 60}m ago"
            else -> "${s}s ago"
        }
    }
}
