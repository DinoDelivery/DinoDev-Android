package com.dinodelivery.app.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Review(
    var id: Int,
    var userId: Int,
    var date: String? = null,
    var rating: Double = 0.0,
    var reviewText: String? = null
) : Parcelable {
    fun toReviewItem(): ReviewItem {
        return ReviewItem(this)
    }
}