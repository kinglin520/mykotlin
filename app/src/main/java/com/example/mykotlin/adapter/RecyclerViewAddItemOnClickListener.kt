package com.example.mykotlin.adapter

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class RecyclerViewAddItemOnClickListener : RecyclerView.OnItemTouchListener {

    interface OnItemClickListener {
        fun onItemCilck(view: View, position: Int)
    }

    private var onItemClickListener: OnItemClickListener
    private var gestureDetector: GestureDetector

    constructor(context: Context, onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
        gestureDetector =
            GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent?): Boolean {
                    return true
                }
//                override fun onLongPress(e: MotionEvent) {
//                    super.onLongPress(e)
//                }
            })
    }


    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        var view: View? = rv.findChildViewUnder(e.getX(), e.getY())
        if (view != null && onItemClickListener != null && gestureDetector.onTouchEvent(e)
            && rv.getChildPosition(view) >= 1
        ) {
            onItemClickListener.onItemCilck(view, rv.getChildPosition(view))
        }
        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
    }
}