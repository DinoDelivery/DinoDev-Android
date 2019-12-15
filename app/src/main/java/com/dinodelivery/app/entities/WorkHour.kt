package com.dinodelivery.app.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WorkHour(
    var id: Int,
    var restaurantId: Int,
    var day: Int,
    var openHour: String? = null,
    var closeHour: String? = null
) : Parcelable {
    companion object {
        const val WEEKDAY: Int = 8
        const val WEEKEND: Int = 9
    }
}