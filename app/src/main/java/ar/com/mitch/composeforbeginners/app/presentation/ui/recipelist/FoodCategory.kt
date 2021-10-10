package ar.com.mitch.composeforbeginners.app.presentation.ui.recipelist

import ar.com.mitch.composeforbeginners.app.presentation.ui.recipelist.FoodCategory.*

enum class FoodCategory(val value: String) {
    CHICKEN("Chicken"),
    BEEF("Beef"),
    SOUP("Soup"),
    DESSERT("Dessert"),
    VEGETARIAN("Vegetarian"),
    MILK("Milk"),
    VEGAN("Vegan"),
    PIZZA("Pizza"),
    DONUT("Donut")
}

fun getAllFoodCategories() = listOf(CHICKEN, BEEF, SOUP, DESSERT, VEGETARIAN, MILK, VEGAN, PIZZA, DONUT)

fun getFoodCategory(value: String): FoodCategory? {
    val map = values().associateBy(FoodCategory::value)
    return map[value]
}