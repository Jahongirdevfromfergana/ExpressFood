package uz.fergana.fastexpress.screen.main

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import uz.fergana.fastexpress.screen.InfoActivity
import uz.fergana.fastexpress.screen.auth.SiginActivity
import uz.fergana.fastexpress.utils.PrefUtils
import uz.fergana.fastexpress.R
import uz.fergana.fastexpress.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            val navHostFragment = fragmentContainerView.getFragment<NavHostFragment>()
            bottomNavigationView.setupWithNavController(navHostFragment.navController)

            navView.setNavigationItemSelectedListener { item ->
                if (item.itemId == R.id.info) {
                    startActivity(Intent(this@MainActivity, InfoActivity::class.java))
                } else if (item.itemId == R.id.call) {
                    val profile_telegram = Intent(ACTION_VIEW, Uri.parse("https://t.me/Jahongir_Muhammadjonov"))
                    startActivity(profile_telegram)

                } else if (item.itemId == R.id.exit) {
                    startActivity(
                        Intent(
                            this@MainActivity,
                            SiginActivity::class.java
                        )
                    )
                    finish()
                    PrefUtils.setToken("")
                }
                false
            }
        }
    }

    fun openCloseNavigationDrawer(view: View) {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }
}