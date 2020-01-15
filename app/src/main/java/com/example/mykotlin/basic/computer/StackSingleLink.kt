package com.example.mykotlin.basic.computer


fun main(args: Array<String>) {
    val stackSingleLink = StackSingleLink()
    stackSingleLink.push(1)
    stackSingleLink.push(2)
    stackSingleLink.push(3)
    while (!stackSingleLink.isEmpty()) {
        print(stackSingleLink.pop().toString() + ",")
    }
}

class StackSingleLink {
    var singleLinkList: SingleLinkList = SingleLinkList()

    fun push(value: Any) {
        singleLinkList.addHead(value)
    }

    fun pop(): Any? {
        return singleLinkList.deleteFromTHead()
    }

    fun isEmpty(): Boolean {
        return singleLinkList.isEmpty()
    }

}