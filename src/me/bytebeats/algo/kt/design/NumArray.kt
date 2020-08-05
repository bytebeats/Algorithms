package me.bytebeats.algo.kt.design

class NumArray(nums: IntArray) {//303

    val preSumMap = mutableMapOf<Int, Int>()

    init {
        preSumMap[-1] = 0
        var sum = 0
        for (i in nums.indices) {
            sum += nums[i]
            preSumMap[i] = sum
        }
    }

    fun sumRange(i: Int, j: Int): Int = preSumMap[j]!! - preSumMap[i - 1]!!

}

class NumArray2(val nums: IntArray) {//303

    init {
        for (i in 1 until nums.size) {
            nums[i] += nums[i - 1]
        }
    }

    fun sumRange(i: Int, j: Int): Int = if (i == 0) nums[j] else nums[j] - nums[i - 1]

}

class NumArray3(val nums: IntArray) { //307
    val preSumMap = mutableMapOf<Int, Int>()

    init {
        preSumMap[-1] = 0
        var sum = 0
        for (i in nums.indices) {
            sum += nums[i]
            preSumMap[i] = sum
        }
    }

    fun update(i: Int, `val`: Int) {
        val diff = `val` - nums[i]
        nums[i] = `val`
        for (j in i until nums.size) {
            preSumMap[j] = preSumMap[j]?.plus(diff) ?: 0
        }
    }

    fun sumRange(i: Int, j: Int): Int = preSumMap[j]!! - preSumMap[i - 1]!!

}