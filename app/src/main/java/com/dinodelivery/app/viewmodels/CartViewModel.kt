package com.dinodelivery.app.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dinodelivery.app.database.entities.DishEntity
import com.dinodelivery.app.database.repository.DishEntityRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val dishEntityRepository: DishEntityRepository = DishEntityRepository()

    private val _cartItems = MutableLiveData<List<Pair<DishEntity, Int>>>()
    val cartItems: LiveData<List<Pair<DishEntity, Int>>>
        get() = _cartItems

    private val _item = MutableLiveData<Pair<DishEntity, Boolean>>()
    val item: LiveData<Pair<DishEntity, Boolean>>
        get() = _item

    fun getCartItems() {
        viewModelScope.launch(CoroutineExceptionHandler { _, e ->
            Log.e(TAG, e.message)
        }) {
            _cartItems.value =
                dishEntityRepository.getDishes()?.groupBy(DishEntity::dishId)
                    ?.map { it.value[0] to it.value.size }
        }
    }

    fun addCartItem(dishEntity: DishEntity) {
        viewModelScope.launch(CoroutineExceptionHandler { _, e ->
            Log.e(TAG, e.message)
        }) {
            dishEntityRepository.addDish(
                DishEntity(
                    dishId = dishEntity.dishId,
                    name = dishEntity.name,
                    price = dishEntity.price,
                    photo = dishEntity.photo
                )
            )
            _item.value = dishEntity to true
        }
    }

    fun deleteCartItem(dishEntity: DishEntity) {
        viewModelScope.launch(CoroutineExceptionHandler { _, e ->
            Log.e(TAG, e.message)
        }) {
            dishEntityRepository.deleteDishById(dishEntity.dishId)
            _item.value = dishEntity to false
        }
    }

    companion object {
        private val TAG = CartViewModel::class.java.simpleName
    }
}