package me.bytebeats.algo.kt

import kotlin.math.absoluteValue

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/9/7 19:30
 * @Version 1.0
 * @Description TO-DO
 */

class Solution13 {
    fun minimumSwitchingTimes(source: Array<IntArray>, target: Array<IntArray>): Int {
        val sm = map(source)
        val tm = map(target)
        sm.forEach { (t, u) ->
            if (tm.containsKey(t)) {
                tm[t] = (tm[t]!! - u).absoluteValue
            } else {
                tm[t] = u
            }
        }
        return tm.values.sum() / 2
    }

    private fun map(src: Array<IntArray>): MutableMap<Int, Int> {
        val map = mutableMapOf<Int, Int>()
        for (ints in src) {
            for (i in ints) {
                map.compute(i) { _, v -> if (v == null) 1 else v + 1 }
            }
        }
        return map
    }

    fun maxmiumScore(cards: IntArray, cnt: Int): Int {
        val evens = mutableListOf<Int>()
        val odds = mutableListOf<Int>()
        for (card in cards) {
            if (card and 1 == 0) {
                evens.add(card)
            } else {
                odds.add(card)
            }
        }
        evens.sort()
        odds.sort()
        if (cnt > evens.size && (cnt - evens.size) and 1 == 0 || evens.isEmpty() && cnt and 1 == 1) {
            println("$cnt, ${cnt - evens.size}")
            return 0
        }
        var sum = 0
        var i = 0
        while (i < cnt) {
            if (odds.size > 1) {
                val odd = odds.takeLast(2).sum()
                if (evens.isEmpty()) {
                    i += 2
                    sum += odd
                    odds.removeAt(odds.lastIndex)
                    odds.removeAt(odds.lastIndex)
                } else {
                    val even = evens.last()
                    if (even >= odd) {
                        sum += even
                        evens.removeAt(evens.lastIndex)
                        i++
                    } else {
                        i += 2
                        sum += odd
                        odds.removeAt(odds.lastIndex)
                        odds.removeAt(odds.lastIndex)
                    }
                }
            } else {
                sum += evens.removeAt(evens.lastIndex)
                i++
            }
        }
        return sum
    }
}