package thevoiceless.realmplayground.di

import com.nhaarman.mockitokotlin2.mock
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import thevoiceless.realmplayground.model.BlackjackHand
import thevoiceless.realmplayground.model.Mapper
import thevoiceless.realmplayground.mvp.MainPresenter
import thevoiceless.realmplayground.mvp.MainPresenterImpl
import thevoiceless.realmplayground.network.Network
import thevoiceless.realmplayground.persistence.Persistence
import thevoiceless.realmplayground.persistence.realm.RealmBlackjackHand
import thevoiceless.realmplayground.util.ResourceProvider
import thevoiceless.realmplayground.util.SchedulerProvider
import javax.inject.Singleton

/*
Technically, you can subclass your modules to override dependency bindings. However, this is not recommended - You can't
add or remove bindings, and you can't change the signature of overridden methods (so you still end up creating the
original dependencies AND all of their dependencies). See https://google.github.io/dagger/testing.html
 */

@Component(modules = [
    ClassesBeingTested::class,
    SchedulerForTest::class,
    ProvidersForTest::class
])
@Singleton
interface TestDependencies : Dependencies {

}

@Module
abstract class ClassesBeingTested {
    @Binds abstract fun mainPresenter(iml: MainPresenterImpl): MainPresenter
}

@Module
open class SchedulerForTest(private val schedulerProvider: SchedulerProvider) {
     @Provides open fun schedulerProvider(): SchedulerProvider = schedulerProvider
}

@Module
open class ProvidersForTest {
    @Provides open fun mockResourceProvider(): ResourceProvider = mock { }
    @Provides open fun mockPersistence(): Persistence = mock { }
    @Provides open fun mockNetwork(): Network = mock { }
    @Provides open fun mockRealmMapper(): Mapper<BlackjackHand, RealmBlackjackHand> = mock { }
    @Provides @Singleton open fun mockMoshi(): Moshi = mock { }
}

//@Module
//class MockAppDependencies {
//    @Provides @Named("App") fun mockAppContext(): Context = mock { }
//}
//
//@Module
//class MockActivityDependencies {
//    @Provides fun mockActivity(): Activity = mock { }
//    @Provides fun mockActivityResources(): ResourceProvider = mock { }
//}