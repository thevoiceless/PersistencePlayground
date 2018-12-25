package thevoiceless.realmplayground.di

import android.app.Activity
import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Component
import dagger.Module
import dagger.Provides
import thevoiceless.realmplayground.mvp.MainPresenter
import thevoiceless.realmplayground.mvp.MainPresenterImpl
import thevoiceless.realmplayground.persistence.Persistence
import thevoiceless.realmplayground.persistence.RealmPersistence
import javax.inject.Singleton

interface DependenciesHolder {
    val dependencies: Dependencies
}

val Activity.dependencies get() = (application as DependenciesHolder).dependencies


@Component(modules = [
    ContextDependencies::class,
    Presenters::class,
    Utilities::class
])
@Singleton
interface Dependencies {
    fun mainPresenter(): MainPresenter

    fun persistence(): Persistence
    fun moshi(): Moshi
}

@Module
class Utilities {
    @Provides
    fun persistence(impl: RealmPersistence): Persistence = impl

    @Provides
    @Singleton
    fun moshi(): Moshi = Moshi.Builder().build()
}

@Module
class ContextDependencies(context: Context) {
    private val appContext: Context = context.applicationContext

    @Provides
    fun appContext(): Context = appContext
}

@Module
class Presenters {
    @Provides
    fun mainPresenter(impl: MainPresenterImpl): MainPresenter = impl
}
