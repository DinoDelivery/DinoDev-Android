package com.dinodelivery.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dinodelivery.app.DinoDeliveryApp
import com.dinodelivery.app.R
import com.dinodelivery.app.entities.WorkHour
import kotlinx.android.synthetic.main.work_hour_item.view.*
import java.util.*

class WorkHourListAdapter(private val workHours: List<WorkHour>) :
    RecyclerView.Adapter<WorkHourListAdapter.WorkHourViewHolder>() {

    val workDays: Array<String> = DinoDeliveryApp.context.resources.getStringArray(R.array.work_days)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkHourViewHolder {

        val itemLayoutView = LayoutInflater.from(parent.context)
            .inflate(R.layout.work_hour_item, parent, false)
        return WorkHourViewHolder(itemLayoutView)

    }

    override fun getItemCount(): Int = workHours.size

    override fun onBindViewHolder(holder: WorkHourViewHolder, position: Int) {
        holder.onBind(workHours[position])
    }

    inner class WorkHourViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(item: WorkHour) = with(item) {
            val day = when (item.day) {
                Calendar.SUNDAY -> workDays[Calendar.SUNDAY - 1]
                Calendar.MONDAY -> workDays[Calendar.MONDAY - 1]
                Calendar.TUESDAY -> workDays[Calendar.TUESDAY - 1]
                Calendar.WEDNESDAY -> workDays[Calendar.WEDNESDAY - 1]
                Calendar.THURSDAY -> workDays[Calendar.THURSDAY - 1]
                Calendar.FRIDAY -> workDays[Calendar.FRIDAY - 1]
                Calendar.SATURDAY -> workDays[Calendar.SATURDAY - 1]
                WorkHour.WEEKDAY -> workDays[WorkHour.WEEKDAY - 1]
                WorkHour.WEEKEND -> workDays[WorkHour.WEEKEND - 1]
                else -> ""
            }
            itemView.txtWorkHour.text = "$day $openHour-$closeHour"
        }
    }

}