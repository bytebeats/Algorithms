package me.bytebeats.algo.kt.design

class MaxQueue() {
    val array = IntArray(10000)
    var start = 0
    var end = 0

    fun max_value(): Int {
        if (start <= end) {
            return -1
        } else {
            var max = Int.MIN_VALUE
            for (i in start..end) {
                if (max < array[i]) {
                    max = array[i]
                }
            }
            return max
        }
    }

    fun push_back(value: Int) {
        array[end] = value
        end += 1
    }

    fun pop_front(): Int {
        if (start <= end) {
            return -1
        }
        val res = array[start]
        start += 1
        return res
    }

}