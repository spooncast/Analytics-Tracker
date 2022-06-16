package co.spoon.analyticstracker.providers.appsflyer

import android.content.Context
import android.util.Log
import co.spoon.analyticstracker.providers.AnalyticsProvider
import com.appsflyer.AppsFlyerLib

class AppsFlyerProvider(
    private val appCtx: Context,
    private val appsFlyer: AppsFlyerLib
) : AnalyticsProvider {

    override fun track(event: String, props: Map<String, Any?>?) {
        Log.d(TAG, "[hive] track - $event: $props")
        when (props.isNullOrEmpty()) {
            true -> emptyMap()
            else -> props
        }.let { eventValues ->
            appsFlyer.logEvent(appCtx, event, eventValues)
        }
    }

    companion object {
        private const val TAG = "AppsFlyerProvider"
    }

}