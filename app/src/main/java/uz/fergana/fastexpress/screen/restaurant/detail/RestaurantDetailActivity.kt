package uz.fergana.fastexpress.screen.restaurant.detail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import uz.fergana.fastexpress.adapters.ProductAdapter
import uz.fergana.fastexpress.models.RestaurantModel
import uz.fergana.fastexpress.screen.restaurant.detail.feedback.FeedbackFragment
import uz.fergana.fastexpress.screen.restaurant.detail.map.OpenMapsActivity
import uz.fergana.fastexpress.utils.Constants.EXTRA_DATA
import uz.fergana.fastexpress.utils.PrefUtils
import uz.fergana.fastexpress.utils.loadImage
import uz.fergana.fastexpress.databinding.ActivityRestaurantDetailBinding

class RestaurantDetailActivity : AppCompatActivity(), ItemClickListener {
    lateinit var binding: ActivityRestaurantDetailBinding
    lateinit var productAdapter: ProductAdapter
    lateinit var restaurantModel: RestaurantModel
    lateinit var viewModel: RestaurantDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT < 33) {
            restaurantModel = intent.getSerializableExtra(EXTRA_DATA) as RestaurantModel
        } else {
            restaurantModel = intent.getSerializableExtra(EXTRA_DATA, RestaurantModel::class.java)!!
        }

        binding.apply {
            viewModel =
                ViewModelProvider(this@RestaurantDetailActivity)[RestaurantDetailViewModel::class.java]

            if (restaurantModel.distance > 0) {
                distanceVisibility.visibility = View.VISIBLE
                tvDistance.text = "${String.format("%.1f", restaurantModel.distance)} km"
            } else {
                distanceVisibility.visibility = View.GONE
            }
            viewModel.errorLiveData.observe(this@RestaurantDetailActivity) {
                Toast.makeText(this@RestaurantDetailActivity, it, Toast.LENGTH_SHORT).show()
            }

            viewModel.progressLiveData.observe(this@RestaurantDetailActivity) {
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

            viewModel.successProductLiveData.observe(this@RestaurantDetailActivity) {
                it?.forEach {
                    it.cartCount = PrefUtils.getCartCount(it.id)
                }
                productAdapter = ProductAdapter(it ?: emptyList())
                rvProduct.adapter = productAdapter
            }

            viewModel.successDetailRestaurantLiveData.observe(this@RestaurantDetailActivity) {
                if (it != null) {
                    restaurantModel = it
                    tvName.text = restaurantModel.name
                    tvAddress.text = restaurantModel.address
                    tvReting.text = restaurantModel.rating.toString()
                    slideImage.loadImage(restaurantModel.main_image)
                    reting.rating = restaurantModel.rating.toFloat()
                    tvContact.text = restaurantModel.phone
                }
            }

            loadData()

            back.setOnClickListener {
                finish()
            }

            btnFeedback.setOnClickListener {
                val fragment = FeedbackFragment.newInstance(restaurantModel.id.toString())
                fragment.show(supportFragmentManager, fragment.tag)
            }
            openMap.setOnClickListener {
                val intent = Intent(this@RestaurantDetailActivity, OpenMapsActivity::class.java)
                intent.putExtra("latitude", restaurantModel.latitude)
                intent.putExtra("longitude", restaurantModel.longitude)
                startActivity(intent)
            }
        }
    }

    fun loadData() {
        viewModel.getProduct(restaurantModel.id)
        viewModel.getDetailRestaurant(restaurantModel.id)
    }

    override fun onItemClick(restaurantModel: RestaurantModel) {
        binding.reting.rating = restaurantModel.rating.toFloat()
        binding.tvReting.text = restaurantModel.rating.toString()
    }
}