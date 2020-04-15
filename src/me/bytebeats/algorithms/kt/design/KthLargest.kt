package me.bytebeats.algorithms.kt.design

class KthLargest(val k: Int, val nums: IntArray) {//703

    private val list = mutableListOf<Int>()

    init {
        list.addAll(nums.toList())
    }

    fun add(`val`: Int): Int {
        list.add(`val`)
        list.sort()
        return list[list.size - k]
    }

}