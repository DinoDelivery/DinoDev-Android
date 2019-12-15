package com.dinodelivery.app.repositories

import com.dinodelivery.app.entities.Order

class OrderRepository {

    fun getOrders(): List<Order> {
        return listOf(
            Order(1, 3, "Благовісна, 144", "12:30", 85.0),
            Order(2, 7, "Котовсбкого, 12", "14:45", 260.35),
            Order(3, 2, "Сумгаїтська, 44", "16:00", 98.55),
            Order(4, 5, "Шевченка, 220", "10:20", 180.95)
        )
    }

}