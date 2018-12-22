package thevoiceless.realmplayground

import android.app.Application
import timber.log.Timber

class RealmPlaygroundApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}