package uz.fergana.fastexpress

import android.app.Application
import com.orhanobut.hawk.Hawk

class MyApp:Application() {
    companion object{
        lateinit var app: MyApp
    }
    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build()
        app =this
    }
}