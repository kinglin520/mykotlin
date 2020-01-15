package com.example.mykotlin.basic.computer.treenode

interface Tree {
    //查找节点
    fun find(key: Int): Node

    //插入新节点
    fun insert(data: Int): Boolean

    //中序遍历
    fun infixOrder(current: Node)

    //前序遍历
    fun preOrder(current: Node)

    //后序遍历
    fun postOrder(current: Node)

    //查找最大值
    fun findMax(): Node

    //查找最小值
    fun findMin(): Node

    //删除节点
    fun delete(key: Int): Boolean
}