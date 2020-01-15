package com.example.mykotlin.basic.computer.treenode

class Node(var data: Int = 0) {
    //    var data: Int = 0   //节点数据
    var leftChild: Node? = null //左子节点的引用
    var rightChild: Node? = null //右子节点的引用
    var parent: Node? = null
    var color = true
    var isDelete: Boolean = false
    //表示节点是否被删除

//    constructor(data: Int) {
//        this.data = data
//    }

    //打印节点内容
    fun display() {
        println(data)
    }

    fun getColor(): String {
        return if (color) {
            "red"
        } else {
            "black"
        }
    }
}