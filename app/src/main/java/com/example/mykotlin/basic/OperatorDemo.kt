package com.example.mykotlin.basic


fun main(args: Array<String>) {
    OperatorDemo().unaryMinusTest()
}

// 操作符(运算符)重载
class OperatorDemo() {
    data class Point(val x: Int, val y: Int)

    /**
    +a	a.unaryPlus()
    -a	a.unaryMinus()
    !a	a.not()
     * */
    operator fun Point.unaryMinus() = Point(+x, +y)

    val point = Point(10, 20)

    fun unaryMinusTest() {
        println(-point)
    }
}