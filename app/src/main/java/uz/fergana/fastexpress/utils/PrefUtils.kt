package uz.fergana.fastexpress.utils

import com.orhanobut.hawk.Hawk
import uz.fergana.fastexpress.MyApp
import uz.fergana.fastexpress.models.CartModel

object PrefUtils {
    const val PREF_TOKEN = "token"
    const val PREF_CART = "cart"

    fun init() {
        Hawk.init(MyApp.app).build()
    }

    fun setToken(value: String?) {
        Hawk.put(PREF_TOKEN, value)
    }

    fun getToken(): String {
        return Hawk.get(PREF_TOKEN, "")
    }

    fun setCartList(value: List<CartModel>) {
        Hawk.put(PREF_CART, value)
    }

    fun getCartList(): List<CartModel> {
        return Hawk.get(PREF_CART, emptyList())
    }

    fun add2Cart(id: Int, count: Int) {
        var items = getCartList().toMutableList()
        var cart = items.firstOrNull { it.id == id }
        if (cart != null) {
            if (count > 0) {
                cart.count = count
            } else {
                items.remove(cart)
            }
        } else {
            if (count > 0) {
                items.add(CartModel(id, count))
            }
        }
        setCartList(items)
    }

    fun getCartCount(id: Int): Int {
        return getCartList().firstOrNull { it.id == id }?.count ?: 0
    }
}