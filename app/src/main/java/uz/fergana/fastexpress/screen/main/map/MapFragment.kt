package uz.fergana.fastexpress.screen.main.map

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.permissionx.guolindev.PermissionX
import uz.fergana.fastexpress.R
import uz.fergana.fastexpress.databinding.FragmentMapBinding


class MapFragment : Fragment() {
    private lateinit var mMap: GoogleMap
    lateinit var binding: FragmentMapBinding
    lateinit var viewModel: MapViewModel
    var locations: ArrayList<LatLng> = ArrayList()
    var locationName: ArrayList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MapViewModel::class.java]

        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment

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

        viewModel.restaurantListLiveData.observe(requireActivity()) {
            it?.forEach {
                locations.add(LatLng(it.latitude, it.longitude))
            }
            it?.forEach {
                locationName.add(it.name)
            }

            supportMapFragment.getMapAsync(object : OnMapReadyCallback {
                @SuppressLint("MissingPermission")
                override fun onMapReady(google_map: GoogleMap) {
                    mMap = google_map

                    PermissionX.init(requireActivity())
                        .permissions(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                        .request { allGranted, grantedList, deniedList ->
                            if (allGranted) {
                                var latLngBoundsBuilder = LatLngBounds.Builder()

                                var position = 0
                                locations.forEach {
                                    mMap.addMarker(
                                        MarkerOptions().position(it).title(locationName[position])
                                    )
                                    position++
                                    latLngBoundsBuilder.include(it)
                                }
                                mMap.moveCamera(
                                    CameraUpdateFactory.newLatLngBounds(
                                        latLngBoundsBuilder.build(),
                                        100
                                    )
                                )
                            } else {
                                Toast.makeText(
                                    requireActivity(),
                                    "These permissions are denied: $deniedList",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                }
            })
        }

        loadData()

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = MapFragment()
    }

    fun loadData() {
        viewModel.getRestaurant()
    }
}