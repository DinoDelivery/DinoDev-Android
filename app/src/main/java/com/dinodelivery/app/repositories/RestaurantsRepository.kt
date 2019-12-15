package com.dinodelivery.app.repositories

import com.dinodelivery.app.entities.*

class RestaurantsRepository {

    fun getRestaurants(): List<Restaurant> {
        return listOf(
            Restaurant(
                1,
                "Masala",
                4.7,
                null,
                "Чудовий ресторан з індійською кухнею",
                49.441013265861436,
                32.06598412245512,
                null,
                listOf(
                    Dish(
                        1,
                        "Шріматі",
                        DishType.DESSERT,
                        DishCuisine.INDIAN,
                        listOf(
                            Review(
                                1,
                                1,
                                "27/10/2018",
                                4.0,
                                "Чудовий десерт! :)"
                            )
                        ),
                        4.6,
                        listOf(
                            Ingredient(1, "Яблуко"),
                            Ingredient(2, "Борошно")
                        ),
                        "Традиційний пиріг з яблуком",
                        null,
                        200,
                        35.90
                    )
                ),
                listOf(
                    WorkHour(
                        1,
                        1,
                        WorkHour.WEEKDAY,
                        "09:00",
                        "20:00"
                    ),
                    WorkHour(
                        2,
                        1,
                        WorkHour.WEEKEND,
                        "11:00",
                        "22:00"
                    )
                ),
                "0472 545 235",
                "бул. Шевченка",
                listOf(
                    Review(
                        1,
                        1,
                        "27/10/2018",
                        4.0,
                        "Чудовий ресторан! :)"
                    )
                )
            ),

            Restaurant(
                1,
                "Masala 2",
                4.7,
                null,
                "Чудовий ресторан з індійською кухнею",
                49.433307428826836,
                32.07641020417213,
                null,
                listOf(
                    Dish(
                        1,
                        "Шріматі",
                        DishType.DESSERT,
                        DishCuisine.INDIAN,
                        listOf(
                            Review(
                                1,
                                1,
                                "27/10/2018",
                                4.0,
                                "Чудовий десерт! :)"
                            )
                        ),
                        4.6,
                        listOf(
                            Ingredient(1, "Яблуко"),
                            Ingredient(2, "Борошно")
                        ),
                        "Традиційний пиріг з яблуком",
                        null,
                        200,
                        35.90
                    )
                ),
                listOf(
                    WorkHour(
                        1,
                        1,
                        WorkHour.WEEKDAY,
                        "09:00",
                        "20:00"
                    ),
                    WorkHour(
                        2,
                        1,
                        WorkHour.WEEKEND,
                        "11:00",
                        "22:00"
                    )
                ),
                "0472 533 235",
                "бул. Шевченка",
                listOf(
                    Review(
                        1,
                        1,
                        "27/10/2018",
                        4.0,
                        "Чудовий ресторан! :)"
                    )
                )
            )
        )
    }

}