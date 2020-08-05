package me.bytebeats.algo.designs

class SortedStack() {
    private val numbers = ArrayList<Int>()

    fun push(`val`: Int) {
        var index = -1;
        for (i in 0 until numbers.size) {
            if (`val` < numbers[i]) {
                index = i
                break
            }
        }
        if (index < 0) {
            numbers.add(`val`)
        } else {
            numbers.add(index, `val`)
        }
        println(numbers)
    }

    fun pop() {
        if (isEmpty()) {
            return
        }
        numbers.removeAt(numbers.size - 1)
    }

    fun peek(): Int {
        return if (isEmpty()) {
            0
        } else {
            numbers[numbers.size - 1]
        }
    }

    fun isEmpty() = numbers.isEmpty()

}