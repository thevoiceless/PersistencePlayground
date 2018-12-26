package thevoiceless.realmplayground.persistence.realm

import io.realm.RealmList
import thevoiceless.realmplayground.model.BlackjackHand
import thevoiceless.realmplayground.model.Card
import thevoiceless.realmplayground.model.Mapper
import thevoiceless.realmplayground.model.Suit
import javax.inject.Inject

class RealmMapper @Inject constructor() : Mapper<BlackjackHand, RealmBlackjackHand> {

    override fun toNetworkModel(persistenceModel: RealmBlackjackHand): BlackjackHand {
        return BlackjackHand(
            hiddenCard = persistenceToNetworkCard(persistenceModel.hiddenCard!!),
            visibleCards = persistenceModel.visibleCards.orEmpty().map { persistenceToNetworkCard(it) }
        )
    }

    override fun toPersistenceModel(networkModel: BlackjackHand): RealmBlackjackHand {
        return RealmBlackjackHand(
            hiddenCard = networkToPersistenceCard(networkModel.hiddenCard),
            visibleCards = RealmList<RealmCard>().apply {
                addAll(networkModel.visibleCards.map { networkToPersistenceCard(it) })
            }
        )
    }

    private fun persistenceToNetworkCard(persistenceCard: RealmCard): Card {
        return Card(
            rank = persistenceCard.rank,
            suit = when (persistenceCard.suit) {
                Suit.Clubs.toString().toLowerCase() -> Suit.Clubs
                Suit.Diamonds.toString().toLowerCase() -> Suit.Diamonds
                Suit.Hearts.toString().toLowerCase() -> Suit.Hearts
                else -> Suit.Spades
            }
        )
    }

    private fun networkToPersistenceCard(networkCard: Card): RealmCard {
        return RealmCard(
            rank = networkCard.rank,
            suit = networkCard.suit.toString().toLowerCase()
        )
    }
}
