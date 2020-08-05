package me.bytebeats.algo.kt.design

interface IRelation {
    fun knows(a: Int, b: Int): Boolean = false

    fun findCelebrity(n: Int): Int
}

class Relation : IRelation {
    override fun findCelebrity(n: Int): Int {//277
        val degrees = IntArray(n) { 0 }
        for (i in 0 until n) {
            for (j in 0 until n) {
                if (knows(i, j)) {
                    degrees[i]--
                    degrees[j]++
                }
            }
        }
        for (i in 0 until n) {
            if (degrees[i] == n - 1) {
                return i
            }
        }
        return -1
    }
}


