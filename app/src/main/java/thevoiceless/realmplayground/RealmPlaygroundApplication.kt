package thevoiceless.realmplayground

import android.app.Activity
import android.app.Application
import thevoiceless.realmplayground.di.*
import timber.log.Timber

class RealmPlaygroundApplication : Application(), DependenciesHolder {

    private var dependencies: Dependencies? = null

    override fun getDependencies(activity: Activity?): Dependencies {
        return dependencies
            ?: DaggerDependencies.builder()
                .appDependencies(AppDependencies(this))
                .also { if (activity != null) it.activityDependencies(ActivityDependencies(activity)) }
                .build()
                .also { dependencies = it }
    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}
