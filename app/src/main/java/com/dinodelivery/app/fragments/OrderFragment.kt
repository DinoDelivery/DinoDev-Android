package com.dinodelivery.app.fragments


import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dinodelivery.app.R
import com.dinodelivery.app.adapters.OrderDividerDecoration
import com.dinodelivery.app.adapters.OrderListAdapter
import com.dinodelivery.app.database.entities.OrderEntity
import com.dinodelivery.app.viewmodels.OrderViewModel
import kotlinx.android.synthetic.main.fragment_order.*
import kotlinx.android.synthetic.main.order_dialog.view.*
import kotlinx.android.synthetic.main.rate_delivery_dialog.view.*


class OrderFragment : Fragment() {

    private val orderViewModel: OrderViewModel by lazy {
        ViewModelProviders.of(requireActivity()).get(OrderViewModel::class.java)
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
            if(it.isNullOrEmpty()) {
                imgEmptyList.visibility = View.VISIBLE
            }else{
                imgEmptyList.visibility = View.GONE
            }
            setOrderList(it)
        })

        orderViewModel.orderChanged.observe(viewLifecycleOwner, Observer{
            orderViewModel.getOrders()
        })
    }

    private fun setOrderList(orders: List<OrderEntity>) {
        orderRecyclerView.adapter =
            OrderListAdapter(orders, object : OrderListAdapter.OrderClickListener {
                override fun onOrderClick(order: OrderEntity) {
                    showOrderDetails(order)
                }
            })
        orderRecyclerView.addItemDecoration(OrderDividerDecoration())
    }

    private fun showOrderDetails(order: OrderEntity) {
        val dialogView = layoutInflater.inflate(R.layout.order_dialog, null)
        val alertDialog = AlertDialog.Builder(requireContext()).setView(dialogView).create()
        alertDialog.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.dialog_background
            )
        )
        with(dialogView) {
            txtOrderItemCount.text = getString(R.string.order_item_count, order.itemCount)
            txtOrderAddress.text = getString(R.string.order_delivery_address, order.deliveryAddress)
            txtOrderTime.text = getString(R.string.order_delivery_time, order.deliveryTime)
            txtOrderPrice.text = getString(R.string.order_price, order.price?.round())
            btnNotDelivered.setOnClickListener { alertDialog.dismiss() }
            btnDelivered.setOnClickListener {
                orderViewModel.deleteOrder(order.id)
                showRateDialog()
                alertDialog.dismiss()
            }
        }
        alertDialog.show()
    }

    private fun showRateDialog() {
        val dialogView = layoutInflater.inflate(R.layout.rate_delivery_dialog, null)
        val alertDialog = AlertDialog.Builder(requireContext()).setView(dialogView).create()
        alertDialog.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.custom_dialog_background
            )
        )
        with(dialogView) {
            ratingSpinner.adapter =
                ArrayAdapter<Int>(context, R.layout.rating_spinner_item, arrayOf(1, 2, 3, 4, 5))
            ratingSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Log.d(TAG, "${parent?.getItemAtPosition(position)}")
                }

            }
            btnRateDelivery.setOnClickListener { alertDialog.dismiss() }
        }
        alertDialog.show()
    }

    private fun Double.round(decimals: Int = 2): String = "%.${decimals}f".format(this)

    companion object {
        private val TAG = OrderFragment::class.java.simpleName
    }
}
