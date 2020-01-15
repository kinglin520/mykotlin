package com.example.mykotlin.basic.computer

fun main(args: Array<String>) {

    val arr = intArrayOf(2, 1, 5, 3, 4)
//    Sort().insertSort(arr)?.forEach { println(it) }
    Sort().quickSort(arr, 0, arr.size - 1)
    arr.forEach { print(",$it") }

}

class Sort {

    fun swap(arr: IntArray, i: Int, j: Int) {
        val temp = arr[i]
        arr[i] = arr[j]
        arr[j] = temp
    }

    /**
     * 冒泡排序
     */
    fun maoPaoSort(arr: IntArray?): IntArray? {
        if (arr == null || arr.size == 0) {
            return null
        }
        val count = arr.size
        for (i in 0 until count - 1) {
            for (j in 0 until count - i - 1) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1)
                }
            }
        }
        return arr
    }

    /**
     * 选择排序
     */
    fun selectSort(arr: IntArray?): IntArray? {
        if (arr == null || arr.size == 0) {
            return null
        }
        val count = arr.size
        for (i in 0 until count) {
            for (j in i + 1 until count) {
                if (arr[i] > arr[j]) {
                    swap(arr, i, j)
                }
            }
        }
        return arr
    }

    /**
     * 直接插入排序
     */
    fun insertSort(arr: IntArray?): IntArray? {
        if (arr == null || arr.size == 0) {
            return null
        }
        val count = arr.size
        // 左边已排好序数量
        var j: Int
        // 默认左边第一个是有序，所以从一开始
        for (i in 1 until count) {
            j = i
            val temp = arr[j]
            // 和左边有序 从右往左依次比较，小则继续往前比较
            while (j > 0 && temp < arr[j - 1]) {
                // 往右挪动
                arr[j] = arr[j - 1]
                j--
            }

            arr[j] = temp

        }
        return arr
    }

    /**
     * 归并排序（对两组有序数组进行合并排列）
     */
    fun mergeSort(a: IntArray, b: IntArray): IntArray {
        val aCount = a.size
        val bCount = b.size
        val c = IntArray(aCount + bCount)
        var aNum = 0
        var bNum = 0
        var cNum = 0
        //比较a数组和b数组的元素，谁更小将谁赋值到c数组
        while (aNum < aCount && bNum < bCount) {
            if (a[aNum] < b[bNum]) {
                c[cNum] = a[aNum]
                aNum++
                cNum++
            } else {
                c[cNum] = b[bNum]
                bNum++
                cNum++
            }
        }
        //如果a数组全部赋值到c数组了，但是b数组还有元素，则将b数组剩余元素按顺序全部复制到c数组
        while (aNum == aCount && bNum < bCount) {
            c[cNum++] = b[bNum++]
        }
        //如果b数组全部赋值到c数组了，但是a数组还有元素，则将a数组剩余元素按顺序全部复制到c数组
        while (bNum == bCount && aNum < aCount) {
            c[cNum++] = a[aNum++]
        }
        return c
    }

    /**
     * 希尔排序（特殊间隔3h+1，2H，2.2的高级插入排序）
     */
    fun shellKnuthSort(arr: IntArray?): IntArray? {
        if (arr == null || arr.size == 0) {
            return null
        }
        val count = arr.size
        var step = 1
        var j: Int
        var temp: Int
        while (step <= count / 3) {
            step = 3 * step + 1
        }
        while (step > 0) {
            for (i in step until count) {
                j = i
                temp = arr[j]
                while (j > step - 1 && temp < arr[j - step]) {
                    arr[j] = arr[j - step]
                    j = j - step
                }
                arr[j] = temp
            }
            step = (step - 1) / 3
        }
        return arr
    }

    /**
     * 快速排序
     */
    fun quickSort(arr: IntArray, left: Int, right: Int) {
        if (right <= left) {
            return
        } else {
            val pivot = partitionIt(arr, left, right)
            // 对分割后左边的数组进行再次遍历分割
            quickSort(arr, left, pivot - 1)
            // 对分割后右边的数组进行再次遍历分割
            quickSort(arr, pivot + 1, right)
        }
    }

    /**
     * 根据基准值分割数组
     */
    fun partitionIt(arr: IntArray, left: Int, right: Int): Int {
        // 左游标 下面从1开始
        var i = left
        // 右游标
        var j = right + 1
        // 设置第一个为基准值
        val pivot = arr[left]

        while (true) {
            // 从左往右遍历获取大于基准值的数值的位置
            while (i < right && arr[++i] < pivot) {
            }
            // 从右往左遍历获取小于基准值的数值的位置
            while (j > 0 && arr[--j] > pivot) {
            }
            // 左右游标相遇时候停止， 所以跳出外部while循环
            if (i >= j) {
                break
            } else {
                // 交换位置
                swap(arr, i, j)
            }
        }
        //基准元素和游标相遇时所指元素交换，为最后一次交换
        swap(arr, left, j)
        // 一趟排序完成， 返回基准元素位置(注意这里基准元素已经交换位置了)
        return j
    }

    /**
     * 第一个、中间、最后一个三个数取中间的数字
     */
    fun medianOf3(arr: IntArray, left: Int, right: Int): Int {
        val center = (left + right) / 3
        if (arr[left] > arr[right]) {
            swap(arr, left, right)
        }
        if (arr[center] > arr[right]) {
            swap(arr, center, right)
        }
        if (arr[center] > arr[left]) {
            swap(arr, center, left)
        }
        //array[left]的值已经被换成三数中的中位数， 将其返回
        return arr[left]
    }

}