package com.example.mykotlin.basic.computer


fun main(args: Array<String>) {
    val orderQueueLinkList = OrderQueueLinkList()
    orderQueueLinkList.insert(2)
    orderQueueLinkList.insert(1)
    orderQueueLinkList.insert(4)
    orderQueueLinkList.insert(45)
    orderQueueLinkList.insert(6)
    orderQueueLinkList.insert(3)
    orderQueueLinkList.deleteFromHead()
    orderQueueLinkList.delete(45)
    orderQueueLinkList.display()


}

/**
 * 有序队列
 */
class OrderQueueLinkList {
    var head: Node? = null
    var size: Int = 0

    inner class Node(val data: Int) {
        var next: Node? = null
    }

    fun insert(value: Int) {
        val newNode = Node(value)
        if (size == 0) {
            head = newNode
            size++
            return
        } else {
            var current: Node? = head
            var preNode: Node? = null
            while (current != null && value > current.data) {
                preNode = current
                current = current.next
            }
            if (preNode == null) {
                head = newNode
                head?.next = current
            } else {
                preNode?.next = newNode
                newNode?.next = current
            }
            size++
        }
    }

    fun find(value: Int): Node? {
        var current: Node? = head
        while (current != null && current.data <= value) {
            if (current.data == value) {
                return current
            }
            current = current.next
        }
        return null
    }

    fun deleteFromHead() {
        if (size > 0) {
            head = head?.next
            size--
        }
    }

    fun delete(value: Int) {
        var current: Node? = head
        var preNode: Node? = null
        while (current != null && value != current.data) {
            preNode = current
            current = current.next
        }
        if (current === head) {
            head = head?.next
            size--
        } else {
            preNode!!.next = current!!.next
            size--
        }
    }

    fun display() {
        var current: Node? = head
        while (current != null) {
            print(current.data.toString() + ",")
            current = current.next
        }
    }
}