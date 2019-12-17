package com.dinodelivery.app.fragments


import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.dinodelivery.app.MainActivity
import com.dinodelivery.app.R
import com.dinodelivery.app.utils.UserCacheUtils
import kotlinx.android.synthetic.main.fragment_cart.btnMap
import kotlinx.android.synthetic.main.fragment_cart.txtOrderPrice
import kotlinx.android.synthetic.main.fragment_create_order.*
import java.text.SimpleDateFormat
import java.util.*

class CreateOrderFragment : Fragment() {

    private var orderPrice: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            orderPrice = it.getString(ORDER_PRICE_KEY)
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
        txtOrderPrice.text = orderPrice
        UserCacheUtils.cachedUserData?.let {
            fUserName.setText(it.username)
            fPhone.setText(it.phone)
        }
        paymentSpinner.adapter =
            ArrayAdapter<String>(requireContext(), R.layout.rating_spinner_item, requireContext().resources.getStringArray(R.array.payment))
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
        btnMap.setOnClickListener {
            (requireActivity() as MainActivity).clearFragments()
            (requireActivity() as MainActivity).navigateToFragment(MapFragment())
        }
        fTime.setOnClickListener { setTime() }
    }

    private fun setTime() {
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            fTime.setText(SimpleDateFormat("HH:mm").format(cal.time))
        }
        TimePickerDialog(requireContext(), timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
    }

    companion object {
        private const val ORDER_PRICE_KEY = "order_price_key"

        private val TAG = CreateOrderFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(price: String) =
            CreateOrderFragment().apply {
                arguments = Bundle().apply {
                    putString(ORDER_PRICE_KEY, price)
                }
            }
    }
}
