package com.dinodelivery.app.fragments


import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dinodelivery.app.R
import com.dinodelivery.app.adapters.OrderDividerDecoration
import com.dinodelivery.app.adapters.OrderListAdapter
import com.dinodelivery.app.entities.Order
import com.dinodelivery.app.viewmodels.OrderViewModel
import kotlinx.android.synthetic.main.fragment_order.*


class OrderFragment : Fragment() {

    private val orderViewModel: OrderViewModel by lazy {
        ViewModelProviders.of(this).get(OrderViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(view) {
            isFocusableInTouchMode = true
            requestFocus()
            setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
                    fragmentManager?.popBackStack()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        initObservers()

        orderViewModel.getOrders()

    }

    private fun initObservers() {
        orderViewModel.orders.observe(viewLifecycleOwner, Observer {
            setOrderList(it)
        })
    }

    private fun setOrderList(orders: List<Order>) {
        orderRecyclerView.adapter = OrderListAdapter(orders)
        orderRecyclerView.addItemDecoration(OrderDividerDecoration())
    }
}
