package com.dinodelivery.app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dinodelivery.app.MainActivity
import com.dinodelivery.app.R
import com.dinodelivery.app.viewmodels.RestaurantsViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private val restaurantsViewModel: RestaurantsViewModel by lazy {
        ViewModelProviders.of(this).get(RestaurantsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun initObservers() {
        restaurantsViewModel.restaurants.observe(viewLifecycleOwner, Observer { restaurants ->
            restaurants.forEach { restaurant ->
                mMap.addMarker(
                    MarkerOptions().position(LatLng(restaurant.lat, restaurant.long)).icon(
                        BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_GREEN
                        )
                    )
                )
            }
            mMap.setOnMarkerClickListener {
                val currentRestaurant =
                    restaurants.filter { currentRestaurant -> it.position.latitude == currentRestaurant.lat && it.position.longitude == currentRestaurant.long }[0]
                (requireActivity() as MainActivity).navigateToFragmentAndAddToStack(SingleRestaurantFragment.newInstance(currentRestaurant))
                return@setOnMarkerClickListener true
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    DOM_TORGOVLI_LAT,
                    DOM_TORGOVLI_LNG
                ), 15f
            )
        )

        initObservers()
        restaurantsViewModel.getRestaurants()
    }

    companion object {
        private const val DOM_TORGOVLI_LAT = 49.441013265861436
        private const val DOM_TORGOVLI_LNG = 32.06598412245512
    }
}
