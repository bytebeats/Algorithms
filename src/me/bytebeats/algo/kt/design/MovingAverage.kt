package me.bytebeats.algo.kt.design

class MovingAverage(val size: Int) {//346

    /** Initialize your data structure here. */
    val table = mutableListOf<Int>()

    fun next(`val`: Int): Double {
        table.add(`val`)
        if (table.size > size) {
            return table.takeLast(size).sumByDouble { it.toDouble() } / size
        } else {
            return table.sumByDouble { it.toDouble() } / table.size
        }
    }

}