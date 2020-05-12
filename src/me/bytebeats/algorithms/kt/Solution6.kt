package me.bytebeats.algorithms.kt

class Solution6 {

    fun nextGreatestLetter(letters: CharArray, target: Char): Char {//744, O(n)
        var index = -1
        for (i in letters.indices) {
            if (letters[i] > target) {
                index = i
                break
            }
        }
        return if (index > -1) letters[index] else letters[0]
    }

    fun nextGreatestLetter1(letters: CharArray, target: Char): Char {//744, O(logn)
        var low = 0
        var high = letters.size
        var mid = 0
        while (low < high) {
            mid = low + (high - low) / 2
            if (letters[mid] <= target) low = mid + 1
            else high = mid
        }
        return letters[low % letters.size]
    }

    fun isToeplitzMatrix(matrix: Array<IntArray>): Boolean {//766
        return false
    }
}