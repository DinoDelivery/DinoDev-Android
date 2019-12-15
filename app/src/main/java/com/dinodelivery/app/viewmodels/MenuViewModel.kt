package com.dinodelivery.app.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dinodelivery.app.DinoDeliveryApp.Companion.context
import com.dinodelivery.app.R
import com.dinodelivery.app.database.repository.DishEntityRepository
import com.dinodelivery.app.entities.Dish
import com.dinodelivery.app.utils.LiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class MenuViewModel(application: Application) : AndroidViewModel(application) {

    private val dishEntityRepository: DishEntityRepository = DishEntityRepository()

    private val _message = LiveEvent<String>()
    val message: LiveEvent<String>
        get() = _message

    fun addDishToCart(dish: Dish) {
        viewModelScope.launch(CoroutineExceptionHandler { _, e ->
            Log.e(TAG, e.message)
            message.value = context.getString(R.string.smth_went_wrong)
        }) {
            dishEntityRepository.addDish(dish.toDishEntity())
            message.value = context.getString(R.string.dish_added_successfully)
        }
    }

    companion object {
        private val TAG = MenuViewModel::class.java.simpleName
    }

}