package com.dinodelivery.app.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.StringSignature
import com.dinodelivery.app.R
import com.dinodelivery.app.adapters.ReviewListAdapter
import com.dinodelivery.app.entities.Dish
import com.dinodelivery.app.entities.Ingredient
import com.dinodelivery.app.entities.ReviewItem
import com.dinodelivery.app.viewmodels.MenuViewModel
import kotlinx.android.synthetic.main.dish_info_dialog.view.*
import kotlinx.android.synthetic.main.fragment_dish.*


class DishFragment : Fragment() {

    private var dish: Dish? = null

    private val menuViewModel: MenuViewModel by lazy {
        ViewModelProviders.of(this).get(MenuViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dish = it.getParcelable(DISH_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dish, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dish?.let {
            toolbarHeader.text = it.name

            it.reviews?.let { reviews ->
                setReviewList(reviews.map { review ->
                    review.toReviewItem().apply {
                        userName = "Владимир Иванов"
                    }
                })
                txtReviewCount.text = getString(R.string.review_count, reviews.size)
            }
            it.photo?.let { photo ->
                Glide.with(requireContext())
                    .load(photo)
                    .placeholder(R.drawable.photo_placeholder)
                    .error(R.drawable.photo_placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .signature(StringSignature(photo))
                    .dontAnimate()
                    .into(imgDishPhoto)
            }
            txtRating.text = it.rating.toString()
            txtDishPrice.text = getString(R.string.price_template, it.price.round())
            txtDishWeight.text = getString(R.string.weight_template, it.weight)
        }

        initListeners()

        initObservers()
    }

    private fun setReviewList(reviews: List<ReviewItem>) {
        reviewRecyclerView.adapter = ReviewListAdapter(reviews)
    }

    private fun initListeners() {
        btnAddToCart.setOnClickListener {
            dish?.let {
                menuViewModel.addDishToCart(it)
            }
        }
        btnInfo.setOnClickListener {
            dish?.ingredients?.let {
                showDishInfo(it)
            }
        }
    }

    private fun initObservers() {
        menuViewModel.message.observe(viewLifecycleOwner, Observer { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        })
    }

    @SuppressLint("DefaultLocale")
    private fun showDishInfo(ingredients: List<Ingredient>) {
        var info = ""
        ingredients.forEachIndexed { index, ingredient ->
            info += if (index != 0) {
                ", ${ingredient.name?.toLowerCase()}"
            } else {
                ingredient.name
            }
        }
        val dialogView = layoutInflater.inflate(R.layout.dish_info_dialog, null)
        val alertDialog = AlertDialog.Builder(requireContext()).setView(dialogView).create()
        with(dialogView) {
            txtIngredients.text = info
        }
        alertDialog.show()
    }

    private fun Double.round(decimals: Int = 2): String = "%.${decimals}f".format(this)

    companion object {

        private const val DISH_KEY = "dish_key"

        @JvmStatic
        fun newInstance(dish: Dish) =
            DishFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(DISH_KEY, dish)
                }
            }
    }
}
