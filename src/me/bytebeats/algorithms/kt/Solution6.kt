package me.bytebeats.algorithms.kt

import me.bytebeats.algorithms.meta.Node

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
        if (matrix.isNotEmpty() && matrix[0].isNotEmpty()) {
            for (i in matrix.indices) {
                for (j in matrix[0].indices) {
                    if (i > 0 && j > 0 && matrix[i][j] != matrix[i - 1][j - 1]) {
                        return false
                    }
                }
            }
        }
        return true
    }

    private val visited = mutableMapOf<Node, Node>()
    fun cloneGraph(node: Node?): Node? {//133
        if (node == null) {
            return null
        }
        if (visited.containsKey(node)) {
            return visited[node]
        }
        val cloned = Node(node.`val`)
        cloned.neighbors = ArrayList()
        visited[node] = cloned
        if (node.neighbors != null) {
            for (neighbor in node.neighbors!!) {
                cloned?.neighbors?.add(cloneGraph(neighbor))
            }
        }
        return cloned
    }

    fun validWordSquare(words: List<String>): Boolean {//422
        if (words.isNotEmpty()) {
            val row = words.size
            val column = words.map { it.length }.max()
            if (row != column) {
                return false
            }
            for (i in 0 until row) {
                if (words[i].length != row) {
                    val sb = StringBuilder(words[i])
                    for (j in 0 until row - words[i].length) {
                        sb.append("0")
                    }
                }
            }
        }
        return true
    }

    fun removeKdigits(num: String, k: Int): String {//402
        if (num.isEmpty() || num.length <= k) {
            return "0"
        }
        if (k <= 0) {
            return num
        }
        var ans = StringBuilder()
        ans.append(num)
        var count = k
        var index = -1
        while (count-- > 0) {
            index = -1
            for (i in 0 until ans.length - 1) {
                if (ans[i] > ans[i + 1]) {
                    index = i
                    break
                }
            }
            ans.deleteCharAt(if (index == -1) ans.lastIndex else index)
        }
        while (ans.isNotEmpty() && ans.first() == '0') {
            ans.deleteCharAt(0)
        }
        if (ans.isEmpty()) {
            ans.append('0')
        }
        return ans.toString()
    }

//    fun maxNumber(nums1: IntArray, nums2: IntArray, k: Int): IntArray {//321
//
//    }

    fun monotoneIncreasingDigits(N: Int): Int {//738
        val chs = N.toString().toCharArray()
        var i = -1
        for (j in 0 until chs.lastIndex) {
            if (chs[j] > chs[j + 1]) {
                i = j
                break
            }
        }
        if (i == -1) {
            return N
        }
        while (i > 0 && chs[i] == chs[i - 1]) {
            i--
        }
        chs[i]--
        for (j in i + 1 until chs.size) {
            chs[j] = '9'
        }
        return String(chs).toInt()
    }

    fun letterCasePermutation(S: String): List<String> {//784
        val ans = mutableListOf<String>()
        val sb = StringBuilder(S)
        return ans
    }

    fun areSentencesSimilar(words1: Array<String>, words2: Array<String>, pairs: List<List<String>>): Boolean {//734
        if (words1.size != words2.size) {
            return false
        }
        for (i in words1.indices) {
            if (words1[i] == words2[i]) {
                continue
            } else {
                var flag = false
                for (j in pairs.indices) {
                    if (pairs[j][0] == words1[i] && pairs[j][1] == words2[i] || pairs[j][1] == words1[i] && pairs[j][0] == words2[i]) {
                        flag = true
                        break
                    }
                }
                if (!flag) {
                    return false
                }
            }
        }
        return true
    }

    fun areSentencesSimilarTwo(words1: Array<String>, words2: Array<String>, pairs: List<List<String>>): Boolean {//737
        if (words1.size != words2.size) {
            return false
        }
        val graph = mutableMapOf<String, MutableList<String>>()
        for (pair in pairs) {
            for (word in pair) {
                if (!graph.containsKey(word)) {
                    graph[word] = mutableListOf()
                }
            }
            graph[pair[0]]?.add(pair[1])
            graph[pair[1]]?.add(pair[0])
        }
        var word1 = ""
        var word2 = ""
        for (i in words1.indices) {
            word1 = words1[i]
            word2 = words2[i]
            val stack = mutableListOf<String>()
            val seen = mutableSetOf<String>()
            stack.add(word1)
            seen.add(word1)
            search@ while (stack.isNotEmpty()) {
                val word = stack.removeAt(stack.lastIndex)
                if (word == word2) break@search
                if (graph.containsKey(word)) {
                    for (similar in graph[word]!!) {
                        if (!seen.contains(similar)) {
                            stack.add(similar)
                            seen.add(similar)
                        }
                    }
                }
                if (stack.isEmpty()) {
                    return false
                }
            }
        }
        return true
    }

    fun replaceWords(dict: List<String>, sentence: String): String {//648
        val words = sentence.split(" ")
        val ans = StringBuilder()
        for (word in words) {
            val roots = dict.filter { word.length > it.length && word.startsWith(it) }
            if (roots.isEmpty()) {
                ans.append(word)
                ans.append(" ")
            } else {
                ans.append(roots.sortedBy { it.length }.first())
                ans.append(" ")
            }
        }
        return ans.trim().toString()
    }

    fun subarraysDivByK(A: IntArray, K: Int): Int {//974
        var ans = 0
        val size = A.size
        A[0] %= K
        for (i in 1 until size) {
            A[i] = (A[i] + A[i - 1]) % K
        }
        val count = IntArray(K)
        count[0]++
        for (num in A) {
            count[(num % K + K) % K]++
        }
        for (c in count) {
            ans += c * (c - 1) / 2
        }
        return ans
    }

    fun maxSubarraySumCircular(A: IntArray): Int {//918
        val s = A.size
        var ans = A[0]
        var cur = A[0]
        for (i in 1 until s) {
            cur = A[i] + Math.max(cur, 0)
            ans = Math.max(ans, cur)
        }
        val rightSums = IntArray(s)
        rightSums[s - 1] = A[s - 1]
        for (i in s - 2 downTo 0) {
            rightSums[i] = rightSums[i + 1] + A[i]
        }
        val maxRight = IntArray(s)
        maxRight[s - 1] = rightSums[s - 1]
        for (i in s - 2 downTo 0) {
            maxRight[i] = Math.max(maxRight[i + 1], rightSums[i])
        }
        var leftSum = 0
        for (i in 0 until s - 2) {
            leftSum += A[i]
            ans = Math.max(ans, leftSum + maxRight[i + 2])
        }
        return ans
    }

    /**
     * 连续子数组的最大值
     */
    fun kadane(A: IntArray): Int {
        var curVal = A[0]
        var ans = A[0]
        for (i in 1 until A.size) {
            curVal = Math.max(A[i], curVal + A[i])
            ans = Math.max(ans, curVal)
        }
        return ans
    }

    fun sumEvenAfterQueries(A: IntArray, queries: Array<IntArray>): IntArray {//985
        val ans = IntArray(queries.size)
        var sum = A.filter { it and 1 == 0 }.sum()
        for (i in queries.indices) {
            if (A[queries[i][1]] and 1 == 0) {
                sum -= A[queries[i][1]]
            }
            A[queries[i][1]] += queries[i][0]
            if (A[queries[i][1]] and 1 == 0) {
                sum += A[queries[i][1]]
            }
            ans[i] = sum
        }
        return ans
    }

    fun isMonotonic(A: IntArray): Boolean {//896
        var asd = false
        var desc = false
        for (i in 1 until A.size) {
            if (A[i - 1] < A[i]) {
                asd = true
            }
            if (A[i - 1] > A[i]) {
                desc = true
            }
            if (asd && desc) {
                return false
            }
        }
        return true
    }

    fun countSubstrings(s: String): Int {//647
        var ans = 0
        for (i in s.indices) {
            var j = i
            while (j < s.length && s[i] == s[j]) {
                ans++
                j++
            }
            var k = i - 1
            while (k > -1 && j < s.length && s[k] == s[j]) {
                k--
                j++
                ans++
            }
        }
        return ans
    }

//    fun subsetsWithDup(nums: IntArray): List<List<Int>> {//90
//
//    }
}