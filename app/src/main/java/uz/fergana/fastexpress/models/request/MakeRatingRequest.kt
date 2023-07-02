package uz.fergana.fastexpress.models.request


import com.google.gson.annotations.SerializedName

data class MakeRatingRequest(
    @SerializedName("comment")
    var comment: String,
    @SerializedName("rating")
    var rating: Int,
    @SerializedName("restaurant_id")
    var restaurantId: Int
)