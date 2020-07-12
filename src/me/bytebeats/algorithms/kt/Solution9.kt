package me.bytebeats.algorithms.kt

import me.bytebeats.algorithms.meta.ListNode
import me.bytebeats.algorithms.meta.TreeNode
import java.util.*

class Solution9 {
    fun avoidFlood(rains: IntArray): IntArray {//1488
        val ans = IntArray(rains.size)
        val lakes = mutableMapOf<Int, Int>()
        val todo = sortedSetOf<Int>()
        for (i in rains.indices) {
            val rain = rains[i]
            if (rain == 0) {
                todo.add(i)
                continue
            } else {
                ans[i] = -1
                if (lakes.containsKey(rain)) {
                    val toPull = todo.higher(lakes[rain]) ?: return IntArray(0)
                    ans[toPull] = rain
                    todo.remove(toPull)
                }
                lakes[rain] = i
            }
        }
        for (i in ans.indices) {
            if (ans[i] == 0) ans[i] = 1
        }
        return ans
    }

    fun letterCasePermutation(S: String): List<String> {//784
        var pow = 0
        for (c in S) {
            if (c.isLetter()) {
                pow++
            }
        }
        val ans = mutableListOf<String>()
        for (i in 0 until Math.pow(2.0, pow.toDouble()).toInt()) {
            var num = i
            var pos = 0
            val sb = StringBuilder()
            while (num > 0) {
                while (S[pos].isDigit()) {//find first character
                    sb.append(S[pos])
                    pos++
                }
                if (pos < S.length) {
                    if (num and 1 == 0) {
                        sb.append(S[pos].toLowerCase())
                    } else {
                        sb.append(S[pos].toUpperCase())
                    }
                    num = num shr 1
                    pos++
                }
            }
            for (j in pos until S.length) {
                if (S[j].isDigit()) {
                    sb.append(S[j])
                } else {
                    sb.append(S[j].toLowerCase())
                }
            }
            ans.add(sb.toString())
        }
        return ans
    }

    fun numTrees(n: Int): Int {//96, 卡特兰数
        var catalan = 1L
        for (i in 1..n) {
            catalan = catalan * 2 * (2 * i - 1) / (i + 1)
        }
        return catalan.toInt()
    }

    fun numTrees1(n: Int): Int {//96
        val dp = IntArray(n + 1)
        dp[0] = 1
        dp[1] = 1
        for (i in 2..n) {
            for (j in 1..i) {
                dp[i] += dp[j - 1] * dp[i - j]
            }
        }
        return dp[n]
    }

    fun generateTrees(n: Int): List<TreeNode?> {//95
        return if (n == 0) listOf() else generateTrees(1, n)
    }

    private fun generateTrees(start: Int, end: Int): List<TreeNode?> {
        val allTrees = mutableListOf<TreeNode?>()
        if (start > end) {
            allTrees.add(null)
            return allTrees
        }
        for (i in start..end) {
            val leftTrees = generateTrees(start, i - 1)
            val rightTrees = generateTrees(i + 1, end)
            for (leftTree in leftTrees) {
                for (rightTree in rightTrees) {
                    val curTree = TreeNode(i)
                    curTree.left = leftTree
                    curTree.right = rightTree
                    allTrees.add(curTree)
                }
            }
        }
        return allTrees
    }

    fun removeDuplicateNodes(head: ListNode?): ListNode? {//面试题 02.01
        if (head != null) {
            val set = mutableSetOf<Int>()
            var p = head
            set.add(head.`val`)
            while (p?.next != null) {
                if (set.contains(p.next.`val`)) {
                    p.next = p.next.next
                } else {
                    p = p.next
                    set.add(p.`val`)
                }
            }
        }
        return head
    }

    fun sumNumbers(root: TreeNode?): Int {//129
        val ans = mutableListOf<String>()
        dfs(root, "", ans)
        return ans.map { it.toInt() }.sum()
    }

    private fun dfs(root: TreeNode?, str: String, list: MutableList<String>) {
        if (root == null) return
        val newStr = "$str${root.`val`}"
        if (root.left == null && root.right == null) {
            list.add(newStr)
        } else {
            if (root.left != null) {
                dfs(root.left, newStr, list)
            }
            if (root.right != null) {
                dfs(root.right, newStr, list)
            }
        }
    }

    fun largestTriangleArea(points: Array<IntArray>): Double {//812
        var ans = 0.0
        for (i in 0..points.size - 3) {
            for (j in i + 1..points.size - 2) {
                for (k in j + 1 until points.size) {
                    ans = ans.coerceAtLeast(areaOfTriangle(points[i], points[j], points[k]))
                }
            }
        }
        return ans
    }

    private fun areaOfTriangle(a: IntArray, b: IntArray, c: IntArray): Double {
        return 0.5 * Math.abs(a[0] * b[1] + b[0] * c[1] + c[0] * a[1] - a[1] * b[0] - b[1] * c[0] - c[1] * a[0])
    }

    fun average(salary: IntArray): Double {
        var min = Int.MAX_VALUE
        var max = Int.MIN_VALUE
        var sum = 0
        for (s in salary) {
            min = min.coerceAtMost(s)
            max = max.coerceAtLeast(s)
            sum += s
        }
        return (sum - min - max) / (salary.size - 2).toDouble()
    }

    fun kthFactor(n: Int, k: Int): Int {
        val sqrt = Math.sqrt(n.toDouble()).toInt()
        val list = mutableListOf<Int>()
        for (i in 1..sqrt) {
            if (n % i == 0) {
                if (i * i == n) {
                    list.add(i)
                } else {
                    list.add(i)
                    list.add(n / i)
                }
            }
        }
        if (list.size < k) {
            return -1
        } else {
            return list.sorted().drop(k - 1).first() ?: 0
        }
    }

    fun longestSubarray(nums: IntArray): Int {
        val idx = mutableListOf<Int>()
        idx.add(-1)
        for (n in nums.indices) {
            if (nums[n] == 0) {
                idx.add(n)
            }
        }
        idx.add(nums.size)
        if (idx.size == nums.size + 2) {
            return 0
        } else if (idx.size == 2) {
            return nums.size - 1
        } else {
            var max = 0
            for (i in 2..idx.lastIndex) {
                max = max.coerceAtLeast(idx[i] - idx[i - 2] - 2)
            }
            return max
        }
    }

    fun minNumberOfSemesters(n: Int, dependencies: Array<IntArray>, k: Int): Int {
        val inDegrees = IntArray(n + 1)
        inDegrees[0] = -1
        val graph = mutableMapOf<Int, MutableList<Int>>()
        for (d in dependencies) {
            inDegrees[d[1]]++
            if (graph.containsKey(d[0])) {
                graph[d[0]]?.add(d[1])
            } else {
                val dependencies = mutableListOf<Int>()
                dependencies.add(d[1])
                graph[d[0]] = dependencies
            }
        }
        val q = mutableListOf<Int>()
        for (i in 1..n) {
            if (inDegrees[i] == 0) q.add(i)
        }
        val replace = mutableListOf<Int>()
        var ans = 0
        while (q.isNotEmpty()) {
            var kk = k
            while (kk-- > 0 && q.isNotEmpty()) {
                val cur = q.removeAt(0)
                graph[cur]?.apply {
                    for (i in 0 until size) {
                        inDegrees[this[i]]--
                        if (inDegrees[this[i]] == 0) {
                            replace.add(this[i])
                        }
                    }
                }
            }
            q.addAll(replace)
            replace.clear()
            ans += 1
        }
        return ans
    }

    fun isPathCrossing(path: String): Boolean {//1496
        var x = 0
        var y = 0
        val set = mutableSetOf<String>()
        set.add("$x,$y")
        for (ch in path) {
            if (ch == 'N') {
                y++
            } else if (ch == 'S') {
                y--
            } else if (ch == 'E') {
                x++
            } else {
                x--
            }
            val point = "$x,$y"
            if (set.contains(point)) {
                return true
            } else {
                set.add(point)
            }
        }
        return false
    }

    fun canArrange(arr: IntArray, k: Int): Boolean {//1497
        val mod = IntArray(k)
        for (i in arr) {
            mod[(i % k + k) % k] += 1//(i % k + k) % k, 使余数为负的转为正
        }
        for (i in 1 until k) {
            if (mod[i] != mod[k - i]) {
                return false
            }
        }
        return mod[0] % 2 == 0
    }

    fun numSubseq(nums: IntArray, target: Int): Int {
        nums.sort()
        return 0
    }

    fun findItinerary(tickets: List<List<String>>): List<String> {//332
        val ans = mutableListOf<String>()
        if (tickets.isNotEmpty()) {
            val graph = mutableMapOf<String, PriorityQueue<String>>()
            for (ticket in tickets) {
                val queue = graph.computeIfAbsent(ticket[0]) {
                    PriorityQueue()//邻接顶点按字典顺序排序, 如果是使用 LinkedList 的话, 不要显式的进行排序
                }
                queue.add(ticket[1])
            }
            visit(graph, "JFK", ans)
        }
        return ans
    }

    /**
     * DFS遍历图, 当不能再走时, 再将节点加入 ans
     */
    private fun visit(graph: Map<String, PriorityQueue<String>>, src: String, ans: MutableList<String>) {
        val neibour = graph[src]
        while (neibour?.isNotEmpty() == true) {
            val dst = neibour.poll()
            visit(graph, dst, ans)
        }
        ans.add(0, src)
    }

    fun canMakeArithmeticProgression(arr: IntArray): Boolean {
        arr.sort()
        val diff = arr[1] - arr[0]
        for (i in 2 until arr.size) {
            if (arr[i] - arr[i - 1] != diff) {
                return false
            }
        }
        return true
    }

    fun hasAlternatingBits(n: Int): Boolean {//693
        var pre = n and 1
        var nn = n shr 1
        var cur = 0
        while (nn > 0) {
            cur = nn and 1
            if (cur xor pre == 0) {
                return false
            }
            nn = nn shr 1
            pre = cur
        }
        return true
    }

    fun maxCount(m: Int, n: Int, ops: Array<IntArray>): Int {//598
        var r = m
        var c = n
        for (op in ops) {
            r = r.coerceAtMost(op[0])
            c = c.coerceAtMost(op[1])
        }
        return r * c
    }

    fun findLUSlength(a: String, b: String): Int {//521
        if (a == b) {
            return -1
        }
        return a.length.coerceAtLeast(b.length)
    }

    fun findWords(words: Array<String>): Array<String> {//500
        val fst = setOf('q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p')
        val scnd = setOf('a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l')
        val trd = setOf('z', 'x', 'c', 'v', 'b', 'n', 'm')
        val ans = mutableListOf<String>()
        for (word in words) {
            if (word.isEmpty()) {
                continue
            }
            val fstChar = word.first().toLowerCase()
            val set = if (fst.contains(fstChar)) {
                fst
            } else if (scnd.contains(fstChar)) {
                scnd
            } else {
                trd
            }
            var flag = true
            for (i in 1 until word.length) {
                if (!set.contains(word[i].toLowerCase())) {
                    flag = false
                    break
                }
            }
            if (flag) {
                ans.add(word)
            }
        }
        return ans.toTypedArray()
    }

    fun findTilt(root: TreeNode?): Int {//563
        if (root == null) {
            return 0
        }
        return Math.abs(sumTree(root.left) - sumTree(root.right)) + findTilt(root.left) + findTilt(root.right)
    }

    fun sumTree(root: TreeNode?): Int {
        if (root == null) return 0
        else return root.`val` + sumTree(root.left) + sumTree(root.right)
    }

    fun numSpecialEquivGroups(A: Array<String>): Int {//893
        val seen = mutableSetOf<String>()
        for (s in A) {
            val count = IntArray(52)
            for (i in s.indices) {
                count[s[i] - 'a' + 26 * (i and 1)] += 1
            }
            seen.add(count.contentToString())
        }
        return seen.size
    }

    fun widthOfBinaryTree(root: TreeNode?): Int {//662, wrong solution
        if (root == null) return 0
        val nodeQueue = mutableListOf<TreeNode>()
        val idxQueue = mutableListOf<Int>()
        nodeQueue.add(root)
        idxQueue.add(0)
        var countDown = 1
        var count = 0
        var minIdx = Int.MAX_VALUE
        var maxIdx = Int.MIN_VALUE
        var node: TreeNode? = null
        var idx = 0
        var ans = 0
        var depth = 1
        while (nodeQueue.isNotEmpty()) {
            node = nodeQueue.removeAt(0)
            idx = idxQueue.removeAt(0)
            countDown--
            minIdx = minIdx.coerceAtMost(idx)
            maxIdx = maxIdx.coerceAtLeast(idx)
            if (node?.left != null) {
                nodeQueue.add(node?.left)
                idxQueue.add(idx - 1)
                count++
            }
            if (node?.right != null) {
                nodeQueue.add(node?.right)
                idxQueue.add(idx + 1)
                count++
            }
            if (countDown == 0) {
                depth++
                ans = ans.coerceAtLeast(maxIdx - minIdx)
                countDown = count
                count = 0
                minIdx = Int.MAX_VALUE
                maxIdx = Int.MIN_VALUE
            }
        }
        return ans
    }

    fun checkRecord(s: String): Boolean {//551
        var countA = 0
        var countL = 0
        for (c in s) {
            if (c == 'L') {
                countL += 1
                if (countL > 2) {
                    return false
                }
            } else {
                if (countL != 0) {
                    countL = 0
                }
                if (c == 'A') {
                    countA += 1
                }
                if (countA > 1) {
                    return false
                }
            }
        }
        return true
    }
}