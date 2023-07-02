package uz.fergana.fastexpress.screen.main.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import uz.fergana.fastexpress.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.apply {
            viewModel = ViewModelProvider(this@ProfileFragment)[ProfileViewModel::class.java]

            viewModel.errorLiveData.observe(requireActivity()) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }

            viewModel.progressLiveData.observe(requireActivity()){
                if (it){
                    binding.flProgress.visibility = View.VISIBLE
                }else{
                    binding.flProgress.visibility = View.GONE
                }
            }

            viewModel.profileLiveData.observe(requireActivity()) {
                tvFullName.text = it?.fullname
                if (it?.phone?.substring(0, 1) == "+") {
                    tvPhone.text = it?.phone
                } else {
                    tvPhone.text = "+" + it?.phone
                }
            }
            loadData()
        }
        return binding.root
    }

    fun loadData() {
        viewModel.getProfile()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}