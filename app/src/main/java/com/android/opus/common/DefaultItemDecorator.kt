package com.android.opus.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class DefaultItemDecorator(private val verticalSpacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = verticalSpacing
    }
}
