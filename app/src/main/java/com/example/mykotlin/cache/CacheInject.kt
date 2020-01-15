package com.example.mykotlin.cache

import java.util.*

class CacheInject {
    companion object {
        var map: MutableMap<String, Any> = HashMap(32)

        fun inject(clazz: Class<*>) {
            val m = clazz.declaredMethods
            for (ms in m) {
                if (ms.isAnnotationPresent(Cache::class.java)) {
                    val cache = ms.getAnnotation(Cache::class.java)
                    if (cache != null) {
                        val path = cache.path
                        val mode = cache.mode
                        if (!map.containsKey(path)) {
                            //添加需要拦截的接口
                            map[path] = mode
                        }
                    }
                }
            }
        }
    }

}