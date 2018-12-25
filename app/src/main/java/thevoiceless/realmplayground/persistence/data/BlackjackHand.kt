package thevoiceless.realmplayground.persistence.data

data class BlackjackHand(
    val hiddenCard: Card,
    val visibleCards: List<Card>
)

data class Card(
    val rank: Char,
    val suit: Suit
)

sealed class Suit {
    object CLUBS : Suit()
    object DIAMONDS : Suit()
    object HEARTS : Suit()
    object SPADES : Suit()
}

object TestData {
    const val CARD_JSON =
        """
        {
          "hidden_card": {
            "rank": "6",
            "suit": "SPADES"
          },
          "visible_cards": [
            {
              "rank": "4",
              "suit": "CLUBS"
            },
            {
              "rank": "A",
              "suit": "HEARTS"
            }
          ]
        }
        """
}
