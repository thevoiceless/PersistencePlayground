package thevoiceless.realmplayground.persistence.data

import com.squareup.moshi.Json

data class BlackjackHand(
    @Json(name = "hidden_card")
    val hiddenCard: Card,

    @Json(name = "visible_cards")
    val visibleCards: List<Card>
)

data class Card(
    val rank: Char,
    val suit: Suit
)

sealed class Suit {
    @Json(name = "CLUBS")
    object CLUBS : Suit()

    @Json(name = "DIAMONDS")
    object DIAMONDS : Suit()

    @Json(name = "HEARTS")
    object HEARTS : Suit()

    @Json(name = "SPADES")
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
