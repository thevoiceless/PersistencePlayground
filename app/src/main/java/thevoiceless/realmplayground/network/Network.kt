package thevoiceless.realmplayground.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.reactivex.Single
import thevoiceless.realmplayground.model.BlackjackHand
import thevoiceless.realmplayground.model.TestData
import javax.inject.Inject

interface Network {
    fun getCards(): Single<List<BlackjackHand>>
}

class NetworkImpl @Inject constructor(
    private val moshi: Moshi
) : Network {
    override fun getCards(): Single<List<BlackjackHand>> {
        return Single.fromCallable {
            val listType = Types.newParameterizedType(List::class.java, BlackjackHand::class.java)
            moshi.adapter<List<BlackjackHand>>(listType).fromJson(TestData.CARD_JSON)
        }
    }
}
