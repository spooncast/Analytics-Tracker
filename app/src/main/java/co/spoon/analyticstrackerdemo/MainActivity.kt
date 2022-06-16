package co.spoon.analyticstrackerdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.spoon.analyticstracker.AnalyticsTracker

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AnalyticsTracker()
    }
}