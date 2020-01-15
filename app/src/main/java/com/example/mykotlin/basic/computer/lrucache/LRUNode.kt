package com.example.mykotlin.basic.computer.lrucache

class LRUNode(var key: String, var value: Any) {
    var pre: LRUNode? = null
    var next: LRUNode? = null
}