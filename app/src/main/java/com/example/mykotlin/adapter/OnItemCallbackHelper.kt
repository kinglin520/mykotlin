package com.example.mykotlin.adapter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class OnItemCallbackHelper : ItemTouchHelper.Callback {

    lateinit var flowLayoutAdapter: FlowLayoutAdapter

    constructor(flowLayoutAdapter: FlowLayoutAdapter) : super() {
        this.flowLayoutAdapter = flowLayoutAdapter
    }

    /**
     * 是否删除
     *
     * @return
     */
    override fun isItemViewSwipeEnabled(): Boolean {
        return false
    }

    /**
     * 是否拖住
     *
     * @return
     */
    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder): Int {
        var dragFlags: Int?
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager || layoutManager is StaggeredGridLayoutManager) {
            dragFlags =
                ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END
        } else {
            dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        }
        var swipeFlag = 0
        return makeMovementFlags(dragFlags, swipeFlag)
    }


    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder): Boolean {
        var touchPosition = viewHolder.adapterPosition
        var targetPosition = target.adapterPosition
        // 我的频道 前3条目不能操作
        if (touchPosition > 2 && targetPosition > 2 && viewHolder is FlowLayoutAdapter.ViewHolderMy) {
            flowLayoutAdapter.onMove(touchPosition, targetPosition)

        }
        return true
    }


    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    }
}