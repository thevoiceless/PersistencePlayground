package thevoiceless.realmplayground.model

import com.squareup.moshi.Json

data class BlackjackHand(
    @Json(name = "hidden_card")
    val hiddenCard: Card,

    @Json(name = "visible_cards")
    val visibleCards: List<Card>
)

data class Card(
    val rank: String,
    val suit: Suit
)

enum class Suit {
    @Json(name = "CLUBS")
    Clubs,

    @Json(name = "DIAMONDS")
    Diamonds,

    @Json(name = "HEARTS")
    Hearts,

    @Json(name = "SPADES")
    Spades
}

object TestData {
    const val CARD_JSON =
        """
        [{
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
        }]
        """
}
