package com.dinodelivery.app.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dinodelivery.app.database.entities.DishEntity

@Dao
interface DishDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDish(dishEntity: DishEntity)

    @Query("SELECT * FROM dishes")
    suspend fun getDishes(): List<DishEntity>?

    @Query("DELETE FROM dishes")
    suspend fun clearAllDishes()
}