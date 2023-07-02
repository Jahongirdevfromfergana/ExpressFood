package uz.fergana.fastexpress.screen.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import uz.fergana.fastexpress.models.request.LoginRequest
import uz.fergana.fastexpress.screen.main.MainActivity
import uz.fergana.fastexpress.utils.PrefUtils
import uz.fergana.fastexpress.databinding.ActivitySiginBinding

class SiginActivity : AppCompatActivity() {
    lateinit var binding: ActivitySiginBinding
    lateinit var viewModel: SiginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySiginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            viewModel = ViewModelProvider(this@SiginActivity)[SiginViewModel::class.java]

            viewModel.errorLiveData.observe(this@SiginActivity) {
                Toast.makeText(this@SiginActivity, it, Toast.LENGTH_SHORT).show()
            }

            viewModel.progressLiveData.observe(this@SiginActivity){
                if (it){
                    binding.flProgress.visibility = View.VISIBLE
                }else{
                    binding.flProgress.visibility = View.GONE
                }
            }

            viewModel.siginLiveData.observe(this@SiginActivity) {
                PrefUtils.setToken(it!!.token)
                startActivity(
                    Intent(
                        this@SiginActivity,
                        MainActivity::class.java
                    )
                )
                finish()
            }

            btnLogin.setOnClickListener {
                loadData()
            }

            tvRegistration.setOnClickListener {
                startActivity(Intent(this@SiginActivity, RegistrationActivity::class.java))
                finish()
            }
        }
    }

    fun loadData() {
        viewModel.getSigin(
            LoginRequest(
                binding.edPassword.text.toString(),
                binding.edPhone.text.toString()
            )
        )
    }
}