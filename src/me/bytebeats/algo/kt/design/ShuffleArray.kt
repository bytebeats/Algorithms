package me.bytebeats.algo.kt.design

import kotlin.random.Random

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/7/20 19:48
 * @Version 1.0
 * @Description TO-DO
 */

class ShuffleArray(private val nums: IntArray) {//384
    private val random = Random(0)

    /** Resets the array to its original configuration and return it. */
    fun reset(): IntArray {
        return nums
    }

    /** Returns a random shuffling of the array. */
    fun shuffle(): IntArray {
        val size = nums.size
        val ans = IntArray(size)
        var i = 0
        val list = nums.toMutableList()
        while (list.isNotEmpty()) {
            ans[i++] = list.removeAt(random.nextInt(list.size))
        }
        return ans
    }

}