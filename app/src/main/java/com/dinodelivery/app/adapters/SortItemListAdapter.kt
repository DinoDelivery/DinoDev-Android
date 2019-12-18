package com.dinodelivery.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dinodelivery.app.R
import com.dinodelivery.app.entities.SortItem
import kotlinx.android.synthetic.main.sort_item.view.*

class SortItemListAdapter(
    private val sortItems: List<SortItem>,
    private val onSortItemClickListener: SortItemClickListener
) :
    RecyclerView.Adapter<SortItemListAdapter.SortItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SortItemViewHolder {
        val itemLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.sort_item, parent, false)
        return SortItemViewHolder(itemLayout)
    }

    override fun getItemCount(): Int = sortItems.size

    override fun onBindViewHolder(holder: SortItemViewHolder, position: Int) {
        holder.onBind(sortItems[position])
    }

    inner class SortItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(sortItem: SortItem) {
            itemView.txtSortTitle.text = sortItem.title
            if(sortItem.selected) {
                itemView.imgSortSelected.setImageDrawable(itemView.context.resources.getDrawable(R.drawable.ic_sort_selected))
            }else{
                itemView.imgSortSelected.setImageDrawable(itemView.context.resources.getDrawable(R.drawable.ic_sort))
            }
            itemView.setOnClickListener {
                sortItems.forEach { it.selected = false }
                sortItem.selected = true
                notifyDataSetChanged()
                onSortItemClickListener.onSortItemSelected(sortItem)
            }
        }
    }

    interface SortItemClickListener {
        fun onSortItemSelected(sortItem: SortItem)
    }

}