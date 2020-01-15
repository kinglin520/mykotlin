package com.example.mykotlin.basic

fun main(args: Array<String>) {
//    var demo = InitOrderDemo("hello")
//    var person = Person("xiaoming", Person("lili"))
//    var constructors = Constructors(1, 2)
//    var order = Derived("hello", "world")
//    val square = Square()
//    square.draw()
    var innerDemo = FilledRectangle().Filler()
    innerDemo.drawAndFill()
//    var d = D()
//    d.bar()
//    d.foo()
}

class InitOrderDemo(name: String) {
    val firstProperty = "First property: $name".also(::println)

    init {
        println("First initializer block that prints ${name}")
    }

    val secondProperty = "Second property: ${name.length}".also(::println)

    init {
        println("Second initializer block that prints ${name.length}")
    }

    //lateinit 对应使用var来声明属性
    //lateinit 修饰不可以修饰原始数据类型（byte，char，short ,int，long，float，double）
    lateinit var geight: String
}

class Person(val name: String) {
    var children: MutableList<Person> = mutableListOf<Person>()

    constructor(name: String, parent: Person) : this(name) {
        parent.children.add(parent)
        parent.children.add(this)
        for (pp in parent.children) {
            println(pp.name)
        }
    }
}

open class Constructors private constructor(val shuzi: Int = 10) {
    init {
        println("Init block")
    }

    constructor(shuzi: Int, l: Int) : this(shuzi) {
        println("Constructor $shuzi")
    }
}

//派生类初始化顺序
//在构造派生类的新实例的过程中，第一步完成其基类的初始化（在之前只有对基类构造函数参数的求值），
//因此发生在派生类的初始化逻辑运行之前。
open class Base(val name: String) {

    init {
        println("Initializing Base")
    }

    open val size: Int =
        name.length.also { println("Initializing size in Base: $it") }
}

class Derived(name: String, lastName: String) :
    Base(name.capitalize().also { println("Argument for Base: $it") }) {

    init {
        println("Initializing Derived")
    }

    override val size: Int =
        (super.size + lastName.length).also { println("Initializing size in Derived: $it") }
}

open class Rectangle {
    open fun draw() {
        println("Rectangle init")
    }
}

interface Polygon {
    fun draw() {
        println("Polygon init")
    } // 接口成员默认就是“open”的
}

// 继承
class Square() : Rectangle(), Polygon {
    // 编译器要求覆盖 draw()：
    override fun draw() {
        super<Rectangle>.draw() // 调用 Rectangle.draw()
        super<Polygon>.draw() // 调用 Polygon.draw()
    }

}

open class Rectangle2 {
    open fun draw() {
        println("Drawing a rectangle")
    }

    open val borderColor: String get() = "black"
}

// 内部类调用外部类 外部类的超类super@outer
class FilledRectangle : Rectangle2() {
    override fun draw() {
        println("FilledRectangle draw")
    }

    override val borderColor: String get() = "black2"

    inner class Filler {
        fun fill() {
            println("inner Filler fill")
        }

        fun drawAndFill() {
            draw()
            super@FilledRectangle.draw() // 调用 Rectangle 的 draw() 实现
            println("Drawn a filled rectangle with color ${super@FilledRectangle.borderColor}") // 使用 Rectangle 所实现的 borderColor 的 getInstance()
        }
    }
}

//实现多个接口时，可能会遇到同一方法继承多个实现的问题。例如
interface A {
    fun foo() {
        println("A")
    }

    fun bar()
}

interface B {
    fun foo() {
        println("B")
    }

    fun bar() {
        println("bar")
    }
}

open class C : A {
    override fun bar() {
        super.foo()
        println("bar")
    }
}

class D : B, C() {
    override fun foo() {
//        super<A>.foo()
        super<C>.foo()
        super<B>.foo()
    }

    override fun bar() {
        super<B>.bar()
    }
}