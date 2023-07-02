package uz.fergana.fastexpress.models.response


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("fullname")
    val fullname: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("token")
    val token: String
)