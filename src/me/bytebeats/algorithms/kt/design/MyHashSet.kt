package me.bytebeats.algorithms.kt.design

class MyHashSet() {
    //705
    private val mod = 769

    /** Initialize your data structure here. */
    val table = Array<MutableList<Int>>(mod) { mutableListOf() }

    private fun hash(key: Int): Int = key % mod

    fun add(key: Int) {
        if (table[hash(key)].indexOf(key) == -1) {
            table[hash(key)].add(key)
        }
    }

    fun remove(key: Int) {
        if (table[hash(key)].indexOf(key) != -1) {
            table[hash(key)].remove(key)
        }
    }

    /** Returns true if this set contains the specified element */
    fun contains(key: Int): Boolean {
        return table[hash(key)].indexOf(key) != -1
    }

}