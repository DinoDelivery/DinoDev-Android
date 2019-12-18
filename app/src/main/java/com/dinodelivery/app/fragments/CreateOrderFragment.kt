package com.dinodelivery.app.fragments


import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.dinodelivery.app.MainActivity
import com.dinodelivery.app.R
import com.dinodelivery.app.database.entities.OrderEntity
import com.dinodelivery.app.utils.UserCacheUtils
import com.dinodelivery.app.viewmodels.OrderViewModel
import kotlinx.android.synthetic.main.cart_order_dialog.view.*
import kotlinx.android.synthetic.main.fragment_cart.btnMap
import kotlinx.android.synthetic.main.fragment_cart.txtOrderPrice
import kotlinx.android.synthetic.main.fragment_create_order.*
import java.text.SimpleDateFormat
import java.util.*

class CreateOrderFragment : Fragment() {

    private var orderPrice: Double = 0.0

    private var orderItemQuantity: Int = 0

    private val orderViewModel: OrderViewModel by lazy {
        ViewModelProviders.of(requireActivity()).get(OrderViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            orderPrice = it.getDouble(ORDER_PRICE_KEY)
            orderItemQuantity = it.getInt(ORDER_ITEM_QUANTITY_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setInitialData()

        initListeners()
    }

    private fun setInitialData() {
        txtOrderPrice.text = getString(R.string.order_cart_price, orderPrice.round())
        UserCacheUtils.cachedUserData?.let {
            fUserName.setText(it.username)
            fPhone.setText(it.phone)
        }
        paymentSpinner.adapter =
            ArrayAdapter<String>(
                requireContext(),
                R.layout.rating_spinner_item,
                requireContext().resources.getStringArray(R.array.payment)
            )
        paymentSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
    }

    private fun initListeners() {
        btnMap.setOnClickListener { goToMapFragment() }
        fTime.setOnClickListener { setTime() }
        btnMakeOrder.setOnClickListener {
            if (fAddress.text.isNullOrBlank() ||
                fTime.text.isNullOrBlank() ||
                fUserName.text.isNullOrBlank() ||
                fPhone.text.isNullOrBlank()
            ) {
                Toast.makeText(requireContext(), getString(R.string.enter_order_info), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            orderViewModel.saveOrder(
                OrderEntity(
                    orderItemQuantity,
                    fAddress.text.toString(),
                    fTime.text.toString(),
                    orderPrice
                )
            )
            showThankDialog()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun setTime() {
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            fTime.setText(SimpleDateFormat("HH:mm").format(cal.time))
        }
        TimePickerDialog(
            requireContext(),
            timeSetListener,
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun showThankDialog() {
        val dialogView = layoutInflater.inflate(R.layout.cart_order_dialog, null)
        val alertDialog = AlertDialog.Builder(requireContext()).setView(dialogView).create()
        alertDialog.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.custom_dialog_background
            )
        )
        dialogView.btnGoToMain.setOnClickListener {
            goToMapFragment()
            alertDialog.dismiss()
        }
        alertDialog.show()

    }

    private fun goToMapFragment() {
        (requireActivity() as MainActivity).clearFragments()
        (requireActivity() as MainActivity).navigateToFragment(MapFragment())
    }

    private fun Double.round(decimals: Int = 2): String = "%.${decimals}f".format(this)

    companion object {
        private const val ORDER_PRICE_KEY = "order_price_key"
        private const val ORDER_ITEM_QUANTITY_KEY = "order_item_quantity_key"

        private val TAG = CreateOrderFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(price: Double, orderItemQuantity: Int) =
            CreateOrderFragment().apply {
                arguments = Bundle().apply {
                    putDouble(ORDER_PRICE_KEY, price)
                    putInt(ORDER_ITEM_QUANTITY_KEY, orderItemQuantity)
                }
            }
    }
}
