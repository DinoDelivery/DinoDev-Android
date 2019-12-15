package com.dinodelivery.app.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dinodelivery.app.entities.Order
import com.dinodelivery.app.repositories.OrderRepository

class OrderViewModel(application: Application) : AndroidViewModel(application) {

    private val orderRepository: OrderRepository = OrderRepository()

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>>
        get() = _orders

    fun getOrders() {
        _orders.value = orderRepository.getOrders()
    }
}