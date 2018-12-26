package thevoiceless.realmplayground.persistence.realm

import io.realm.RealmList
import io.realm.RealmObject

open class RealmBlackjackHand(
    var hiddenCard: RealmCard? = null,
    var visibleCards: RealmList<RealmCard>? = null
) : RealmObject()

open class RealmCard(
    var rank: String = "",
    var suit: String = ""
) : RealmObject()
