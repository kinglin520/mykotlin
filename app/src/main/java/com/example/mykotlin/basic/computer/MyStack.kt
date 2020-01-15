package com.example.mykotlin.basic.computer

import java.util.*

fun main(args: Array<String>) {
    val stack = MyStack(1)
    val s = "how are u"
    val chars = s.toCharArray()
    for (i in chars) {
        stack.push(i)
    }
    while (!stack.isEmpty()) {
        print(stack.pop())
    }
}

class MyStack {
    internal var top: Int = 0
    internal var maxSize = 10
    /**
     * 存储元素的数组,声明为Object类型能存储任意类型的数据
     */
    var arrs: Array<Any>? = null

    constructor (size: Int) {
        this.maxSize = size
        arrs = arrayOf(arrayOfNulls<Any>(size))
        this.top = -1
    }

    fun push(value: Any) {
        isGrow(top + 1)
        if (top < maxSize - 1) {
            arrs?.set(++top, value)
        }
    }

    fun pop(): Any? {
        val temp = peek()
        remove(top)
        return temp
    }

    fun peek(): Any? {
        if (top == -1) {
            throw EmptyStackException()
        }
        return arrs?.get(top)
    }

    fun isEmpty(): Boolean {
        return if (top == -1) {
            true
        } else false
    }

    fun remove(top: Int) {
        arrs?.set(top, "")
        this.top--
    }

    fun isGrow(minCapacity: Int): Boolean {
        if (minCapacity >= maxSize) {
            while (minCapacity >= maxSize) {
                maxSize = 2 * maxSize
            }
            arrs = Arrays.copyOf(arrs, maxSize)
            return true
        }
        return false
    }
}