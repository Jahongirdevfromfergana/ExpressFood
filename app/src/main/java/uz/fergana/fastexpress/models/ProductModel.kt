package uz.fergana.fastexpress.models


import com.google.gson.annotations.SerializedName

data class ProductModel(
    @SerializedName("category_id")
    val categoryId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("ingridents")
    val ingridents: List<String>,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("restaurant_id")
    val restaurantId: Int,
    var cartCount: Int = 0
)