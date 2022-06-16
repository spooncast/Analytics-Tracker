package co.spoon.analyticstracker.providers.amplitude

import android.util.Log
import co.spoon.analyticstracker.providers.AnalyticsProvider
import com.amplitude.api.AmplitudeClient
import com.amplitude.api.Identify
import org.json.JSONException
import org.json.JSONObject

class AmplitudeProvider(
    private val client: AmplitudeClient
): AnalyticsProvider {

    override fun track(event: String, props: Map<String, Any?>?) {
        if (props.isNullOrEmpty()) {
            Log.d(TAG, "track - $event")
            client.logEvent(event)
        } else {
            try {
                val eventProps = JSONObject()
                props.forEach { (k, v) ->
                    when(v) {
                        is Boolean -> eventProps.put(k, v)
                        is Double -> eventProps.put(k, v)
                        is Int -> eventProps.put(k, v)
                        is Long -> eventProps.put(k, v)
                        else -> eventProps.put(k, v)
                    }
                }
                Log.d(TAG, "track - $event: $eventProps")
                client.logEvent(event, eventProps)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }

    fun setUserProperties(
        userId: String,
        props: Map<String, Any?>? = null,
        setOnceProps: Map<String, Any?>? = null
    ) {
        val identify = Identify().apply {
            props?.keys?.forEach { key ->
                when(val value = props[key]){
                    is Int -> set(key, value)
                    is Long -> set(key, value)
                    is Float -> set(key, value)
                    is Double -> set(key, value)
                    is Boolean -> set(key, value)
                    is String -> set(key, value)
                }
            }

            setOnceProps?.keys?.forEach { key ->
                when (val value = setOnceProps[key]) {
                    is Int -> setOnce(key, value)
                    is Long -> setOnce(key, value)
                    is Float -> setOnce(key, value)
                    is Double -> setOnce(key, value)
                    is Boolean -> setOnce(key, value)
                    is String -> setOnce(key, value)
                }
            }
        }
        client.identify(identify)
        client.userId = userId
    }

    companion object {
        private const val TAG = "AmplitudeProvider"
    }
}