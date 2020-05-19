package me.bytebeats.algorithms.kt.design

class CQueue() {//面试题09
    val push = mutableListOf<Int>()
    val pop = mutableListOf<Int>()

    var isPushing = false

    fun appendTail(value: Int) {
        if (!isPushing) {
            isPushing = true
            while (pop.isNotEmpty()) {
                push.add(pop.removeAt(pop.lastIndex))
            }
        }
        push.add(value)
    }

    fun deleteHead(): Int {
        if (isPushing) {
            isPushing = false
            while (push.isNotEmpty()) {
                pop.add(push.removeAt(push.lastIndex))
            }
        }
        if (pop.isEmpty()) {
            return -1
        }
        return pop.removeAt(pop.lastIndex)
    }

}