package thevoiceless.realmplayground.persistence

import io.reactivex.Completable
import io.reactivex.Observable
import thevoiceless.realmplayground.model.BlackjackHand


interface Persistence {
    fun loadCards(): Observable<List<BlackjackHand>>
    fun saveCards(cards: List<BlackjackHand>): Completable
}
