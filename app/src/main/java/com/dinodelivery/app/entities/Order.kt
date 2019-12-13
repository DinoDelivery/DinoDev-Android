package com.dinodelivery.app.entities

data class Order(
    var id: Int,
    var itemCount: Int,
    var deliveryAddress: String,
    var deliveryTime: String,
    var price: Double
)