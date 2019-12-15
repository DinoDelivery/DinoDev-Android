package com.dinodelivery.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.StringSignature
import com.dinodelivery.app.R
import com.dinodelivery.app.entities.Dish
import kotlinx.android.synthetic.main.dish_item.view.*

class DishListAdapter(
    private val dishes: List<Dish>,
    private val onDishClickListener: DishClickListener
) :
    RecyclerView.Adapter<DishListAdapter.DishViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val itemLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.dish_item, parent, false)
        return DishViewHolder(itemLayout)
    }

    override fun getItemCount(): Int = dishes.size

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        holder.onBind(dishes[position])
    }

    private fun Double.round(decimals: Int = 2): String = "%.${decimals}f".format(this)

    inner class DishViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(dish: Dish) {
            dish.photo?.let {
                Glide.with(itemView.context)
                    .load(it)
                    .placeholder(R.drawable.photo_placeholder)
                    .error(R.drawable.photo_placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .signature(StringSignature(it))
                    .dontAnimate()
                    .into(itemView.imgDishPhoto)
            }
            itemView.txtDishName.text = dish.name
            itemView.txtDishPrice.text = itemView.context.getString(R.string.price_template, dish.price.round())
            itemView.btnAddToCart.setOnClickListener { onDishClickListener.onDishSelect(dish) }

            itemView.setOnClickListener { onDishClickListener.onDishClick(dish) }
        }
    }

    interface DishClickListener {
        fun onDishClick(dish: Dish)
        fun onDishSelect(dish: Dish)
    }

}