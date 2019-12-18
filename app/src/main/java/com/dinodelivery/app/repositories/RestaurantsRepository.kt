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
                "https://media-cdn.tripadvisor.com/media/photo-s/11/4a/54/fe/essence-restaurant.jpg",
                listOf(
                    Dish(
                        1,
                        "Шріматі",
                        Dish.DishType.DESSERT,
                        Dish.DishCuisine.INDIAN,
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
                        "https://raobcn.com/wp-content/uploads/2019/11/Rao-restaurant-romantic-restaurant-barcelona.jpg",
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
                ),
                Dish.DishCuisine.INDIAN
            ),

            Restaurant(
                1,
                "Masala 2",
                2.7,
                null,
                "Чудовий ресторан з індійською кухнею",
                49.433307428826836,
                32.07641020417213,
                "https://www.wien.info/media/images/restaurant-mraz-sohn-innen-3to2.jpeg",
                listOf(
                    Dish(
                        1,
                        "Шріматі",
                        Dish.DishType.DESSERT,
                        Dish.DishCuisine.INDIAN,
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
                    ),
                    Dish(
                        3,
                        "Шріматі 2",
                        Dish.DishType.DESSERT,
                        Dish.DishCuisine.INDIAN,
                        listOf(
                            Review(
                                2,
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
                        3.0,
                        "Не сподобалося"
                    )
                ),
                Dish.DishCuisine.INDIAN
            ),
            Restaurant(
                1,
                "L'italiano",
                5.0,
                null,
                "Ресторан, в якому готують найсмачнішу пасту :)",
                49.433307428826836,
                32.09641020417213,
                "https://www.lofrestaurant.com/wp-content/uploads/sites/13/2018/10/Pillows_Grand_Hotel_Reylof_Gent_Lof_Restaurant_05-1052x675.jpg",
                listOf(
                    Dish(
                        1,
                        "Паста з морепродуктами",
                        Dish.DishType.MAIN,
                        Dish.DishCuisine.ITALIAN,
                        listOf(
                            Review(
                                1,
                                1,
                                "27/10/2018",
                                4.9,
                                "Смакота :)"
                            )
                        ),
                        4.6,
                        listOf(
                            Ingredient(1, "Риба"),
                            Ingredient(2, "Спагеті")
                        ),
                        "Традиційна паста",
                        null,
                        250,
                        78.75
                    ),
                    Dish(
                        3,
                        "Тірамісу",
                        Dish.DishType.DESSERT,
                        Dish.DishCuisine.ITALIAN,
                        listOf(
                            Review(
                                2,
                                1,
                                "27/10/2018",
                                5.0,
                                "Дуже яскравий смак"
                            )
                        ),
                        4.6,
                        listOf(
                            Ingredient(1, "Вино"),
                            Ingredient(2, "Маскарпоне")
                        ),
                        "Традиційний італійський десерт",
                        "https://cookery.site/wp-content/uploads/2019/08/58697323-360x381.png.pagespeed.ce.htl5iQBArt.png",
                        200,
                        65.00
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
                ),
                Dish.DishCuisine.ITALIAN
            )
        )
    }

}