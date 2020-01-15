package com.example.mykotlin.basic.computer

fun main(args: Array<String>) {
    val temp = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    println(HalfFind().halfFindRecursion(temp, 3, 0, 8))

    println(HalfFind().halfFindRecursion2(temp, 3))
}

class HalfFind {
    /**
     * 循环
     */
    fun halfFind(arrs: IntArray, value: Int): Int {
        val count = arrs.size
        var low = 0
        var high = count - 1
        var middle: Int
        while (low <= high) {
            middle = (low + high) / 2
            if (value == arrs[middle]) {
                return middle
            } else if (value > arrs[middle]) {
                low = middle + 1
            } else {
                high = middle - 1
            }
        }
        return -1
    }

    /**
     * 递归
     */
    fun halfFindRecursion(arrs: IntArray, value: Int, low: Int, high: Int): Int {
        if (low > high) {
            return -1
        }
        val middle = (low + high) / 2

        return if (value == arrs[middle]) {
            middle
        } else if (value > arrs[middle]) {
            halfFindRecursion(arrs, value, middle + 1, high)
        } else {
            halfFindRecursion(arrs, value, low, middle - 1)
        }
    }

    fun halfFindRecursion2(arrs: IntArray, value: Int): Int {

        var low = 0
        var high = arrs?.size

        while (low < high) {
            var mid = (low + high) / 2
            if (value == arrs[mid]) {
                return mid
            } else if (value > arrs[mid]) {
                low = mid + 1
            } else {
                high = mid - 1
            }
        }
        return -1
    }
}