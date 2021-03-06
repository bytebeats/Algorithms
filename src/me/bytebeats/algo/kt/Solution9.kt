package me.bytebeats.algo.kt

import java.util.*
import me.bytebeats.algs.ds.ListNode
import me.bytebeats.algs.ds.TreeNode

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

    fun average(salary: IntArray): Double {//1491
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

    fun canMakeArithmeticProgression(arr: IntArray): Boolean {//1502
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

    fun isSameTree(p: TreeNode?, q: TreeNode?): Boolean {//100
        if (p == null) return q == null
        else if (p != null && q != null) {
            return p.`val` == q.`val` && isSameTree(p.left, q.left) && isSameTree(p.right, q.right)
        } else return false
    }

    fun findLHS(nums: IntArray): Int {//594
        val map = mutableMapOf<Int, Int>()
        var ans = 0
        for (num in nums) {
            map.compute(num) { _, v -> if (v == null) 1 else v + 1 }
            if (map.containsKey(num - 1)) {
                ans = ans.coerceAtLeast(map[num]!! + map[num - 1]!!)
            }
            if (map.containsKey(num + 1)) {
                ans = ans.coerceAtLeast(map[num]!! + map[num + 1]!!)
            }
        }
        return ans
    }

    fun numIdenticalPairs(nums: IntArray): Int {//1512
        var ans = 0
        val map = mutableMapOf<Int, MutableList<Int>>()
        for (i in nums.indices) {
            if (map.containsKey(nums[i])) {
                ans += map[nums[i]]!!.size
            }
            map.compute(nums[i]) { _, v ->
                if (v == null) {
                    val list = mutableListOf<Int>()
                    list.add(i)
                    list
                } else {
                    v.add(i)
                    v
                }
            }
        }
        return ans
    }

    fun numberOfDays(Y: Int, M: Int): Int {//1184
        val map = mapOf(
                1 to 31,
                2 to 28,
                3 to 31,
                4 to 30,
                5 to 31,
                6 to 30,
                7 to 31,
                8 to 31,
                9 to 30,
                10 to 31,
                11 to 30,
                12 to 31
        )
        if (M != 2) {
            return map[M]!!
        } else {
            var days = map[2]!!
            if (Y % 400 == 0 || Y % 4 == 0 && Y % 100 != 0) {
                days += 1
            }
            return days
        }
    }

    val UNCOLORED = 0
    val RED = 1
    val GREEN = 2
    var valid = false
    fun isBipartite(graph: Array<IntArray>): Boolean {//785, dfs
        val n = graph.size
        val colors = IntArray(n) { UNCOLORED }
        valid = true
        var node = 0
        while (node < n && valid) {
            if (colors[node] == UNCOLORED) {
                dfs(node, RED, graph, colors)
            }
            node += 1
        }
        return valid
    }

    private fun dfs(node: Int, color: Int, graph: Array<IntArray>, colors: IntArray) {
        colors[node] = color
        val colorOfNeighbor = if (color == RED) GREEN else RED
        for (neighber in graph[node]) {
            if (colors[neighber] == UNCOLORED) {
                dfs(neighber, colorOfNeighbor, graph, colors)
                if (!valid) {
                    return
                }
            } else if (colors[neighber] != colorOfNeighbor) {
                valid = false
                return
            }
        }
    }

    fun isBipartite1(graph: Array<IntArray>): Boolean {//785, bfs
        val n = graph.size
        val colors = IntArray(n) { UNCOLORED }
        for (i in 0 until n) {//node
            if (colors[i] == UNCOLORED) {
                val q = mutableListOf<Int>()
                q.add(i)
                colors[i] = RED
                while (q.isNotEmpty()) {
                    val node = q.removeAt(0)
                    val colorOfNeighbor = if (colors[node] == RED) GREEN else RED
                    for (neighber in graph[node]) {
                        if (colors[neighber] == UNCOLORED) {
                            q.add(neighber)
                            colors[neighber] = colorOfNeighbor
                        } else if (colors[neighber] != colorOfNeighbor) {
                            return false
                        }
                    }
                }
            }
        }
        return true
    }

    fun destCity(paths: List<List<String>>): String {//1436, 计算每个点的出度
        val graph = mutableMapOf<String, Int>()
        for (path in paths) {
            graph.compute(path[0]) { _, v -> if (v == null) 1 else v + 1 }
            graph.compute(path[1]) { _, v -> v ?: 0 }
        }
        return graph.entries.filter { it.value == 0 }.map { it.key }.first()
    }

    fun minSubsequence(nums: IntArray): List<Int> {//1403
        nums.sortDescending()
        val ans = mutableListOf<Int>()
        var sum = nums.sum()
        var sub = 0
        for (num in nums) {
            sub += num
            sum -= num
            ans.add(num)
            if (sub > sum) {
                return ans
            }
        }
        return ans
    }

    fun freqAlphabets(s: String): String {//1309
        val map = mutableMapOf<String, Char>()
        for (i in 1..26) {
            if (i < 10) {
                map[i.toString()] = 'a' + i - 1
            } else {
                map["$i#"] = 'a' + i - 1
            }
        }
        val sb = StringBuilder()
        val size = s.length
        var i = size - 1
        while (i >= 0) {
            if (s[i] == '#') {
                sb.append(map[s.substring(i - 2, i + 1)])
                i -= 3
            } else {
                sb.append(map[s[i].toString()])
                i--
            }
        }
        return sb.reversed().toString()
    }

    fun calPoints(ops: Array<String>): Int {//682
        val stack = mutableListOf<Int>()
        for (op in ops) {
            if (op == "+") {
                val top = stack.removeAt(stack.lastIndex)
                val newTop = top + stack.last()
                stack.add(top)
                stack.add(newTop)
            } else if (op == "C") {
                stack.removeAt(stack.lastIndex)
            } else if (op == "D") {
                stack.add(2 * stack.last())
            } else {
                stack.add(op.toInt())
            }
        }
        return stack.sum()
    }

    fun repeatedStringMatch(A: String, B: String): Int {//686
        var a = ""
        val aL = A.length
        val bL = B.length
        for (i in 1..(bL / aL + 2)) {//reduced loop
            a = "$a$A"
            if (a.length >= B.length && a.contains(B)) {
                return i
            }
        }
        return -1
    }

    fun countBinarySubstrings(s: String): Int {//696
        val size = s.length
        val dp = IntArray(size)
        dp[0] = 1
        var t = 0
        for (i in 1 until size) {
            if (s[i - 1] != s[i]) {
                dp[++t] = 1
            } else {
                dp[t] += 1
            }
        }
        var ans = 0
        for (i in 1..t) {
            ans += dp[i - 1].coerceAtMost(dp[i])
        }
        return ans
    }

    fun allPathsSourceTarget(graph: Array<IntArray>): List<List<Int>> {//797
        return solve(graph, 0)
    }

    private fun solve(graph: Array<IntArray>, node: Int): MutableList<MutableList<Int>> {
        val n = graph.size
        val ans = mutableListOf<MutableList<Int>>()
        if (node == n - 1) {
            val path = mutableListOf<Int>()
            path.add(node)
            ans.add(path)
            return ans
        }
        for (nb in graph[node]) {
            for (path in solve(graph, nb)) {
                path.add(0, node)
                ans.add(path)
            }
        }
        return ans
    }

    fun splitArray(nums: IntArray, m: Int): Int {//410
        val n = nums.size
        val dp = Array(n + 1) { IntArray(m + 1) { Int.MAX_VALUE } }
        val sub = IntArray(n + 1)
        for (i in 0 until n) {
            sub[i + 1] = sub[i] + nums[i]
        }
        dp[0][0] = 0
        for (i in 1..n) {
            for (j in 1..m.coerceAtMost(i)) {
                for (k in 0 until i) {
                    dp[i][j] = dp[i][j].coerceAtMost(dp[k][j - 1].coerceAtLeast(sub[i] - sub[k]))
                }
            }
        }
        return dp[n][m]
    }
}