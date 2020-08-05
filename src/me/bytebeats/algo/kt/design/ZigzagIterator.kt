package me.bytebeats.algo.kt.design

class ZigzagIterator(val v1: IntArray, val v2: IntArray) { //281
    var size = v1.size + v2.size
    var i = 0
    var j = 0

    fun next(): Int {
        size--
        if (i > v1.lastIndex) {
            return v2[j++]
        } else if (j > v2.lastIndex) {
            return v1[i++]
        } else {
            if (i > j) {
                return v2[j++]
            } else {
                return v1[i++]
            }
        }
    }

    fun hasNext(): Boolean = size > 0
}