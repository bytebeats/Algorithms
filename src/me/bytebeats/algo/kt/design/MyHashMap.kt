package me.bytebeats.algo.kt.design

class MyHashMap() {//706

    /** Initialize your data structure here. */
    private val mod = 2069

    private val buckets = Array<MutableList<Pair>>(mod) { mutableListOf() }

    private fun hash(key: Int): Int = (key % mod + mod) % mod

    /** value will always be non-negative. */
    fun put(key: Int, value: Int) {
        val hashKey = hash(key)
        val bucket = buckets[hashKey]
        var found = false
        for (pair in bucket) {
            if (pair.first == key) {
                pair.second = value
                found = true
            }
        }
        if (!found) {
            bucket.add(Pair(key, value))
        }
    }

    /** Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key */
    fun get(key: Int): Int {
        val hashKey = hash(key)
        val bucket = buckets[hashKey]
        for (pair in bucket) {
            if (pair.first == key) {
                return pair.second
            }
        }
        return -1
    }

    /** Removes the mapping of the specified value key if this map contains a mapping for the key */
    fun remove(key: Int) {
        val hashKey = hash(key)
        val bucket = buckets[hashKey]
        for (pair in bucket) {
            if (pair.first == key) {
                bucket.remove(pair)
                break
            }
        }
    }

    class Pair(val first: Int, var second: Int)

}