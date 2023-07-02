package uz.fergana.fastexpress.models.response


import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("data")
    val `data`: T,
    @SerializedName("error_code")
    val errorCode: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)