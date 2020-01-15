package com.example.mykotlin.basic.computer

fun main(args: Array<String>) {
    val twoWayLinkList = TwoWayLinkList()
    twoWayLinkList.addHead(3)
    twoWayLinkList.addHead(2)
    twoWayLinkList.addHead(9)
    twoWayLinkList.addHead(4)
    twoWayLinkList.addHead(6)
    twoWayLinkList.addHead(5)
    twoWayLinkList.addHead(7)
    //        twoWayLinkList.display();
    //        twoWayLinkList.deleteHead();
    //        twoWayLinkList.display();
    //        twoWayLinkList.deleteTail();


    TwoWayLinkList().quickSort(twoWayLinkList.head, twoWayLinkList.tail)
    twoWayLinkList.display()
}

/**
 * 双向链表
 */
class TwoWayLinkList {
    inner class Node(var data: Int) {
        var next: Node? = null
        var pre: Node? = null
    }

    var head: Node? = null
    var tail: Node? = null
    var size: Int = 0

    fun addHead(value: Int) {
        val newNode = Node(value)
        if (size == 0) {
            head = newNode
            tail = newNode
            size++
        } else {
            newNode.next = head
            head?.pre = newNode
            head = newNode
            size++
        }
    }

//     void addTail(int value) {
//        Node newNode = new Node(value);
//        if (size == 0) {
//            head = newNode;
//            tail = newNode;
//            size++;
//        } else {
//            newNode.pre = tail;
//            tail.next = newNode;
//            tail = newNode;
//            size++;
//        }
//    }

    //在链表尾增加节点
    fun addTail(value: Int) {
        val newNode = Node(value)
        if (size == 0) {
            head = newNode
            tail = newNode
            size++
        } else {
            newNode.pre = tail
            tail?.next = newNode
            tail = newNode
            size++
        }
    }

    fun deleteHead() {
        head?.data = -1
        head = head?.next
        size--

    }

    fun deleteTail() {
        tail?.data = -1
        tail = tail?.pre
        size--
    }

    fun display() {
        var current: Node? = head
        while (current != null && current.data >= 0) {
            print(current.data.toString() + ",")
            current = current.next
        }
    }

    /**
     * 双向链表快速排序
     * 核心思想：1、同数组快速排序思路2、只交换节点值，不交换节点3、顺序：小的放左边 再大的放右边
     */
    fun quickSort(head: Node?, tail: Node?) {
        if (head == null || tail == null || head === tail || head.next === tail) {
            return
        }
        if (head !== tail) {
            val mid = getMid(head, tail)
            quickSort(head, mid)
            quickSort(mid.next, tail)
        }
    }

    fun getMid(start: Node, end: Node): Node {
        var start = start
        var end = end
        val base = start.data
        while (start !== end) {
            while (start !== end && base <= end.data) {
                end = end?.pre!!
            }
            start.data = end.data
            while (start !== end && base >= start.data) {
                start = start?.next!!
            }
            end.data = start.data
        }
        start.data = base
        return start
    }
}