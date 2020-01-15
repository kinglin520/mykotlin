package com.example.mykotlin

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.mykotlin.adapter.FlowLayoutAdapter
import com.example.mykotlin.adapter.OnItemCallbackHelper
import com.example.mykotlin.adapter.RecyclerViewAddItemExchange
import com.example.mykotlin.adapter.RecyclerViewAddItemOnClickListener
import com.example.mykotlin.bean.TagBean
import kotlinx.android.synthetic.main.activity_slide_layout.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private val mY: Float = 0.toFloat()
    internal var list: MutableList<TagBean> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slide_layout)
        initData()
        initView()

    }

    private fun initData() {
        for (i in 0..3) {
            if (i == 1) {
                for (j in 0..9) {
                    val bean = TagBean("选中$j", 1, 0)
                    list.add(bean)
                }
            }
            val bean = TagBean("wq$i", i, 0)
            list.add(bean)
        }
        for (i in 0..9) {
            val bean = TagBean("未选中$i", 3, 0)
            list.add(bean)
        }
    }


    private fun initView() {
        val gridLayoutManager = GridLayoutManager(this.applicationContext, 4)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val tagBean = list[position]
                return if (tagBean.viewType === 0 || tagBean.viewType === 2) {
                    4
                } else {
                    1
                }
            }
        }

        val flowLayoutAdapter = FlowLayoutAdapter(this, list)
        rv_list.itemAnimator = DefaultItemAnimator()

        val onItemCallbackHelper = OnItemCallbackHelper(flowLayoutAdapter)
        val itemTouchHelper = ItemTouchHelper(onItemCallbackHelper)
        itemTouchHelper.attachToRecyclerView(rv_list)
        flowLayoutAdapter.setItemTouchHelper(itemTouchHelper)

        rv_list.layoutManager = gridLayoutManager

        rv_list.addOnItemTouchListener(
            RecyclerViewAddItemOnClickListener(this,
                object : RecyclerViewAddItemOnClickListener.OnItemClickListener {
                    override fun onItemCilck(view: View, position: Int) {
                        Log.e("---", "点击了$position")
                        val tagbean = list[position]
                        if (flowLayoutAdapter != null && flowLayoutAdapter.getTag() === 0
                            && (tagbean.viewType === 1 || position < 3)
                        ) {
                            Toast.makeText(baseContext, "点击了$position", Toast.LENGTH_SHORT).show()
                        } else if (tagbean.viewType == 2) {
                        } else {
                            RecyclerViewAddItemExchange(position, flowLayoutAdapter, list)
                        }
                    }

                })
        )
        rv_list.adapter = flowLayoutAdapter
    }


}
