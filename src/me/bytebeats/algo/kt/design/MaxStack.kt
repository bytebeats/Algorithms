package me.bytebeats.algo.kt.design

class MaxStack() {

    /** initialize your data structure here. */
    val table = ArrayList<Int>()


    fun push(x: Int) {
        table.add(x)
    }

    fun pop(): Int = table.removeAt(table.lastIndex)

    fun top(): Int = table.last()

    fun peekMax(): Int = table.max() ?: 0

    fun popMax(): Int {
        val max = peekMax()
        var indexOfMaxVal = 0
        for (i in table.lastIndex downTo 0) {
            if (max == table[i]) {
                indexOfMaxVal = i
                break
            }
        }
        return table.removeAt(indexOfMaxVal)
    }

}