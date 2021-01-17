package me.bytebeats.algo.kt

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2020/12/14 20:31
 * @Version 1.0
 * @Description TO-DO
 */

class Solution12 {
    fun findSubArray(arr: IntArray, target: Int): Int {
        var minL = Int.MAX_VALUE
        val size = arr.size
        for (i in 0 until size) {
            var sum = arr[i]
            var tmp = 1
            for (j in i + 1 until size) {
                if (sum >= target && tmp < minL) {
                    minL = tmp
                    break
                }
                sum += arr[j]
                tmp += 1
            }
            if (sum >= target && tmp < minL) {
                minL = tmp
            }
        }
        return minL
    }

    fun findSubArray1(arr: IntArray, target: Int): Int {
        var minL = Int.MAX_VALUE
        var i = 0
        var j = 0
        var sum = 0
        while (j < arr.size) {
            sum += arr[j]
            if (sum >= target) {
                if (j - i + 1 < minL) {
                    minL = j - i + 1
                }
                while (sum >= target) {
                    sum -= arr[i++]
                }
            }
            j++
        }
        return minL
    }

    fun lengthOfLongestSubstring(s: String): Int {//3
        var max = 0
        if (s.isNotEmpty()) {
            val set = mutableSetOf<Char>()
            for (c in s) {
                if (set.contains(c)) {
                    set.clear()
                    set.add(c)
                } else {
                    set.add(c)
                    max = max.coerceAtLeast(set.size)
                }
            }
        }
        return max
    }

    fun arrayStringsAreEqual(word1: Array<String>, word2: Array<String>): Boolean {//1662
        return word1.joinToString(separator = "") == word2.joinToString(separator = "")
    }

    fun getMaximumGenerated(n: Int): Int {//1646
        if (n < 2) {
            return n
        }
        val arr = IntArray(n + 1)
        arr[0] = 0
        arr[1] = 1
        var max = 1
        for (i in 2 .. n) {
            if (i and 1 == 0) {
                arr[i] = arr[i / 2]
            } else {
                arr[i] = arr[i / 2] + arr[i / 2 + 1]
            }
            max = max.coerceAtLeast(arr[i])
        }
        return max
    }

    fun countVowelStrings(n: Int): Int {//1641
        return (n + 4) * (n + 3) * (n + 2) * (n + 1) / 24
    }
}