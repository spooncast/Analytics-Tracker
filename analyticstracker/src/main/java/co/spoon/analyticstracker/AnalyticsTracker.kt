package co.spoon.analyticstracker

import android.app.Application
import co.spoon.analyticstracker.providers.AmplitudeConfig
import co.spoon.analyticstracker.providers.AnalyticsProvider
import co.spoon.analyticstracker.providers.AppsFlyerConfig
import co.spoon.analyticstracker.providers.FirebaseAnalyticsConfig
import co.spoon.analyticstracker.providers.Tracker
import co.spoon.analyticstracker.providers.amplitude.AmplitudeProvider
import co.spoon.analyticstracker.providers.appsflyer.AppsFlyerProvider
import co.spoon.analyticstracker.providers.firebase.FirebaseAnalyticsProvider
import com.amplitude.api.Amplitude
import com.appsflyer.AppsFlyerLib

class AnalyticsTracker(
    amplitudeConfig: AmplitudeConfig? = null,
    appsFlyerConfig: AppsFlyerConfig? = null,
    firebaseAnalyticsConfig: FirebaseAnalyticsConfig? = null
) {

    private val providerMap = mutableMapOf<Tracker, AnalyticsProvider>()

    init {
        if (amplitudeConfig != null) {
            val amplitudeClient = Amplitude.getInstance()
                .initialize(amplitudeConfig.appCtx, amplitudeConfig.key)
                .enableForegroundTracking(amplitudeConfig.appCtx as Application)
            providerMap[amplitudeConfig.tracker] = AmplitudeProvider(amplitudeClient)
        }

        if (appsFlyerConfig != null) {
            val appsFlyer = AppsFlyerLib.getInstance().apply {
                init(appsFlyerConfig.key, null, appsFlyerConfig.appCtx)
                setAppInviteOneLink(appsFlyerConfig.oneLink)
                start(appsFlyerConfig.appCtx)
                if (BuildConfig.DEBUG) {
                    setDebugLog(true)
                }
            }
            providerMap[appsFlyerConfig.tracker] = AppsFlyerProvider(appsFlyerConfig.appCtx, appsFlyer)
        }

        if(firebaseAnalyticsConfig != null) {
            providerMap[firebaseAnalyticsConfig.tracker] = FirebaseAnalyticsProvider(firebaseAnalyticsConfig.firebaseAnalytics)
        }
    }

    fun setUserProperties(
        userId: String,
        props: Map<String, Any?>? = null,
        setOnceProps: Map<String, Any?>? = null
    ) {
        (providerMap[Tracker.AP] as? AmplitudeProvider)?.setUserProperties(userId, props, setOnceProps)
    }

    fun track(
        trackers: List<Tracker>,
        event: String,
        props: Map<String, Any?>? = null,
    ) {
        trackers.forEach { tracker ->
            track(tracker, event, props)
        }
    }

    fun track(
        tracker: Tracker,
        event: String,
        props: Map<String, Any?>? = null,
    ) {
        providerMap[tracker]?.track(event, props)
    }

}