package uz.fergana.fastexpress.models


data class AllRestaurant(
    val region_id: Int = 0,
    val district_id: Int = 0,
    val category_id: Int = 0,
    val food_id: Int = 0,
    val keyword: String = "",
    val sort: String = "",
    val limit: Int = 0,
    val latitude: Double = 40.3618636,
    val longitude: Double = 71.7804718
)