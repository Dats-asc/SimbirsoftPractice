package com.example.simbirsoftpracticeapp.search

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.example.simbirsoftpracticeapp.R


class CustomItemDecorator(context: Context) : RecyclerView.ItemDecoration() {

    private val customDecorator = ContextCompat.getDrawable(context, R.drawable.item_decorator)

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        parent.adapter?.let { adapter ->
            parent.children
                .forEach { view ->
                    val childAdapterPosition = parent.getChildAdapterPosition(view)
                        .let { if (it == RecyclerView.NO_POSITION) return else it }

                    customDecorator?.drawSeparator(view, parent, c)
                }
        }
        super.onDraw(c, parent, state)
    }

    private fun Drawable.drawSeparator(view: View, parent: RecyclerView, canvas: Canvas) =
        apply {
            val left = view.right
            val top = parent.paddingTop
            val right = left + intrinsicWidth
            val bottom = top + intrinsicHeight - parent.paddingBottom
            bounds = Rect(left, top, right, bottom)
            draw(canvas)
        }

}