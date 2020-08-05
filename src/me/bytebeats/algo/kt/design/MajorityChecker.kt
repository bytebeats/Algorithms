package me.bytebeats.algo.kt.design

class MajorityChecker(val arr: IntArray) {
    fun query(left: Int, right: Int, threshold: Int): Int {//1157
        val map = mutableMapOf<Int, Int>()
        for (i in left..right) {
            map.compute(arr[i]) { _, v -> if (v == null) 1 else v + 1 }
        }
        val entries = map.entries.filter { it.value >= threshold }
        if (entries.isNotEmpty()) {
            return entries[0].value
        } else {
            return -1
        }
    }
}