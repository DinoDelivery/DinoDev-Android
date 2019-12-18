package com.dinodelivery.app.entities

data class SortItem(
    var title: String? = null,
    var criteria: SortCriteria? = null,
    var selected: Boolean = false
) {
    enum class SortCriteria {
        ALPHABET,
        PRICE,
        RATING
    }
}