package uz.fergana.fastexpress.screen.main.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import uz.fergana.fastexpress.adapters.CategoryAdapter
import uz.fergana.fastexpress.adapters.RestaurantAdapter
import uz.fergana.fastexpress.adapters.SlideAdapter
import uz.fergana.fastexpress.adapters.TopRestaurantAdapter
import uz.fergana.fastexpress.screen.main.MainActivity
import uz.fergana.fastexpress.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding
    lateinit var slideAdapter: SlideAdapter
    lateinit var categoryAdapter: CategoryAdapter
    lateinit var restaurantAdapter: RestaurantAdapter
    lateinit var topRestaurantAdapter: TopRestaurantAdapter
    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.apply {
            viewModel = ViewModelProvider(this@MainFragment)[MainViewModel::class.java]

            menu.setOnClickListener { v ->
                (activity as MainActivity).openCloseNavigationDrawer(v)
            }

            viewModel.errorLiveData.observe(requireActivity()) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }

            viewModel.progressLiveData.observe(requireActivity()) {
                swipe.isRefreshing = it
                if (it) {
                    binding.flProgress.visibility = View.VISIBLE
                } else {
                    binding.flProgress.visibility = View.GONE
                }
            }

            swipe.setOnRefreshListener {
                loadData()
            }

            viewModel.offerListLiveData.observe(requireActivity()) {
                slideAdapter = SlideAdapter(it ?: emptyList())
                rvSlide.adapter = slideAdapter
            }


            viewModel.categoryListLiveData.observe(requireActivity()) {
                categoryAdapter = CategoryAdapter(it ?: emptyList())
                rvCategories.adapter = categoryAdapter
            }

            viewModel.restaurantListLiveData.observe(requireActivity()) {
                restaurantAdapter = RestaurantAdapter(it ?: emptyList())
                rvNearbyRestaurants.adapter = restaurantAdapter
            }

            viewModel.restaurantTopListLiveData.observe(requireActivity()) {
                topRestaurantAdapter = TopRestaurantAdapter(it ?: emptyList())
                rvTopRestaurants.adapter = topRestaurantAdapter
            }

            loadData()
        }
        return binding.root
    }

    fun loadData() {
        viewModel.getOffers()
        viewModel.getCategory()
        viewModel.getRestaurant()
        viewModel.getTopRestaurant()
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}