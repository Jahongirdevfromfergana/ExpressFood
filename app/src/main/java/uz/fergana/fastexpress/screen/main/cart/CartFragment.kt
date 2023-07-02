package uz.fergana.fastexpress.screen.main.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import uz.fergana.fastexpress.adapters.CartProductAdapter
import uz.fergana.fastexpress.adapters.CartProductAdapterCallback
import uz.fergana.fastexpress.screen.main.order.checkout.CheckoutActivity
import uz.fergana.fastexpress.utils.Constants
import uz.fergana.fastexpress.databinding.FragmentCartBinding

class CartFragment : Fragment(), CartProductAdapterCallback {
    lateinit var binding: FragmentCartBinding
    lateinit var cartProductAdapter: CartProductAdapter
    lateinit var viewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        binding.apply {

            viewModel = ViewModelProvider(this@CartFragment)[CartViewModel::class.java]

            viewModel.errorLiveData.observe(requireActivity()) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }

            viewModel.progressLiveData.observe(requireActivity()){
                swipe.isRefreshing=it
                if (it){
                    binding.flProgress.visibility = View.VISIBLE
                }else{
                    binding.flProgress.visibility = View.GONE
                }
            }

            swipe.setOnRefreshListener {
                viewModel.getFoods()
            }

            viewModel.foodListLiveData.observe(requireActivity()) {
                cartProductAdapter = CartProductAdapter(it ?: emptyList(), this@CartFragment)
                rvCart.adapter = cartProductAdapter
                lottie.visibility = if (it!!.isNotEmpty()) View.GONE else View.VISIBLE
                lyCartAction.visibility = if (it.isNotEmpty()) View.VISIBLE else View.GONE
                tvTotalAmount.text = it.sumOf { it.cartCount * it.price }.toString() + " UZS"
            }

            checkout.setOnClickListener {
                val intent = Intent(requireActivity(), CheckoutActivity::class.java)
                intent.putExtra(
                    Constants.EXTRA_DATA,
                    viewModel.foodListLiveData.value?.sumOf { it.cartCount * it.price }
                        .toString()
                )
                startActivity(intent)
            }

            viewModel.getFoods()

        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = CartFragment()
    }

    override fun onUpdate(count: Int) {
        if (count == 0) {
            binding.lottie.visibility =
                if (viewModel.foodListLiveData.value!!.isNotEmpty()) View.GONE else View.VISIBLE
            viewModel.getFoods()
        } else {
            binding.tvTotalAmount.text =
                viewModel.foodListLiveData.value?.sumOf { it.cartCount * it.price }
                    .toString() + " UZS"
        }
    }
}