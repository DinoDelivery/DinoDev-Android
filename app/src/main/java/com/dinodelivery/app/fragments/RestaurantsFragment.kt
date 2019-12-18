package com.dinodelivery.app.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dinodelivery.app.MainActivity
import com.dinodelivery.app.R
import com.dinodelivery.app.adapters.RestaurantListAdapter
import com.dinodelivery.app.entities.Restaurant
import com.dinodelivery.app.viewmodels.RestaurantsViewModel
import kotlinx.android.synthetic.main.fragment_restaurants.*
import kotlinx.android.synthetic.main.restaurant_filter_dialog.view.*


class RestaurantsFragment : Fragment() {

    private var restaurants = listOf<Restaurant>()

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

        initListeners()

        initObservers()

        restaurantsViewModel.getRestaurants()
    }

    private fun initListeners() {
        btnMap.setOnClickListener {
            (requireActivity() as MainActivity).clearFragments()
            (requireActivity() as MainActivity).navigateToFragment(MapFragment())
        }

        btnFilter.setOnClickListener { showFilterDialog() }

        btnFilter.setOnLongClickListener {
            setRestaurantsRecyclerView(restaurants)
            true
        }
    }

    private fun initObservers() {
        restaurantsViewModel.restaurants.observe(viewLifecycleOwner, Observer {
            restaurants = it
            setRestaurantsRecyclerView(it)
        })
    }

    private fun setRestaurantsRecyclerView(restaurantList: List<Restaurant>) {
        restaurantsRecyclerView.adapter =
            RestaurantListAdapter(
                restaurantList,
                object : RestaurantListAdapter.RestaurantClickListener {
                    override fun onRestaurantClick(restaurant: Restaurant) {
                        (requireActivity() as MainActivity).navigateToFragmentAndAddToStack(
                            SingleRestaurantFragment.newInstance(restaurant)
                        )
                    }
                })
    }

    private fun showFilterDialog() {
        val dialogView = layoutInflater.inflate(R.layout.restaurant_filter_dialog, null)
        val alertDialog = AlertDialog.Builder(requireContext()).setView(dialogView).create()
        alertDialog.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.dialog_background
            )
        )
        with(dialogView) {
            ratingSpinner.adapter =
                ArrayAdapter<Int>(context, R.layout.rating_spinner_item, arrayOf(1, 2, 3, 4, 5))
            cuisineSpinner.adapter = ArrayAdapter<String>(
                context,
                R.layout.rating_spinner_item,
                context.resources.getStringArray(R.array.cuisine)
            )
            btnFilterRestaurants.setOnClickListener {
                filterRestaurants(
                    ratingSpinner.selectedItem.toString().toInt(),
                    cuisineSpinner.selectedItem.toString()
                )
                alertDialog.dismiss()
            }
        }
        alertDialog.show()
    }

    private fun filterRestaurants(rating: Int?, cuisine: String?) {
        setRestaurantsRecyclerView(
            restaurants.filter { it.rating >= rating ?: 0 }.filter {
                it.cuisine == restaurantsViewModel.getCuisine(
                    cuisine
                ) ?: it.cuisine
            }
        )
    }

    companion object {
        private val TAG = RestaurantsFragment::class.java.simpleName
    }
}
