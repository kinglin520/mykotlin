package com.example.mykotlin.basic.computer


fun main(args: Array<String>) {
    val list = SingleLinkList()
    list.addHead(1)
    list.addHead(2)
    list.addHead(3)
    list.deleteFromTHead()
    list.addHead(4)
    list.addHead(5)
    list.delelteNode(2)
    list.addHead(10)
    list.deleteBest(4)
    var node: SingleLinkList.Node? = list.head
    while (node != null) {
        print("${node!!.data},")
        node = node!!.nextNode
    }

}

class SingleLinkList {
    inner class Node(var data: Any) {
        var nextNode: Node? = null
    }

    var head: Node? = null
    var size: Int = 0

    /**
     * 在头部插入节点
     */
    fun addHead(data: Any) {
        val newNode = Node(data)
        if (size == 0) {
            head = newNode
        } else {
            newNode.nextNode = head
            head = newNode
        }
        size++
    }

    fun deleteFromTHead(): Any? {
        if (size == 0) {
            return null
        } else if (size == 1) {
            size = 0
            return head?.data
        } else {
            val temp = head?.data
            head?.data = Unit
            head = head?.nextNode
            size--
            return temp
        }
    }

    /**
     * 查找指定元素，从头节点开始查找，找到了返回节点Node，找不到返回null
     */
    fun find(obj: Any): Node? {
        var current = head
        var tempSize = size
        while (tempSize > 0) {
            if (current?.data == obj) {
                return current
            } else {
                current = current?.nextNode
            }
            tempSize--
        }
        return null
    }

    fun find2(`object`: Any): Node? {
        var current: Node? = head
        while (current != null) {
            if (current.data == `object`) {
                return current
            } else {
                current = current.nextNode
            }
        }
        return null
    }

    /**
     * 删除指定节点
     */
    fun delelteNode(obj: Any): Boolean {
        if (size == 0) {
            return false
        }
        if (size == 1 && head?.data == obj) {
            head = null
            size = 0
            return true
        }
        var current = head?.nextNode
        var preNode = head
        var tempSize = size
        while (tempSize > 1) {
            if (current?.data == obj) {
                current?.data = Unit
                preNode?.nextNode = current.nextNode
                return true
            } else {
                preNode = current
                current = current?.nextNode
            }
            tempSize--
        }
        return false
    }

    /**
     * 删除指定节点
     */
    fun deleteBest(value: Any): Boolean {
        var current = head
        var preNode = head
        while (current?.data !== value) {
            if (current?.nextNode == null) {
                return false
            } else {
                preNode = current
                current = current?.nextNode
            }
        }
        if (current === head) {
            head = current?.nextNode
            size--
        } else {
            current?.data = Unit
            preNode?.nextNode = current?.nextNode
            size--
        }
        return true
    }

    fun isEmpty(): Boolean {
        return if (size == 0) {
            true
        } else false
    }

}