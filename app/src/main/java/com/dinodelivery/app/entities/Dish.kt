package com.dinodelivery.app.entities

import android.os.Parcelable
import com.dinodelivery.app.database.entities.DishEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Dish(
    var id: Int = 0,
    var name: String? = null,
    var dishType: DishType? = null,
    var cuisine: DishCuisine? = null,
    var reviews: List<Review>? = null,
    var rating: Double = 0.0,
    var ingredients: List<Ingredient>? = null,
    var description: String? = null,
    var photo: String? = null,
    var weight: Int,
    var price: Double
) : Parcelable {

    fun toDishEntity(): DishEntity {
        return DishEntity(dishId = id, name = name, price = price)
    }

    enum class DishType {
        SOUP,
        DESSERT,
        SALAD,
        APPETIZER,
        MAIN,
        DRINK
    }

    enum class DishCuisine {
        UKRAINIAN,
        CHINESE,
        INDIAN,
        ITALIAN,
        JAPANESE,
        GENERAL,
        FRENCH
    }
}