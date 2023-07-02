package uz.fergana.fastexpress.screen.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import uz.fergana.fastexpress.models.request.RegistrationRequest
import uz.fergana.fastexpress.screen.main.MainActivity
import uz.fergana.fastexpress.utils.PrefUtils
import uz.fergana.fastexpress.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistrationBinding
    lateinit var viewModel: RegistrationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            viewModel =
                ViewModelProvider(this@RegistrationActivity)[RegistrationViewModel::class.java]

            viewModel.errorLiveData.observe(this@RegistrationActivity) {
                Toast.makeText(this@RegistrationActivity, it, Toast.LENGTH_SHORT).show()
            }

            viewModel.progressLiveData.observe(this@RegistrationActivity){
                if (it){
                    binding.flProgress.visibility = View.VISIBLE
                }else{
                    binding.flProgress.visibility = View.GONE
                }
            }

            viewModel.registrationLiveData.observe(this@RegistrationActivity) {
                PrefUtils.setToken(it!!.token)
                startActivity(
                    Intent(
                        this@RegistrationActivity,
                        MainActivity::class.java
                    )
                )
                finish()
            }

            btnRegistration.setOnClickListener {
                if (edPassword.text.toString() == edRepassword.text.toString()) {
                    loadData()
                } else {
                    Toast.makeText(
                        this@RegistrationActivity,
                        "Iltimos parolni tekshiring",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            tvLogin.setOnClickListener {
                startActivity(Intent(this@RegistrationActivity, SiginActivity::class.java))
                finish()
            }
        }
    }

    fun loadData() {
        viewModel.getRegistration(
            RegistrationRequest(
                binding.edFullname.text.toString(),
                binding.edPhone.text.toString(),
                binding.edPassword.text.toString()
            )
        )
    }
}