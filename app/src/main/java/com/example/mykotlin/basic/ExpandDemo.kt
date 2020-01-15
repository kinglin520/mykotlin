package com.example.mykotlin.basic

fun main(args: Array<String>) {
    var expandDemo = ExpandDemo()
    expandDemo.swap()

    Example().printFunctionType(1)

    MyClass.printCompanion()

    Connection(Host("kotl.in"), 443).connect()

    BaseCaller().call(Base2())   // “Base extension function in BaseCaller”
    DerivedCaller().call(Base2())  // “Base extension function in DerivedCaller”——分发接收者虚拟解析
    DerivedCaller().call2(Derived2())

    var tt = ttDemo()
    var test = listOf(1, 2, 3, 4)
    for (item in tt.singletonList(test)) {
        println(item)
    }

    var aa = AA(1)
    aa.foo()

    val name = Name("Kotlin")
    name.greet() // `greet` 方法会作为一个静态方法被调用
    println(name.length) // 属性的 get 方法会作为一个静态方法被调用

    val b = BaseImpl(10)
    val derived = Derived3(b)
    derived.print()
    println(derived.message)
}

class ExpandDemo {
    //扩展函数
    //声明一个扩展函数，我们需要用一个 接收者类型 也就是被扩展的类型来作为他的前缀。
    //下面代码为 MutableList<Int> 添加一个swap 函数
    fun MutableList<Int>.swap(index1: Int, index2: Int) {
        val tmp = this[index1] // “this”对应该列表
        this[index1] = this[index2]
        this[index2] = tmp
    }

    fun swap() {
        val list = mutableListOf(1, 2, 3)
        list.swap(0, 2) // “swap()”内部的“this”会保存“list”的值
        println(list)
    }
}

class Example {
    fun printFunctionType() {
        println("Class method")
    }
}

fun Example.printFunctionType(i: Int) {
    println("Extension function")
}

class MyClass {
    companion object {}  // 将被称为 "Companion"
}

fun MyClass.Companion.printCompanion() {
    println("companion")
}

class Host(val hostname: String) {
    fun printHostname() {
        print(hostname)
    }
}

class Connection(val host: Host, val port: Int) {
    fun printPort() {
        println(port)
    }

    fun Host.printConnectionString() {
        printHostname()   // 调用 Host.printHostname()
        print(":")
        printPort()   // 调用 Connection.printPort()
    }

    fun connect() {
        /*……*/
        host.printConnectionString()   // 调用扩展函数
    }
}

open class Base2

class Derived2 : Base2()

open class BaseCaller {
    open fun Base2.printFunctionInfo() {
        println("Base extension function in BaseCaller")
    }

    open fun Derived2.printFunctionInfo() {
        println("Derived extension function in BaseCaller")
    }

    fun call(b: Base2) {
        b.printFunctionInfo()   // 调用扩展函数
    }

    fun call2(d: Derived2) {
        d.printFunctionInfo()
    }

}

class DerivedCaller : BaseCaller() {
    override fun Base2.printFunctionInfo() {
        println("Base extension function in DerivedCaller")
    }

    override fun Derived2.printFunctionInfo() {
        println("Derived extension function in DerivedCaller")
    }
}

class ttDemo() {
    fun <T> singletonList(item: T): List<Int> {
        var list = mutableListOf<Int>()
        if (item is Array<*>) {
            for (i in item.indices) {
                list.add(item.get(i) as Int)
            }
        }
        return list
    }
}

open class AA(x: Int) {
    public open val y: Int = x
//如果我们只需要“一个对象而已”，并不需要特殊超类型，那么我们可以简单地写
    fun foo() {
        val adHoc = object {
            var x: Int = 1
            var y: Int = 2
        }
        print(adHoc.x + adHoc.y)
    }
}

interface BB {}

val aabb: AA = object : AA(1), BB {
    override val y = 15
}

//匿名对象可以用作只在本地和私有作用域中声明的类型(object)
class CC {
    // 私有函数，所以其返回类型是匿名对象类型
    private fun foo() = object {
        val x: String = "x"
    }

    // 公有函数，所以其返回类型是 Any
    fun publicFoo() = object {
        val x: String = "x"
    }

    fun bar() {
        val x1 = foo().x        // 没问题
//        val x2 = publicFoo().x  // 错误：未能解析的引用“x”
    }
}

inline class Name(val s: String) {
    val length: Int
        get() = s.length

    fun greet() {
        println("Hello, $s")
    }
}

interface Base3 {
    val message: String
    fun print()
}

class BaseImpl(val x: Int) : Base3 {
    override val message = "BaseImpl: x = $x"
    override fun print() { println(message) }
}

class Derived3(b: Base3) : Base3 by b {
    // 在 b 的 `print` 实现中不会访问到这个属性
    override val message = "Message of Derived"
}
