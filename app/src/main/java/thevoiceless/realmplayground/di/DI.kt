package thevoiceless.realmplayground.di

import android.app.Activity
import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Component
import dagger.Module
import dagger.Provides
import thevoiceless.realmplayground.model.BlackjackHand
import thevoiceless.realmplayground.model.Mapper
import thevoiceless.realmplayground.mvp.MainPresenter
import thevoiceless.realmplayground.mvp.MainPresenterImpl
import thevoiceless.realmplayground.network.Network
import thevoiceless.realmplayground.network.NetworkImpl
import thevoiceless.realmplayground.persistence.Persistence
import thevoiceless.realmplayground.persistence.realm.RealmBlackjackHand
import thevoiceless.realmplayground.persistence.realm.RealmMapper
import thevoiceless.realmplayground.persistence.realm.RealmPersistence
import javax.inject.Singleton

interface DependenciesHolder {
    val dependencies: Dependencies
}

val Activity.dependencies get() = (application as DependenciesHolder).dependencies
/*
A component specifies what dependencies are available. It requires one or more modules that
control how dependencies are created. The generated component is what we and Dagger both use
to obtain dependencies.
 */
@Component(modules = [
    ContextDependencies::class,
    Presenters::class,
    Utilities::class
])
@Singleton
interface Dependencies {
    /*
    I've chosen to return interfaces rather than concrete implementations. If I had specified
    concrete classes, Dagger would use their @Inject-annotated constructors. Instead, it will
    use the provider methods from the modules.
     */
    fun mainPresenter(): MainPresenter
    fun persistence(): Persistence
    fun realmMapper(): Mapper<BlackjackHand, RealmBlackjackHand>
    fun network(): Network
    fun moshi(): Moshi
}

@Module
class Utilities {
    /*
    This provider method creates the Persistence implementation by returning a concrete instance
    of the RealmPersistence class. Dagger creates that instance via the @Inject-annotated constructor
    and then passes it as the argument to this method.
     */
    @Provides
    fun persistence(impl: RealmPersistence): Persistence = impl

    @Provides
    fun network(impl: NetworkImpl): Network = impl

    @Provides
    fun realmMapper(impl: RealmMapper): Mapper<BlackjackHand, RealmBlackjackHand> = impl

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
