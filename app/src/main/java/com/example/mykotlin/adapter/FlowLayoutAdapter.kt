package com.example.mykotlin.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mykotlin.R
import com.example.mykotlin.bean.TagBean
import java.util.*

class FlowLayoutAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>,
    RecyclerViewAddItemCallBackListener {

    private lateinit var mContext: Context
    private lateinit var tagBeans: List<TagBean>
    private lateinit var itemTouchHelper: ItemTouchHelper
    private var tag = 0

    constructor(mContext: Context, tagBeans: List<TagBean>) {
        this.mContext = mContext
        this.tagBeans = tagBeans
    }

    fun setItemTouchHelper(mItemTouchHelper: ItemTouchHelper) {
        this.itemTouchHelper = mItemTouchHelper
    }

    fun getTag(): Int {
        return tag
    }

    fun setEditorTag() {
        if (tag == 0) {
            this.tag = 1
            for (i in tagBeans.indices) {
                if (tagBeans[i].viewType == 1) {
                    tagBeans[i].myTag = 1

                }
            }
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View
        var viewHolder: RecyclerView.ViewHolder

        when (viewType) {
            0 -> {
                view = LayoutInflater.from(mContext)
                    .inflate(R.layout.recyclerview_add_item_one, parent, false)
                viewHolder = ViewHolderOne(view)
            }
            1 -> {
                view = LayoutInflater.from(mContext)
                    .inflate(R.layout.recyclerview_add_item_my, parent, false)
                viewHolder = ViewHolderMy(view)
            }
            2 -> {
                view = LayoutInflater.from(mContext)
                    .inflate(R.layout.recyclerview_add_item_tow, parent, false)
                viewHolder = MyTitleViewHolder(view)
            }
            else -> {
                view = LayoutInflater.from(mContext)
                    .inflate(R.layout.recyclerview_add_item_pindao, parent, false)
                viewHolder = ViewHolderPinDao(view)
            }

        }
        return viewHolder
    }

    override fun onBindViewHolder(myViewHolder: RecyclerView.ViewHolder, position: Int) {
        var tagBean: TagBean = tagBeans.get(position)
        when (tagBean.viewType) {
            0 -> {
                val viewHolderOne = myViewHolder as ViewHolderOne
                if (tag == 1) {
                    viewHolderOne.textView_one.text = "完 成"
                    viewHolderOne.textView_tuodong.text = "拖动可以排序"
                } else {
                    viewHolderOne.textView_one.text = "编 辑"
                    viewHolderOne.textView_tuodong.text = ""
                }

                viewHolderOne.textView_one.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(p0: View?) {
                        if (tag == 0) {
                            viewHolderOne.textView_one.setText("完 成")
                            viewHolderOne.textView_tuodong.setText("拖动可以排序")
                            tag = 1
                            Log.e("----t2", "" + tag)
                            for (i in tagBeans.indices) {
                                if (tagBeans.get(i).viewType == 1) {
                                    tagBeans.get(i).myTag = 1
                                }
                            }
                            notifyDataSetChanged()
                        } else {
                            viewHolderOne.textView_one.setText("编 辑")
                            viewHolderOne.textView_tuodong.setText("")
                            tag = 0
                            Log.e("----t1", "" + tag)
                            for (i in tagBeans.indices) {
                                if (tagBeans.get(i).viewType == 1) {
                                    tagBeans.get(i).myTag = 0
                                }
                            }
                            notifyDataSetChanged()
                        }
                    }
                })
            }
            1 -> {
                val viewHolderMy = myViewHolder as ViewHolderMy

                val mContent = viewHolderMy.textView_my

                // 我的频道 前2不能操作
                if (position == 1 || position == 2) {
                    mContent.setBackgroundResource(R.drawable.item_textview_bg)
                } else {
                    mContent.setBackgroundColor(mContent.resources.getColor(R.color.baise))
                }

                mContent.setText(tagBean.content)

                val imageView = viewHolderMy.imageView_my

                if (tagBean.myTag === 1 && position > 2) {
                    imageView.visibility = View.VISIBLE
                } else {
                    imageView.visibility = View.INVISIBLE
                }
//                viewHolderMy.textView_my.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View view, MotionEvent motionEvent) {
//                        if (position > 2 && MotionEventCompat.getActionMasked(motionEvent)
//                                == MotionEvent.ACTION_DOWN ) {
//                            if (itemTouchHelper != null) {
//                                itemTouchHelper.startDrag(viewHolderMy);
//                            }
//                        }
//                        return false;
//                    }
//                });
                viewHolderMy.textView_my.setOnLongClickListener {
                    Toast.makeText(mContext, "${position.toString()}", Toast.LENGTH_SHORT).show()
                    setEditorTag()
                    false
                }
            }
            2 -> {
                val textView2 = (myViewHolder as MyTitleViewHolder).mTitle
                textView2.text = "推荐频道"
            }
            else -> {
                val mContent2 = (myViewHolder as ViewHolderPinDao).textView_pin

                mContent2.setText(tagBean.content)

            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        var tagBean: TagBean = tagBeans.get(position)
        when (tagBean.viewType) {
            0 -> {
                return 0
            }
            1 -> {
                return 1
            }
            2 -> {
                return 2
            }
            else -> {
                return 3
            }
        }
        return 0
    }

    override fun getItemCount(): Int {
        return tagBeans.size
    }


    override fun onMove(fromPosition: Int, toPosition: Int) {
        val addPinDao = tagBeans[fromPosition]
        val addPinDao1 = tagBeans.get(toPosition)
        if (fromPosition != 1 && toPosition != 1) {
            if ((addPinDao.viewType === 1 && addPinDao1.viewType === 1) || (addPinDao.viewType === 3
                        && addPinDao1.viewType === 3)) {
                //通知数组的移动
                Collections.swap(tagBeans, fromPosition, toPosition)
                notifyItemMoved(fromPosition, toPosition)
            }
        }
    }

    internal class ViewHolderOne(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView_tuodong: TextView
        var textView_one: TextView

        init {
            textView_one = itemView.findViewById(R.id.textview_recyclerview_add_item_one)
            textView_tuodong =
                itemView.findViewById(R.id.textview_recyclerview_add_item_one_tuodong)
        }

    }

    internal class MyTitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mTitle: TextView

        init {
            mTitle = itemView.findViewById(R.id.textview_recyelerview_add_item_pindao)
        }
    }

    internal inner class ViewHolderPinDao(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textView_pin: TextView

        init {
            textView_pin =
                itemView.findViewById(R.id.textview_recyelerview_add_item_pindao) as TextView
        }
    }

    internal inner class ViewHolderMy(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView_my: TextView
        val imageView_my: ImageView

        init {
            imageView_my =
                itemView.findViewById(R.id.imageView_recyclerview_add_itme_my) as ImageView
            textView_my = itemView.findViewById(R.id.textview_recyelerview_add_item_my) as TextView
        }
    }
}