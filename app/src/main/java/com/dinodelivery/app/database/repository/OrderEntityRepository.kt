package com.dinodelivery.app.database.repository

import com.dinodelivery.app.DinoDeliveryApp.Companion.db
import com.dinodelivery.app.database.dao.OrderDao
import com.dinodelivery.app.database.entities.OrderEntity

class OrderEntityRepository {

    private val orderDao: OrderDao? = db?.orderDao()

    suspend fun addOrder(orderEntity: OrderEntity) {
        orderDao?.addOrder(orderEntity)
    }

    suspend fun getAllOrders(): List<OrderEntity>? {
        return orderDao?.getAllOrders()
    }

    suspend fun deleteOrderById(id: Int) {
        orderDao?.deleteOrderById(id)
    }

}