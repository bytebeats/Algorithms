package me.bytebeats.algo.kt.design

class LRUCache(val capacity: Int) : LinkedHashMap<Int, Int>(capacity, 0.75F, true) {//面试题16.25

    override fun get(key: Int): Int = super.getOrDefault(key, -1)

    override fun put(key: Int, value: Int): Int? = super.put(key, value)

    override fun removeEldestEntry(eldest: MutableMap.MutableEntry<Int, Int>?): Boolean {
        return size > capacity
    }
}