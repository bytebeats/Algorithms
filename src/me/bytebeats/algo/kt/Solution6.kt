package me.bytebeats.algo.kt

import kotlin.collections.ArrayList
import me.bytebeats.algs.ds.ListNode
import me.bytebeats.algs.ds.Node
import me.bytebeats.algs.ds.TreeNode

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
            val column = words.map { it.length }.maxOfOrNull { it }
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
    /**
     * K 个一组逆序链表
     *1->2->3->4->5 ==> 3->2->1->4->5
     * @param head
     * @param k
     * @return
     */
    fun reverseKGroup(head: ListNode?, k: Int): ListNode? {//25
        val dummy = ListNode(-1)
        dummy.next = head
        var pre: ListNode? = dummy
        var end: ListNode? = dummy
        while (end?.next != null) {
            var count = 0
            while (count < k && end != null) {
                end = end.next
                count++
            }
            if (end == null) break
            var start = pre?.next
            var next = end?.next
            end?.next = null
            pre?.next = reverse(start)
            start?.next = next
            pre = start
            end = pre
        }
        return dummy.next
    }

    private fun reverse(head: ListNode?): ListNode? {
        var pre: ListNode? = null
        var p = head
        while (p != null) {
            val next = p.next
            p.next = pre
            pre = p
            p = next
        }
        return pre
    }

    /**
     * 将单链表平均分成 k 组
     */
    fun splitListToParts(root: ListNode?, k: Int): Array<ListNode?> {//728
        var count = 0
        var p = root
        while (p != null) {//计算链表长度
            p = p.next
            count++
        }

        val s = count / k//每组 s 个元素
        var c = count % k//不能整除时, 余下的结点个数
        val ans = Array<ListNode?>(k) { null }
        p = root
        for (i in 0 until k) {//k 组
            ans[i] = p
            var left = s
            var pre = p
            while (left-- > 0) {//每组分配的结点
                pre = p
                p = p?.next
            }
            if (c > 0) {//余下的结点个数, 分配到前 c 个元素
                pre = p
                p = p?.next
                c--
            }
            pre?.next = null//将该组最后一个结点的引用去掉.
        }
        return ans
    }

    fun reorderList(head: ListNode?): Unit {//143
        var count = 0
        var p = head
        while (p != null) {
            count++
            p = p.next
        }
        val half = (count + 1) / 2
        var pre: ListNode? = null
        p = head
        var i = 0
        while (i++ < half) {
            pre = p
            p = p?.next
        }
        pre?.next = null
        p = reverse(p)
        var q = head
        while (p != null) {
            val next = q?.next
            q?.next = p
            p = p.next
            q?.next?.next = next
            q = next
        }
    }

    fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {//面试题02.05
        var count1 = 0
        var p = l1
        while (p != null) {
            count1++
            p = p.next
        }
        var count2 = 0
        var q = l2
        while (q != null) {
            count2++
            q = q.next
        }
        if (count1 > count2) {
            p = l1
            q = l2
        } else {
            p = l2
            q = l1
        }
        val head = p
        var c = 0
        var pre: ListNode? = null
        while (p != null && q != null) {
            p.`val` += q.`val` + c
            c = p.`val` / 10
            p.`val` %= 10
            pre = p
            p = p.next
            q = q.next
        }
        while (c != 0) {
            if (p != null) {
                p.`val` += c
                c = p.`val` / 10
                p.`val` %= 10
                pre = p
            } else {
                pre?.next = ListNode(c)
                break
            }
        }
        return head
    }

    fun getKthFromEnd(head: ListNode?, k: Int): ListNode? {//面试题22
        var count = 0
        var p = head
        while (p != null) {
            p = p.next
            count++
        }
        var diff = count - k
        if (diff < 0) {
            return null
        }
        p = head
        while (diff-- > -1) {
            p = p?.next
        }
        return p
    }

    fun maxPower(s: String): Int {//5396
        var max = 1
        var ch = s[0]
        var count = 1
        for (i in 1 until s.length) {
            if (ch == s[i]) {
                count++
                if (max < count) {
                    max = count
                }
            } else {
                ch = s[i]
                count = 1
            }
        }
        return max
    }

    fun simplifiedFractions(n: Int): List<String> {//5397,1447
        val ans = mutableListOf<String>()
        for (i in 2..n) {
            for (j in 1 until i) {
                if (j == 1 || gcd(i, j) == 1) {
                    ans.add("$j/$i")
                }
            }
        }
        return ans
    }

    private fun gcd(m: Int, n: Int): Int {
        var j = m
        var k = n
        var rem = 0
        while (k != 0) {
            rem = j % k
            j = k
            k = rem
        }
        return j
    }

    var count = 0
    fun goodNodes(root: TreeNode?): Int {//5398, 1448
        count = 0
        goodNodes(root, 0, true)
        return count
    }

    private fun goodNodes(root: TreeNode?, max: Int, isRoot: Boolean) {
        if (root == null) {
            return
        }
        if (isRoot) {
            count++
            goodNodes(root.left, root.`val`, false)
            goodNodes(root.right, root.`val`, false)
        } else {
            if (root.`val` >= max) {
                count++
            }
            val newMax = Math.max(root.`val`, max)
            goodNodes(root.left, newMax, false)
            goodNodes(root.right, newMax, false)
        }
    }

    var ans = "0"
    fun largestNumber(cost: IntArray, target: Int): String {//5399
        ans = "0"
        val map = sortedMapOf<Int, Int>()
        for (i in 8 downTo 0) {
            if (!map.containsValue(cost[i])) {
                map[i] = cost[i]
            }
        }
        largestNumber(map.entries.reversed(), target, StringBuilder())
        return ans
    }

    private fun largestNumber(cost: List<MutableMap.MutableEntry<Int, Int>>, target: Int, sb: StringBuilder) {
        if (target < 0) {
            return
        } else if (target == 0) {
            if (ans.length < sb.length) {
                ans = sb.toString()
            } else if (ans.length == sb.length) {
                if (ans < sb.toString()) {
                    ans = sb.toString()
                }
            }
        } else {
            cost.forEach {
                if (it.value <= target) {
                    val newSb = StringBuilder(sb)
                    newSb.append(it.key + 1)
                    largestNumber(cost, target - it.value, newSb)
                }
            }
        }
    }

    fun busyStudent(startTime: IntArray, endTime: IntArray, queryTime: Int): Int {//5412, 1450
        var count = 0
        for (i in startTime.indices) {
            if (queryTime in startTime[i]..endTime[i]) {
                count++
            }
        }
        return count
    }

    fun arrangeWords(text: String): String {//5413, 1451
        val ans = StringBuilder()
        val words = text.split(" ").sortedBy { it.length }
        for (i in words.indices) {
            if (i == 0) {
                val sb = StringBuilder(words[i])
                sb[0] = sb[0].toUpperCase()
                ans.append(sb)
            } else {
                ans.append(" ")
                if (words[i][0].isUpperCase()) {
                    ans.append(words[i].toLowerCase())
                } else {
                    ans.append(words[i])
                }
            }
        }
        return ans.toString()
    }

    fun peopleIndexes(favoriteCompanies: List<List<String>>): List<Int> {//5414, 1452
        var set = mutableSetOf<Int>()
        for (i in 0 until favoriteCompanies.lastIndex) {
            for (j in i + 1 until favoriteCompanies.size) {
                if (favoriteCompanies[i].size > favoriteCompanies[j].size
                    && contains(favoriteCompanies[i], favoriteCompanies[j])
                ) {
                    set.add(j)
                } else if (favoriteCompanies[i].size < favoriteCompanies[j].size
                    && contains(favoriteCompanies[j], favoriteCompanies[i])
                ) {
                    set.add(i)
                }
            }
        }
        val ans = mutableListOf<Int>()
        for (i in favoriteCompanies.indices) {
            ans.add(i)
        }
        ans.removeAll(set)
        return ans
    }

    private fun contains(long: List<String>, short: List<String>): Boolean {
        for (i in short.indices) {
            if (!long.contains(short[i])) {
                return false
            }
        }
        return true
    }

    fun canFinish(numCourses: Int, prerequisites: Array<IntArray>): Boolean {//207
        val inDegrees = IntArray(numCourses)
        val graph = mutableMapOf<Int, MutableList<Int>>()
        for (prerequisite in prerequisites) {
            inDegrees[prerequisite[0]]++
            if (graph.containsKey(prerequisite[1])) {
                graph[prerequisite[1]]?.add(prerequisite[0])
            } else {
                val dependencies = mutableListOf<Int>()
                dependencies.add(prerequisite[0])
                graph[prerequisite[1]] = dependencies
            }
        }
        val q = mutableListOf<Int>()
        for (i in 0 until numCourses) {
            if (inDegrees[i] == 0) q.add(i)
        }
        while (q.isNotEmpty()) {
            val cur = q.removeAt(0)
            graph[cur]?.apply {
                for (i in 0 until size) {
                    inDegrees[this[i]]--
                    if (inDegrees[this[i]] == 0) {
                        q.add(this[i])
                    }
                }
            }
        }
        for (i in 0 until numCourses) {
            if (inDegrees[i] != 0) return false
        }
        return true
    }

    fun findOrder(numCourses: Int, prerequisites: Array<IntArray>): IntArray {//210
        val inDegrees = IntArray(numCourses) { 0 }
        val graph = mutableMapOf<Int, MutableList<Int>>()
        for (prerequisite in prerequisites) {
            inDegrees[prerequisite[0]]++
            if (graph.containsKey(prerequisite[1])) {
                graph[prerequisite[1]]?.add(prerequisite[0])
            } else {
                val dependencies = mutableListOf<Int>()
                dependencies.add(prerequisite[0])
                graph[prerequisite[1]] = dependencies
            }
        }
        val ans = mutableListOf<Int>()
        val q = mutableListOf<Int>()//used as queue
        for (i in 0 until numCourses) {
            if (inDegrees[i] == 0) q.add(i)
        }
        while (q.isNotEmpty()) {
            val cur = q.removeAt(0)
            ans.add(cur)
            graph[cur]?.apply {
                for (i in 0 until size) {
                    inDegrees[this[i]]--
                    if (inDegrees[this[i]] == 0) {
                        q.add(this[i])
                    }
                }
            }
        }
        return if (ans.size == numCourses) ans.toIntArray() else IntArray(0)
    }

    fun findAnagrams(s: String, p: String): List<Int> {//438
        val ans = mutableListOf<Int>()
        if (s.isNotEmpty() && s.length >= p.length) {
            val pChs = IntArray(26) { 0 }
            for (element in p) {
                pChs[element - 'a']++
            }
            val sChs = IntArray(26) { 0 }
            for (i in 0 until p.lastIndex) {
                sChs[s[i] - 'a']++
            }
            for (i in p.lastIndex until s.length) {
                sChs[s[i] - 'a']++
                if (isEqual(pChs, sChs)) {
                    ans.add(i - p.length + 1)
                }
                sChs[s[i - p.length + 1] - 'a']--
            }

        }
        return ans
    }

    private fun isEqual(s: IntArray, p: IntArray): Boolean {
        for (i in s.indices) {
            if (s[i] != p[i]) {
                return false
            }
        }
        return true
    }

    fun uncommonFromSentences(A: String, B: String): Array<String> {//884
        val amap = mutableMapOf<String, Int>()
        A.split(" ").forEach { amap.compute(it) { _, v -> if (v == null) 1 else v + 1 } }
        val bmap = mutableMapOf<String, Int>()
        B.split(" ").forEach { bmap.compute(it) { _, v -> if (v == null) 1 else v + 1 } }
        val ans = mutableListOf<String>()
        amap.entries.filter { it.value == 1 }.map { it.key }.forEach {
            if (!bmap.containsKey(it)) {
                ans.add(it)
            }
        }
        bmap.entries.filter { it.value == 1 }.map { it.key }.forEach {
            if (!amap.containsKey(it)) {
                ans.add(it)
            }
        }
        return ans.toTypedArray()
    }

    fun distributeCandies(candies: IntArray): Int {//575
        val set = mutableSetOf<Int>()
        val size = candies.size
        for (i in 0 until size) {
            set.add(candies[i])
            if (set.size >= size / 2) {
                return size / 2
            }
        }
        return set.size
    }

    fun multiply(A: Array<IntArray>, B: Array<IntArray>): Array<IntArray> {//311
        val rowA = A.size
        val columnA = A[0].size
        val rowB = B.size
        val columnB = B[0].size
        val ans = Array(rowA) { IntArray(columnB) }
        for (i in 0 until rowA) {
            for (j in 0 until columnB) {
                for (k in 0 until rowB) {
                    ans[i][j] += A[i][k] * B[k][j]
                }
            }
        }
        return ans
    }

    fun numSubarrayProductLessThanK(nums: IntArray, k: Int): Int {//713
        var ans = 0
        if (nums.isNotEmpty() && k > 1) {//if k <= 1 return 0
            var product = 1
            var left = 0
            for (right in nums.indices) {
                product *= nums[right]
                while (product >= k) product /= nums[left++]//essence!
                ans += right - left + 1
            }
        }
        return ans
    }

    fun twoSumLessThanK(A: IntArray, K: Int): Int {//1099
        var sum = -1
        if (A.size > 1) {
            A.sort()
            var i = 0
            var j = A.lastIndex
            var tmp = 0
            while (i < j) {
                tmp = A[i] + A[j]
                if (tmp >= K) {
                    j--
                } else {
                    if (tmp > sum) {
                        sum = tmp
                    }
                    i++
                }
            }
        }
        return sum
    }

    fun maxSubArrayLen(nums: IntArray, k: Int): Int {//325, prefix sum hashmap
        var ans = 0
        val preSums = mutableMapOf<Int, Int>()
        preSums[0] = -1
        var sum = 0
        for (i in nums.indices) {
            sum += nums[i]
            if (preSums.containsKey(sum - k)) {
                ans = Math.max(ans, i - preSums[sum - k]!!)
            }
            if (!preSums.containsKey(sum)) {
                preSums[sum] = i
            }
        }
        return ans
    }

    fun checkInclusion(s1: String, s2: String): Boolean {//567
        if (s1.length > s2.length) {
            return false
        }
        val ch1 = CharArray(26)
        for (c in s1) {
            ch1[c - 'a']++
        }
        val ch2 = CharArray(26)
        for (i in 0 until s1.lastIndex) {
            ch2[s2[i] - 'a']++
        }
        for (i in s1.lastIndex until s2.length) {
            ch2[s2[i] - 'a']++
            var flag = false
            for (j in 0 until 26) {
                if (ch1[j] != ch2[j]) {
                    flag = true
                    break
                }
            }
            if (!flag) {
                return true
            }
            ch2[s2[i - s1.length + 1] - 'a']--
        }
        return false
    }

    fun validPalindrome(s: String): Boolean {//680
        var i = 0
        var j = s.lastIndex
        while (i < j) {
            if (s[i] == s[j]) {
                i++
                j--
            } else {
                return validPalindrome(s, i + 1, j) || validPalindrome(s, i, j - 1)
            }
        }
        return true
    }

    private fun validPalindrome(s: String, left: Int, right: Int): Boolean {//680
        var i = left
        var j = right
        while (i < j) {
            if (s[i] == s[j]) {
                i++
                j--
            } else {
                return false
            }
        }
        return true
    }

    fun countSquares(matrix: Array<IntArray>): Int {//1277
        var ans = 0
        if (matrix.isNotEmpty()) {
            val f = Array(matrix.size) { IntArray(matrix[0].size) { 0 } }
            for (i in matrix.indices) {
                for (j in matrix[0].indices) {
                    if (i == 0 || j == 0) {
                        f[i][j] = matrix[i][j]
                    } else if (matrix[i][j] == 0) {
                        f[i][j] = 0
                    } else {
                        f[i][j] = Math.min(Math.min(f[i][j - 1], f[i - 1][j]), f[i - 1][j - 1]) + 1
                    }
                    ans += f[i][j]
                }
            }
        }
        return ans
    }

    fun findLongestSubarray(array: Array<String>): Array<String> {//面试题17.05
        var s = 0
        var e = 0
        var ans = 0
        var count = 0
        val size = array.size
        val memo = IntArray((size shr 1) + 1) { -2 }
        memo[size] = -1
        for (i in array.indices) {
            count += if (array[i][0].isLetter()) 1 else -1
            if (memo[count + size] > -2) {
                if (i - memo[count + size] > ans) {
                    s = memo[count + size] + 1
                    e = i + 1
                    ans = i - memo[count + size]
                }
            } else {
                memo[count + size] = i
            }
        }
        return array.copyOfRange(s, e)
    }

    fun frequencySort(s: String): String {//451
        val map = sortedMapOf<Char, Int>()
        for (c in s) {
            map.compute(c) { _, v -> if (v == null) 1 else v + 1 }
        }
        val ans = StringBuilder()
        map.entries.sortedByDescending { it.value }.map {
            var k = it.value
            while (k-- > 0) {
                ans.append(it.key)
            }
        }
        return ans.toString()
    }

    fun trailingZeroes(n: Int): Int {//面试题16.05
        var ans = 0
        var k = n
        while (k > 1) {
            k /= 5
            ans++
        }
        return ans
    }

    fun findNumberIn2DArray(matrix: Array<IntArray>, target: Int): Boolean {// 面试题04
        if (matrix.isNotEmpty() && matrix[0].isNotEmpty()) {
            var i = 0
            var j = matrix[0].lastIndex
            while (i < matrix.size && j > -1) {
                if (matrix[i][j] < target) {
                    i++
                } else if (matrix[i][j] > target) {
                    j--
                } else {
                    return true
                }
            }
        }
        return false
    }

    fun replaceSpace(s: String): String {//面试题05
        val ans = StringBuilder()
        for (c in s) {
            if (c == ' ') {
                ans.append("%20")
            } else {
                ans.append(c)
            }
        }
        return ans.toString()
    }

    fun numWays(n: Int): Int {//面试题10-II
        val MOD = 1_000_000_007
        var pre = 1
        var ans = 1
        var tmp = 0
        var k = n
        while (k-- > 1) {
            tmp = (pre + ans) % MOD
            pre = ans
            ans = tmp
        }
        return ans
    }

    fun minArray(numbers: IntArray): Int {//面试题11
        var left = 0
        var right = numbers.lastIndex
        var mid = 0
        while (left < right) {
            mid = left + (right - left) / 2
            if (numbers[mid] < numbers[right]) {
                right = mid
            } else if (numbers[mid] > numbers[right]) {
                left = mid + 1
            } else {
                right--
            }
        }
        return numbers[left]
    }

    fun firstUniqChar(s: String): Char {//面试题50
        val map = mutableMapOf<Char, Int>()
        for (c in s) {
            map.compute(c) { _, v -> if (v == null) 1 else v + 1 }
        }
        val chs = map.entries.filter { it.value == 1 }.map { it.key }
        if (chs.isNotEmpty()) {
            for (i in s.indices) {
                if (chs.contains(s[i])) {
                    return s[i]
                }
            }
        }
        return ' '
    }

    fun missingNumber(nums: IntArray): Int {//面试题53-II
        var ans = 0
        val size = nums.size
        ans = ans xor size
        for (i in nums.indices) {
            ans = ans xor i
            ans = ans xor nums[i]
        }
        return ans
    }

    fun printNumbers(n: Int): IntArray {//面试题17
        var size = 1
        for (i in 0 until n) {
            size *= 10
        }
        val ans = IntArray(size - 1)
        for (i in 0 until size - 1) {
            ans[i] = i + 1
        }
        return ans
    }

    fun twoSum(nums: IntArray, target: Int): IntArray {//面试题57
        val ans = IntArray(2) { -1 }
        var i = 0
        var j = nums.lastIndex
        while (i < j) {
            if (nums[i] + nums[j] > target) {
                j--
            } else if (nums[i] + nums[j] < target) {
                i++
            } else {
                ans[0] = nums[i]
                ans[1] = nums[j]
                break
            }
        }
        return ans
    }

    fun search(nums: IntArray, target: Int): Int {//面试题53 - I
        var i = 0
        var j = nums.lastIndex
        var mid = 0
        while (i <= j) {
            mid = i + (j - i) / 2
            if (nums[mid] > target) {
                j = mid - 1
            } else if (nums[mid] < target) {
                i = mid + 1
            } else {
                i = mid
                j = mid
                while (i > 0 && nums[i] == nums[i - 1]) {
                    i--
                }
                while (j < nums.lastIndex && nums[j] == nums[j + 1]) {
                    j++
                }
                return j - i + 1
            }
        }
        return 0
    }

    fun arrangeCoins(n: Int): Int {//441
        var left = n
        var rows = 1
        var ans = 0
        while (left >= rows) {
            left -= rows
            rows++
            ans++
        }
        return ans
    }

    fun dayOfYear(date: String): Int {//1154
        val year = date.split("-")[0]
        val firstDate = "$year-01-01"
        return daysOfDate(date) - daysOfDate(firstDate) + 1
    }

    private fun daysOfDate(date: String): Int {//Zeller formula
        val arr = date.split("-")
        var day = arr[2].toInt()
        var month = arr[1].toInt()
        var year = arr[0].toInt()
        if (month <= 2) {
            month += 10
            year--
        } else {
            month -= 2
        }
        return day + month * 30 + (3 * month - 1) / 5 + 365 * year + year / 4 - year / 100 + year / 400
    }

    fun getHint(secret: String, guess: String): String {//299
        var bulls = 0
        var cows = 0
        val bucket = IntArray(10) { 0 }
        for (i in secret.indices) {
            if (guess[i] == secret[i]) {
                bulls++
            } else {
                bucket[secret[i] - '0'] += 1
                bucket[guess[i] - '0'] -= 1
            }
        }
        for (i in bucket) {
            if (i > 0) {
                cows += i
            }
        }
        cows = secret.length - cows - bulls
        return "${bulls}A${cows}B"
    }


    fun convertInteger(A: Int, B: Int): Int {//面试题05.06
        var a = A
        var b = B
        var ans = 0
        while (a != 0 || b != 0) {
            if (a and 1 != b and 1) {
                ans++
            }
            a = a ushr 1
            b = b ushr 1
        }
        return ans
    }

    fun convertInteger1(A: Int, B: Int): Int {//面试题05.06
        var ans = 0
        var n = A xor B
        while (n != 0) {
            n = n and (n - 1)
            ans++
        }
        return ans
    }

    fun isPrefixOfWord(sentence: String, searchWord: String): Int {//5416, 1455
        val words = sentence.split(" ")
        for (i in words.indices) {
            if (words[i].startsWith(searchWord)) {
                return i + 1
            }
        }
        return -1
    }

    fun maxVowels(s: String, k: Int): Int {//5417
        val vowels = setOf('a', 'e', 'i', 'o', 'u')
        var ans = 0
        var count = 0
        for (i in 0 until k - 1) {
            if (s[i] in vowels) {
                count++
            }
        }
        for (i in k - 1 until s.length) {
            if (s[i] in vowels) {
                count++
            }
            ans = Math.max(ans, count)
            if (s[i - k + 1] in vowels) {
                count--
            }
        }
        return ans
    }

    var paths = 0
    fun pseudoPalindromicPaths(root: TreeNode?): Int {//5418
        paths = 0
        pseudoPalindromicPaths(root, mutableListOf())
        return paths
    }

    private fun pseudoPalindromicPaths(root: TreeNode?, list: MutableList<Int>) {
        if (root == null) {
            return
        }
        if (root.left == null && root.right == null) {
            list.add(root.`val`)
            if (containsPalindromic(list)) {
                paths++
            }
        } else {
            if (root.left != null) {
                val left = mutableListOf<Int>()
                left.addAll(list)
                left.add(root.`val`)
                pseudoPalindromicPaths(root.left, left)
            }
            if (root.right != null) {
                val right = mutableListOf<Int>()
                right.addAll(list)
                right.add(root.`val`)
                pseudoPalindromicPaths(root.right, right)
            }
        }
    }

    private fun containsPalindromic(list: MutableList<Int>): Boolean {
        return list.groupBy { it }.entries.filter { it.value.size and 1 == 1 }.size <= 1
    }

    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {//4
        val totalSize = nums1.size + nums2.size
        if (totalSize and 1 == 1) {
            return getKthElement(nums1, nums2, totalSize / 2 + 1).toDouble()
        } else {
            val mid1 = totalSize / 2 - 1
            val mid2 = totalSize / 2
            return (getKthElement(nums1, nums2, mid1 + 1) + getKthElement(nums1, nums2, mid2 + 1)) / 2.0
        }
    }

    private fun getKthElement(nums1: IntArray, nums2: IntArray, K: Int): Int {
        /* 主要思路：要找到第 k (k>1) 小的元素，那么就取 pivot1 = nums1[k/2-1] 和 pivot2 = nums2[k/2-1] 进行比较
        * 这里的 "/" 表示整除
        * nums1 中小于等于 pivot1 的元素有 nums1[0 .. k/2-2] 共计 k/2-1 个
        * nums2 中小于等于 pivot2 的元素有 nums2[0 .. k/2-2] 共计 k/2-1 个
        * 取 pivot = min(pivot1, pivot2)，两个数组中小于等于 pivot 的元素共计不会超过 (k/2-1) + (k/2-1) <= k-2 个
        * 这样 pivot 本身最大也只能是第 k-1 小的元素
        * 如果 pivot = pivot1，那么 nums1[0 .. k/2-1] 都不可能是第 k 小的元素。把这些元素全部 "删除"，剩下的作为新的 nums1 数组
        * 如果 pivot = pivot2，那么 nums2[0 .. k/2-1] 都不可能是第 k 小的元素。把这些元素全部 "删除"，剩下的作为新的 nums2 数组
        * 由于我们 "删除" 了一些元素（这些元素都比第 k 小的元素要小），因此需要修改 k 的值，减去删除的数的个数
        */

        val size1 = nums1.size
        val size2 = nums2.size
        var i1 = 0
        var i2 = 0
        var kth = 0
        var k = K
        while (true) {
            if (i1 == size1) {
                return nums2[i2 + k - 1]
            }
            if (i2 == size2) {
                return nums1[i1 + k - 1]
            }
            if (k == 1) {
                return Math.min(nums1[i1], nums2[i2])
            }
            val half = k / 2
            val newI1 = Math.min(i1 + half, size1) - 1
            val newI2 = Math.min(i2 + half, size2) - 1
            if (nums1[newI1] <= nums2[newI2]) {
                k -= (newI1 - i1 + 1)
                i1 = newI1 + 1
            } else {
                k -= (newI2 - i2 + 1)
                i2 = newI2 + 1
            }
        }
        return 0
    }

    fun validWordAbbreviation(word: String, abbr: String): Boolean {//408
        if (word == abbr) {
            return true
        }
        if (word.length < abbr.length || abbr.startsWith('0')) {
            return false
        }
        var i = 0
        var j = 0
        while (i < word.length && j < abbr.length) {
            if (abbr[j].isLetter()) {
                if (word[i] != abbr[j]) {
                    return false
                } else {
                    i++
                    j++
                }
            } else {
                var k = j + 1
                while (k < abbr.length && abbr[k].isDigit()) {
                    k++
                }
                val sub = abbr.substring(j, k)
                val count = sub.toInt()
                if (sub.length != count.toString().length) {
                    return false
                }
                i += count
                j = k
            }
        }
        return i == word.length && j == abbr.length
    }

    fun detectCapitalUse(word: String): Boolean {//520
        var upper = 0
        var lower = 0
        for (ch in word) {
            if (ch.isLowerCase()) {
                lower++
            } else {
                upper++
            }
        }
        if (upper == 0 || lower == 0) {
            return true
        } else {
            if (upper == word.length) {
                return true
            } else if (upper >= 2) {
                return lower == 0
            } else if (upper == 1) {
                return word[0].isUpperCase()
            } else {
                return true
            }
        }
    }

}