package com.example.mykotlin.basic.computer.lrucache

import java.util.*


fun main(args: Array<String>) {
    val lruCache = LRUCache(4)
    lruCache.put("1", "h")
    lruCache.put("2", "e")
    lruCache.put("3", "l")
    lruCache.disPlay()
    lruCache.put("1", "H")
    lruCache.put("4", "o")

    lruCache.disPlay()
    //        lruCache.put("4", "o");
    //        lruCache.put("5", "W");

    //        lruCache.disPlay();
}

class LRUCache(
    /**
     * 缓存长度
     */
    var capacity: Int = 0
) {
    var map: MutableMap<String, LRUNode> = HashMap()
    var head: LRUNode? = null
    var tail: LRUNode? = null

    /**
     * 将值存入map，如果已存在更新值，并操作链表
     */
    fun put(key: String, value: Any) {
        if (head == null) {
            head = LRUNode(key, value)
            tail = head
            map[key] = head!!
        }
        val node = map[key]
        if (node != null) {
            // 更新节点新值
            node.value = value

            removeAndInsert(node)

        } else {
            val tmp = LRUNode(key, value)
            // 缓存长度已满
            // 删除尾部节点
            if (map.size >= capacity) {
                map.remove(tail?.key)
                tail = tail?.pre
                tail?.next = null
            }
            map[key] = tmp
            // 并插入到头部
            tmp.next = head
            head?.pre = tmp
            head = tmp
        }
    }

    /**
     * 从map中获取node，并操作链表，删除node和将node插入到头节点
     */
    operator fun get(key: String): Any? {
        val node = map[key] ?: return null
        removeAndInsert(node)
        return node.value
    }

    /**
     * 删除当前节点，并插入到头节点
     */
    fun removeAndInsert(node: LRUNode) {
        // 特殊情况先判断，例如该节点是头结点或是尾部节点
        if (node === head) {
            return
        } else if (node === tail) {
            tail = node.pre
            tail?.next = null
        } else {
            node.pre?.next = node.next
            node.next?.pre = node.pre
        }
        // 将节点插入到头部
        head?.pre = node
        node.next = head
        node.pre = null
        head = node
    }

    fun disPlay() {
        print("\n")
        for (o in map.values) {
            print(o.value.toString() + ",")
        }
        print("\n")
        var current: LRUNode? = head
        while (current != null) {
            System.out.print(current.value)
            current = current.next
        }
    }

}