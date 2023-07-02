package uz.fergana.fastexpress.screen.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import uz.fergana.fastexpress.screen.auth.SiginActivity
import uz.fergana.fastexpress.screen.main.MainActivity
import uz.fergana.fastexpress.utils.PrefUtils
import uz.fergana.fastexpress.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            Handler().postDelayed({
                if (PrefUtils.getToken().isEmpty()){
                    startActivity(Intent(this@SplashActivity, SiginActivity::class.java))
                    finish()
                }else{
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }
            }, 3000)
        }
    }
}