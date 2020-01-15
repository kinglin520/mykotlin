package com.example.mykotlin.basic

fun main(args:Array<String>){
    Color.BLUE
    ProtocolState.TALKING
}

/**
 * 枚举
 * */
enum class ProtocolState {
    WAITING {
        override fun signal() = TALKING
    },

    TALKING {
        override fun signal() = WAITING
    };

    abstract fun signal(): ProtocolState
}

enum class Color(val rgb: Int) {
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF)
}

enum class Direction {
    NORTH, SOUTH, WEST, EAST
}

