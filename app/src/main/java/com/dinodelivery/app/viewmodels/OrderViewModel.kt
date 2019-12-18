package com.dinodelivery.app.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dinodelivery.app.database.entities.OrderEntity
import com.dinodelivery.app.database.repository.DishEntityRepository
import com.dinodelivery.app.database.repository.OrderEntityRepository
import com.dinodelivery.app.utils.LiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class OrderViewModel(application: Application) : AndroidViewModel(application) {

    private val orderEntityRepository: OrderEntityRepository = OrderEntityRepository()

    private val dishEntityRepository: DishEntityRepository = DishEntityRepository()

    private val _orders = MutableLiveData<List<OrderEntity>>()
    val orders: LiveData<List<OrderEntity>>
        get() = _orders

    private val _orderChanged = LiveEvent<Boolean>()
    val orderChanged: LiveEvent<Boolean>
        get() = _orderChanged

    fun getOrders() {
        viewModelScope.launch(CoroutineExceptionHandler { _, e ->
            Log.e(TAG, e.message)
        }) {
            _orders.value = orderEntityRepository.getAllOrders()
        }
    }

    fun saveOrder(orderEntity: OrderEntity) {
        viewModelScope.launch(CoroutineExceptionHandler { _, e ->
            Log.e(TAG, e.message)
        }) {
            orderEntityRepository.addOrder(orderEntity)
            _orderChanged.value = true
            dishEntityRepository.clearAllDishes()
        }
    }

    fun deleteOrder(id: Int) {
        viewModelScope.launch(CoroutineExceptionHandler { _, e ->
            Log.e(TAG, e.message)
        }) {
            orderEntityRepository.deleteOrderById(id)
            _orderChanged.value = true
        }
    }

    companion object {
        private val TAG = OrderViewModel::class.java.simpleName
    }
}