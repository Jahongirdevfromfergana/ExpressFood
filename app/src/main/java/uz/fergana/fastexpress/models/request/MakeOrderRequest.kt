package uz.fergana.fastexpress.models.request


import com.google.gson.annotations.SerializedName
import uz.fergana.fastexpress.models.CartModel
import uz.fergana.fastexpress.models.enum.OrderType

data class MakeOrderRequest(
    @SerializedName("adress")
    var address: String,
    @SerializedName("latitude")
    var latitude: Double,
    @SerializedName("longitude")
    var longitude: Double,
    @SerializedName("order_products")
    var orderProducts: List<CartModel>,
    @SerializedName("order_type")
    var orderType: OrderType
)