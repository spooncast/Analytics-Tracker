package co.spoon.analyticstracker.providers

internal interface AnalyticsProvider {
    fun track(
        event: String,
        props: Map<String, Any?>?
    )
}

enum class Tracker {
    /**
     * Amplitude
     */
    AP,

    /**
     * Appsflyer
     */
    AF,

    /**
     * Firebase Analytics
     */
    FA,
}