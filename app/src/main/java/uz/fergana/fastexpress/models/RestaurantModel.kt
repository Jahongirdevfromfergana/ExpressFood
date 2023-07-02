package uz.fergana.fastexpress.models

import java.io.Serializable

data class RestaurantModel(
    val id:Int,
    val main_image: String,
    val name: String,
    val phone: String,
    val address: String,
    val distance: Double,
    val rating: Double,
    val latitude:Double,
    val longitude:Double
):Serializable
