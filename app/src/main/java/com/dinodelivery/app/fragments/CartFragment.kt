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
import com.dinodelivery.app.adapters.CartItemListAdapter
import com.dinodelivery.app.adapters.OrderDividerDecoration
import com.dinodelivery.app.database.entities.DishEntity
import com.dinodelivery.app.viewmodels.CartViewModel
import kotlinx.android.synthetic.main.fragment_cart.*


class CartFragment : Fragment() {

    private var orderPrice: Double = 0.0
    private var itemQuantity = 0

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

        initListeners()

        initObservers()
    }

    private fun initListeners() {
        btnMap.setOnClickListener {
            (requireActivity() as MainActivity).clearFragments()
            (requireActivity() as MainActivity).navigateToFragment(MapFragment())
        }

        btnMakeOrder.setOnClickListener {
            (requireActivity() as MainActivity).navigateToFragment(
                CreateOrderFragment.newInstance(orderPrice, itemQuantity)
            )
        }
    }

    private fun initObservers() {
        cartViewModel.cartItems.observe(viewLifecycleOwner, Observer {

            if(it.isNullOrEmpty()) {
                btnMakeOrder.isEnabled = false
            }

            itemQuantity = getItemQuantity(it)

            orderPrice = getPrice(it)
            txtOrderPrice.text = getString(R.string.order_cart_price, orderPrice.round())

            cartItemsRecyclerView.adapter =
                CartItemListAdapter(it, object : CartItemListAdapter.CartItemClickListener {
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

    private fun getPrice(cartItems: List<Pair<DishEntity, Int>>): Double {
        return cartItems.map { it.first.price * it.second }.sum()
    }

    private fun getItemQuantity(cartItems: List<Pair<DishEntity, Int>>): Int {
        return cartItems.map { it.second }.sum()
    }

    private fun Double.round(decimals: Int = 2): String = "%.${decimals}f".format(this)

}
