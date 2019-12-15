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
import com.dinodelivery.app.adapters.RestaurantListAdapter
import com.dinodelivery.app.entities.Restaurant
import com.dinodelivery.app.viewmodels.RestaurantsViewModel
import kotlinx.android.synthetic.main.fragment_restaurants.*


class RestaurantsFragment : Fragment() {

    private val restaurantsViewModel: RestaurantsViewModel by lazy {
        ViewModelProviders.of(this).get(RestaurantsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_restaurants, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()

        restaurantsViewModel.getRestaurants()
    }

    private fun initObservers() {
        restaurantsViewModel.restaurants.observe(viewLifecycleOwner, Observer {
            restaurantsRecyclerView.adapter =
                RestaurantListAdapter(it, object : RestaurantListAdapter.RestaurantClickListener {
                    override fun onRestaurantClick(restaurant: Restaurant) {
                        (requireActivity() as MainActivity).navigateToFragmentAndAddToStack(SingleRestaurantFragment.newInstance(restaurant))
                    }
                })
        })
    }
}
