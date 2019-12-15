package com.dinodelivery.app.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ingredient(
    var id: Int = 0,
    var name: String? = null
) : Parcelable