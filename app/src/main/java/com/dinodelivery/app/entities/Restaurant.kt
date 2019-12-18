package com.dinodelivery.app.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Restaurant(
    var id: Int,
    var name: String? = null,
    var rating: Double,
    var link: String? = null,
    var description: String? = null,
    var lat: Double,
    var long: Double,
    var photo: String? = null,
    var dishes: List<Dish>? = null,
    var workHours: List<WorkHour>? = null,
    var phone: String? = null,
    var address: String? = null,
    var reviews: List<Review>? = null,
    var cuisine: Dish.DishCuisine? = null
    ) : Parcelable