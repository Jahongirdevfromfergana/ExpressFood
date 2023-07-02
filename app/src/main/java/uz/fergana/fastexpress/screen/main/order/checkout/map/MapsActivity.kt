package uz.fergana.fastexpress.screen.main.order.checkout.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.permissionx.guolindev.PermissionX
import uz.fergana.fastexpress.R
import uz.fergana.fastexpress.databinding.ActivityMapsBinding


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    var latitude = 0.0
    var longitude = 0.0
    private lateinit var myMarker: Marker

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.btnLocation.setOnClickListener {
            val intent = Intent()
            intent.putExtra("latitude", latitude)
            intent.putExtra("longitude", longitude)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        setUpMap()

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
                                mMap.addMarker(
                                    MarkerOptions().position(
                                        LatLng(
                                            latitude,
                                            longitude
                                        )
                                    )
                                )
                                mMap.moveCamera(
                                    CameraUpdateFactory.newLatLngZoom(
                                        LatLng(
                                            latitude,
                                            longitude
                                        ), 14F
                                    )
                                )
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

    private fun setUpMap() {
        mMap.setOnMapClickListener { p0 ->
            mMap.clear()
            latitude = p0.latitude
            longitude = p0.longitude
            myMarker = mMap.addMarker(
                MarkerOptions()
                    .position(p0)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            )!!
            binding.btnLocation.visibility = View.VISIBLE
        }
    }
}