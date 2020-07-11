package me.bytebeats.algorithms.kt.design

class RecentCounter() {
    //933
    val pings = mutableListOf<Int>()
    fun ping(t: Int): Int {
        pings.add(t)
        var left = 0
        var right = pings.size - 1
        var mid = 0
        while (left < right) {
            mid = left + (right - left) / 2
            if (pings[mid] + 3000 < t) {
                left = mid + 1
            } else if (pings[mid] + 3000 >= t) {
                right = mid
            }
        }
        return pings.size - left
    }
}