package com.dinodelivery.app.utils

import android.preference.PreferenceManager
import com.dinodelivery.app.DinoDeliveryApp.Companion.MAPPER
import com.dinodelivery.app.DinoDeliveryApp.Companion.context
import com.dinodelivery.app.entities.UserProfileData


object UserCacheUtils {

    private const val CACHED_USER_INFO = "cached_user_info"
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun cleanCache() {
        sharedPreferences.edit().clear().apply()
    }

    @get:Synchronized
    @set:Synchronized
    var cachedUserData: UserProfileData?
        get() = MAPPER.fromJson(sharedPreferences.getString(CACHED_USER_INFO, null), UserProfileData::class.java)
        set(userProfileData) = sharedPreferences.edit().putString(CACHED_USER_INFO, MAPPER.toJson(userProfileData)).apply()
}