package com.dinodelivery.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.StringSignature
import com.dinodelivery.app.R
import com.dinodelivery.app.database.entities.DishEntity
import kotlinx.android.synthetic.main.cart_order_item.view.*

class CartItemListAdapter (
    private val cartItems: List<Pair<DishEntity, Int>>,
    private val onCartItemClickListener: CartItemClickListener
) :
    RecyclerView.Adapter<CartItemListAdapter.CartItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val itemLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.cart_order_item, parent, false)
        return CartItemViewHolder(itemLayout)
    }

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        holder.onBind(cartItems[position])
    }

    private fun Double.round(decimals: Int = 2): String = "%.${decimals}f".format(this)

    inner class CartItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(cartItem: Pair<DishEntity, Int>) {
            with(cartItem.first) {
                this.photo?.let {
                    Glide.with(itemView.context)
                        .load(it)
                        .placeholder(R.drawable.photo_placeholder)
                        .error(R.drawable.photo_placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .signature(StringSignature(it))
                        .dontAnimate()
                        .into(itemView.imgCartItemPhoto)
                }
                itemView.txtCartItemName.text = this.name
                itemView.txtCartItemPrice.text = itemView.context.getString(R.string.price_template, this.price.round())
                itemView.btnAddItem.setOnClickListener { onCartItemClickListener.onCartItemAdded(this) }
                itemView.btnRemoveItem.setOnClickListener { onCartItemClickListener.onCartItemRemoved(this) }
                itemView.txtItemQuantity.text = cartItem.second.toString()
            }

        }
    }

    interface CartItemClickListener {
        fun onCartItemAdded(dish: DishEntity)
        fun onCartItemRemoved(dish: DishEntity)
    }

}