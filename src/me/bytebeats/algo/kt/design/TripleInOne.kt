package me.bytebeats.algo.kt.design

class TripleInOne(val stackSize: Int) {//面试题03.01
    val table = IntArray(stackSize * 3) { -1 }
    var sizes = IntArray(3) { 0 }

    fun push(stackNum: Int, value: Int) {
        if (sizes[stackNum] >= stackSize) {
            return
        }
        table[(sizes[stackNum]++) * 3 + stackNum] = value
    }

    fun pop(stackNum: Int): Int {
        if (isEmpty(stackNum)) {
            return -1
        } else {
            return table[3 * (--sizes[stackNum]) + stackNum]
        }
    }

    fun peek(stackNum: Int): Int {
        if (isEmpty(stackNum)) {
            return -1
        } else {
            return table[3 * (sizes[stackNum] - 1) + stackNum]
        }
    }

    fun isEmpty(stackNum: Int): Boolean {
        return sizes[stackNum] == 0
    }

}