package thevoiceless.realmplayground.util

import com.squareup.moshi.Moshi
import thevoiceless.realmplayground.network.Network
import javax.inject.Inject

class Something @Inject constructor(
    private val moshi: Moshi,
    private val network: Network
) {
    fun foo() { }
}
