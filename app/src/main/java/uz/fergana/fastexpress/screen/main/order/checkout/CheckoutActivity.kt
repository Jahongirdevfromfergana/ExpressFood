package uz.fergana.fastexpress.screen.main.order.checkout

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.location.*
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.model.LatLng
import com.permissionx.guolindev.PermissionX
import uz.fergana.fastexpress.models.enum.OrderType
import uz.fergana.fastexpress.models.request.MakeOrderRequest
import uz.fergana.fastexpress.screen.main.MainActivity
import uz.fergana.fastexpress.screen.main.order.checkout.map.MapsActivity
import uz.fergana.fastexpress.utils.Constants
import uz.fergana.fastexpress.utils.PrefUtils
import uz.fergana.fastexpress.R
import uz.fergana.fastexpress.databinding.ActivityCheckoutBinding
import java.util.*


class CheckoutActivity : AppCompatActivity() {
    lateinit var binding: ActivityCheckoutBinding
    lateinit var viewModel: CheckoutViewModel
    var latitude = 0.0
    var longitude = 0.0
    var bol = false
    var request = MakeOrderRequest("A.Navoiy", 0.0, 0.0, PrefUtils.getCartList(), OrderType.CARD)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bol = false
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[CheckoutViewModel::class.java]

        viewModel.errorLiveData.observe(    this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.successOrderLiveData.observe(this) {

        }

        viewModel.progressLiveData.observe(this){
            if (it){
                binding.flProgress.visibility = View.VISIBLE
            }else{
                binding.flProgress.visibility = View.GONE
            }
        }

        binding.tvTotalAmount.text = intent.getStringExtra(Constants.EXTRA_DATA) + " UZS"

        binding.apply {

            lyPaymentCash.setOnClickListener {
                request.orderType = OrderType.CASH
                lyPaymentCash.setBackgroundResource(R.drawable.shape_active)
                imgPaymentCash.setColorFilter(
                    ContextCompat.getColor(
                        this@CheckoutActivity,
                        R.color.color_accent
                    ), android.graphics.PorterDuff.Mode.SRC_IN
                )
                tvPaymentCash.setTextColor(
                    ContextCompat.getColor(
                        this@CheckoutActivity,
                        R.color.color_accent
                    )
                )

                lyPaymentCard.setBackgroundResource(R.drawable.shape_inactive)
                imgPaymentCard.setColorFilter(
                    ContextCompat.getColor(
                        this@CheckoutActivity,
                        R.color.grey_color
                    ), android.graphics.PorterDuff.Mode.SRC_IN
                )
                tvPaymentCard.setTextColor(
                    ContextCompat.getColor(
                        this@CheckoutActivity,
                        R.color.grey_color
                    )
                )
            }

            lyPaymentCard.setOnClickListener {
                request.orderType = OrderType.CARD
                lyPaymentCard.setBackgroundResource(R.drawable.shape_active)
                imgPaymentCard.setColorFilter(
                    ContextCompat.getColor(
                        this@CheckoutActivity,
                        R.color.color_accent
                    ), android.graphics.PorterDuff.Mode.SRC_IN
                )
                tvPaymentCard.setTextColor(
                    ContextCompat.getColor(
                        this@CheckoutActivity,
                        R.color.color_accent
                    )
                )

                lyPaymentCash.setBackgroundResource(R.drawable.shape_inactive)
                imgPaymentCash.setColorFilter(
                    ContextCompat.getColor(
                        this@CheckoutActivity,
                        R.color.grey_color
                    ), android.graphics.PorterDuff.Mode.SRC_IN
                )
                tvPaymentCash.setTextColor(
                    ContextCompat.getColor(
                        this@CheckoutActivity,
                        R.color.grey_color
                    )
                )
            }

            binding.btnLocation.setOnClickListener {
                bol = true
                val intent = Intent(this@CheckoutActivity, MapsActivity::class.java)
                resultLauncher.launch(intent)
            }

            back.setOnClickListener {
                finish()
            }

            btnMakeOrder.setOnClickListener {
                Toast.makeText(this@CheckoutActivity, "Order saved!", Toast.LENGTH_SHORT).show()
                PrefUtils.setCartList(emptyList())
                val i = Intent(this@CheckoutActivity, MainActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)

                request.address = edAddress.text.toString()
                viewModel.getMakeOrder(request)
            }
        }
        getLocation()
    }

    @SuppressLint("MissingPermission")
    fun getLocation() {
        PermissionX.init(this)
            .permissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        5000L,
                        5F,
                        object : LocationListener {
                            override fun onLocationChanged(location: Location) {
                                latitude = location.latitude
                                longitude = location.longitude
                                val address = getAddress(LatLng(latitude, longitude))
                                binding.edAddress.setText(address)
                                request.latitude = latitude
                                request.longitude = longitude
                            }
                        })
                } else {
                    Toast.makeText(
                        this,
                        "These permissions are denied: $deniedList",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    fun getAddress(latLng: LatLng): String {
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address>?
        val address: Address?
        var addressText = ""

        addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

        if (addresses?.isNotEmpty() == true) {
            address = addresses[0]
            addressText = address.getAddressLine(0)
        } else {
            addressText = "its not appear"
        }
        return addressText
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                latitude = data?.getDoubleExtra("latitude", 0.0)!!
                longitude = data.getDoubleExtra("longitude", 0.0)
                val address = getAddress(LatLng(latitude, longitude))
                binding.edAddress.setText(address)
                request.latitude = latitude
                request.longitude = longitude
            }
        }
}