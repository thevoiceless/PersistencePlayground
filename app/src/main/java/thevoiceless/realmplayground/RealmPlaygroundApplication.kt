package thevoiceless.realmplayground

import android.app.Application
import thevoiceless.realmplayground.di.ContextDependencies
import thevoiceless.realmplayground.di.DaggerDependencies
import thevoiceless.realmplayground.di.Dependencies
import thevoiceless.realmplayground.di.DependenciesHolder
import timber.log.Timber

class RealmPlaygroundApplication : Application(), DependenciesHolder {

    override val dependencies: Dependencies by lazy {
        DaggerDependencies.builder()
            .contextDependencies(ContextDependencies(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}