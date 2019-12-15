package com.dinodelivery.app.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.dinodelivery.app.MainActivity
import com.dinodelivery.app.R
import com.dinodelivery.app.adapters.ReviewListAdapter
import com.dinodelivery.app.adapters.WorkHourListAdapter
import com.dinodelivery.app.entities.Restaurant
import com.dinodelivery.app.entities.ReviewItem
import kotlinx.android.synthetic.main.fragment_single_restaurant.*
import kotlinx.android.synthetic.main.restaurant_info_dialog.view.*


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        restaurant?.let {
            toolbarHeader.text = it.name
            txtRating.text = it.rating.toString()
            it.reviews?.let { reviews ->
                txtReviewCount.text = resources.getString(R.string.review_count, reviews.size)
                setReviewList(reviews.map { review ->
                    review.toReviewItem().apply {
                        userName = "Владимир Иванов"
                    }
                })
            }
        }

        initListeners()
    }

    private fun initListeners() {
        btnMap.setOnClickListener {
            (requireActivity() as MainActivity).clearFragments()
            (requireActivity() as MainActivity).navigateToFragment(MapFragment())
        }
        btnInfo.setOnClickListener { showRestaurantInfo() }
        btnMenu.setOnClickListener { (requireActivity() as MainActivity).navigateToFragmentAndAddToStack(MenuFragment.newInstance(restaurant?.dishes?.toMutableList())) }
    }

    private fun setReviewList(reviews: List<ReviewItem>) {
        reviewRecyclerView.adapter = ReviewListAdapter(reviews)
    }

    private fun showRestaurantInfo() {
        val dialogView = layoutInflater.inflate(R.layout.restaurant_info_dialog, null)
        val alertDialog = AlertDialog.Builder(requireContext()).setView(dialogView).create()
        alertDialog.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.restaurant_info_background
            )
        )
        with(dialogView) {
            restaurant?.let {
                it.workHours?.let { workHours ->
                    workHoursRecyclerView.adapter = WorkHourListAdapter(workHours)
                }
                txtPhone.text = it.phone
                txtAddress.text = it.address
            }
        }
        alertDialog.show()
    }

    companion object {

        private const val RESTAURANT_KEY = "restaurant_key"

        @JvmStatic
        fun newInstance(restaurant: Restaurant) =
            SingleRestaurantFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(RESTAURANT_KEY, restaurant)
                }
            }
    }
}
