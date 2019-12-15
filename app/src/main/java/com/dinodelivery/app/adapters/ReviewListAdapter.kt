package com.dinodelivery.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dinodelivery.app.R
import com.dinodelivery.app.entities.ReviewItem
import kotlinx.android.synthetic.main.review_item.view.*

class ReviewListAdapter(private val reviews: List<ReviewItem>) :
    RecyclerView.Adapter<ReviewListAdapter.ReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {

        val itemLayoutView = LayoutInflater.from(parent.context)
            .inflate(R.layout.review_item, parent, false)
        return ReviewViewHolder(itemLayoutView)

    }

    override fun getItemCount(): Int = reviews.size

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.onBind(reviews[position])
    }

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(item: ReviewItem) = with(item) {

            this.userPhoto?.let { photo ->
                Glide.with(itemView.context)
                    .load(photo)
                    .placeholder(R.drawable.user_photo_placeholder)
                    .error(R.drawable.user_photo_placeholder)
                    .diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.ALL)
                    .signature(com.bumptech.glide.signature.StringSignature(photo))
                    .dontAnimate()
                    .into(itemView.imgUserPhoto)
            }

            itemView.txtUserName.text = this.userName
            itemView.txtReviewBody.text = this.review.reviewText
        }
    }

}