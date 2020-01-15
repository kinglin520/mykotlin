package com.example.mykotlin.basic

import java.lang.Integer.parseInt


fun main(args: Array<String>) {
    println("Hello World!")
    val value = StudyDemo(1)
//    value.whenDemo("3")
    value.wenhaoDemo()
    value.forDemo()
//    value.whileDemo()
//    value.collectionDemo()
//    value.listDemo();
//    value.mutableListDemo()
//    value.mutableDemo()
//    value.mapDemo()
//    value.mutableMapDemo()
//    val prod = StudyDemo.ProducerImpl()
//    prod.produce()
}

class StudyDemo(x: Int) {
    // 伴生对象
    companion object {
        const val MAX = 10

        @JvmField
        val CONST = 1

        val VERSION = 9

        @JvmStatic
        fun callNot() {

        }
    }

    @JvmField
    public val list: ArrayList<Int> = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    // 求和
    fun sum(a: Int, b: Int): Int {

        return a + b
    }

    // 求和2
    fun sum2(a: Int, b: Int): Int = a + b

    //"$"使用
    fun printSum(a: Int, b: Int) {
        println("first=$a , second=$b , sum=${a + b}")
    }

    // 定义只读局部变量使用关键字 val 定义。只能为其赋值一次。
    val a: Int = 1  // 立即赋值
    val b = 2   // 自动推断出 `Int` 类型
    val c: Int = 3   // 如果没有初始值类型不能省略

    val PI = 3.14

    // 可重新赋值的变量使用 var 关键字：
    var x = 0

    fun incrementX() {
        x += 1
        println(x)
    }

    var f = 1

    val s1 = "f is $f"

    //${} 使用
    fun s1s2() {
        println(s1)

        f = 2
        val s2 = "${s1.replace("is", "was")}, but now is $f"
        println(s2)
    }

    // 求最大
    fun maxOf(a: Int, b: Int): Int {
        if (a > b) {
            return a
        } else {
            return b
        }
    }

    // 求最大2
    fun maxOf2(a: Int, b: Int): Int = if (a > b) {
        a
    } else {
        b
    }

    // for使用 ..区间 step间隔
    fun forDemo() {
        for (i in 1..3) {
            println(i)
        }
//        for (j in 6 until 0 step 2) {
        for (j in 6 downTo 0 step 2) {
            println(j)
        }

        var array = arrayListOf<String>()
        array.add("a")
        array.add("b")
        array.add("c")

        for (k in array.indices) println(array.get(k))

        for (l in array.indices) println(l)
    }

    // when 表达式  ..区间
    fun whenDemo(a: Any) {
        val s = "3"
        val validNumbers = 11..13
        when (a) {
            1 -> println("a == 1")
            2 -> println("a == 2")
            parseInt(s) -> println("a encode s")
            in 4..10 -> {
                println("a in the range")
            }
            in validNumbers -> {
                println("a in validNumbers")
            }
            !in 10..20 -> {
                println("a is outside the range")
            }
            else -> {
                println("a = other")
            }
        }
    }

    // ? 可空 ?.判空(安全调用操作符) !!.表示当前对象如果为空也执行，然后会抛出空异常
    fun wenhaoDemo(): Any? {
        var a: String? = "abc"
        a = null
        println(a)

        val l = if (a != null) a.length else -1

        println("值为 $l 和 $a")

        val c = "Kotlin"
        val d: String? = null
        println(c?.length)
        println(d?.length)

        val listOf: List<String?> = listOf("kotlin", null)
        for (item in listOf.indices) {
            item?.let { println(listOf.get(item)) }

            if (item != null) {
                println("me too ${listOf.get(item)}")
            }
        }

        val notNullList = listOf.filterNotNull()

        return null
    }

    // while
    fun whileDemo() {
        val items = listOf("123", "345", "567")
        var i = 0;
        while (i < items.size) {
            println("item at $i is ${items[i]}")
            i++
        }
    }


    // 集合
    // collection
    fun printAll(strings: Collection<String>) {
        for (s in strings) print("$s ")
        println()
    }

    fun collectionDemo() {
        var stringlist = listOf("one", "two", "three")
        printAll(stringlist)

        var stringSet = setOf("four", "five", "six")
        printAll(stringSet)
    }

    // list
    fun listDemo() {
        val number = listOf("oooone", "two", "three")
        println("Number of elemnts:${number.size}")
        println("third is ${number.get(2)}")
        println("Index of element 'two' ${number.indexOf("two")}")

        number.filter { it.endsWith("e") }
            .sortedBy { it.length }
            .map { it.toUpperCase() }
            .forEach { println(it) }

        val bob = Person("Bob", 31)
        val people = listOf<Person>(Person("Adam", 20), bob, bob)
        val people2 = listOf<Person>(Person("Adam", 20), Person("Bob", 31), bob)
        println(people == people2)
        bob.age = 29
        println(people == people2)

        people2.filter { it.age > 25 }
            .sortedBy { it.age }
            .map { it.name }
            .forEach {
                println(it)
            }
    }

    fun mutableListDemo() {
        val numbers = mutableListOf(1, 2, 3, 4)
        numbers.add(5)
        numbers.removeAt(1)
        numbers.add(0, 0)
        println(numbers)
    }

    class Person {
        var name: String = ""
        var age: Int = 0

        constructor(name: String, age: Int)
    }

    // set
    fun setDemo() {
        val number = setOf(1, 2, 3, 4)
        if (number.contains(1)) println("1 in the set")

        val numberBackwords = setOf(4, 3, 2, 1)
        //当两个 set 具有相同的大小并且对于一个 set 中的每个元素都能在另一个 set 中存在相同元素，则两个 set 相等。
        println("The sets are equl: ${number == numberBackwords}")
    }

    // MutableSet
    fun mutableDemo() {
        val number = mutableSetOf(1, 2, 3, 4)
        val numberBackwords = mutableSetOf(4, 3, 2, 1)

        println(number.first() == numberBackwords.first())
        println(number.first() == numberBackwords.last())
    }

    // Map
    fun mapDemo() {
        val numberMap = mapOf("key1" to 1, "key2" to 2, "key3" to 3)
        println("numberMap all key ${numberMap.keys}")
        println("nuberMap all value ${numberMap.values}")
        if ("key2" in numberMap) {
            println("value by key2  = ${numberMap.get("key2")}")
        }
        if (1 in numberMap.values) {
            println("1 is in the map")
        }
        if (numberMap.containsValue(1)) {
            println("map containvalue 1")
        }

        val numbersMap = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key4" to 1)
        val anotherMap = mapOf("key2" to 2, "key1" to 1, "key4" to 1, "key3" to 3)
        // 无论键值对的顺序如何，包含相同键值对的两个 Map 是相等的。
        println("The maps are equal: ${numbersMap == anotherMap}")
    }

    // mutableMap
    fun mutableMapDemo() {
        val numberMap = mutableMapOf<String, Int>()
        numberMap.put("key1", 1)
        numberMap.put("key2", 2)
        numberMap.put("key3", 3)
        numberMap.put("key1", 10)
        numberMap["key2"] = 20
        println(numberMap)
    }


    interface Producer {
        fun produce() {
            println("interface method")
        }
    }

    class ProducerImpl : Producer {
        override fun produce() {
            super.produce()
            println("class method")
        }
    }

    /**
    在 Kotlin 中使用 JNI
    要声明一个在本地（C 或 C++）代码中实现的函数，你需要使用 external 修饰符来标记它：
    要告诉 Kotlin 某个声明是用纯 JavaScript 编写的，你应该用 external 修饰符来标记它
     * */
    external fun foo(x: Int): Double
}


