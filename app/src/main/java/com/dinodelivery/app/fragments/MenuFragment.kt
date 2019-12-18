package com.dinodelivery.app.fragments


import android.app.AlertDialog
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dinodelivery.app.MainActivity
import com.dinodelivery.app.R
import com.dinodelivery.app.adapters.DishListAdapter
import com.dinodelivery.app.adapters.OrderDividerDecoration
import com.dinodelivery.app.adapters.SortItemListAdapter
import com.dinodelivery.app.entities.Dish
import com.dinodelivery.app.entities.SortItem
import com.dinodelivery.app.viewmodels.MenuViewModel
import kotlinx.android.synthetic.main.dish_sort_dialog.view.*
import kotlinx.android.synthetic.main.dishes_filter_dialog.view.*
import kotlinx.android.synthetic.main.fragment_menu.*


class MenuFragment : Fragment() {

    private var dishes: List<Dish>? = ArrayList()

    private val menuViewModel: MenuViewModel by lazy {
        ViewModelProviders.of(this).get(MenuViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dishes = it.getParcelableArrayList(MENU_DISHES)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dishes?.let {
            setDishes(it)
        }

        initListeners()

        initObservers()
    }

    private fun setDishes(dishes: List<Dish>) {
        menuRecyclerView.adapter =
            DishListAdapter(dishes, object : DishListAdapter.DishClickListener {
                override fun onDishClick(dish: Dish) {
                    (requireActivity() as MainActivity).navigateToFragmentAndAddToStack(
                        DishFragment.newInstance(
                            dish
                        )
                    )
                }

                override fun onDishSelect(dish: Dish) {
                    menuViewModel.addDishToCart(dish)
                }
            })
    }

    private fun initListeners() {
        btnSort.setOnClickListener { showSortDialog() }

        btnSort.setOnLongClickListener {
            dishes?.let { setDishes(it) }
            true
        }


        btnFilter.setOnClickListener { showFilterDialog() }

        btnFilter.setOnLongClickListener {
            dishes?.let { setDishes(it) }
            true
        }
    }

    private fun initObservers() {
        menuViewModel.message.observe(viewLifecycleOwner, Observer { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        })
    }

    private fun showSortDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dish_sort_dialog, null)
        val alertDialog = AlertDialog.Builder(requireContext()).setView(dialogView).create()
        alertDialog.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.dialog_background
            )
        )
        var sortedDishes = dishes

        with(dialogView) {
            sortCriteriaRecyclerView.adapter = SortItemListAdapter(
                menuViewModel.getSortItemList(),
                object : SortItemListAdapter.SortItemClickListener {
                    override fun onSortItemSelected(sortItem: SortItem) {
                        sortedDishes = when (sortItem.criteria) {
                            SortItem.SortCriteria.ALPHABET -> dishes?.sortedBy { it.name }
                            SortItem.SortCriteria.PRICE -> dishes?.sortedBy { it.price }
                            SortItem.SortCriteria.RATING -> dishes?.sortedByDescending { it.rating }
                            else -> dishes
                        }
                    }
                })

            sortCriteriaRecyclerView.addItemDecoration(OrderDividerDecoration())

            btnSort.setOnClickListener {
                sortedDishes?.let {
                    setDishes(it)
                }
                alertDialog.dismiss()
            }
        }

        alertDialog.show()
    }

    private fun showFilterDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dishes_filter_dialog, null)
        val alertDialog = AlertDialog.Builder(requireContext()).setView(dialogView).create()
        alertDialog.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.dishes_filter_background
            )
        )
        with(dialogView) {
            ratingSpinner.adapter =
                ArrayAdapter<Int>(context, R.layout.rating_spinner_item, arrayOf(1, 2, 3, 4, 5))
            typeSpinner.adapter = ArrayAdapter<String>(
                context,
                R.layout.rating_spinner_item,
                context.resources.getStringArray(R.array.dish_type)
            )
            cuisineSpinner.adapter = ArrayAdapter<String>(
                context,
                R.layout.rating_spinner_item,
                context.resources.getStringArray(R.array.cuisine)
            )
            btnFilterRestaurants.setOnClickListener {
                filterDishes(
                    ratingSpinner.selectedItem.toString().toInt(),
                    typeSpinner.selectedItem.toString(),
                    cuisineSpinner.selectedItem.toString()
                )
                alertDialog.dismiss()
            }
        }
        alertDialog.show()
    }

    private fun filterDishes(rating: Int?, dishType: String?, cuisine: String?) {
        dishes?.filter { it.rating >= rating ?: 0 }?.filter { it.dishType == menuViewModel.getDishType(dishType)?:it.dishType }?.filter {
            it.cuisine == menuViewModel.getCuisine(
                cuisine
            ) ?: it.cuisine
        }?.let {
            setDishes(it)
        }
    }

    companion object {

        private const val MENU_DISHES = "menu_dishes"

        @JvmStatic
        fun newInstance(dishes: MutableList<Dish>?) =
            MenuFragment().apply {
                arguments = Bundle().apply {
                    dishes?.let {
                        putParcelableArrayList(
                            MENU_DISHES,
                            it as java.util.ArrayList<out Parcelable>
                        )
                    }
                }
            }
    }
}
