package me.bytebeats.algo.kt

import kotlin.math.log2

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
            var j = 0
            val size = s.length
            for (i in 0 until size) {
                if (i != 0) {
                    set.remove(s[i - 1])
                }
                while (j < size && !set.contains(s[j])) {
                    set.add(s[j])
                    j += 1
                }
                if (set.size > max) {
                    max = set.size
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
        for (i in 2..n) {
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

    fun halvesAreAlike(s: String): Boolean {//1704
        val half = s.length / 2
        var vowels1 = 0
        var vowels2 = 0
        for (i in 0 until half) {
            if (isVowel(s[i].toLowerCase())) {
                vowels1 += 1
            }
            if (isVowel(s[i + half])) {
                vowels2 += 1
            }
        }
        return vowels1 == vowels2
    }

    private fun isVowel(ch: Char): Boolean {
        return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u'
                || ch == 'A' || ch == 'E' || ch == 'I' || ch == 'O' || ch == 'U'
    }

    fun closeStrings(word1: String, word2: String): Boolean {//1657
        val length = word1.length
        if (length != word2.length) return false
        val arr1 = IntArray(26)
        val arr2 = IntArray(26)
        for (i in 0 until length) {
            arr1[word1[i] - 'a'] += 1
            arr2[word2[i] - 'a'] += 1
        }
        for (i in 0 until 26) {
            if (arr1[i] != 0 && arr2[i] == 0 || arr1[i] == 0 && arr2[i] != 0) {
                return false
            }
        }
        arr1.sort()
        arr2.sort()
        for (i in 0 until 26) {
            if (arr1[i] != arr2[i]) {
                return false
            }
        }
        return true
    }

    fun concatenatedBinary(n: Int): Int {//1680
        val mod = 1000000007
        var res = 0
        var shift = 0
        for (i in 1..n) {
            if (i and (i - 1) == 0) {
                shift += 1
            }
            res = (((res.toLong() shl shift) + i) % mod).toInt()
        }
        return res
    }

    fun getSmallestString(n: Int, k: Int): String {//1663
        val ans = CharArray(n) { 'a' }
        val bound = (k - n) / 25
        val rest = (k - n) % 25
        if (n - bound - 1 >= 0) ans[n - bound - 1] = 'a' + rest
        for (i in n - bound until n) {
            ans[i] = 'z'
        }
        return String(ans)
    }

    fun medianSlidingWindow(nums: IntArray, k: Int): DoubleArray {//480
        val size = nums.size
        val window = mutableListOf<Int>()
        val ans = DoubleArray(size - k + 1)
        for (i in 0 until k - 1) {
            window.add(nums[i])
        }
        window.sort()
        for (i in 0..size - k) {
            val e = nums[i + k - 1]
            if (window.isEmpty()) {
                window.add(e)
            } else if (e <= window.first()) {
                window.add(0, e)
            } else if (e >= window.last()) {
                window.add(e)
            } else {
                for (j in 0 until k - 1) {
                    if (e <= window[j]) {
                        window.add(j, e)
                        break
                    }
                }
            }
            if (k and 1 == 0) {
                ans[i] = (window[(k - 1) / 2].toDouble() + window[(k - 1) / 2 + 1].toDouble()) / 2.0
            } else {
                ans[i] = window[(k - 1) / 2].toDouble()
            }
            for (j in 0 until k) {
                if (window[j] == nums[i]) {
                    window.removeAt(j)
                    break
                }
            }
        }
        return ans
    }

    fun simplifyPath(path: String): String {//71
        val stack = mutableListOf<String>()
        for (p in path.split("/")) {
            if (p.isEmpty()) {
                continue
            } else if (p == ".") {
                continue
            } else if (p == "..") {
                if (stack.isNotEmpty()) {
                    stack.removeAt(stack.lastIndex)
                }
            } else {
                stack.add(p)
            }
        }
        return stack.joinToString(separator = "/", prefix = "/")
    }

    fun validateStackSequences(pushed: IntArray, popped: IntArray): Boolean {//946
        val stack = mutableListOf<Int>()
        val size = pushed.size
        var j = 0
        for (i in 0 until size) {
            stack.add(pushed[i])
            while (stack.isNotEmpty() && j < size && stack.last() == popped[j]) {
                stack.removeAt(stack.lastIndex)
                j++
            }
        }
        return j == size
    }

    fun evalRPN(tokens: Array<String>): Int {//150
        val stack = mutableListOf<Int>()
        var first = 0
        var second = 0
        for (token in tokens) {
            if (token == "+") {
                second = stack.removeAt(stack.lastIndex)
                first = stack.removeAt(stack.lastIndex)
                stack.add(first + second)
            } else if (token == "-") {
                second = stack.removeAt(stack.lastIndex)
                first = stack.removeAt(stack.lastIndex)
                stack.add(first - second)
            } else if (token == "*") {
                second = stack.removeAt(stack.lastIndex)
                first = stack.removeAt(stack.lastIndex)
                stack.add(first * second)
            } else if (token == "/") {
                second = stack.removeAt(stack.lastIndex)
                first = stack.removeAt(stack.lastIndex)
                stack.add(first / second)
            } else {
                stack.add(token.toInt())
            }
        }
        return stack.last()
    }

    fun wordSubsets(A: Array<String>, B: Array<String>): List<String> {//916
        val ans = mutableListOf<String>()
        val bmax = count("")
        for (s in B) {
            val bCount = count(s)
            for (i in 0 until 26) {
                bmax[i] = bmax[i].coerceAtLeast(bCount[i])
            }
        }
        for (s in A) {
            val aCount = count(s)
            var toAdd = true
            for (i in 0 until 26) {
                if (aCount[i] < bmax[i]) {
                    toAdd = false
                    break
                }
            }
            if (toAdd) {
                ans.add(s)
            }
        }
        return ans
    }

    private fun count(s: String): IntArray {
        val ans = IntArray(26)
        for (c in s) {
            ans[c - 'a'] += 1
        }
        return ans
    }

    fun clumsy(N: Int): Int {//1006
        var ans = 0
        var n = N
        var plus = true
        while (n > 3) {
            val tmp = n * (n - 1) / (n - 2)
            if (plus) {
                plus = !plus
                ans += tmp
            } else {
                ans -= tmp
            }
            ans += n - 3
            n -= 4
        }
        if (plus)
            when (n) {
                3 -> ans += 3 * 2 / 1
                2 -> ans += 2 * 1
                1 -> ans += 1
            }
        else
            when (n) {
                3 -> ans -= 3 * 2 / 1
                2 -> ans -= 2 * 1
                1 -> ans -= 1
            }
        return ans
    }

    fun trap(height: IntArray): Int {//17.21
        var ans = 0
        var left = 0
        var right = height.size - 1
        var leftMax = 0
        var rightMax = 0
        while (left < right) {
            leftMax = leftMax.coerceAtLeast(height[left])
            rightMax = rightMax.coerceAtLeast(height[right])
            if (height[left] < height[right]) {
                ans += leftMax - height[left]
                left += 1
            } else {
                ans += rightMax - height[right]
                right -= 1
            }
        }
        return ans
    }

    fun findMaxForm(strs: Array<String>, m: Int, n: Int): Int {//474
        val dp = Array(m + 1) { IntArray(n + 1) }
        for (str in strs) {
            val cs = countDigits(str)
            for (zeros in m downTo cs[0]) {
                for (ones in n downTo cs[1]) {
                    dp[zeros][ones] = dp[zeros][ones].coerceAtLeast(1 + dp[zeros - cs[0]][ones - cs[1]])
                }
            }

        }
        return dp[m][n]
    }

    private fun countDigits(str: String): IntArray {
        val cs = IntArray(2)
        for (c in str) {
            cs[c - '0']++
        }
        return cs
    }

    fun purchasePlans(nums: IntArray, target: Int): Int {
        val mod = 1000000007
        var ans = 0
        nums.sort()
        var i = 0
        var j = nums.lastIndex
        while (i < j) {
            var icount = 1
            while (i + 1 < j && nums[i + 1] == nums[i]) {
                i += 1
                icount += 1
            }
            var jcount = 1
            while (i < j - 1 && nums[j - 1] == nums[j]) {
                j -= 1
                jcount += 1
            }
            if (nums[i] + nums[j] <= target) {
                ans += icount * jcount
                ans %= mod
                ans = (ans + mod) % mod
                i += 1
                j -= 1
            } else {
                j -= 1
            }
        }
        return ans
    }

    fun purchasePlans2(nums: IntArray, target: Int): Int {
        val mod = 1000000007
        var ans = 0
        val size = nums.size
        for (i in 0 until size - 1) {
            for (j in i + 1 until size) {
                if (nums[i] + nums[j] <= target) {
                    ans += 1
                } else break
            }
        }
        return (ans % mod + mod) % mod
    }

    fun minOperations(n: Int): Int {//1551
        var ans = 0
        for (i in 0 until n) {
            val x = 2 * i + 1
            if (x > n) {
                ans += x - n
            }
        }
        return ans
    }
}