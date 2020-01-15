package com.example.mykotlin.ui

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.mykotlin.MainActivity
import com.example.mykotlin.R
import com.example.mykotlin.base.BaseActivity
import com.example.mykotlin.bean.GirlInfoBean
import com.example.mykotlin.http.error.ErrorType
import kotlinx.android.synthetic.main.activity_girl_main.*

class GirlMainActivity : BaseActivity<TestView, TestPresenter>(), TestView, View.OnClickListener {
    override fun showLoading() {
        Log.i("test", "showloading")
    }

    override fun hideLoading() {
        Log.e("test", "hideLoading")
    }

    var pageIndex = 1

    override fun onClick(v: View?) {
        when (v) {
            btn_desc -> {
                mPresenter?.getGirlInfo(pageIndex++.toString())

            }
            btn_flow -> {
                val intent = Intent(this@GirlMainActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun getView(): Int {
        return R.layout.activity_girl_main
    }

    override fun initPresenter(): TestPresenter {
        return TestPresenter(this)
    }

    override fun initView() {
        btn_desc.setOnClickListener(this)
        btn_flow.setOnClickListener(this)
    }


    override fun setGirlInfo(list: List<GirlInfoBean>) {
        if (list != null && list.size > 0) {
            tv_name.setText(list[0].getDesc())
            Glide.with(this).load(list[0].getUrl()).into(iv_girl)

            val tt = "你好"

            val tt2 = tt[0].toInt() % 4

            Toast.makeText(
                this,
                tt + " " + tt[0] + " " + tt[0].toInt() + " " + tt2,
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    override fun showErrorTip(errorType: ErrorType?, errorCode: Int, message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}