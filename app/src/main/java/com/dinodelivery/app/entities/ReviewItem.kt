package com.dinodelivery.app.entities

data class ReviewItem(
    var review: Review,
    var userName: String? = null,
    var userPhoto: String? = null
)