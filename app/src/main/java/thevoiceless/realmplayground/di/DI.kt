package thevoiceless.realmplayground.di

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Component
import dagger.Module
import dagger.Provides
import thevoiceless.realmplayground.persistence.RealmPersistence
import javax.inject.Singleton

@Component(modules = [
    Contexts::class,
    Utilities::class
])
@Singleton
interface Dependencies {
    fun persistence(): RealmPersistence
    fun moshi(): Moshi
}


@Module
class Utilities {
    @Provides
    @Singleton
    fun moshi(): Moshi = Moshi.Builder().build()
}


@Module
class Contexts(context: Context) {

    private val appContext: Context = context.applicationContext

    @Provides
    fun appContext(): Context = appContext
}
