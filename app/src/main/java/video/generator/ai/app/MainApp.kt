package video.generator.ai.app

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp
import io.appmetrica.analytics.AppMetrica
import io.appmetrica.analytics.AppMetricaConfig

const val METRICA_KEY = "4960ba26-2686-4ffe-b85e-87c9d95c74f7"

@HiltAndroidApp
class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Firebase
        FirebaseApp.initializeApp(this)

        // Metrica SDK
        val config = AppMetricaConfig.newConfigBuilder(METRICA_KEY).build()
        AppMetrica.activate(this, config)
        AppMetrica.enableActivityAutoTracking(this)
    }
}