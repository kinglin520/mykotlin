package com.example.mykotlin.basic.computer

fun main(args: Array<String>) {
    val list = DoubleLinkList()
    list.addHead(1)
    list.addHead(2)
    list.addHead(3)
    list.deleteFromTHead()
    list.addHead(4)
    list.addHead(5)
    list.addHead(10)
    list.deleteBest(4)
    var node = list.head
    while (node != null) {
        print(node.data.toString() + ",")
        node = node.nextNode
    }
}

class DoubleLinkList {
    inner class Node(var data: Any) {
        var nextNode: Node? = null
    }

    var head: Node? = null
    var tail: Node? = null
    private var size = 0

    fun getSize(): Int {
        return size
    }

    /**
     * 在头部插入节点
     */
    fun addHead(data: Any) {
        val newNode = Node(data)
        if (size == 0) {
            head = newNode
            tail = newNode
        } else {
            newNode.nextNode = head
            head = newNode
        }
        size++
    }

    /**
     * 在尾部插入节点
     */
    fun addTail(value: Any) {
        val newNode = Node(value)
        if (size == 0) {
            head = newNode
            tail = newNode
        } else {
            tail?.nextNode = newNode
            tail = newNode
        }
        size++
    }

    fun deleteFromTHead(): Any? {
        if (size == 0) {
            return null
        } else if (size == 1) {
            size = 0
            val temp = head?.data
            head = null
            tail = null
            return temp
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
            head = current.nextNode
            size--
        } else if (current === tail) {
            current.data = Unit
            tail = preNode
            size--
        } else {
            current.data = Unit
            preNode?.nextNode = current.nextNode
            size--
        }
        return true
    }

    fun isEmpty(): Boolean {
        return size == 0
    }

    fun display() {
        var current: Node? = head
        while (current != null) {
            print(current.data.toString() + ",")
            current = current.nextNode
        }
    }

}