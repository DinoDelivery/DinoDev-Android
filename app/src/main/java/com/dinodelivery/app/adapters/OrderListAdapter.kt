package com.dinodelivery.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dinodelivery.app.R
import com.dinodelivery.app.database.entities.OrderEntity
import kotlinx.android.synthetic.main.order_item.view.*

class OrderListAdapter(private val orders: List<OrderEntity>, private val onOrderClickListener: OrderClickListener) :
    RecyclerView.Adapter<OrderListAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val itemLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.order_item, parent, false)
        return OrderViewHolder(itemLayout)
    }

    override fun getItemCount(): Int = orders.size

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.onBind(orders[position])
    }

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(order: OrderEntity) {
            itemView.txtOrder.text = itemView.context.getString(R.string.order_number, order.id)
            itemView.setOnClickListener { onOrderClickListener.onOrderClick(order) }
        }
    }

    interface OrderClickListener {
        fun onOrderClick(order: OrderEntity)
    }

}