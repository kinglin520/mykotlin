package com.example.mykotlin.basic

import kotlin.random.Random


/**let	it	Lambda 表达式结果	是
run	this	Lambda 表达式结果	是
run	-	Lambda 表达式结果	不是：调用无需上下文对象
with	this	Lambda 表达式结果	不是：把上下文对象当做参数
apply	this	上下文对象	是
also	it	上下文对象	是**/

fun main(args: Array<String>) {
    // 上下文对象作为接收者（it）
    Person2("Alice", 20, "Amsterdam").let {
        println(it)
    }

    val str = "Hello"
    // this
    str.run {
        println("The receiver string length: $length")
        //println("The receiver string length: ${this.length}") // 和上句效果相同
    }
    // it
    str.let {
        println("The receiver string's length is ${it.length}")
    }

    ScopeAction().applyDemo()
    ScopeAction().alseDemo()

}

class ScopeAction() {
    val i = getRandomInt()
    // 上下文对象作为接收者（it）
    // 使用 it 作为上下文对象会更好。若在代码块中使用多个变量，则 it 也更好。
    fun getRandomInt(): Int {
        return Random.nextInt(100).also {
            println("getRandomInt() generated value $it")
        }
    }

    //若代码块仅包含以 it 作为参数的单个函数，则可以使用方法引用(::)代替 lambda 表达式：
    fun maohaoDemo() {
        val numbers = mutableListOf("one", "two", "three", "four", "five")
        numbers.map { it.length }.filter { it > 3 }.let(::println)
    }

    fun nullLetDemo() {
        val str: String? = "Hello"
        //processNonNullString(str)       // 编译错误：str 可能为空
        val length = str?.let {
            println("let() called on $it")
//            processNonNullString(it)      // 编译通过：'it' 在 '?.let { }' 中必不为空
            println(it.length)
        }
    }

    //建议将上下文对象作为接收者（this）
    fun applyDemo() {
        val adam = Person2("Adam").apply {
            age = 20                       // 和 this.age = 20 或者 adam.age = 20 一样
            adress = "London"
        }
        println("name = ${adam.name}  age = ${adam.age} , address = ${adam.adress} ")
    }

    // 上下文对象作为接收者（it）
    fun alseDemo() {
        val numbers = mutableListOf("one", "two", "three")
        numbers
            .also { println("The list elements before adding new one: $it") }
            .add("four")
    }


}

data class Person2(var name: String) {
    var age: Int = 0
    var adress: String = ""

    constructor(name: String, age: Int, adress: String) : this(name)
}

