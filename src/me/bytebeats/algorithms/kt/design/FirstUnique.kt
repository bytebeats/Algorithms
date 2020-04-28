package me.bytebeats.algorithms.kt.design

class FirstUnique(nums: IntArray) {//time limitation exception
    val values = mutableListOf<Int>()
    val repeatedValues = sortedSetOf<Int>()

    init {
        for (i in nums.indices) {
            if (repeatedValues.contains(nums[i])) {
                continue
            } else if (values.contains(nums[i])) {
                values.remove(nums[i])
                repeatedValues.add(nums[i])
            } else {
                values.add(nums[i])
            }
        }
    }

    fun showFirstUnique(): Int {
        if (values.isEmpty()) {
            return -1
        } else {
            return values.first()
        }
    }

    fun add(value: Int) {
        if (repeatedValues.contains(value)) {
            return
        } else if (values.contains(value)) {
            values.remove(value)
            repeatedValues.add(value)
        } else {
            values.add(value)
        }
    }

}