package com.dinodelivery.app.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
     @ColumnInfo(name = "itemCount") var itemCount: Int,
     @ColumnInfo(name = "address") var deliveryAddress: String,
     @ColumnInfo(name = "time") var deliveryTime: String,
     @ColumnInfo(name = "price") var price: Double? = 0.0
) {
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int = 0
}