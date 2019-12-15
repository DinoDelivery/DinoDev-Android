package com.dinodelivery.app.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dinodelivery.app.R
import com.dinodelivery.app.entities.Restaurant



class SingleRestaurantFragment : Fragment() {

    private var restaurant: Restaurant? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            restaurant = it.getParcelable(RESTAURANT_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_single_restaurant, container, false)
    }


    companion object {

        private const val RESTAURANT_KEY = "restaurant_key"

        @JvmStatic
        fun newInstance(restaurant: Restaurant) =
            RestaurantsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(RESTAURANT_KEY, restaurant)
                }
            }
    }
}
