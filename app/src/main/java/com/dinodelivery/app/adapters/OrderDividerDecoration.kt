package com.dinodelivery.app.adapters

import android.graphics.Canvas
import android.graphics.Paint
import android.util.TypedValue
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.dinodelivery.app.DinoDeliveryApp.Companion.context
import com.dinodelivery.app.R

class OrderDividerDecoration : RecyclerView.ItemDecoration() {

    private var paint: Paint = Paint().apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.divider_color)
    }
    private var heightDp = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        1.0f,
        context.resources.displayMetrics
    ).toInt()

    private var horizontalMargin = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        32.0f,
        context.resources.displayMetrics
    ).toInt()


    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        parent.children.forEach { view ->
            val position = parent.getChildAdapterPosition(view)
            parent.adapter?.let { adapter ->
                if (position != adapter.itemCount - 1) {
                    c.drawRect(
                        view.left.toFloat() + horizontalMargin,
                        view.bottom.toFloat(),
                        view.right.toFloat() - horizontalMargin,
                        view.bottom.toFloat() + heightDp,
                        paint
                    )
                }
            }
        }
    }
}