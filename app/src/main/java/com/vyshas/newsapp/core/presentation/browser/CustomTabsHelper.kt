package com.vyshas.newsapp.core.presentation.browser

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.text.TextUtils
import timber.log.Timber

object CustomTabsHelper {

    private const val HTTP = "http"
    private const val STABLE_PACKAGE = "com.android.chrome"
    private const val BETA_PACKAGE = "com.chrome.beta"
    private const val DEV_PACKAGE = "com.chrome.dev"
    private const val LOCAL_PACKAGE = "com.google.android.apps.chrome"
    private const val ACTION_CUSTOM_TABS_CONNECTION = "android.support.customtabs.action.CustomTabsService"

    /**
     * Goes through all apps that handle VIEW intents and have a warmup service. Picks
     * the one chosen by the user if there is one, otherwise makes a best effort to return a
     * valid package name.
     *
     *  This is **not** threadsafe.
     *
     * @param context [Context] to use for accessing [PackageManager].
     * @return The package name recommended to use for connecting to custom tabs related components.
     */
    fun getPackageNameToUse(context: Context): String? {
        val packageNameToUse: String?
        val packageManager = context.packageManager
        val activityIntent = Intent(Intent.ACTION_VIEW, Uri.fromParts(HTTP, "", null))

        // Get default VIEW intent handler.
        val defaultHandlerInfo = packageManager.resolveActivity(activityIntent, 0)
        val defaultHandlerPackageName: String? = defaultHandlerInfo?.activityInfo?.packageName

        // Get all apps that can handle VIEW intents.
        val resolvedActivityList = packageManager.queryIntentActivities(activityIntent, 0)
        val packagesSupportingCustomTabs = ArrayList<String>()
        for (info in resolvedActivityList) {
            val serviceIntent = Intent().apply {
                action = ACTION_CUSTOM_TABS_CONNECTION
                setPackage(info.activityInfo.packageName)
            }
            if (packageManager.resolveService(serviceIntent, 0) != null) {
                packagesSupportingCustomTabs.add(info.activityInfo.packageName)
            }
        }

        // packagesSupportingCustomTabs contains all apps that can handle VIEW intents
        packageNameToUse = when {
            packagesSupportingCustomTabs.isEmpty() -> null
            packagesSupportingCustomTabs.size == 1 -> packagesSupportingCustomTabs.first()
            !TextUtils.isEmpty(defaultHandlerPackageName) &&
                !hasSpecializedHandlerIntents(context, activityIntent)
                && packagesSupportingCustomTabs.contains(defaultHandlerPackageName)
            -> defaultHandlerPackageName
            packagesSupportingCustomTabs.contains(STABLE_PACKAGE) -> STABLE_PACKAGE
            packagesSupportingCustomTabs.contains(BETA_PACKAGE) -> BETA_PACKAGE
            packagesSupportingCustomTabs.contains(DEV_PACKAGE) -> DEV_PACKAGE
            packagesSupportingCustomTabs.contains(LOCAL_PACKAGE) -> LOCAL_PACKAGE
            else -> null
        }
        return packageNameToUse
    }

    /**
     * Used to check whether there is a specialized handler for a given intent.
     * @param intent The intent to check with.
     * @return Whether there is a specialized handler for the given intent.
     */
    private fun hasSpecializedHandlerIntents(context: Context, intent: Intent): Boolean {
        try {
            val pm = context.packageManager
            val handlers = pm.queryIntentActivities(
                intent,
                PackageManager.GET_RESOLVED_FILTER
            )
            if (handlers.size == 0) {
                return false
            }
            for (resolveInfo in handlers) {
                val filter = resolveInfo.filter ?: continue
                if (filter.countDataAuthorities() == 0 || filter.countDataPaths() == 0) continue
                if (resolveInfo.activityInfo == null) continue
                return true
            }
        } catch (e: RuntimeException) {
            Timber.e("Runtime exception while getting specialized handlers")
        }
        return false
    }
}
