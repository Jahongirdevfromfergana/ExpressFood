package uz.fergana.fastexpress.models


import com.google.gson.annotations.SerializedName

data class OfferModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("title")
    val title: String
)