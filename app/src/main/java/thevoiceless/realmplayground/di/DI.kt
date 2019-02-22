package thevoiceless.realmplayground.di

import android.app.Activity
import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Binds
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
import thevoiceless.realmplayground.util.*
import javax.inject.Named
import javax.inject.Singleton

interface DependenciesHolder {
    fun getDependencies(activity: Activity?): Dependencies
}

val Activity.dependencies get() = (application as DependenciesHolder).getDependencies(this)

/*
A component specifies what dependencies are available. It requires one or more modules that control how dependencies are
created. The generated code is what we and Dagger both use to obtain dependencies.
 */
@Component(modules = [
    AppDependencies::class,
    ActivityDependencies::class,
    Presenters::class,
//    Utilities::class
    Utilities2::class
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
    We should generally favor @Inject-annotated constructors. For code that we don't control, or to return concrete
    implementations for interfaces, we can use methods annotated with @Provides to create the instances ourselves (ex: Moshi).

    For example, the persistence() method returns a Persistence implementation; Dagger will use this method any time we
    need to inject a Persistence instance. Dagger only knows the return type, but we've implemented the method to take a
    RealmPersistence argument and return it. The RealmPersistence object is constructed using its @Inject-annotated constructor.
     */
    @Provides
    fun persistence(impl: RealmPersistence): Persistence = impl

    @Provides
    fun network(impl: NetworkImpl): Network = impl

    @Provides
    fun realmMapper(impl: RealmMapper): Mapper<BlackjackHand, RealmBlackjackHand> = impl

    /*
    The @Singleton annotation tells Dagger to create an instance as (you guessed it) a singleton. The annotation can be
    used on the provider method, but I've read that the best practice is to put it on the @Inject-annotated constructor
    if possible (ex: RealmPersistence).
     */
    @Provides
    @Singleton
    fun moshi(): Moshi = Moshi.Builder().build()

    /*
    If we don't need to specify how a dependency is provided, we don't even need a provider method as long as it has an
    @Inject-annotated constructor
     */
//    @Provides
//    fun something(): Something = Something()
}

@Module
abstract class Utilities2 {
    /*
    If a provider method simply returns the injected parameter, you can increase performance by using a @Binds method
    instead. A binds method can only have a single parameter whose type is assignable to the return type, it must be
    abstract, and therefore the module class must be abstract as well.

    This is more efficient because provider methods are instance methods and therefore need an instance of the module in
    order to be invoked. If everything is abstract, Dagger will not instantiate the module; instead it will create the
    injected dependencies and use them directly.
     */
    @Binds
    abstract fun persistence(impl: RealmPersistence): Persistence

    @Binds
    abstract fun network(impl: NetworkImpl): Network

    @Binds
    abstract fun realmMapper(impl: RealmMapper): Mapper<BlackjackHand, RealmBlackjackHand>

    @Binds
    abstract fun schedulerProvider(impl: AndroidSchedulerProvider): SchedulerProvider
    /*
    If we require any provider methods, they must be made static in order to live in the abstract module class.

    The cleanest way to do this in Kotlin is with @JvmStatic methods in a top-level object annotated with @Module.
    If you want to keep everything in the module class, you'll have to use a companion object annotated with @Module.
    See https://github.com/google/dagger/issues/900#issuecomment-337043187
     */
    // TODO: https://github.com/google/dagger/issues/900#issuecomment-410041915
    @Module
    companion object {
        @Provides
        @Singleton
        @JvmStatic
        fun moshi(): Moshi = Moshi.Builder().build()
    }
}

@Module
class AppDependencies(context: Context) {
    /*
    Modules that return values from instance fields cannot be abstract/use @Binds
     */
    private val appContext: Context = context.applicationContext

    @Provides
    @Named("App")
    fun appContext(): Context = appContext
}

@Module
class ActivityDependencies(activity: Activity) {
    private val activityContext: Activity = activity

    @Provides
    fun activity(): Activity = activityContext

    @Provides
    fun activityResources(impl: ActivityResourceProvider): ResourceProvider = impl
}

@Module
abstract class Presenters {
    @Binds
    abstract fun mainPresenter(impl: MainPresenterImpl): MainPresenter
}
