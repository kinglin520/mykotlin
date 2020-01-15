package com.example.mykotlin.ui

import com.example.mykotlin.base.IBaseView
import com.example.mykotlin.bean.GirlInfoBean

interface TestView : IBaseView {
    fun setGirlInfo(list: List<GirlInfoBean>)

}