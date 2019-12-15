package com.dinodelivery.app.database.repository

import com.dinodelivery.app.DinoDeliveryApp.Companion.db
import com.dinodelivery.app.database.dao.DishDao
import com.dinodelivery.app.database.entities.DishEntity

class DishEntityRepository {

    private val dishDao: DishDao? = db?.dishDao()

    suspend fun addDish(dish: DishEntity) {
        dishDao?.addDish(dish)
    }

    suspend fun getDishes(): List<DishEntity>? {
        return dishDao?.getDishes()
    }

    suspend fun clearAllDishes() {
        dishDao?.clearAllDishes()
    }
}