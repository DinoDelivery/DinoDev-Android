package com.dinodelivery.app.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dinodelivery.app.DinoDeliveryApp.Companion.context
import com.dinodelivery.app.R
import com.dinodelivery.app.database.repository.DishEntityRepository
import com.dinodelivery.app.entities.Dish
import com.dinodelivery.app.entities.SortItem
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

    fun getSortItemList(): List<SortItem> {
        return listOf(
            SortItem("За алфавітом", SortItem.SortCriteria.ALPHABET, false),
            SortItem("За ціною", SortItem.SortCriteria.PRICE, false),
            SortItem("За оцінкою", SortItem.SortCriteria.RATING, false)
        )
    }


    fun getDishType(dishType: String?): Dish.DishType? {
        return when (dishType) {
            "супи" -> Dish.DishType.SOUP
            "десерти" -> Dish.DishType.DESSERT
            "салати" -> Dish.DishType.SALAD
            "закуски" -> Dish.DishType.APPETIZER
            "основні" -> Dish.DishType.MAIN
            "напої" -> Dish.DishType.DRINK
            else -> null
        }
    }

    fun getCuisine(cuisine: String?): Dish.DishCuisine? {
        return when (cuisine) {
            "українська" -> Dish.DishCuisine.UKRAINIAN
            "китайська" -> Dish.DishCuisine.CHINESE
            "індійська" -> Dish.DishCuisine.INDIAN
            "італійська" -> Dish.DishCuisine.ITALIAN
            "японська" -> Dish.DishCuisine.JAPANESE
            "французька" -> Dish.DishCuisine.FRENCH
            "загальна" -> Dish.DishCuisine.GENERAL
            else -> null
        }
    }

    companion object {
        private val TAG = MenuViewModel::class.java.simpleName
    }

}