package me.bytebeats.algo.kt

import me.bytebeats.algo.kt.design.CustomFunction
import me.bytebeats.algs.ds.ListNode
import me.bytebeats.algs.ds.TreeNode

class Solution8 {

    fun pathSum(root: TreeNode?, sum: Int): Int {//面试题04.12
        if (root == null) {
            return 0
        }
        return pathSum(root, 0, sum) + pathSum(root?.left, sum) + pathSum(root?.right, sum)
    }

    private fun pathSum(root: TreeNode?, subSum: Int, sum: Int): Int {
        if (root == null) {
            return 0
        }
        var count = 0
        val nextSum = subSum + root.`val`
        if (nextSum == sum) {
            count++
        }
        count += pathSum(root.left, nextSum, sum)
        count += pathSum(root.right, nextSum, sum)
        return count
    }

    fun twoCitySchedCost(costs: Array<IntArray>): Int {//1029
        var ans = 0
        val half = costs.size / 2
        costs.sortBy { it[0] - it[1] }
        for (i in costs.indices) {
            if (i < half) {
                ans += costs[i][0]
            } else {
                ans += costs[i][1]
            }
        }
        return ans
    }

    fun reverseString(s: CharArray): Unit {//344
        var left = 0
        var right = s.lastIndex
        var ch = ' '
        while (left < right) {
            ch = s[left]
            s[left] = s[right]
            s[right] = ch
            left++
            right--
        }
    }

    fun reverseVowels(s: String): String {//557
        val chArr = s.toCharArray()
        var i = 0
        var j = s.lastIndex
        var ch = ' '
        while (i < j) {
            while (i < j && !isVowel(chArr[i])) {
                i++
            }
            while (i < j && !isVowel(chArr[j])) {
                j--
            }
            if (i < j) {
                ch = chArr[i]
                chArr[i] = chArr[j]
                chArr[j] = ch
                i++
                j--
            }
        }
        return String(chArr)
    }

    private fun isVowel(ch: Char): Boolean {
        return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u' || ch == 'A' || ch == 'E' || ch == 'I' || ch == 'O' || ch == 'U'
    }

    fun removeVowels(S: String): String {//1119
        val ans = StringBuilder()
        for (c in S) {
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                continue
            } else {
                ans.append(c)
            }
        }
        return ans.toString()
    }

    fun reverseStr(s: String, k: Int): String {//541
        val ans = s.toCharArray()
        var left = 0
        while (left < s.length) {
            if (s.length - left <= k) {
                reverse(ans, left, s.length - 1)
            } else {
                reverse(ans, left, left + k - 1)
            }
            left += 2 * k
        }
        return String(ans)
    }

    private fun reverse(charArray: CharArray, left: Int, right: Int) {
        var i = left
        var j = right
        var ch = ' '
        while (i < j) {
            ch = charArray[i]
            charArray[i] = charArray[j]
            charArray[j] = ch
            i++
            j--
        }
    }

    fun countLetters(S: String): Int {//1180
        var ans = 0
        var i = 0
        var j = 0
        while (i < S.length) {
            j = i
            while (j < S.length && S[j] == S[i]) {
                j++
            }
            ans += count(j - i)
            i = j
        }
        return ans
    }

    private fun count(n: Int): Int = n * (n + 1) / 2

    fun customSortString(S: String, T: String): String {//791
        val map = mutableMapOf<Char, Int>()
        for (i in S.indices) {
            map[S[i]] = i
        }
        return String(T.toList().sortedBy { map[it] ?: -1 }.toCharArray())
    }

    fun complexNumberMultiply(a: String, b: String): String {//537
        val ans = StringBuilder()
        val cn1 = extract(a)
        val cn2 = extract(b)
        val r = cn1[0] * cn2[0] - cn1[1] * cn2[1]
        val v = cn1[1] * cn2[0] + cn1[0] * cn2[1]
        ans.append(r)
        ans.append('+')
        ans.append(v)
        ans.append('i')
        return ans.toString()
    }

    private fun extract(cn: String): List<Int> = cn.split("+").map {
        if (it.endsWith('i')) {
            it.substring(0, it.lastIndex)
        } else {
            it
        }
    }.map { it.toInt() }

    fun maximumSwap(num: Int): Int {//670
        val ans = num.toString().toCharArray()
        var max = '0'
        var tmp = '0'
        for (i in 0 until ans.lastIndex) {
            max = ans.drop(i + 1).max() ?: '0'
            if (ans[i] < max) {
                for (j in ans.lastIndex downTo i + 1) {
                    if (ans[j] == max) {
                        tmp = ans[i]
                        ans[i] = ans[j]
                        ans[j] = tmp
                        return String(ans).toInt()
                    }
                }
            }
        }
        return num
    }

    fun longestConsecutive(nums: IntArray): Int {//128
        val set = mutableSetOf<Int>()
        for (num in nums) {
            set.add(num)
        }
        var ans = 0
        var curNum = 0
        var curStreak = 0
        for (num in set) {
            if (!set.contains(num - 1)) {
                curNum = num
                curStreak = 1
                while (set.contains(curNum + 1)) {
                    curNum++
                    curStreak++
                }
                ans = ans.coerceAtLeast(curStreak)
            }
        }
        return ans
    }

    fun reconstructQueue(people: Array<IntArray>): Array<IntArray> {//406
        val queue = people.sortedWith(Comparator { o1, o2 ->
            if (o1[0] == o2[0])
                o1[1] - o2[1]
            else
                o2[0] - o1[0]
        })
        val list = mutableListOf<IntArray>()
        for (q in queue) {
            list.add(q[1], q)
        }
        return list.toTypedArray()
    }

    fun findSolution(customfunction: CustomFunction, z: Int): List<List<Int>> {//1237
        val ans = mutableListOf<List<Int>>()
        var i = 1
        var j = 1000
        var res = 0
        while (i <= z && j >= 1) {
            res = customfunction.f(i, j)
            if (res < z) {
                i++
            } else if (res > z) {
                j--
            } else {
                ans.add(listOf(i, j))
                i++
                j++
            }
        }
        return ans
    }

    fun shuffle(nums: IntArray, n: Int): IntArray {//5428, 1470
        val list = mutableListOf<Int>()
        for (i in 0 until n) {
            list.add(nums[i])
            list.add(nums[i + n])
        }
        return list.toIntArray()
    }

    fun getStrongest(arr: IntArray, k: Int): IntArray {//5429, 1471
        val ans = IntArray(k)
        arr.sort()
        val median = arr[(arr.size - 1) / 2]
        val sorted = arr.reversed().sortedByDescending { Math.abs(it - median) }
        for (i in 0 until k) {
            ans[i] = sorted[i]
        }
        return ans
    }

    fun change(amount: Int, coins: IntArray): Int {//512
        val dp = IntArray(amount + 1)
        dp[0] = 1
        for (coin in coins) {
            for (i in coin..amount) {
                dp[i] += dp[i - coin]
            }
        }
        return dp[amount]
    }

    fun equationsPossible(equations: Array<String>): Boolean {//990
        val parent = IntArray(26) { it }
        for (e in equations) {
            if (e[1] == '=') {
                union(parent, e[0] - 'a', e[3] - 'a')
            }
        }
        for (e in equations) {
            if (e[1] == '!') {
                if (find(parent, e[0] - 'a') == find(parent, e[3] - 'a')) {
                    return false
                }
            }
        }
        return true
    }

    private fun union(parent: IntArray, index1: Int, index2: Int) {
        parent[find(parent, index1)] = parent[find(parent, index2)]
    }

    private fun find(parent: IntArray, index: Int): Int {
        var i = index
        while (parent[i] != i) {
            parent[i] = parent[parent[i]]
            i = parent[i]
        }
        return i
    }

    fun isPowerOfTwo(n: Int): Boolean {//231
        if (n < 1) {
            return false
        }
        var num = n
        var has1 = false
        while (num > 1) {
            if (has1) {
                return false
            }
            if (num and 1 == 1) {
                has1 = true
            }
            num = num shr 1
        }
        return true
    }

    fun translateNum(num: Int): Int {//面试题46
        var ans = 1
        val src = num.toString()
        var p = 0
        var q = 0
        var pre = ""
        for (i in src.indices) {
            p = q
            q = ans
            ans = 0
            ans += q
            if (i == 0) {
                continue
            }
            pre = src.substring(i - 1, i + 1)
            if (pre <= "25" && pre >= "10") {
                ans += p
            }
        }
        return ans
    }

    fun isSubsequence(s: String, t: String): Boolean {//392
        var count = 0
        var i = 0
        var j = 0
        while (i < s.length && j < t.length) {
            if (s[i] == t[j]) {
                count++
                i++
                j++
            } else {
                j++
            }
        }
        return count == s.length
    }

    fun getLonelyNodes(root: TreeNode?): List<Int> {//1469
        val ans = mutableListOf<Int>()
        dfs(root, ans)
        return ans
    }

    fun dfs(node: TreeNode?, ans: MutableList<Int>) {
        if (node == null || node.left == null && node.right == null) {
            return
        }
        if (node.left != null && node.right == null) {
            ans.add(node.left.`val`)
            dfs(node.left, ans)
        } else if (node.left == null && node.right != null) {
            ans.add(node.right.`val`)
            dfs(node.right, ans)
        } else {
            dfs(node.left, ans)
            dfs(node.right, ans)
        }
    }

    fun threeSum(nums: IntArray): List<List<Int>> {//15
        val ans = mutableListOf<MutableList<Int>>()
        nums.sort()
        var i = 0
        while (i < nums.size - 2) {
            var left = i + 1
            var right = nums.size - 1
            var sum = 0
            while (left < right) {
                sum = nums[i] + nums[left] + nums[right]
                if (sum < 0) {
                    left++
                } else if (sum > 0) {
                    right--
                } else {
                    val e = mutableListOf<Int>()
                    e.add(nums[i])
                    e.add(nums[left])
                    e.add(nums[right])
                    ans.add(e)
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++
                    }
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--
                    }
                    left++
                    right--
                }
            }
            while (i < nums.size - 2 && nums[i] == nums[i + 1]) {
                i++
            }
            i++
        }
        return ans
    }

    fun finalPrices(prices: IntArray): IntArray {//1475
        var j = -1
        for (i in prices.indices) {
            j = -1
            for (k in i + 1 until prices.size) {
                if (prices[k] <= prices[i]) {
                    j = k
                    break
                }
            }
            if (j != -1) {
                prices[i] -= prices[j]
            }
        }
        return prices
    }

    fun deleteNodes(head: ListNode?, m: Int, n: Int): ListNode? {//1474
        var p = head
        var k = 0
        while (p != null) {
            k = m - 1
            while (p?.next != null && k > 0) {
                p = p?.next
                k--
            }
            k = n
            while (p?.next != null && k > 0) {
                p.next = p.next.next
                k--
            }
            p = p?.next
        }
        return head
    }

    fun runningSum(nums: IntArray): IntArray {//1480
        for (i in 1 until nums.size) {
            nums[i] += nums[i - 1]
        }
        return nums
    }

    fun findLeastNumOfUniqueInts(arr: IntArray, k: Int): Int {
        val map = mutableMapOf<Int, Int>()
        arr.forEach { map.compute(it) { _, v -> if (v == null) 1 else v + 1 } }
        val sorted = map.entries.sortedBy { it.value }
        var kk = k
        for (entry in sorted) {
            if (kk >= entry.value) {
                kk -= entry.value
                map.remove(entry.key)
            } else {
                break
            }
        }
        return map.size
    }

    fun findBestValue(arr: IntArray, target: Int): Int {//1300
        arr.sort()
        var sum = 0
        for (i in arr.indices) {
            val x = (target - sum) / (arr.size - i)
            if (x < arr[i]) {
                val t = (target - sum).toDouble() / (arr.size - i)
                return if (t - x > .5) {
                    x + 1
                } else {
                    x
                }
            }
            sum += arr[i]
        }
        return arr.last()
    }

    fun longestCommonPrefix(strs: Array<String>): String {//14
        var prefix = ""
        val min = strs.map { it.length }.min() ?: 0
        for (i in 1..min) {
            var flag = true
            for (j in 1 until strs.size) {
                if (strs[j].substring(0, i) != strs[j - 1].substring(0, i)) {
                    flag = false
                    break
                }
            }
            if (flag) {
                prefix = strs[0].substring(0, i)
            }
        }
        return prefix
    }

    var longest = 0
    fun longestUnivaluePath(root: TreeNode?): Int {//687
        longest = 0
        pathLength(root)
        return longest
    }

    private fun pathLength(root: TreeNode?): Int {
        if (root == null) {
            return 0
        }
        val left = pathLength(root.left)
        val right = pathLength(root.right)
        var pathLeft = 0
        var pathRight = 0
        if (root.left != null && root.left.`val` == root.`val`) {
            pathLeft += (left + 1)
        }
        if (root.right != null && root.right.`val` == root.`val`) {
            pathRight += (right + 1)
        }
        longest = longest.coerceAtLeast(pathLeft + pathRight)
        return pathLeft.coerceAtLeast(pathRight)
    }

    fun licenseKeyFormatting(S: String, K: Int): String {//482
        val ans = StringBuilder()
        var kk = K
        for (i in S.lastIndex downTo 0) {
            if (S[i] != '-') {
                if (kk == 0) {
                    kk = K
                    ans.append('-')
                }
                if (S[i].isDigit()) {
                    ans.append(S[i])
                    kk--
                } else if (S[i].isLetter()) {
                    ans.append(S[i].toUpperCase())
                    kk--
                }
            }
        }
        return ans.reversed().toString()
    }

    fun constructRectangle(area: Int): IntArray {//492
        var l = Math.ceil(Math.sqrt(area.toDouble())).toInt()
        for (i in l..area) {
            if (area % i == 0) {
                return intArrayOf(i, area / i)
            }
        }
        return intArrayOf()
    }

    fun validIPAddress(IP: String): String {//468
        if (IP.contains(".") && isValidIpV4(IP)) {
            return "IPv4"
        } else if (IP.contains(":") && isValidIpV6(IP)) {
            return "IPv6"
        } else {
            return "Neither"
        }
    }

    private fun isValidIpV4(ip: String): Boolean {
        val segs = ip.split(".")
        if (segs.size != 4) {
            return false
        }
        for (seg in segs) {
            if (!isValidIPv4Seg(seg)) {
                return false
            }
        }
        return true
    }

    private fun isValidIPv4Seg(seg: String): Boolean {//468
        if (seg.isEmpty() || seg.length > 3) {
            return false
        }
        for (c in seg) {
            if (!isValidIPv4Char(c)) {
                return false
            }
        }
        val num = seg.toInt()
        if (num > 255) {
            return false
        }
        if (seg.length == 2 && num < 10 || seg.length == 3 && num < 100) {
            return false
        }
        return true
    }

    private fun isValidIPv4Char(ch: Char): Boolean = ch in '0'..'9'

    private fun isValidIpV6(ip: String): Boolean {
        val segs = ip.split(":")
        if (segs.size != 8) {
            return false
        }
        for (seg in segs) {
            if (!isValidIPv6Seg(seg)) {
                return false
            }
        }
        return true
    }

    private fun isValidIPv6Seg(seg: String): Boolean {
        if (seg.isEmpty() || seg.length > 4) {
            return false
        }
        for (c in seg) {
            if (!isValidIPv6Char(c)) {
                return false
            }
        }
        return true
    }

    private fun isValidIPv6Char(ch: Char): Boolean = ch in '0'..'9' || ch in 'a'..'f' || ch in 'A'..'F'

    fun solve(board: Array<CharArray>): Unit {//130
        if (board.isNotEmpty() && board[0].isNotEmpty()) {
            val row = board.size
            val column = board[0].size
            var i = 0
            for (j in 0 until column) {
                if (board[i][j] == 'O') {
                    color(board, i, j)
                }
            }
            for (j in 0 until row) {
                if (board[j][i] == 'O') {
                    color(board, j, i)
                }
            }
            i = board.lastIndex
            for (j in 0 until column) {
                if (board[i][j] == 'O') {
                    color(board, i, j)
                }
            }
            i = board[0].lastIndex
            for (j in 0 until row) {
                if (board[j][i] == 'O') {
                    color(board, j, i)
                }
            }
            for (i in 0 until row) {
                for (j in 0 until column) {
                    if (board[i][j] == 'O') {
                        board[i][j] = 'X'
                    } else if (board[i][j] == '#') {
                        board[i][j] = 'O'
                    }
                }
            }
        }
    }

    private fun color(board: Array<CharArray>, x: Int, y: Int) {
        if (x < 0 || y < 0 || x >= board.size || y >= board[0].size) {
            return
        }
        board[x][y] = '#'
        if (x > 0 && board[x - 1][y] == 'O') {
            color(board, x - 1, y)
        }
        if (x < board.size - 1 && board[x + 1][y] == 'O') {
            color(board, x + 1, y)
        }
        if (y > 0 && board[x][y - 1] == 'O') {
            color(board, x, y - 1)
        }
        if (y < board[0].size - 1 && board[x][y + 1] == 'O') {
            color(board, x, y + 1)
        }
    }

    fun recoverFromPreorder(S: String): TreeNode? {//1028
        val path = mutableListOf<TreeNode>()
        var pos = 0
        while (pos < S.length) {
            var level = 0
            while (S[pos] == '-') {
                level++
                pos++
            }
            var value = 0
            while (pos < S.length && S[pos].isDigit()) {
                value *= 10
                value += S[pos] - '0'
                pos++
            }
            val node = TreeNode(value)
            if (level == path.size) {
                if (path.isNotEmpty()) {
                    path.last()?.left = node
                }
            } else {
                while (level != path.size) {
                    path.removeAt(path.lastIndex)
                }
                path.last()?.right = node
            }
            path.add(node)
        }
        while (path.size > 1) {
            path.removeAt(path.lastIndex)
        }
        return path.last()
    }

    fun hIndex(citations: IntArray): Int {//274
        citations.sort()
//        var ans = 0
//        for (i in citations.indices) {
//            if (citations.size - i <= citations[i]) {
//                ans = ans.coerceAtLeast((citations.size - i).coerceAtMost(citations[i]))
//            }
//        }
//        return ans
        val s = citations.size
        if (s == 0) {
            return 0
        }
        var l = 0
        var h = s - 1
        var mid = 0
        while (l < h) {
            mid = l + (h - l) / 2
            if (s - mid > citations[mid]) {
                l = mid + 1
            } else if (s - mid <= citations[mid]) {
                h = mid
            }
        }
        return citations[l].coerceAtMost(s - l)
    }

    fun hIndex1(citations: IntArray): Int {//275
        var ans = 0
        for (i in citations.indices) {
            if (citations.size - i <= citations[i]) {
                ans = ans.coerceAtLeast((citations.size - i).coerceAtMost(citations[i]))
            }
        }
        return ans
    }

    fun hIndex2(citations: IntArray): Int {//275
        val s = citations.size
        if (s == 0) {
            return 0
        }
        var l = 0
        var h = s - 1
        var mid = 0
        while (l < h) {
            mid = l + (h - l) / 2
            if (s - mid > citations[mid]) {
                l = mid + 1
            } else if (s - mid <= citations[mid]) {
                h = mid
            }
        }
        return citations[l].coerceAtMost(s - l)
    }

    private val EMPTY = Int.MAX_VALUE
    private val GATE = 0
    private val directions = arrayOf(arrayOf(1, 0), arrayOf(-1, 0), arrayOf(0, 1), arrayOf(0, -1))
    fun wallsAndGates(rooms: Array<IntArray>): Unit {//286
        if (rooms.isNotEmpty() && rooms[0].isNotEmpty()) {
            val row = rooms.size
            val column = rooms[0].size
            val q = mutableListOf<IntArray>()
            for (i in 0 until row) {
                for (j in 0 until column) {
                    if (rooms[i][j] == GATE) {
                        q.add(intArrayOf(i, j))
                    }
                }
            }
            while (q.isNotEmpty()) {
                val point = q.removeAt(0)
                for (d in directions) {
                    val r = point[0] + d[0]
                    val c = point[1] + d[1]
                    if (r < 0 || c < 0 || r >= row || c >= column || rooms[r][c] != EMPTY) {
                        continue
                    }
                    rooms[r][c] = rooms[point[0]][point[1]] + 1
                    q.add(intArrayOf(r, c))
                }
            }
        }
    }

    fun trimBST(root: TreeNode?, L: Int, R: Int): TreeNode? {//669
        if (root == null) {
            return null
        }
        if (root.`val` > R) {
            return trimBST(root.left, L, R)
        }
        if (root.`val` < L) {
            return trimBST(root.right, L, R)
        }
        root.left = trimBST(root.left, L, R)
        root.right = trimBST(root.right, L, R)
        return root
    }

    fun longestDupSubstring(S: String): String {//1044
        val size = S.length
        val nums = IntArray(size)
        for (i in S.indices) {
            nums[i] = S[i] - 'a'
        }
        val modulus = Math.pow(2.0, 32.0).toLong()
        var left = 1
        var right = size
        var mid = 0
        while (left < right) {
            mid = left + (right - left) / 2
            if (search(mid, modulus, nums) > -1) {
                left = mid + 1
            } else {
                right = mid
            }
        }
        val start = search(left - 1, modulus, nums)
        if (start > -1) {
            return S.substring(start, start + left - 1)
        } else {
            return ""
        }
    }

    private fun search(length: Int, modulus: Long, nums: IntArray): Int {
        var hash = 0L
        for (i in 0 until length) {
            hash = (hash * 26 + nums[i]) % modulus
        }
        val seen = mutableSetOf<Long>()
        seen.add(hash)
        var aL = 1L
        for (i in 1..length) {
            aL = (aL * 26L) % modulus
        }
        for (start in 1..nums.size - length) {
            hash = (hash * 26 - nums[start - 1] * aL % modulus + modulus) % modulus
            hash = (hash + nums[start + length - 1]) % modulus
            if (seen.contains(hash)) return start
            seen.add(hash)
        }
        return -1
    }

    fun isMatch(s: String, p: String): Boolean {//10
        val m = s.length
        val n = p.length
        val f = Array(m + 1) { BooleanArray(n + 1) { false } }
        f[0][0] = true
        for (i in 0..m) {
            for (j in 1..n) {
                if (p[j - 1] == '*') {
                    f[i][j] = f[i][j - 2]
                    if (matches(s, p, i, j - 1)) {
                        f[i][j] = f[i][j] || f[i - 1][j]
                    }
                } else {
                    if (matches(s, p, i, j)) {
                        f[i][j] = f[i - 1][j - 1]
                    }
                }
            }
        }
        return f[m][n]
    }

    private fun matches(s: String, p: String, i: Int, j: Int): Boolean {
        if (i == 0) return false
        if (p[j - 1] == '.') return true
        return s[i - 1] == p[j - 1]
    }

    fun isMatch1(s: String, p: String): Boolean {//44
        val m = s.length
        val n = p.length
        var sIdx = 0
        var pIdx = 0
        var startIdx = -1
        var sTmpIdx = -1
        while (sIdx < m) {
            // If the pattern caracter = string character
            // or pattern character = '?'
            if (pIdx < n && (p[pIdx] == '?' || s[sIdx] == p[pIdx])) {
                ++sIdx
                ++pIdx
            }// If pattern character = '*'
            else if (pIdx < n && p[pIdx] == '*') {
                // Check the situation
                // when '*' matches no characters
                startIdx = pIdx
                sTmpIdx = sIdx
                ++pIdx
            } // If pattern character != string character
            // or pattern is used up
            // and there was no '*' character in pattern
            else if (startIdx == -1) {
                return false
            }
            // If pattern character != string character
            // or pattern is used up
            // and there was '*' character in pattern before
            else {
                // Backtrack: check the situation
                // when '*' matches one more character
                pIdx = startIdx + 1
                sIdx = sTmpIdx + 1
                sTmpIdx = sIdx
            }
        }
        // The remaining characters in the pattern should all be '*' characters
        for (i in pIdx until n) {
            if (p[i] != '*') return false
        }
        return true
    }

    fun getPermutation(n: Int, k: Int): String {//60
        val factorials = IntArray(n)
        val nums = mutableListOf<Int>()
        factorials[0] = 1
        nums.add(1)
        for (i in 1 until n) {
            factorials[i] = i * factorials[i - 1]
            nums.add(i + 1)
        }
        var kk = k - 1
        val ans = StringBuilder()
        var idx = 0
        for (i in n - 1 downTo 0) {
            idx = kk / factorials[i]
            kk -= idx * factorials[i]
            ans.append(nums[idx])
            nums.removeAt(idx)
        }
        return ans.toString()
    }

    fun calculateMinimumHP(dungeon: Array<IntArray>): Int {//174
        if (dungeon.isNotEmpty() && dungeon[0].isNotEmpty()) {
            val row = dungeon.size
            val column = dungeon[0].size
            val dp = Array(row) { IntArray(column) }
            dp[row - 1][column - 1] = 0.coerceAtLeast(-dungeon[row - 1][column - 1])
            for (i in row - 2 downTo 0) {
                val needMin = dp[i + 1][column - 1] - dungeon[i][column - 1]
                dp[i][column - 1] = 0.coerceAtLeast(needMin)
            }
            for (j in column - 2 downTo 0) {
                val needMin = dp[row - 1][j + 1] - dungeon[row - 1][j]
                dp[row - 1][j] = 0.coerceAtLeast(needMin)
            }
            for (i in row - 2 downTo 0) {
                for (j in column - 2 downTo 0) {
                    val needMin = dp[i + 1][j].coerceAtMost(dp[i][j + 1]) - dungeon[i][j]
                    dp[i][j] = 0.coerceAtLeast(needMin)
                }
            }
            return dp[0][0] + 1
        }
        return 0
    }

    fun patternMatching(pattern: String, value: String): Boolean {//面试题16.18
        var countA = 0
        var countB = 0
        for (ch in pattern) {
            if (ch == 'a') {
                ++countA
            } else {
                ++countB
            }
        }
        var ptn = pattern
        if (countA < countB) {
            val tmp = countA
            countA = countB
            countB = tmp
            val chArr = pattern.toCharArray()
            for (i in chArr.indices) {
                chArr[i] = if (chArr[i] == 'a') 'b' else 'a'
            }
            ptn = String(chArr)
        }
        if (value.isEmpty()) {
            return countA == 0
        }
        if (ptn.isEmpty()) {
            return false
        }
        var lenA = 0
        while (lenA * countA <= value.length) {
            val left = value.length - lenA * countA
            if (countB == 0 && left == 0 || countB != 0 && left % countB == 0) {
                val len_b = if (countB == 0) 0 else left / countB
                var pos = 0
                var correct = true
                var valA = ""
                var valB = ""
                for (ch in ptn) {
                    if (ch == 'a') {
                        val sub = value.substring(pos, pos + lenA)
                        if (valA.isEmpty()) {
                            valA = sub
                        } else if (valA != sub) {
                            correct = false
                            break
                        }
                        pos += lenA
                    } else {
                        val sub = value.substring(pos, pos + len_b)
                        if (valB.isEmpty()) {
                            valB = sub
                        } else if (valB != sub) {
                            correct = false
                            break
                        }
                        pos += len_b
                    }
                }
                if (correct && valA != valB) {
                    return true
                }
            }
            ++lenA
        }
        return false
    }

    fun singleNumber(nums: IntArray): Int {//137
        var seen1 = 0
        var seen2 = 0
        for (n in nums) {
            seen1 = seen2.inv() and (seen1 xor n)
            seen2 = seen1.inv() and (seen2 xor n)
        }
        return seen1
    }

    fun findContentChildren(g: IntArray, s: IntArray): Int {//455
        g.sort()
        s.sort()
        var i = 0
        var j = 0
        var ans = 0
        while (i < g.size && j < s.size) {
            if (g[i] > s[j]) {
                while (j < s.size && g[i] > s[j]) {
                    ++j
                }
                if (j < s.size) {
                    ++i
                    ++j
                    ++ans
                }
            } else {
                ++i
                ++j
                ++ans
            }
        }
        return ans
    }

    fun findRadius(houses: IntArray, heaters: IntArray): Int {//475
        houses.sort()
        heaters.sort()
        var ans = 0
        var k = 0
        for (i in houses.indices) {
            var radius = Int.MAX_VALUE
            for (j in k until heaters.size) {
                k = if (houses[i] >= heaters[j]) j else k
                radius = radius.coerceAtMost(Math.abs(houses[i] - heaters[j]))
                if (houses[i] < heaters[j]) break
            }
            ans = ans.coerceAtLeast(radius)
        }
        return ans
    }

    fun countNodes(root: TreeNode?): Int {//222
        if (root == null) return 0
        val depth = depthOfCompleteBT(root)
        if (depth == 0) return 1
        var left = 1
        var right = Math.pow(2.0, depth.toDouble()).toInt() - 1
        var pivot = 0
        while (left <= right) {
            pivot = left + (right - left) / 2
            if (exists(pivot, depth, root)) left = pivot + 1
            else right = pivot - 1
        }
        return Math.pow(2.0, depth.toDouble()).toInt() - 1 + left
    }

    private fun depthOfCompleteBT(root: TreeNode?): Int {
        var depth = 0
        var p = root?.left
        while (p != null) {
            ++depth
            p = p.left
        }
        return depth
    }

    private fun exists(idx: Int, depth: Int, node: TreeNode?): Boolean {
        var left = 0
        var right = Math.pow(2.0, depth.toDouble()).toInt() - 1
        var pivot = 0
        var p = node
        for (i in 0 until depth) {
            pivot = left + (right - left) / 2
            if (idx <= pivot) {
                p = p?.left
                right = pivot
            } else {
                p = p?.right
                left = pivot + 1
            }
        }
        return p != null
    }


    fun xorOperation(n: Int, start: Int): Int {//1486
        var xorval = 0
        for (i in 0 until n) {
            xorval = xorval xor (start + 2 * i)
        }
        return xorval
    }

    fun getFolderNames(names: Array<String>): Array<String> {
        val map = mutableMapOf<String, Int>()
        for (i in names.indices) {
            if (map.containsKey(names[i])) {
                map.compute(names[i]) { _, v -> if (v == null) 1 else v + 1 }
                var j = map[names[i]]!! - 1
                var tmp = "${names[i]}($j)"
                while (map.containsKey(tmp)) {
                    ++j
                    tmp = "${names[i]}($j)"
                }
                names[i] = tmp
            } else {
                map[names[i]] = 1
            }
        }
        return names
    }
}