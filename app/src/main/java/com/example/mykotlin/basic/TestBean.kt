package com.example.mykotlin.basic

import android.os.Parcel
import android.os.Parcelable

fun main(args: Array<String>) {
    var t = TestBean("liming")
    t.counter = 20
//    t.table.
    println(t.counter)

    val jack = User("Jack", 1)
    val olderJack = jack.copy(age = 2)

    println(jack)
    println(olderJack)

    val jane = User("Jane", 35)
    val (name, age) = jane
    println("$name, $age years of age") // 输出 "Jane, 35 years of age"

    val ints: Array<Int> = arrayOf(1, 2, 3)
    val any = Array<Any>(3) { "" }
    t.copy2(ints, any)
//    for (i in any.indices) {
//        println(any.getInstance(i))
//    }
    for (item in any) {
        println(item)
    }

}

data class TestBean(val name: String?) : Parcelable {

    var old = 1
        set(value) {
            value
        }

    var stringRepresentation: String
        get() = this.toString()
        set(value) {
            value
        }

    var counter = 0 // 注意：这个初始器直接为幕后字段赋值
        get() {
            if (field > 10) return field else return 0
        }
        set(value) {
            if (value >= 0) field = value
        }

    private var _table: Map<String, Int>? = null

    val table: Map<String, Int>
        get() {
            if (_table == null) {
                _table = HashMap() // 类型参数已推断出
            }
            return _table ?: throw AssertionError("Set to null by another thread")
        }


    var weight: Int? = 60
    var myName: String = "liming"
    var age: Int = 0

    //在很多情况下，我们需要复制一个对象改变它的一些属性，
    //但其余部分保持不变。 copy() 函数就是为此而生成。对于上文的 User 类，其实现会类似下面这样：
    fun copy(myName: String = this.myName, age: Int = this.age) = User(myName, age)

    fun copy2(from: Array<out Any>, to: Array<Any>) {
        assert(from.size == to.size)
        for (i in from.indices)
            to[i] = from[i]
    }

    constructor(source: Parcel) : this(
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TestBean> = object : Parcelable.Creator<TestBean> {
            override fun createFromParcel(source: Parcel): TestBean = TestBean(source)
            override fun newArray(size: Int): Array<TestBean?> = arrayOfNulls(size)
        }
    }

}

data class User(var myName: String, var age: Int)
