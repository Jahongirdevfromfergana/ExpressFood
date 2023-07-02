package uz.fergana.fastexpress.screen.restaurant.detail.map

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.permissionx.guolindev.PermissionX
import uz.fergana.fastexpress.R
import uz.fergana.fastexpress.databinding.ActivityOpenMapsBinding

class OpenMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityOpenMapsBinding
    var latitude=0.0
    var longitude=0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOpenMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

         latitude = intent.getDoubleExtra("latitude", 0.0)
         longitude = intent.getDoubleExtra("longitude", 0.0)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        PermissionX.init(this)
            .permissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
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
                } else {
                    Toast.makeText(
                        this,
                        "These permissions are denied: $deniedList",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}