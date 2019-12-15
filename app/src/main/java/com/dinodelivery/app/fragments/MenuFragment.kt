package com.dinodelivery.app.fragments


import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dinodelivery.app.R
import com.dinodelivery.app.adapters.DishListAdapter
import com.dinodelivery.app.entities.Dish
import kotlinx.android.synthetic.main.fragment_menu.*


class MenuFragment : Fragment() {

    private var dishes: List<Dish>? = ArrayList()

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
    }

    private fun setDishes(dishes: List<Dish>) {
        menuRecyclerView.adapter =
            DishListAdapter(dishes, object : DishListAdapter.DishClickListener {
                override fun onDishClick(dish: Dish) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDishSelect(dish: Dish) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
    }

    companion object {

        private const val MENU_DISHES = "menu_dishes"

        @JvmStatic
        fun newInstance(dishes: MutableList<Dish>?) =
            MenuFragment().apply {
                arguments = Bundle().apply {
                    dishes?.let {
                        putParcelableArrayList(MENU_DISHES, it as java.util.ArrayList<out Parcelable>)
                    }
                }
            }
    }
}
