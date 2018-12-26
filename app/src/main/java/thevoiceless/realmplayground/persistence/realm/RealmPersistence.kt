package thevoiceless.realmplayground.persistence.realm

import android.content.Context
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.where
import thevoiceless.realmplayground.model.BlackjackHand
import thevoiceless.realmplayground.model.Mapper
import thevoiceless.realmplayground.persistence.Persistence
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealmPersistence @Inject constructor(
    context: Context,
    private val mapper: Mapper<BlackjackHand, RealmBlackjackHand>
) : Persistence {

    init {
        Realm.init(context)
    }

    private val realm: Realm
        get() {
            val config = RealmConfiguration.Builder()
                .name(REALM_NAME)
                .schemaVersion(SCHEMA_VERSION)
                .build()
            Realm.setDefaultConfiguration(config)

            return Realm.getDefaultInstance()
        }

    override fun loadCards(): Observable<List<BlackjackHand>> {
        return realm.where<RealmBlackjackHand>().findAllAsync()
            .asFlowable()
            .toObservable()
            .map { results -> results.map { mapper.toNetworkModel(it) } }
    }

    override fun saveCards(cards: List<BlackjackHand>): Completable {
        return Completable.create { emitter ->
            realm.executeTransactionAsync(
                { cards.map { card -> it.insert(mapper.toPersistenceModel(card)) } },
                { emitter.onComplete() },
                { throwable -> emitter.onError(throwable) }
            )
        }
    }

    companion object {
        // TODO: Investigate injecting enironment + user to use as part of the name
        private const val REALM_NAME = "test.realm"
        private const val SCHEMA_VERSION = 1L
    }
}
