package me.bytebeats.algorithms.designs

class MedianFinder() {

    /** initialize your data structure here. */
    private val numbers = ArrayList<Int>()

    fun addNum(num: Int) {
        var index = -1
        for (i in 0 until numbers.size) {
            if (num < numbers[i]) {
                index = i
                break
            }
        }
        if (index < 0) {
            numbers.add(num)
        } else {
            numbers.add(index, num)
        }
    }

    fun findMedian(): Double {
        println(numbers)
        val size = numbers.size
        return if (size % 2 == 1) {
            numbers[size.div(2)].toDouble()
        } else {
            if (size > 0) {
                val midIndex = size / 2
                (numbers[midIndex] + numbers[midIndex - 1]).div(2.0)
            } else {
                0.0
            }
        }
    }
}