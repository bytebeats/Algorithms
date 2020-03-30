package me.bytebeats.algorithms.designs

import java.util.function.BiFunction

class HitCounter() {

    /** Initialize your data structure here. */
    private val counter = HashMap<Int, Int>()

    /** Record a hit.
    @param timestamp - The current timestamp (in seconds granularity). */
    fun hit(timestamp: Int) {
        counter.compute(timestamp, BiFunction { k: Int, u: Int? -> u?.plus(1) ?: 1 })
    }

    /** Return the number of hits in the past 5 minutes.
    @param timestamp - The current timestamp (in seconds granularity). */
    fun getHits(timestamp: Int): Int = counter.filter { timestamp - it.key < 300 }.map { it.value }.sum()

}