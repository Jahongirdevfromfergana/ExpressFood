package uz.fergana.fastexpress.screen.restaurant.detail

import uz.fergana.fastexpress.models.RestaurantModel

interface ItemClickListener {
    fun onItemClick(restaurantModel: RestaurantModel)
}