package me.bytebeats.algo.kt.design

import java.util.*

class RandomWeightIndexPicker(w: IntArray) {
    //528
    val preSumArr = IntArray(w.size)
    var weightSum = 0
    val randGenerator = Random()

    init {
        if (w.isNotEmpty()) {
            preSumArr[0] = w[0]
            for (i in 1 until w.size) {
                preSumArr[i] = w[i] + preSumArr[i - 1]
            }
        }
        weightSum = preSumArr.last()
    }

    fun pickIndex(): Int {
        val rand = randGenerator.nextInt(weightSum)
        var low = 0
        var high = preSumArr.lastIndex
        var mid = 0
        while (low < high) {
            mid = low + (high - low) / 2
            if (preSumArr[mid] <= rand) {
                low = mid + 1
            } else {
                high = mid
            }
        }
        return low
    }
}