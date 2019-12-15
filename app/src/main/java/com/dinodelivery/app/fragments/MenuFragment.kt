package com.dinodelivery.app.fragments


import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dinodelivery.app.MainActivity
import com.dinodelivery.app.R
import com.dinodelivery.app.adapters.DishListAdapter
import com.dinodelivery.app.entities.Dish
import com.dinodelivery.app.viewmodels.MenuViewModel
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

        initObservers()
    }

    private fun setDishes(dishes: List<Dish>) {
        menuRecyclerView.adapter =
            DishListAdapter(dishes, object : DishListAdapter.DishClickListener {
                override fun onDishClick(dish: Dish) {
                    (requireActivity() as MainActivity).navigateToFragmentAndAddToStack(DishFragment.newInstance(dish))
                }

                override fun onDishSelect(dish: Dish) {
                    menuViewModel.addDishToCart(dish)
                }
            })
    }

    private fun initObservers() {
        menuViewModel.message.observe(viewLifecycleOwner, Observer { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        })
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
