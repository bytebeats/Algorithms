package me.bytebeats.algo.kt

import me.bytebeats.algs.ds.TreeNode

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

    fun countGoodTriplets(arr: IntArray, a: Int, b: Int, c: Int): Int {//5475
        var count = 0
        val s = arr.size
        for (i in 0 until s - 2) {
            for (j in i + 1 until s - 1) {
                for (k in j + 1 until s) {
                    if (Math.abs(arr[i] - arr[j]) <= a && Math.abs(arr[j] - arr[k]) <= b && Math.abs(arr[i] - arr[k]) <= c) {
                        count += 1
                    }
                }
            }
        }
        return count
    }

    fun surfaceArea(grid: Array<IntArray>): Int {//892
        val dr = intArrayOf(0, 1, 0, -1)
        val dc = intArrayOf(1, 0, -1, 0)
        val N = grid.size
        var ans = 0
        for (r in 0 until N) for (c in 0 until N) if (grid[r][c] > 0) {
            ans += 2
            for (k in 0..3) {
                val nr = r + dr[k]
                val nc = c + dc[k]
                var nv = 0
                if (nr in 0 until N && nc in 0 until N) nv = grid[nr][nc]
                ans += Math.max(grid[r][c] - nv, 0)
            }
        }
        return ans
    }

    fun allCellsDistOrder(R: Int, C: Int, r0: Int, c0: Int): Array<IntArray> {//1030
        val ans = Array(R * C) { r -> intArrayOf(r / C, r % C) }
        ans.sortBy { arr -> Math.abs(arr[0] - r0) + Math.abs(arr[1] - c0) }
        return ans
    }

    fun numRookCaptures(board: Array<CharArray>): Int {//999
        var ans = 0
        var start = 0
        var end = 0
        val dx = intArrayOf(0, 1, 0, -1)
        val dy = intArrayOf(1, 0, -1, 0)
        for (i in 0 until 8) for (j in 0 until 8) {
            if (board[i][j] == 'R') {
                start = i
                end = j
                break
            }
        }
        for (i in 0 until 4) {
            var step = 0
            while (true) {
                val tx = start + step * dx[i]
                val ty = end + step * dy[i]
                if (tx < 0 || tx >= 8 || ty < 0 || ty >= 8 || board[tx][ty] == 'B') break
                if (board[tx][ty] == 'p') {
                    ans += 1
                    break
                }
                step += 1
            }
        }
        return ans
    }

    fun reorderLogFiles(logs: Array<String>): Array<String> {//937
        logs.sortWith(Comparator { o1, o2 ->
            val regex = Regex(" ")
            val split1 = o1.split(regex, 2)
            val split2 = o2.split(regex, 2)
            val isDigit1 = split1[1][0].isDigit()
            val isDigit2 = split2[1][0].isDigit()
            if (!isDigit1 && !isDigit2) {
                val cmp = split1[1].compareTo(split2[1])
                if (cmp != 0) return@Comparator cmp
                else return@Comparator split1[0].compareTo(split2[0])
            }
            return@Comparator if (isDigit1) if (isDigit2) 0 else 1 else -1
        })
        return logs
    }

    fun numMagicSquaresInside(grid: Array<IntArray>): Int {//840
        var count = 0
        val row = grid.size
        val col = grid[0].size
        for (i in 0 until row - 2) for (j in 0 until col - 2) {
            val set = mutableSetOf<Int>()
            var min = Int.MAX_VALUE
            var max = Int.MIN_VALUE
            for (ii in i..i + 2) for (jj in j..j + 2) {
                set.add(grid[ii][jj])
                min = min.coerceAtMost(grid[ii][jj])
                max = max.coerceAtLeast(grid[ii][jj])
            }
            if (set.size != 9 || min != 1 || max != 9) {
                continue
            }
            val sum = grid[i][j] + grid[i][j + 1] + grid[i][j + 2]
            if (sum != grid[i + 1][j] + grid[i + 1][j + 1] + grid[i + 1][j + 2]) continue
            if (sum != grid[i + 2][j] + grid[i + 2][j + 1] + grid[i + 2][j + 2]) continue
            if (sum != grid[i][j] + grid[i + 1][j] + grid[i + 2][j]) continue
            if (sum != grid[i][j + 1] + grid[i + 1][j + 1] + grid[i + 2][j + 1]) continue
            if (sum != grid[i][j + 2] + grid[i + 1][j + 2] + grid[i + 2][j + 2]) continue
            if (sum != grid[i][j] + grid[i + 1][j + 1] + grid[i + 2][j + 2]) continue
            if (sum != grid[i + 2][j] + grid[i + 1][j + 1] + grid[i][j + 2]) continue
            count += 1
        }
        return count
    }

    fun countPrimeSetBits(L: Int, R: Int): Int {//762
        var count = 0
        for (num in L..R) {
            if (isSmallPrime(compute(num))) {
                count += 1
            }
        }
        return count
    }

    private fun compute(num: Int): Int {
        var count = 0
        var n = num
        while (n != 0) {
            count += n and 1
            n = n shr 1
        }
        return count
    }

    private fun isSmallPrime(n: Int): Boolean = (n == 2 || n == 3 || n == 5 || n == 7 ||
            n == 11 || n == 13 || n == 17 || n == 19)

    fun maxDistToClosest(seats: IntArray): Int {//849
        val idx = mutableListOf<Int>()
        for (i in seats.indices) {
            if (seats[i] == 1) {
                idx.add(i)
            }
        }
        var ans = idx.first().coerceAtLeast(seats.size - 1 - idx.last())
        for (i in 1..idx.lastIndex) {
            if (idx[i] - idx[i - 1] > 1) {
                ans = ans.coerceAtLeast((idx[i] - idx[i - 1]) / 2)
            }
        }
        return ans
    }

    fun transpose(A: Array<IntArray>): Array<IntArray> {//867
        val row = A.size
        val col = A[0].size
        val ans = Array(col) { IntArray(row) }
        for (i in 0 until row) for (j in 0 until col) {
            ans[j][i] = A[i][j]
        }
        return ans
    }

    fun isLongPressedName(name: String, typed: String): Boolean {//925
        var j = 0
        for (c in name) {
            if (j == typed.length) return false
            if (c != typed[j]) {
                if (j == 0 || typed[j - 1] != typed[j]) return false
                val ch = typed[j]
                while (j < typed.length && typed[j] == ch) j += 1
                if (j == typed.length || c != typed[j]) return false
            }
            j += 1
        }
        while (j < typed.length && typed[j - 1] == typed[j]) j += 1
        return j == typed.length
    }

    fun diStringMatch(S: String): IntArray {//942
        val n = S.length
        var low = 0
        var high = n
        val ans = IntArray(n + 1)
        for (i in S.indices) {
            if (S[i] == 'I') {
                ans[i] = low++
            } else {
                ans[i] = high--
            }
        }
        ans[n] = low
        return ans
    }

    fun minDeletionSize(A: Array<String>): Int {//944
        var count = 0
        val r = A.size
        val c = A[0].length
        for (j in 0 until c) for (i in 1 until r) {
            if (A[i][j] < A[i - 1][j]) {
                count += 1
                break
            }
        }
        return count
    }

    fun orangesRotting(grid: Array<IntArray>): Int {//994
        val r = grid.size
        val c = grid[0].size
        val rottens = mutableListOf<IntArray>()
        var fresh = 0
        for (i in 0 until r) for (j in 0 until c) {
            if (grid[i][j] == 2) rottens.add(intArrayOf(i, j))
            else if (grid[i][j] == 1) fresh += 1
        }
        if (rottens.isEmpty()) {
            if (fresh == 0) return 0
            else return -1
        }
        var seconds = -1
        val adjacent = mutableListOf<IntArray>()
        while (rottens.isNotEmpty()) {
            while (rottens.isNotEmpty()) {
                val rotten = rottens.removeAt(0)
                val x = rotten[0]
                val y = rotten[1]
                if (x > 0 && grid[x - 1][y] == 1) {
                    grid[x - 1][y] = 2
                    adjacent.add(intArrayOf(x - 1, y))
                }
                if (x < r - 1 && grid[x + 1][y] == 1) {
                    grid[x + 1][y] = 2
                    adjacent.add(intArrayOf(x + 1, y))
                }
                if (y > 0 && grid[x][y - 1] == 1) {
                    grid[x][y - 1] = 2
                    adjacent.add(intArrayOf(x, y - 1))
                }
                if (y < c - 1 && grid[x][y + 1] == 1) {
                    grid[x][y + 1] = 2
                    adjacent.add(intArrayOf(x, y + 1))
                }
            }
            rottens.addAll(adjacent)
            adjacent.clear()
            seconds += 1
        }
        for (i in 0 until r) for (j in 0 until c) {
            if (grid[i][j] == 1) return -1
        }
        return seconds
    }

    fun restoreIpAddresses(s: String): List<String> {//93
        val n = s.length
        val ans = mutableListOf<String>()
        if (n in 4..12) {
            for (i in 0..2) {
                if (n - i - 1 !in 3..9) continue
                for (j in i + 1..i + 3) {
                    if (n - j - 1 !in 2..6) continue
                    for (k in j + 1..j + 3) {
                        if (n - k - 1 !in 1..3) continue
                        val first = s.substring(0, i + 1)
                        if (first.toInt() !in 0..255 || first.toInt().toString() != first) continue
                        val second = s.substring(i + 1, j + 1)
                        if (second.toInt() !in 0..255 || second.toInt().toString() != second) continue
                        val third = s.substring(j + 1, k + 1)
                        if (third.toInt() !in 0..255 || third.toInt().toString() != third) continue
                        val forth = s.substring(k + 1, n)
                        if (forth.toInt() !in 0..255 || forth.toInt().toString() != forth) continue
                        ans.add("$first.$second.$third.$forth")
                    }
                }
            }
        }
        return ans
    }

    fun findKthPositive(arr: IntArray, k: Int): Int {//5648
        val set = mutableSetOf<Int>()
        for (i in arr) {
            set.add(i)
        }
        val max = arr.last()
        for (i in 1..max) {
            if (set.contains(i)) set.remove(i)
            else set.add(i)
        }
        if (k <= set.size) {
            return set.toList()[k - 1]
        } else {
            return max + (k - set.size)
        }
    }

    fun makeGood(s: String): String {//5483, 1544
        var ss = s
        var idx = idx(ss)
        while (idx > -1) {
            ss = "${ss.substring(0, idx)}${ss.substring(idx + 2, ss.length)}"
            idx = idx(ss)
        }
        return ss
    }

    private fun idx(s: String): Int {
        for (i in 0 until s.length - 1) {
            if (s[i] != s[i + 1] && s[i].toLowerCase() == s[i + 1].toLowerCase()) {
                return i
            }
        }
        return -1
    }

    fun maxArea(height: IntArray): Int {//11
        var max = 0
        var tmp = 0
        var i = 0
        var j = height.size - 1
        while (i < j) {
            if (height[i] > height[j]) {
                tmp = height[j] * (j - i)
                j--
            } else {
                tmp = height[i] * (j - i)
                i++
            }
            max = max.coerceAtLeast(tmp)
        }
        return max
    }

    fun letterCombinations(digits: String): List<String> {//17
        val map = mapOf(
            '2' to charArrayOf('a', 'b', 'c'),
            '3' to charArrayOf('d', 'e', 'f'),
            '4' to charArrayOf('g', 'h', 'i'),
            '5' to charArrayOf('j', 'k', 'l'),
            '6' to charArrayOf('m', 'n', 'o'),
            '7' to charArrayOf('p', 'q', 'r', 's'),
            '8' to charArrayOf('t', 'u', 'v'),
            '9' to charArrayOf('w', 'x', 'y', 'z')
        )
        val ans = mutableListOf<String>()
        for (d in digits) {
            val chs = map[d]!!
            val tmp = mutableListOf<String>()
            tmp.addAll(ans)
            ans.clear()
            if (tmp.isEmpty()) {
                for (ch in chs) {
                    ans.add(ch.toString())
                }
            } else {
                for (ch in chs) {
                    for (str in tmp) {
                        ans.add("$str$ch")
                    }
                }
            }
        }
        return ans
    }

    fun titleToNumber(s: String): Int {
        var num = 0
        for (c in s) {
            num *= 26
            num += c - 'A' + 1
        }
        return num
    }

    fun getRow(rowIndex: Int): List<Int> {
        val triangle = mutableListOf<Int>()
        for (i in 1..rowIndex + 1) {
            for (j in i - 1 downTo 0) {
                if (j == i - 1) {
                    triangle.add(1)
                } else if (j == 0) {
                    break
                } else {
                    triangle[j] = triangle[j] + triangle[j - 1]
                }
            }
        }
        return triangle
    }

    fun isValid(s: String): Boolean {//20
        val stack = mutableListOf<Char>()
        for (c in s) {
            if (c == '(' || c == '[' || c == '{') {
                stack.add(c)
            } else if (c == ')') {
                if (stack.isEmpty() || stack.last() != '(') return false
                stack.removeAt(stack.lastIndex)
            } else if (c == ']') {
                if (stack.isEmpty() || stack.last() != '[') return false
                stack.removeAt(stack.lastIndex)
            } else if (c == '}') {
                if (stack.isEmpty() || stack.last() != '{') return false
                stack.removeAt(stack.lastIndex)
            }
        }
        return stack.isEmpty()
    }

    fun longestPalindrome(s: String): Int {//409
        val counts = IntArray(52)
        for (c in s) {
            if (c >= 'a') {
                counts[c - 'a' + 26] += 1
            } else {
                counts[c - 'A'] += 1
            }
        }
        var evenSum = 0
        var oddSum = 0
        var oddCount = 0
        for (count in counts) {
            if (count and 1 == 0) {
                evenSum += count
            } else {
                oddCount += 1
                oddSum += count
            }
        }
        if (oddCount > 0) {
            oddSum -= oddCount - 1
        }
        return evenSum + oddSum
    }

    fun eraseOverlapIntervals(intervals: Array<IntArray>): Int {//435
        if (intervals.isEmpty()) return 0
        intervals.sortWith(Comparator { o1, o2 -> o1[0] - o2[0] })
        var prev = 0
        var count = 0
        for (i in 1 until intervals.size) {
            if (intervals[prev][1] > intervals[i][0]) {
                if (intervals[prev][1] > intervals[i][1]) {
                    prev = i
                }
                count += 1
            } else {
                prev = i
            }
        }
        return count
    }

    fun threeConsecutiveOdds(arr: IntArray): Boolean {//5185, 1550
        if (arr.size < 3) return false
        for (i in 0 until arr.size - 2) {
            if (arr[i] and 1 == 1 && arr[i + 1] and 1 == 1 && arr[i + 2] and 1 == 1) {
                return true
            }
        }
        return false
    }

    fun minDepth(root: TreeNode?): Int {//111
        var minDepth = 0
        if (root != null) {
            val q = mutableListOf<TreeNode>()
            q.add(root)
            var cur = 0
            var countDown = 1
            var p = root
            minDepth += 1
            while (q.isNotEmpty()) {
                p = q.removeAt(0)
                if (p.left == null && p.right == null) {
                    return minDepth
                }
                countDown -= 1
                if (p.left != null) {
                    q.add(p.left)
                    cur += 1
                }
                if (p.right != null) {
                    q.add(p.right)
                    cur += 1
                }
                if (countDown == 0) {
                    minDepth += 1
                    countDown = cur
                    cur = 0
                }
            }
        }
        return minDepth
    }

    fun rangeBitwiseAnd(m: Int, n: Int): Int {//201
        var ans = n
        while (ans > m) {
            ans = ans and (ans - 1)
        }
        return ans
    }

    fun hammingDistance(x: Int, y: Int): Int {//461
        var xx = x
        var yy = y
        var ans = 0
        while (xx != 0 || yy != 0) {
            ans += (xx and 1) xor (yy and 1)
            xx = xx shr 1
            yy = yy shr 1
        }
        return ans
    }

    fun thousandSeparator(n: Int): String {//5479, 1556
        val ans = StringBuilder()
        val src = n.toString().reversed()
        var three = 3
        for (i in src.indices) {
            three -= 1
            ans.append(src[i])
            if (three == 0 && src[i].isDigit() && i != src.lastIndex) {
                ans.append(".")
                three = 3
            }
        }
        return ans.reverse().toString()
    }

    fun findSubsequences(nums: IntArray): List<List<Int>> {//491
        val ans = mutableListOf<MutableList<Int>>()
        for (num in nums) {
            val size = ans.size
            for (i in 0 until size) {
                if (ans[i].last() <= num) {
                    val newList = mutableListOf<Int>()
                    newList.addAll(ans[i])
                    newList.add(num)
                    ans.add(newList)
                }
            }
            val list = mutableListOf<Int>()
            list.add(num)
            ans.add(list)
        }
        return ans.distinctBy { it.joinToString() }.filter { it.size > 1 }
    }

    fun shortestPalindrome(s: String): String {//214
        var maxIdx = 0
        for (i in s.lastIndex downTo 0) {
            if (isPalindrome(s, i)) {
                maxIdx = i
                break
            }
        }
        val ans = StringBuilder()
        for (j in maxIdx + 1 until s.length) {
            ans.append(s[j])
        }
        ans.reverse()
        ans.append(s)
        return ans.toString()
    }

    private fun isPalindrome(s: String, end: Int): Boolean {
        var i = 0
        var j = end
        while (i < j) {
            if (s[i] != s[j]) {
                return false
            }
            i += 1
            j -= 1
        }
        return true
    }

    fun pancakeSort(A: IntArray): List<Int> {//969
        val ans = mutableListOf<Int>()
        var idx = A.lastIndex
        while (idx >= 0) {
            val idxOfMax = findIdxOfMax(A, idx)
            if (idxOfMax != idx) {
                ans.add(idxOfMax + 1)
                ans.add(idx + 1)
                pancake(A, idxOfMax)
                pancake(A, idx)
            }
            idx -= 1
        }
        return ans
    }

    private fun pancake(A: IntArray, end: Int) {
        var i = 0
        var j = end
        var tmp = 0
        while (i < j) {
            tmp = A[i]
            A[i] = A[j]
            A[j] = tmp
            i += 1
            j -= 1
        }
    }

    private fun findIdxOfMax(A: IntArray, end: Int): Int {
        var idxOfMax = end
        for (i in 0 until end) {
            if (A[i] > A[idxOfMax]) {
                idxOfMax = i
            }
        }
        return idxOfMax
    }

    fun canVisitAllRooms(rooms: List<List<Int>>): Boolean {//841
        val visited = mutableSetOf<Int>()
        val inRooms = mutableListOf<Int>()
        inRooms.add(0)
        visited.add(0)
        while (inRooms.isNotEmpty()) {
            val curKeys = rooms[inRooms.removeAt(0)]
            for (key in curKeys) {
                if (!visited.contains(key)) {
                    inRooms.add(key)
                    visited.add(key)
                }
            }
        }
        return visited.size == rooms.size
    }

    fun deleteNode(root: TreeNode?, key: Int): TreeNode? {//450
        if (root == null) return null
        if (root.`val` > key) {
            root.left = deleteNode(root.left, key)
            return root
        } else if (root.`val` < key) {
            root.right = deleteNode(root.right, key)
            return root
        } else {
            var p = root.left ?: return root.right
            while (p.right != null) {
                p = p.right
            }
            p.right = root.right
            return root.left
        }
    }

    fun containsPattern(arr: IntArray, m: Int, k: Int): Boolean {//1566
        if (m * k <= arr.size) {
            for (i in 0..arr.size - m * k) {
                val sub = arr.copyOfRange(i, i + m).joinToString()
                var cnt = 0
                while (arr.copyOfRange(i + m * cnt, i + m * cnt + m).joinToString() == sub) {
                    cnt += 1
                    if (cnt >= k) {
                        return true
                    }
                }
            }
        }
        return false
    }

    fun PredictTheWinner(nums: IntArray): Boolean {//486
        return total(nums, 0, nums.lastIndex, 1) >= 0
    }

    private fun total(nums: IntArray, start: Int, end: Int, turn: Int): Int {
        if (start == end) return nums[start] * turn
        val scoreStart = nums[start] * turn + total(nums, start + 1, end, -turn)
        val scoreEnd = nums[end] * turn + total(nums, start, end - 1, -turn)
        return (scoreStart * turn).coerceAtLeast(scoreEnd * turn) * turn
    }

    fun largestTimeFromDigits(A: IntArray): String {//949
        var ans = -1
        //choose i, j, k, l as a permutation of 0, 1, 2, 3
        for (i in 0 until 4) for (j in 0 until 4) if (i != j) for (k in 0 until 4) if (k != i && k != j) {
            val l = 6 - i - j - k
            val hours = 10 * A[i] + A[j]
            val mins = 10 * A[k] + A[l]
            if (hours < 24 && mins < 60) {
                ans = ans.coerceAtLeast(hours * 60 + mins)
            }
        }
        return if (ans >= 0) "%02d:%02d".format(ans / 60, ans % 60) else ""
    }
}