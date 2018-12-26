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
    fun getDependencies(activity: Activity?): Dependencies
}

val Activity.dependencies get() = (application as DependenciesHolder).getDependencies(this)

/*
A component specifies what dependencies are available. It requires one or more modules that
control how dependencies are created. The generated component is what we and Dagger both use
to obtain dependencies.
 */
@Component(modules = [
    AppDependencies::class,
    ActivityDependencies::class,
    Presenters::class,
    Utilities::class
])
@Singleton
interface Dependencies {
    /*
    Our code can use the component to access dependencies via the methods declared here. If we don't need to get a
    dependency in our own code, all that Dagger needs is an @Inject-annotated constructor.
     */
    fun mainPresenter(): MainPresenter
}

@Module
class Utilities {
    /*
    We should generally favor @Inject-annotated constructors. For code that we don't control, we can use methods annotated
    with @Provides to create the instances ourselves (ex: Moshi).
    These methods also let us return concrete implementations of interfaces. For example, the persistence() method
    returns a Persistence implementation; Dagger will use this method any time we need to inject a Persistence instance.
    Dagger only knows the return type, but we've implemented the method to take a RealmPersistence argument and return
    it. The RealmPersistence object is constructed using its @Inject-annotated constructor.
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
class AppDependencies(context: Context) {
    private val appContext: Context = context.applicationContext

    @Provides
    fun appContext(): Context = appContext
}

@Module
class ActivityDependencies(activity: Activity) {
    // TODO: Things like ResourceProvider that should use an Activity context instead of the app context
}

@Module
class Presenters {
    @Provides
    fun mainPresenter(impl: MainPresenterImpl): MainPresenter = impl
}
