package thevoiceless.realmplayground.util

import android.app.Activity
import android.support.annotation.StringRes
import javax.inject.Inject

interface ResourceProvider {
    fun getString(@StringRes resId: Int): String
}

class ActivityResourceProvider @Inject constructor(private val activity: Activity) : ResourceProvider {
    override fun getString(resId: Int): String {
        return activity.getString(resId)
    }
}
