package com.dinodelivery.app.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dinodelivery.app.entities.Restaurant
import com.dinodelivery.app.repositories.RestaurantsRepository

class RestaurantsViewModel (application: Application) :AndroidViewModel(application) {

    private val restaurantsRepository: RestaurantsRepository = RestaurantsRepository()

    private val _restaurants = MutableLiveData<List<Restaurant>>()
    val restaurants: LiveData<List<Restaurant>>
        get() = _restaurants

    fun getRestaurants() {
        _restaurants.value = restaurantsRepository.getRestaurants()
    }
}