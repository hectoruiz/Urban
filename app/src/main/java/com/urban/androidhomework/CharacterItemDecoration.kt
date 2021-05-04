package com.urban.androidhomework

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

class CharacterItemDecoration(context: Context, orientation: Int) : DividerItemDecoration(context, orientation) {

    companion object {
        const val PADDING = 30
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = PADDING
        outRect.bottom = PADDING
    }
}