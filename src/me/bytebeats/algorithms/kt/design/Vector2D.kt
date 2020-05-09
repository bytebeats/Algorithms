package me.bytebeats.algorithms.kt.design

class Vector2D(val v: Array<IntArray>) { //251
    var size = 0

    init {
        v.forEach {
            size += it.size
            it.reverse()
        }
        v.reverse()
    }


    fun next(): Int {
        var count = size
        for (i in v.indices) {
            if (count > v[i].size) {
                count -= v[i].size
            } else {
                size--
                return v[i][count - 1]
            }
        }
        return -1
    }

    fun hasNext(): Boolean = size > 0

}