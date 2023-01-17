package com.vyshas.newsapp.core.presentation.browser

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent

/**
 * Opens the URL on a Custom Tab if possible. Otherwise fallback
 *
 * @param context The host activity.
 * @param url the Url to be opened.
 */
fun CustomTabsIntent.launchWithFallback(
    context: Context,
    url: String,
    onException: () -> Unit
) {
    val uri = Uri.parse(url)
    val packageName = CustomTabsHelper.getPackageNameToUse(context)
    if (packageName == null) {
        // fallback when Custom Tabs is not available
        val intent = Intent(Intent.ACTION_VIEW, uri)
        try {
            context.startActivity(intent)
        } catch (exception: ActivityNotFoundException) {
            onException()
        }
    } else {
        intent.setPackage(packageName)
        launchUrl(context, uri)
    }
}

fun launchCustomChromeTab(context: Context, uri: String) {
    val customTabBarColor = CustomTabColorSchemeParams.Builder().build()
    val customTabsIntent = CustomTabsIntent.Builder()
        .setDefaultColorSchemeParams(customTabBarColor)
        .build()
    customTabsIntent.launchWithFallback(context,uri,{})
}