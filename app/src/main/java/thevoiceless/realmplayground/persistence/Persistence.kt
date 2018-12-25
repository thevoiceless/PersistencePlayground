package thevoiceless.realmplayground.persistence

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Inject
import javax.inject.Singleton


interface Persistence {

}

@Singleton
class RealmPersistence @Inject constructor(
    context: Context,
    private val realmName: String = REALM_NAME,
    private val schemaVersion: Long = SCHEMA_VERSION
) : Persistence {

    init {
        Realm.init(context)
    }

    private val realm: Realm
        get() {
            val config = RealmConfiguration.Builder()
                .name(realmName)
                .schemaVersion(schemaVersion)
                .build()
            Realm.setDefaultConfiguration(config)

            return Realm.getDefaultInstance()
        }

    companion object {
        // TODO: Investigate injecting enironment + user to use as part of the name
        private const val REALM_NAME = "test.realm"
        private const val SCHEMA_VERSION = 1L
    }
}
