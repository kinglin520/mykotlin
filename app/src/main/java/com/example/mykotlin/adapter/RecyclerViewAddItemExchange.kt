package com.example.mykotlin.adapter

import com.example.mykotlin.bean.TagBean

open class RecyclerViewAddItemExchange {

    private var position: Int
    private val mFlowLayoutAdapter: FlowLayoutAdapter
    private val addPinDaos: MutableList<TagBean>

    constructor(position: Int,
        mFlowLayoutAdapter: FlowLayoutAdapter,
        addPinDaos: MutableList<TagBean>) {
        this.position = position
        this.mFlowLayoutAdapter = mFlowLayoutAdapter
        this.addPinDaos = addPinDaos
        itemExchange();
    }

    private fun itemExchange() {
        val tagBean = addPinDaos[position]
        var toposition = position

        if (tagBean.viewType == 1) {
            for (i in 2 until addPinDaos.size) {
                if (addPinDaos[i].viewType === 3) {
                    toposition = i
                    break
                }
            }
            tagBean.viewType = 3
            tagBean.myTag = 0
            addPinDaos.removeAt(position)
            mFlowLayoutAdapter.notifyItemRemoved(position)
            addPinDaos.add(toposition - 1, tagBean)
            mFlowLayoutAdapter.notifyItemInserted(toposition - 1)
        } else {
            addPinDaos[position].viewType = 1
            for (i in 2 until addPinDaos.size) {
                if (addPinDaos[i].viewType === 2) {
                    toposition = i
                    break
                }
            }
            tagBean.viewType = 1
            if (mFlowLayoutAdapter.getTag() === 1) {
                tagBean.myTag = 1
            }
            addPinDaos.removeAt(position)
            mFlowLayoutAdapter.notifyItemRemoved(position)
            addPinDaos.add(toposition, tagBean)
            mFlowLayoutAdapter.notifyItemInserted(toposition)
        }
    }
}