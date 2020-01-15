package com.example.mykotlin.basic


fun main(args: Array<String>) {
    var demo = SingletonDemo.get()
    demo.show()
    var demo2 = SingletonDemo2.getInstance2()
    demo2.show()
    var demo3 = SingletonDemo3.instance
    demo3.show()

}

// 懒汉 线程安全
class SingletonDemo private constructor() {
    companion object {
        private var instance: SingletonDemo? = null
            get() {
                if (field == null) {
                    field = SingletonDemo()
                }
                return field
            }

        @Synchronized
        fun get(): SingletonDemo {
            return instance!!
        }
    }

    fun show() {
        println("SingletonDemo")
    }
}

// 双重校验单例
class SingletonDemo2 private constructor() {
    companion object {
        private var instance: SingletonDemo2? = null
            get() {
                if (field == null) {
//                    synchronized(this){}
                    synchronized(SingletonDemo2::class.java) {
                        if (field == null) {
                            field = SingletonDemo2()
                        }
                    }
                }
                return field
            }

        fun getInstance2(): SingletonDemo2 {
            return instance!!
        }
    }

    fun show() {
        println("SingletonDemo2")
    }
}

//kotlin实现 延迟属性 Lazy
class SingletonDemo3 private constructor() {
    companion object {
        val instance: SingletonDemo3 by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SingletonDemo3()
        }
    }

    fun show() {
        println("SingletonDemo3")
    }
}