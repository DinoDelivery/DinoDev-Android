package com.dinodelivery.app.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dinodelivery.app.R
import com.dinodelivery.app.adapters.CartItemListAdapter
import com.dinodelivery.app.adapters.OrderDividerDecoration
import com.dinodelivery.app.database.entities.DishEntity
import com.dinodelivery.app.viewmodels.CartViewModel
import kotlinx.android.synthetic.main.fragment_cart.*


class CartFragment : Fragment() {

    private val cartViewModel: CartViewModel by lazy {
        ViewModelProviders.of(this).get(CartViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartViewModel.getCartItems()
        initObservers()
    }

    private fun initObservers() {
        cartViewModel.cartItems.observe(viewLifecycleOwner, Observer {

            txtOrderPrice.text = getString(R.string.order_cart_price, getPrice(it))

            cartItemsRecyclerView.adapter = CartItemListAdapter(it, object: CartItemListAdapter.CartItemClickListener {
                override fun onCartItemAdded(dish: DishEntity) {
                    cartViewModel.addCartItem(dish)
                }

                override fun onCartItemRemoved(dish: DishEntity) {
                    cartViewModel.deleteCartItem(dish)
                }

            })
            cartItemsRecyclerView.addItemDecoration(OrderDividerDecoration())
        })

        cartViewModel.item.observe(viewLifecycleOwner, Observer {
            cartViewModel.getCartItems()
        })
    }

    private fun getPrice(cartItems: List<Pair<DishEntity, Int>>): String {
        return cartItems.map { it.first.price * it.second }.sum().round()
    }

    private fun Double.round(decimals: Int = 2): String = "%.${decimals}f".format(this)

}
