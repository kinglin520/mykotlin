package com.example.mykotlin.cache

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@kotlin.annotation.Target
@Retention(RetentionPolicy.RUNTIME)
@Documented
annotation class Cache(
    val path: String//缓存接口路径
    , val mode: Boolean = false//false:网络层已经处理好缓存，不会走Error；true：业务逻辑需要自己处理，在Error里面
)
