package com.dinodelivery.app.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.StringSignature
import com.dinodelivery.app.R
import com.dinodelivery.app.entities.Restaurant
import com.dinodelivery.app.entities.WorkHour
import kotlinx.android.synthetic.main.restaurant_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class RestaurantListAdapter(
    private val restaurants: List<Restaurant>,
    private val onRestaurantClickListener: RestaurantClickListener
) :
    RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val itemLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.restaurant_item, parent, false)
        return RestaurantViewHolder(itemLayout)
    }

    override fun getItemCount(): Int = restaurants.size

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.onBind(restaurants[position])
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("H:mm")
        return sdf.format(Calendar.getInstance().time)
    }

    private fun isOpen(openHour: String, closeHour: String): Boolean {
        return getCurrentTime() in openHour..closeHour
    }

    inner class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(restaurant: Restaurant) {
            restaurant.photo?.let {
                Glide.with(itemView.context)
                    .load(it)
                    .placeholder(R.drawable.photo_placeholder)
                    .error(R.drawable.photo_placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .signature(StringSignature(it))
                    .dontAnimate()
                    .into(itemView.imgRestaurantPhoto)
            }
            itemView.txtRestaurantName.text = restaurant.name
            restaurant.workHours?.let { workHours ->
                val day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
                val dayInWeek = when (day) {
                    1, 7 -> WorkHour.WEEKEND
                    else -> WorkHour.WEEKDAY
                }
                workHours.forEach {
                    if (it.day == day || it.day == dayInWeek) {
                        if (isOpen(it.openHour!!, it.closeHour!!)) {
                            itemView.txtWorkStatus.text = itemView.context.getString(R.string.open)
                            itemView.txtWorkStatus.setTextColor(
                                itemView.context.resources.getColor(
                                    R.color.status_green
                                )
                            )
                        } else {
                            itemView.txtWorkStatus.text =
                                itemView.context.getString(R.string.closed)
                            itemView.txtWorkStatus.setTextColor(
                                itemView.context.resources.getColor(
                                    R.color.status_red
                                )
                            )
                        }
                    }
                }
            }
            itemView.txtRating.text = restaurant.rating.toString()
            itemView.setOnClickListener { onRestaurantClickListener.onRestaurantClick(restaurant) }
        }
    }

    interface RestaurantClickListener {
        fun onRestaurantClick(restaurant: Restaurant)
    }

}