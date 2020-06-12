package me.bytebeats.algorithms.kt.design

import java.util.*

class RandomizedSet() {//380

    /** Initialize your data structure here. */
    val map = mutableMapOf<Int, Int>()
    val list = mutableListOf<Int>()
    val rand = Random()

    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    fun insert(`val`: Int): Boolean {
        if (map.containsKey(`val`)) return false
        map[`val`] = list.size
        list.add(list.size, `val`)
        return true
    }

    /** Removes a value from the set. Returns true if the set contained the specified element. */
    fun remove(`val`: Int): Boolean {
        if (!map.containsKey(`val`)) return false
        val last = list.last()
        val idx = map[`val`] ?: 0
        map[last] = idx
        list[idx] = last
        list.removeAt(list.lastIndex)
        map.remove(`val`)
        return true
    }

    /** Get a random element from the set. */
    fun getRandom(): Int {
        val r = rand.nextInt(list.size)
        return list[r]
    }

}