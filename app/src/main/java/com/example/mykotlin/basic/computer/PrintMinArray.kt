package com.example.mykotlin.basic.computer

fun main(args: Array<String>) {
    PrintMinArray().orderArray(intArrayOf(32, 321, 332)).forEach {
        println(it)
    }


}

class PrintMinArray() {
    fun orderArray(array: IntArray): Array<String?> {

        val count = array.size
        val s = arrayOfNulls<String?>(count)
        val sb = StringBuilder()
        for (i in 0 until count) {
            s[i] = array[i].toString()
        }

        for (i in 0 until count - 1) {
            for (j in 0 until count - i - 1) {
                val c1 = s[j] + s[j + 1]
                val c2 = s[j + 1] + s[j]
                if (c1.compareTo(c2) > 0) {
                    exchange(s, j, j + 1)
                }
            }
        }
        return s
    }

    /**
     * 交换位置
     */
    private fun exchange(array: Array<String?>, i: Int, j: Int) {
        val temp = array[i]
        array[i] = array[j]
        array[j] = temp
    }
}