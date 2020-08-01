package me.bytebeats.algorithms.kt

import me.bytebeats.algorithms.meta.TreeNode
import kotlin.math.sign

class Solution10 {
    class Node(val `val`: Int) {
        var prev: Node? = null
        var next: Node? = null
        var child: Node? = null
    }

    fun flatten(root: Node?): Node? {//430, recursive
        if (root == null) return root
        val dummy = Node(-1)
        dummy.next = root
        flattenDFS(dummy, root)
        dummy.next?.prev = null
        return dummy.next
    }

    private fun flattenDFS(pre: Node?, cur: Node?): Node? {
        if (cur == null) return pre
        pre?.next = cur
        cur?.prev = pre

        val next = cur.next
        val tail = flattenDFS(cur, cur?.child)
        cur?.child = null
        return flattenDFS(tail, next)
    }

    fun flatten2(root: Node?): Node? {//430, loop
        if (root == null) return root
        val dummy = Node(-1)
        dummy.next = root
        var pre: Node? = dummy
        var cur: Node? = null
        val stack = mutableListOf<Node>()
        stack.add(root)
        while (stack.isNotEmpty()) {
            cur = stack.removeAt(stack.lastIndex)
            pre?.next = cur
            cur?.prev = pre

            if (cur?.next != null) stack.add(cur?.next!!)
            if (cur?.child != null) {
                stack.add(cur?.child!!)
                cur?.child = null
            }
            pre = cur
        }
        dummy.next?.prev = null
        return dummy.next
    }

    fun countSmaller(nums: IntArray): List<Int> {//315
        val ans = mutableListOf<Int>()
        val set = mutableSetOf<Int>()
        set.addAll(nums.toTypedArray())
        val a = set.toList().sorted()
        val c = IntArray(nums.size + 5) { 0 }
        var id = 0
        for (i in nums.lastIndex downTo 0) {
            id = a.binarySearch(nums[i]) + 1
            var ret = 0
            var pos = id - 1
            while (pos > 0) {
                ret += c[pos]
                pos -= pos and (-pos)
            }
            ans.add(ret)
            pos = id
            while (pos < c.size) {
                c[pos] += 1
                pos += pos and -pos
            }
        }
        return ans.reversed()
    }

    fun countSmaller1(nums: IntArray): List<Int> {//315
        val ans = mutableListOf<Int>()
        if (nums.isNotEmpty()) {
            ans.add(0)
            val size = nums.size
            for (i in size - 2 downTo 0) {
                nums.sort(i + 1)
                if (nums.last() < nums[i]) {
                    ans.add(size - i - 1)
                } else if (nums[i] <= nums[i + 1]) {
                    ans.add(0)
                } else {
                    var left = i + 1
                    var right = size - 1
                    var mid = 0
                    while (left < right) {
                        mid = left + (right - left) / 2
                        if (nums[mid] >= nums[i]) {
                            right = mid
                        } else {
                            left = mid + 1
                        }
                    }
                    ans.add(left - i - 1)
                }
            }
        }
        return ans.reversed()
    }

    fun flatten(root: TreeNode?): Unit {//114
        if (root == null) return
        if (root.left != null) {
            val right = root.right//获取右子树
            root.right = root.left//右子树的位置放置左子树
            root.left = null
            var p = root
            while (p?.right != null) {
                p = p?.right
            }
            p?.right = right//右子树放在原先左子树的(右子树的)右子树
        }
        flatten(root.right)
    }

    fun subsets(nums: IntArray): List<List<Int>> {//78
        val ans = mutableListOf<MutableList<Int>>()
        var pow = 1 shl nums.size
        for (i in 0 until pow) {
            val list = mutableListOf<Int>()
            var nn = i
            var j = 0
            while (nn > 0) {
                if (nn and 1 == 1) {
                    list.add(nums[j])
                }
                j += 1
                nn /= 2
            }
            ans.add(list)
        }
        return ans
    }

    fun reformatDate(date: String): String {//1507
        val months = mapOf(
            "Jan" to 1,
            "Feb" to 2,
            "Mar" to 3,
            "Apr" to 4,
            "May" to 5,
            "Jun" to 6,
            "Jul" to 7,
            "Aug" to 8,
            "Sep" to 9,
            "Oct" to 10,
            "Nov" to 11,
            "Dec" to 12
        )
        val strs = date.split(" ")
        var d = strs[0].substring(0, strs[0].length - 2)
        if (d.length < 2) {
            d = "0$d"
        }
        var m = months[strs[1]].toString()
        if (m.length < 2) {
            m = "0$m"
        }
        return "${strs[2]}-${m}-${d}"
    }

    fun rangeSum(nums: IntArray, n: Int, left: Int, right: Int): Int {
        val mod = Math.pow(10.0, 9.0).toInt() + 7
        val arr = IntArray(n * (n + 1) / 2)
        var i = 0
        for (j in nums.indices) {
            var sum = 0
            for (k in j until nums.size) {
                sum += nums[k]
                arr[i++] = sum
            }
        }
        arr.sort()
        var sum = 0
        for (i in left - 1 until right) {
            sum += arr[i]
            sum %= mod
        }
        return sum
    }

    fun minDifference(nums: IntArray): Int {
        val s = nums.size
        if (s <= 4) return 0
        nums.sort()
        return (nums[s - 4] - nums[0]).coerceAtMost(nums[s - 1] - nums[3])
    }

    fun winnerSquareGame(n: Int): Boolean {
        var nn = n
        var isAlice = true
        var sqrt = Math.sqrt(nn.toDouble()).toInt()
        var sn = sqrt * sqrt
        while (sn <= nn) {
            nn -= sn
            if (nn == 0) {
                return isAlice
            }
            sqrt = Math.sqrt(nn.toDouble()).toInt()
            sn = sqrt * sqrt
            isAlice = !isAlice
        }
        return false
    }

    fun numIdenticalPairs(nums: IntArray): Int {
        var ans = 0
        for (i in 0 until nums.size - 1) {
            for (j in i + 1 until nums.size) {
                if (nums[i] == nums[j]) {
                    ans += 1
                }
            }
        }
        return ans
    }

    fun numSub(s: String): Int {//1513
        var ans = 0L
        var count = 0L
        var mod = 1000000007L
        for (i in s.indices) {
            if (s[i] == '1') {
                count += 1L
            } else {
                ans = (ans + count * (count + 1) / 2L) % mod
                count = 0L
            }
        }
        ans = (ans + count * (count + 1) / 2L) % mod
        return ans.toInt()
    }

    fun getMinDistSum(positions: Array<IntArray>): Double {
        if (positions.size <= 1) return 0.0
        var p1 = positions[0]
        var p2 = positions[1]
        var cx = (p1[0] + p2[0]) / 2.0
        var cy = (p1[1] + p2[1]) / 2.0
        var a = p1[0] - cx
        var b = p1[1] - cy
        var square = a * a + b * b
        println("x=$cx, y=$cy, squre=$square")
        for (i in 2 until positions.size) {
            val p = positions[i]
            val aa = cx - p[0]
            val bb = cy - p[1]
            if (aa * aa + bb * bb <= square) {
                continue
            } else {
                for (j in 0 until i) {
                    var x = (positions[j][0] + p[0]) / 2.0
                    var y = (positions[j][1] + p[1]) / 2.0
                    var flag = true
                    for (k in 0 until i) {
                        val xx = positions[k][0] - x
                        val yy = positions[k][1] - y
                        var flag = true
                        if (xx * xx + yy * yy > square) {
                            cx = (p[0] + positions[k][0]) / 2.0
                            cy = (p[1] + positions[k][1]) / 2.0
                            val xxx = cx - p[0]
                            val yyy = cy - p[1]
                            square = xxx * xxx + yyy * yyy
                            flag = false
                            break
                        }
                        if (!flag) {
                            continue
                        }
                    }
                }
            }
        }
        var ans = 0.0
        for (i in positions.indices) {
            val p = positions[i]
            val x = p[0]
            val y = p[1]
            val a = cx - x
            val b = cy - y
            ans += Math.sqrt(a * a + b * b)
        }
        return ans
    }

    fun reverseBits(n: Int): Int {//190
        var ans = 0
        var l = 32
        var nn = n
        while (l-- > 0) {
            ans = ans shl 1
            ans = ans or (nn and 1)
            nn = nn shr 1
        }
        return ans
    }

    fun findRelativeRanks(nums: IntArray): Array<String> {//506
        val map = mutableMapOf<Int, Int>()
        nums.sortedDescending().forEachIndexed { index, i -> map[i] = index }
        val ans = Array(nums.size) { "" }
        for (i in nums.indices) {
            val rank = map[nums[i]] ?: 0
            if (rank == 0) {
                ans[i] = "Gold Medal"
            } else if (rank == 1) {
                ans[i] = "Silver Medal"
            } else if (rank == 2) {
                ans[i] = "Bronze Medal"
            } else {
                ans[i] = "${rank + 1}"
            }
        }
        return ans
    }

    private val dirs = arrayOf(intArrayOf(-1, 0), intArrayOf(1, 0), intArrayOf(0, -1), intArrayOf(0, 1))

    fun longestIncreasingPath(matrix: Array<IntArray>): Int {//329
        if (matrix.isEmpty() || matrix[0].isEmpty()) return 0
        val row = matrix.size
        val column = matrix[0].size
        val dp = Array(row) { IntArray(column) }
        var ans = 0
        for (i in 0 until row) {
            for (j in 0 until column) {
                ans = ans.coerceAtLeast(dfs(matrix, i, j, dp, row, column))
            }
        }
        return ans
    }

    private fun dfs(matrix: Array<IntArray>, r: Int, c: Int, dp: Array<IntArray>, m: Int, n: Int): Int {
        if (dp[r][c] != 0) return dp[r][c]
        dp[r][c] += 1
        for (dir in dirs) {
            val newR = r + dir[0]
            val newC = c + dir[1]
            if (newR in 0 until m && newC in 0 until n && matrix[newR][newC] > matrix[r][c]) {
                dp[r][c] = dp[r][c].coerceAtLeast(dfs(matrix, newR, newC, dp, m, n) + 1)
            }
        }
        return dp[r][c]
    }

    fun leastInterval(tasks: CharArray, n: Int): Int {//621
        val map = IntArray(26)
        for (task in tasks) {
            map[task - 'A'] += 1
        }
        map.sort()
        var time = 0
        while (map[25] > 0) {
            var i = 0
            while (i <= n) {
                if (map[25] == 0) break
                if (i < 26 && map[25 - i] > 0) {
                    map[25 - i]--
                }
                time++
                i++
            }
            map.sort()
        }
        return time
    }

    fun wordBreak(s: String, wordDict: List<String>): List<String> {//140
        val size = s.length
        val dp = BooleanArray(size) { false }
        val set = mutableSetOf<String>()
        set.addAll(wordDict)

        for (i in 0 until size) {
            if (set.contains(s.substring(0, i + 1))) {
                dp[i] = true
                continue
            }
            for (j in 0 until i) {
                if (dp[j] && set.contains(s.substring(j + 1, i + 1))) {
                    dp[i] = true
                    break
                }
            }
        }
        val ans = mutableListOf<String>()
        if (dp.last()) {
            val q = mutableListOf<String>()
            dfs(s, size - 1, set, ans, q, dp)
            return ans
        }
        return ans
    }

    private fun dfs(
        s: String,
        end: Int,
        wordSet: Set<String>,
        ans: MutableList<String>,
        q: MutableList<String>,
        dp: BooleanArray
    ) {
        if (wordSet.contains(s.substring(0, end + 1))) {
            q.add(0, s.substring(0, end + 1))
            val sb = StringBuilder()
            for (word in q) {
                sb.append(word)
                sb.append(" ")
            }
            sb.deleteCharAt(sb.lastIndex)
            ans.add(sb.toString())
            q.removeAt(0)
        }
        for (i in 0 until end) {
            if (dp[i]) {
                val suffix = s.substring(i + 1, end + 1)
                if (wordSet.contains(suffix)) {
                    q.add(0, suffix)
                    dfs(s, i, wordSet, ans, q, dp)
                    q.removeAt(0)
                }
            }
        }
    }

    fun smallestRange(nums: List<List<Int>>): IntArray {//632
        val s = nums.size
        val indices = mutableMapOf<Int, MutableList<Int>>()
        var xMin = Int.MAX_VALUE
        var xMax = Int.MIN_VALUE
        for (i in 0 until s) {
            for (x in nums[i]) {
                val list = indices.getOrDefault(x, mutableListOf())
                list.add(i)
                indices[x] = list
                xMin = xMin.coerceAtMost(x)
                xMax = xMax.coerceAtLeast(x)
            }
        }
        val freq = IntArray(s)
        var inside = 0
        var left = xMin
        var right = xMin - 1
        var bestLeft = xMin
        var bestRight = xMax
        while (right < xMax) {
            right += 1
            if (indices.containsKey(right)) {
                indices[right]?.forEach {
                    freq[it] += 1
                    if (freq[it] == 1) {
                        inside += 1
                    }
                }
                while (inside == s) {
                    if (right - left < bestRight - bestLeft) {
                        bestLeft = left
                        bestRight = right
                    }
                    if (indices.containsKey(left)) {
                        indices[left]?.forEach {
                            freq[it] -= 1
                            if (freq[it] == 0) {
                                inside -= 1
                            }
                        }
                    }
                    left += 1
                }
            }
        }
        return intArrayOf(bestLeft, bestRight)
    }

    fun buddyStrings(A: String, B: String): Boolean {//859
        if (A.length != B.length) return false
        if (A == B) {
            val count = IntArray(26)
            for (i in A.indices) {
                count[A[i] - 'a'] += 1
            }
            for (c in count) {
                if (c > 1) return true
            }
            return false
        } else {
            var first = -1
            var second = -1
            for (i in A.indices) {
                if (A[i] != B[i]) {
                    if (first == -1) {
                        first = i
                    } else if (second == -1) {
                        second = i
                    } else {
                        return false
                    }
                }
            }
            return second != -1 && A[first] == B[second] && A[second] == B[first]
        }
    }

}