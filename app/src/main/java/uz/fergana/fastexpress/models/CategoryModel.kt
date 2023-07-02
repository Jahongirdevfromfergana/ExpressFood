package uz.fergana.fastexpress.models

import com.google.gson.annotations.SerializedName

data class CategoryModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("title")
    val title: String,
    var active: Boolean = false
)
