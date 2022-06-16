package co.spoon.analyticstracker.providers

import android.content.Context

sealed class AnalyticsConfig(
    val tracker: Tracker
)

data class AmplitudeConfig(
    val appCtx: Context,
    val key: String
) : AnalyticsConfig(Tracker.AP)

data class AppsFlyerConfig(
    val appCtx: Context,
    val key: String,
    val oneLink: String
) : AnalyticsConfig(Tracker.AF)
