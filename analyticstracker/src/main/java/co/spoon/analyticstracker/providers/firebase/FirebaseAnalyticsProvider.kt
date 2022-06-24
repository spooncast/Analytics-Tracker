package co.spoon.analyticstracker.providers.firebase

import android.os.Parcelable
import android.util.Log
import androidx.core.os.bundleOf
import co.spoon.analyticstracker.providers.AnalyticsProvider
import com.google.firebase.analytics.FirebaseAnalytics

class FirebaseAnalyticsProvider(
    private val firebaseAnalytics: FirebaseAnalytics
) : AnalyticsProvider {

    override fun track(event: String, props: Map<String, Any?>?) {
        Log.d(TAG, "[hive] track - $event: $props")
        when (props.isNullOrEmpty()) {
            true -> bundleOf()
            else ->{
                val eventProps = bundleOf()
                props.forEach { (k, v) ->
                    when(v) {
                        is Boolean -> eventProps.putBoolean(k, v)
                        is Double -> eventProps.putDouble(k, v)
                        is Int -> eventProps.putInt(k, v)
                        is Long -> eventProps.putLong(k, v)
                        is String -> eventProps.putString(k, v)
                        is Byte -> eventProps.putByte(k, v)
                        is Char -> eventProps.putChar(k, v)
                        is Float -> eventProps.putFloat(k, v)
                        is Parcelable -> eventProps.putParcelable(k, v)
                    }
                }
                eventProps
            }
        }.let { eventProps ->
            firebaseAnalytics.logEvent(event, eventProps)
        }
    }

    companion object {
        private const val TAG = "FirebaseAnalyticsProvider"
    }

}