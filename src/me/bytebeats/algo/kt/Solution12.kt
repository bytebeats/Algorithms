package me.bytebeats.algo.kt

import me.bytebeats.algs.ds.TreeNode

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

    fun trap(height: IntArray): Int {//17.21, 48
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

    fun checkIfPangram(sentence: String): Boolean {//1832
        val counts = IntArray(26)
        for (c in sentence) {
            counts[c - 'a'] += 1
        }
        return counts.all { it > 0 }
    }

    fun countConsistentStrings(allowed: String, words: Array<String>): Int {//1684
        val allowedCounts = count(allowed)
        var count = 0
        for (word in words) {
            val wordCounts = count(word)
            if (greaterOrEqual(allowedCounts, wordCounts)) {
                count += 1
            }
        }
        return count
    }

    private fun greaterOrEqual(allowed: IntArray, word: IntArray): Boolean {
        for (i in 0 until 26) {
            if (allowed[i] == 0 && word[i] != 0) {
                return false
            }
        }
        return true
    }

    fun minDistance(word1: String, word2: String): Int {//583
        val m = word1.length
        val n = word2.length
        val dp = Array(m + 1) { IntArray(n + 1) }
        for (i in 1..m) {
            for (j in 1..n) {
                dp[i][j] = if (word1[i - 1] == word2[j - 1]) {
                    dp[i - 1][j - 1] + 1
                } else {
                    dp[i - 1][j].coerceAtLeast(dp[i][j - 1])
                }
            }
        }
        return m + n - 2 * dp[m][n]
    }

    fun xorOperation(n: Int, start: Int): Int {//1486
        var ans = 0
        for (i in 0 until n) {
            ans = ans xor (start + i * 2)
        }
        return ans
    }

    fun sumOfUnique(nums: IntArray): Int {//1748
        val counts = mutableMapOf<Int, Int>()
        for (num in nums) {
            counts.compute(num) { _, v -> if (v == null) 1 else v + 1 }
        }
        return counts.entries.filter { it.value == 1 }.map { it.key }.sum()
    }

    fun truncateSentence(s: String, k: Int): String {//1816
        var count = 0
        var idx = -1
        for (i in s.indices) {
            if (s[i] == ' ') {
                count += 1
            }
            if (count >= k) {
                idx = i
                break
            }
        }
        if (idx == -1) {
            return s
        } else {
            return s.substring(0, idx)
        }
    }

    fun sumBase(n: Int, k: Int): Int {//1837
        var nn = n
        var sum = 0
        while (nn != 0) {
            sum += nn % k
            nn /= k
        }
        return sum
    }

    fun specialArray(nums: IntArray): Int {//1608
        for (i in 0..nums.size) {
            var count = 0
            for (j in 0 until nums.size) {
                if (nums[j] >= i) {
                    count++
                }
            }
            if (count == i) {
                return i
            }
        }
        return -1
    }

    fun frequencySort(nums: IntArray): IntArray {//1636
        val freqs = mutableMapOf<Int, Int>()
        for (num in nums) {
            freqs.compute(num) { _, v -> if (v == null) 1 else v + 1 }
        }
        val comparator = Comparator<Int> { o1, o2 ->
            val f1 = freqs[o1]!!
            val f2 = freqs[o2]!!
            if (f1 == f2) {
                return@Comparator if (o1 > o2) -1 else if (o1 == o2) 0 else 1
            } else {
                return@Comparator if (f1 > f2) 1 else if (f1 == f2) 0 else -1
            }
        }
        return nums.sortedWith(comparator).toIntArray()
    }

    fun slowestKey(releaseTimes: IntArray, keysPressed: String): Char {//1629
        val n = releaseTimes.size
        var max = 0
        var code = ' '
        for (i in 0 until n) {
            val k = keysPressed[i]
            val time = if (i == 0) releaseTimes[0] else releaseTimes[i] - releaseTimes[i - 1]
            if (max < time || (max == time && code < k)) {
                max = time
                code = k
            }
        }
        return code
    }

    fun decode(encoded: IntArray): IntArray {//1734
        val size = encoded.size
        val perm = IntArray(size + 1)
        var total = 0
        for (i in 1..size + 1) {
            total = total xor i
        }
        var odd = 0
        for (i in 1 until size step 2) {
            odd = odd xor encoded[i]
        }
        perm[0] = total xor odd
        for (i in 0 until size) {
            perm[i + 1] = perm[i] xor encoded[i]
        }
        return perm
    }

    fun xorQueries(arr: IntArray, queries: Array<IntArray>): IntArray {//1310
        val ansSize = queries.size
        val size = arr.size
        for (i in 1 until size) {
            arr[i] = arr[i] xor arr[i - 1]
        }
        val ans = IntArray(ansSize)
        for (i in 0 until ansSize) {
            val left = queries[i][0]
            val right = queries[i][1]
            if (left > 0) {
                ans[i] = arr[left - 1] xor arr[right]
            } else {
                ans[i] = arr[right]
            }
        }
        return ans
    }

    fun countGoodRectangles(rectangles: Array<IntArray>): Int {//1725
        var maxLength = 0
        var maxCount = 0
        for (r in rectangles) {
            val min = r[0].coerceAtMost(r[1])
            if (min > maxLength) {
                maxLength = min
                maxCount = 1
            } else if (min == maxLength) {
                maxCount++
            }
        }
        return maxCount
    }

    fun numDifferentIntegers(word: String): Int {//1805
        val nums = mutableSetOf<String>()
        var left = -1
        var right = 0
        while (right < word.length) {
            if (word[right].isDigit()) {
                if (left == -1) {
                    left = right
                }
                right++
            } else {
                if (left == -1) {
                    right++
                } else {
                    while (left < right && word[left] == '0') {
                        left++
                    }
                    if (left <= right) {
                        nums.add(word.substring(left, right))
                    }
                    left = -1
                    right++
                }
            }
        }
        if (left != -1) {
            while (left < right && word[left] == '0') {
                left++
            }
            if (left <= right) {
                nums.add(word.substring(left, right))
            }
        }
        return nums.size
    }

    private var count = 0
    fun findTargetSumWays(nums: IntArray, target: Int): Int {//494
        count = 0
        findTargetSumWays(nums, target, 0, 0)
        return count
    }

    fun findTargetSumWays(nums: IntArray, target: Int, sum: Int, index: Int) {//494
        if (index >= nums.size) return
        if (index == nums.size - 1) {
            if (sum + nums[index] == target) count++
            if (sum - nums[index] == target) count++
        } else {
            findTargetSumWays(nums, target, sum + nums[index], index + 1)
            findTargetSumWays(nums, target, sum - nums[index], index + 1)
        }
    }

    fun minOperations(logs: Array<String>): Int {//1598
        var count = 0
        for (log in logs) {
            if (log == "../") {
                if (count > 0) {
                    count--
                } else {
                    continue
                }
            } else if (log == "./") {
                continue
            } else {
                count++
            }
        }
        return count
    }

    fun maxDepth(s: String): Int {//1614
        var max = 0
        var depth = 0
        for (c in s) {
            if (c == '(') {
                depth++
                max = max.coerceAtLeast(depth)
            } else if (c == ')') {
                depth--
            }
        }
        return max
    }

    fun trimMean(arr: IntArray): Double {//1619
        val count2Remove = arr.size / 20
        arr.sort()
        return arr.drop(count2Remove).dropLast(count2Remove).average()
    }

    fun maxLengthBetweenEqualCharacters(s: String): Int {//1624
        var max = -1
        var width = -1
        for (i in 0 until s.lastIndex) {
            width = -1
            for (j in i + 1 until s.length) {
                if (s[i] == s[j]) {
                    width = j - i - 1
                    max = max.coerceAtLeast(width)
                }
            }
        }
        return max
    }

    fun decrypt(code: IntArray, k: Int): IntArray {//1652
        val size = code.size
        val ans = IntArray(size) { 0 }
        if (k != 0) {
            for (i in 0 until size) {
                var s = 0
                var e = 0
                if (k > 0) {
                    s = i + 1
                    e = i + k
                } else {
                    s = i + k
                    e = i - 1
                }
                for (j in s..e) {
                    ans[i] += code[(j % size + size) % size]
                }
            }
        }
        return ans
    }

    fun maxRepeating(sequence: String, word: String): Int {//1668
        var count = 0
        val container = StringBuilder(word)
        while (sequence.contains(container)) {
            count++
            container.append(word)
        }
        return count
    }

    fun maximumWealth(accounts: Array<IntArray>): Int {//1672
        var maxAssets = 0
        for (account in accounts) {
            val assets = account.sum()
            if (assets > maxAssets) {
                maxAssets = assets
            }
        }
        return maxAssets
    }

    fun interpret(command: String): String {//1678
        return command.replace("()", "").replace("(al)", "al")
    }

    fun numberOfMatches(n: Int): Int {//1688
        var loop = 0
        var nn = n
        while (nn > 1) {
            if (nn and 1 == 0) {
                nn = nn shr 1
                loop += nn
            } else {
                nn = (nn + 1) shr 1
                loop += nn - 1
            }
        }
        return loop
    }

    fun grayCode(n: Int): List<Int> {//89
        val ans = mutableListOf<Int>()
        ans.add(0)
        var head = 1
        for (i in 0 until n) {
            for (j in ans.lastIndex downTo 0) {
                ans.add(head + ans[j])
            }
            head = head shl 1
        }
        return ans
    }

    fun maxIceCream(costs: IntArray, coins: Int): Int {//1833
        var ans = 0
        costs.sort()
        var left = coins
        for (cost in costs) {
            if (left >= cost) {
                ans += 1
                left -= cost
            } else {
                break
            }
        }
        return ans
    }

    fun minPairSum(nums: IntArray): Int {//1877
        nums.sort()
        var max = nums.first() + nums.last()
        for (i in 1 until nums.size / 2) {
            max = max.coerceAtLeast(nums[i] + nums[nums.size - 1 - i])
        }
        return max
    }

    fun pruneTree(root: TreeNode?): TreeNode? {//814
        return if (contains1(root)) root else null
    }

    private fun contains1(node: TreeNode?): Boolean {
        if (node == null) return false
        val hasLeft = contains1(node.left)
        val hasRight = contains1(node.right)
        if (!hasLeft) node.left = null
        if (!hasRight) node.right = null
        return node.`val` == 1 || hasLeft || hasRight
    }

    fun isCovered(ranges: Array<IntArray>, left: Int, right: Int): Boolean {//1893
        val diff = IntArray(52)
        for (range in ranges) {
            diff[range[0]]++
            diff[range[1] + 1]--
        }
        var preSum = 0
        for (i in 1..50) {
            preSum += diff[i]
            if (i in left..right && preSum <= 0) {
                return false
            }
        }
        return true
    }

    fun beautifulArray(n: Int): IntArray {//932
        val map = mutableMapOf<Int, IntArray>()
        return f(n, map)
    }

    private fun f(n: Int, map: MutableMap<Int, IntArray>): IntArray {
        if (map.containsKey(n)) return map[n]!!
        val ans = IntArray(n)
        if (n == 1) {
            ans[0] = 1
        } else {
            var t = 0
            for (x in f((n + 1) / 2, map)) {
                ans[t++] = x * 2 - 1
            }
            for (x in f(n / 2, map)) {
                ans[t++] = x * 2
            }
        }
        map[n] = ans
        return ans
    }

    private val parents = mutableMapOf<Int, TreeNode>()
    val ans = mutableListOf<Int>()
    fun distanceK(root: TreeNode?, target: TreeNode?, k: Int): List<Int> {//863
        findParents(root)
        findAns(target, null, 0, k)
        return ans
    }

    private fun findParents(node: TreeNode?) {
        if (node?.left != null) {
            parents[node.left.`val`] = node
            findParents(node.left)
        }
        if (node?.right != null) {
            parents[node.right.`val`] = node
            findParents(node.right)
        }
    }

    private fun findAns(node: TreeNode?, from: TreeNode?, depth: Int, k: Int) {
        if (node == null) return
        if (depth == k) {
            ans.add(node.`val`)
            return
        }
        if (node.left != from) {
            findAns(node.left, node, depth + 1, k)
        }
        if (node.right != null) {
            findAns(node.right, node, depth + 1, k)
        }
        if (parents[node.`val`] != from) {
            findAns(parents[node.`val`], node, depth + 1, k)
        }
    }
}