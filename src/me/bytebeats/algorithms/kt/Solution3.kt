package me.bytebeats.algorithms.kt

import me.bytebeats.algorithms.meta.ListNode
import me.bytebeats.algorithms.meta.TreeNode

class Solution3 {
    fun removeDuplicates(nums: IntArray): Int {
        var size = nums.size
        var k = 0
        var j = 0
        var num = 0
        while (k < size) {
            num = nums[k]
            j = k + 1
            while (j < size && num == nums[j]) {
                j++
            }
            if (j - k > 1) {
                for (h in j until size) {
                    nums[h - j + k + 2] = nums[h]
                }
                size -= (j - k - 2)
                k += 2
            } else {
                k = j
            }
        }
        return size
    }

    fun countLargestGroup(n: Int): Int {
        val map = HashMap<Int, ArrayList<Int>>()
        for (i in 1..n) {
            map.compute(getSum(i)) { k, v ->
                if (v == null) {
                    val e = ArrayList<Int>()
                    e.add(i)
                    e
                } else {
                    v.add(i)
                    v
                }
            }
        }
        val max = map.values.map { it.size }.max()
        return map.values.filter { it.size == max }.count()
    }

    private fun getSum(num: Int): Int {
        var sum = 0
        var n = num
        while (n != 0) {
            sum += n % 10
            n /= 10
        }
        return sum
    }

    fun canConstruct(s: String, k: Int): Boolean {
        if (s.length < k) {
            return false
        }
        if (s.length == k) {
            return true
        }
        val counts = IntArray(26)
        for (c in s) {
            counts[c - 'a']++
        }
        var oddCount = 0
        for (ch in counts) {
            if (ch > 0) {
                if (ch and 1 == 1) {
                    oddCount++
                }
            }
        }
        return oddCount <= k
    }

    fun checkOverlap(radius: Int, x_center: Int, y_center: Int, x1: Int, y1: Int, x2: Int, y2: Int): Boolean {
        val x = (x1.toDouble() + x2) / 2
        val y = (y1.toDouble() + y2) / 2
        val p = Math.sqrt(Math.pow((x1.toDouble() - x2) / 2, 2.0) + Math.pow((y1.toDouble() - y2) / 2, 2.0))
        val q = Math.sqrt(Math.pow((x - x_center), 2.0) + Math.pow((y - y_center), 2.0))
        return q - p <= radius
    }

    fun groupAnagrams(strs: Array<String>): List<List<String>> {
        val map = mutableMapOf<String, MutableList<String>>()
        strs.forEach {
            val key = key(it)
            if (map.containsKey(key)) {
                map[key]?.add(it)
            } else {
                val list = arrayListOf<String>()
                list.add(it)
                map[key] = list
            }
        }
        return map.values.toList()
    }

    private fun key(str: String): String {
        val chars = IntArray(26)
        str.forEach { chars[it - 'a'] += 1 }
        val ans = StringBuilder()
        chars.forEach {
            ans.append(it)
            ans.append(',')
        }
        return ans.toString()
    }

    fun containsNearbyAlmostDuplicate(nums: IntArray, k: Int, t: Int): Boolean {
        if (nums.size == 0 || k < 1) {
            return false
        }
        var count = k
        while (count > 0) {
            for (i in count until nums.size) {
                if (Math.abs(nums[i] - nums[i - count]) <= t) {
                    return true
                }
            }
            count--
        }
        return false
    }

    fun containsNearbyAlmostDuplicate1(nums: IntArray, k: Int, t: Int): Boolean {
        val set = sortedSetOf<Int>()
        for (i in nums.indices) {
            val s = set.ceiling(nums[i])
            if (s != null && s <= nums[i] + t) {
                return true
            }
            val g = set.floor(nums[i])
            if (g != null && nums[i] <= g + t) {
                return true
            }
            set.add(nums[i])
            if (set.size > k) {
                set.remove(nums[i - k])
            }
        }
        return false
    }

    fun deleteNode(head: ListNode?, `val`: Int): ListNode? {// 面试题18,  删除链表节点
        if (head == null) {
            return null
        }
        if (head.`val` == `val`) {
            return head.next
        }
        var pre = head
        var cur = head.next
        while (cur != null && cur.`val` != `val`) {
            pre = cur
            cur = cur.next
        }
        pre?.next = cur?.next
        return head
    }

    fun rotateRight(head: ListNode?, k: Int): ListNode? {//61
        var size = 0
        var p = head
        while (p != null) {
            size++
            p = p.next
        }
        if (size == 0 || k % size == 0) {
            return head
        }
        var d = size - k % size
        p = head
        var pre: ListNode? = null
        while (d-- > 0) {
            pre = p
            p = p?.next
        }
        pre?.next = null
        pre = p
        while (p != null && p.next != null) {
            p = p.next
        }
        p?.next = head
        return pre
    }

    fun rotateRight1(head: ListNode?, k: Int): ListNode? {//61, 连接成环, 移动后再断开
        if (head?.next == null || k == 0) {
            return head
        }
        var size = 0
        var p = head
        var pre: ListNode? = null
        while (p != null) {
            size++
            pre = p
            p = p.next
        }
        if (k % size == 0) {
            return head
        }
        pre?.next = head
        var d = size - k % size
        p = head
        pre = null
        while (d-- > 0) {
            pre = p
            p = p?.next
        }
        pre?.next = null
        return p
    }

    fun superEggDrop(K: Int, N: Int): Int {//887
        // Right now, dp[i] represents dp(1, i)
        var dp = IntArray(N + 1)
        for (i in 0..N) {
            dp[i] = i
        }
        for (k in 2..K) {
            // Now, we will develop dp2[i] = dp(k, i)
            val dp2 = IntArray(N + 1)
            var x = 1
            for (n in 1..N) {
                // Let's find dp2[n] = dp(k, n)
                // Increase our optimal x while we can make our answer better.
                // Notice max(dp[x-1], dp2[n-x]) > max(dp[x], dp2[n-x-1])
                // is simply max(T1(x-1), T2(x-1)) > max(T1(x), T2(x)).
                while (x < n && Math.max(dp[x - 1], dp2[n - x]) > Math.max(dp[x], dp2[n - x - 1])) {
                    x++
                }
                // The final answer happens at this x.
                dp2[n] = 1 + Math.max(dp[x - 1], dp2[n - x])
            }
            dp = dp2
        }
        return dp[N]
    }

    fun wordPattern(pattern: String, str: String): Boolean {//290
        if (pattern.isEmpty() || str.isEmpty() || pattern.length != str.split(" ").size) {
            return false
        }
        val map = mutableMapOf<Char, String>()
        val words = str.split(" ")
        pattern.forEachIndexed { index, c ->
            if (map.containsKey(c)) {
                if (map[c] != words[index]) {
                    return false
                }
            } else if (map.containsValue(words[index])) {
                return false
            } else {
                map[c] = words[index]
            }
        }
        return true
    }

    fun suggestedProducts(products: Array<String>, searchWord: String): List<List<String>> {//1268
        val ans = mutableListOf<List<String>>()
        var pre = StringBuilder()
        searchWord.forEach {
            pre.append(it)
            ans.add(products.filter { it.startsWith(pre) }.sorted().take(3))
        }
        return ans
    }

    fun stringMatching(words: Array<String>): List<String> {
        val set = mutableSetOf<String>()
        val ans = mutableListOf<String>()
        set.addAll(words)
        words.forEach { word ->
            if (set.filter { it.contains(word) && it != word }.count() > 0) {
                ans.add(word)
            }
        }
        return ans
    }

    fun processQueries(queries: IntArray, m: Int): IntArray {
        val p = IntArray(m)
        for (i in 1..m) {
            p[i - 1] = i
        }
        queries.forEachIndexed { index, i ->
            val position = p.indexOf(i)
            for (j in position downTo 1) {
                p[j] = p[j - 1]
            }
            p[0] = i
            queries[index] = position
        }
        return queries
    }

    fun entityParser(text: String): String {
        val map = mapOf(
                "&quot;" to "\"",
                "&apos;" to "'",
                "&amp;" to "&",
                "&gt;" to ">",
                "&lt;" to "<",
                "&frasl;" to "/"
        )
        var ans = text
        map.forEach { key, value -> ans = ans.replace(key, value) }
        return ans
    }

    fun numOfWays(n: Int): Int {
        var ans = 0
        return ans
    }

    fun intersection(start1: IntArray, end1: IntArray, start2: IntArray, end2: IntArray): DoubleArray {//面试题16.03 相交
        val ans = mutableListOf<Double>()
        val x1 = start1[0]
        val y1 = start1[1]
        val x2 = end1[0]
        val y2 = end1[1]
        val x3 = start2[0]
        val y3 = start2[1]
        val x4 = end2[0]
        val y4 = end2[1]
        // 判断 (x1, y1)~(x2, y2) 和 (x3, y3)~(x4, y3) 是否平行
        if ((y4 - y3) * (x2 - x1) == (y2 - y1) * (x4 - x3)) {
            // 若平行，则判断 (x3, y3) 是否在「直线」(x1, y1)~(x2, y2) 上
            if ((y2 - y1) * (x3 - x1) == (y3 - y1) * (x2 - x1)) {
                // 判断 (x3, y3) 是否在「线段」(x1, y1)~(x2, y2) 上
                if (inside(x1, y1, x2, y2, x3, y3)) {
                    update(ans, x3.toDouble(), y3.toDouble())
                }
                // 判断 (x4, y4) 是否在「线段」(x1, y1)~(x2, y2) 上
                if (inside(x1, y1, x2, y2, x4, y4)) {
                    update(ans, x4.toDouble(), y4.toDouble())
                }
                // 判断 (x1, y1) 是否在「线段」(x3, y3)~(x4, y4) 上
                if (inside(x3, y3, x4, y4, x1, y1)) {
                    update(ans, x1.toDouble(), y1.toDouble())
                }
                // 判断 (x2, y2) 是否在「线段」(x3, y3)~(x4, y4) 上
                if (inside(x3, y3, x4, y4, x2, y2)) {
                    update(ans, x2.toDouble(), y2.toDouble())
                }
            }
            // 在平行时，其余的所有情况都不会有交点
        } else {
            // 联立方程得到 t1 和 t2 的值
            val t1 =
                    (x3.toDouble() * (y4 - y3) + y1 * (x4 - x3) - y3 * (x4 - x3) - x1 * (y4 - y3)) / ((x2 - x1) * (y4 - y3) - (x4 - x3) * (y2 - y1));
            val t2 =
                    (x1.toDouble() * (y2 - y1) + y3 * (x2 - x1) - y1 * (x2 - x1) - x3 * (y2 - y1)) / ((x4 - x3) * (y2 - y1) - (x2 - x1) * (y4 - y3));
            // 判断 t1 和 t2 是否均在 [0, 1] 之间
            if (t1 in 0.0..1.0 && t2 in 0.0..1.0) {
                ans.add(x1 + t1 * (x2 - x1))
                ans.add(y1 + t1 * (y2 - y1))
            }
        }
        return ans.toDoubleArray()
    }

    // 判断 (xk, yk) 是否在「线段」(x1, y1)~(x2, y2) 上
    // 这里的前提是 (xk, yk) 一定在「直线」(x1, y1)~(x2, y2) 上
    private fun inside(x1: Int, y1: Int, x2: Int, y2: Int, xk: Int, yk: Int): Boolean {
        // 若与 x 轴平行，只需要判断 x 的部分
        // 若与 y 轴平行，只需要判断 y 的部分
        // 若为普通线段，则都要判断
        return (x1 == x2 || xk >= Math.min(x1, x2) && xk <= Math.max(x1, x2))
                && (y1 == y2 || yk >= Math.min(y1, y2) && yk <= Math.max(y1, y2))
    }

    private fun update(ans: MutableList<Double>, xk: Double, yk: Double) {
        if (ans.isEmpty()) {
            ans.add(xk)
            ans.add(yk)
        } else if (xk < ans[0]) {
            ans[0] = xk
            ans[1] = yk
        } else if (xk == ans[0] && yk < ans[1]) {
            ans[0] = xk
            ans[1] = yk
        }
    }

    fun fizzBuzz(n: Int): List<String> {//412
        val ans = mutableListOf<String>()
        for (i in 1..n) {
            if (i % 15 == 0) {
                ans.add("FizzBuzz")
            } else if (i % 5 == 0) {
                ans.add("Buzz")
            } else if (i % 3 == 0) {
                ans.add("Fizz")
            } else {
                ans.add("$i")
            }
        }
        return ans
    }

    fun thirdMax(nums: IntArray): Int {//414
        var max = Long.MIN_VALUE
        nums.forEach {
            if (max < it) {
                max = it.toLong()
            }
        }
        var max2 = Long.MIN_VALUE
        nums.forEach {
            if (it < max && max2 < it) {
                max2 = it.toLong()
            }
        }
        var max3 = Long.MIN_VALUE
        nums.forEach {
            if (it < max2 && max3 < it) {
                max3 = it.toLong()
            }
        }
        if (max3 > Long.MIN_VALUE) {
            return max3.toInt()
        } else {
            return max.toInt()
        }
    }

    fun findMaxLength(nums: IntArray): Int {//525
        val counts = IntArray(nums.size * 2 + 1) { -2 }
        counts[nums.size] = -1
        var max = 0
        var count = 0
        for (i in nums.indices) {
            count += if (nums[i] == 0) -1 else 1
            if (counts[count + nums.size] >= -1) {
                if (max < i - counts[count + nums.size]) {
                    max = i - counts[count + nums.size]
                }
            } else {
                counts[count + nums.size] = i
            }
        }
        return max
    }

    fun pathSum(root: TreeNode?, sum: Int): Int {//437
        if (root == null) {
            return 0
        }
        return pathSumHelper(root, sum) + pathSum(root?.left, sum) + pathSum(root?.right, sum)
    }

    private fun pathSumHelper(node: TreeNode?, sum: Int): Int {
        if (node == null) {
            return 0
        } else {
            val nextSum = sum - node.`val`
            return (if (nextSum == 0) 1 else 0) + pathSumHelper(node.left, nextSum) + pathSumHelper(node.right, nextSum)
        }
    }

    fun pathSum1(root: TreeNode?, sum: Int): Int {//437
        if (root == null) {
            return 0
        }
        return pathSum1(root, sum, 0) + pathSum1(root?.left, sum) + pathSum1(root?.right, sum)
    }

    private fun pathSum1(node: TreeNode?, sum: Int, subSum: Int): Int {
        if (node == null) {
            return 0
        } else {
            var count = 0
            val nextSum = subSum + node.`val`
            if (nextSum == sum) {
                count++
            }
            count += pathSum1(node.left, sum, nextSum)
            count += pathSum1(node.right, sum, nextSum)
            return count
        }
    }

    fun stringShift(s: String, shift: Array<IntArray>): String {
        val ans = s.toCharArray().toMutableList()
        var ch = ' '
        shift.forEach {
            var bits = it[1] % ans.size
            while (bits-- > 0) {
                if (it[0] == 0) {
                    ch = ans.first()
                    ans.removeAt(0)
                    ans.add(ch)
                } else {
                    ch = ans.last()
                    ans.removeAt(ans.lastIndex)
                    ans.add(0, ch)
                }
            }
        }
        return String(ans.toCharArray())
    }

//    fun groupStrings(strings: Array<String>): List<List<String>> {
//        val ans = mutableMapOf<String, List<String>>()
//        strings.forEach { str ->
//            if (contains(ans.keys, str)) {
//                ans[]
//            } else {
//                ans[str]
//            }
//        }
//        return ans.values.toList()
//    }

//    private fun contains(keys: Set<String>, key: String): Boolean {
//        var contains = false
//        keys.filter { key.length == it.length }.forEach { str ->
//            if (key.length == 1) {
//                contains = true
//            } else {
//                val diff = key[0] - str[0]
//                var same = true
//                for (i in 1..key.lastIndex) {
//                    if (key[i] - str[i] != diff) {
//                        same = false
//                    }
//                }
//                contains = contains or same
//            }
//        }
//        return contains
//    }

//    fun shiftGrid(grid: Array<IntArray>, k: Int): List<List<Int>> {
//        val count = grid.size * grid[0].size
//        val shift = k % count
//
//    }

    fun sumRootToLeaf(root: TreeNode?): Int {//1022
        if (root == null) {
            return 0
        }
        val binaryList = mutableListOf<String>()
        sumRootToLeaf(root, binaryList, StringBuilder())
        var ans = 0
        binaryList.forEach { ans += it.toInt(2) }
        return ans
    }

    fun sumRootToLeaf(root: TreeNode?, binaryList: MutableList<String>, seq: StringBuilder) {
        if (root == null) {
            return
        } else if (root.left == null && root.right == null) {
            seq.append(root.`val`)
            binaryList.add(seq.toString())
        } else {
            val value = root.`val`
            if (root.left != null) {
                val newSeq = StringBuilder(seq)
                newSeq.append(value)
                sumRootToLeaf(root.left, binaryList, newSeq)
            }
            if (root.right != null) {
                val newSeq = StringBuilder(seq)
                newSeq.append(value)
                sumRootToLeaf(root.right, binaryList, newSeq)
            }
        }
    }

    fun updateMatrix(matrix: Array<IntArray>): Array<IntArray> {//542
        val row = matrix.size
        val column = matrix[0].size
        val dist = Array(row) { IntArray(column) { 0 } }
        for (i in 0 until row) {
            for (j in 0 until column) {
                dist[i][j] = Int.MAX_VALUE - 10
                if (matrix[i][j] == 0) {
                    dist[i][j] = 0
                } else {
                    if (i > 0) {
                        dist[i][j] = min(dist[i][j], dist[i - 1][j] + 1)
                    }
                    if (j > 0) {
                        dist[i][j] = min(dist[i][j], dist[i][j - 1] + 1)
                    }
                }
            }
        }
        for (i in row - 1 downTo 0) {
            for (j in column - 1 downTo 0) {
                if (i < row - 1) {
                    dist[i][j] = min(dist[i][j], dist[i + 1][j] + 1)
                }
                if (j < column - 1) {
                    dist[i][j] = min(dist[i][j], dist[i][j + 1] + 1)
                }
            }
        }
        return dist
    }

    private fun min(x: Int, y: Int): Int = if (x > y) y else x

    var closest = Double.MAX_VALUE
    var value = 0
    fun closestValue(root: TreeNode?, target: Double): Int {//270
        closest = Double.MAX_VALUE
        value = 0
        preReversal(root, target)
        return value
    }

    private fun preReversal(root: TreeNode?, target: Double) {
        if (root == null) {
            return
        }
        if (Math.abs(root.`val` - target) < closest) {
            closest = Math.abs(root.`val` - target)
            value = root.`val`
        }
        if (root.left != null) {
            preReversal(root.left, target)
        }
        if (root.right != null) {
            preReversal(root.right, target)
        }
    }

    fun insertIntoBST(root: TreeNode?, `val`: Int): TreeNode? {//701
        if (root == null) {
            return TreeNode(`val`)
        }
        var p = root
        while (p != null) {
            if (p.`val` > `val`) {
                if (p.left != null) {
                    p = p.left
                } else {
                    p.left = TreeNode(`val`)
                    p = null
                }
            } else {
                if (p.right != null) {
                    p = p.right
                } else {
                    p.right = TreeNode(`val`)
                    p = null
                }
            }
        }
        return root
    }

//    fun findLeaves(root: TreeNode?): List<List<Int>> {//366
//
//    }

    fun productExceptSelf(nums: IntArray): IntArray {//238
        var sum = 1
        var count = 0 //count zero
        nums.forEach {
            if (it == 0) {
                count++
            } else {
                sum *= it//multiply except 0s
            }
        }
        nums.forEachIndexed { index, num ->
            if (num == 0) {
                if (count > 1) {
                    nums[index] = 0
                } else {
                    nums[index] = sum
                }
            } else {
                if (count > 0) {
                    nums[index] = 0
                } else {
                    nums[index] = sum / num
                }
            }
        }
        return nums
    }

    fun maxProduct(nums: IntArray): Int {//152
        var max = Int.MIN_VALUE
        var imax = 1
        var imin = 1
        nums.forEach {
            if (it < 0) {
                val tmp = imax
                imax = imin
                imin = tmp
            }
            imax = Math.max(imax * it, it)
            imin = Math.min(imin * it, it)
            max = Math.max(imax, max)
        }
        return max
    }

    fun maximumProduct(nums: IntArray): Int {//628
        nums.sort()
        if (nums.first() >= 0 || nums.last() <= 0) {
            val last = nums.lastIndex
            return nums[last] * nums[last - 1] * nums[last - 2]
        } else {
            var index = 0
            for (i in nums.indices) {
                if (nums[i] >= 0) {
                    index = i - 1
                    break
                }
            }
            if (index < 1) {
                val filtered = nums.takeLast(3)
                return filtered[0] * filtered[1] * filtered[2]
            } else {
                val p1 = nums[0] * nums[1] * nums.last()
                val last = nums.lastIndex
                val p2 = nums[last] * nums[last - 1] * nums[last - 2]
                return if (p1 > p2) p1 else p2
            }
        }
    }

    fun merge(intervals: Array<IntArray>): Array<IntArray> {//56
        val ans = mutableListOf<IntArray>()
        if (intervals.isNotEmpty()) {
            intervals.sortBy { it[0] }
            var tmp = intervals[0]
            for (i in 1 until intervals.size) {
                if (tmp[1] >= intervals[i][0]) {
                    val newRange = IntArray(2)
                    newRange[0] = tmp[0]
                    newRange[1] = if (tmp[1] > intervals[i][1]) tmp[1] else intervals[i][1]
                    tmp = newRange
                } else {
                    ans.add(tmp)
                    tmp = intervals[i]
                }
            }
            ans.add(tmp)
        }
        return ans.toTypedArray()
    }

    fun canAttendMeetings(intervals: Array<IntArray>): Boolean {//252
        val range = IntArray(2) { -1 }
        if (intervals.isNotEmpty()) {
            intervals.sortBy { it[0] }
            range[0] = intervals[0][0]
            range[1] = intervals[0][1]
            for (i in 1 until intervals.size) {
                if (range[1] < intervals[i][0]) {
                    range[0] = intervals[i][0]
                    range[1] = intervals[i][1]
                } else {
                    return false
                }
            }
        }
        return true
    }

    fun minMeetingRooms(intervals: Array<IntArray>): Int {//253
        intervals.sortBy { it[0] }
        val intervalList = intervals.toMutableList()
        val ans = mutableListOf<List<IntArray>>()
        while (intervalList.isNotEmpty()) {
            val room = mutableListOf<IntArray>()
            room.add(intervalList[0])
            var tmp = intervalList[0]
            for (i in 1 until intervalList.size) {
                if (tmp[1] <= intervalList[i][0]) {
                    room.add(intervalList[i])
                    tmp = intervalList[i]
                }
            }
            ans.add(room)
            room.forEach { intervalList.removeAt(intervalList.indexOf(it)) }
        }
        return ans.size
    }

    fun insert(intervals: Array<IntArray>, newInterval: IntArray): Array<IntArray> {//57
        val ans = mutableListOf<IntArray>()
        for (i in intervals.indices) {
            if (intervals[i][1] < newInterval[0]) {
                ans.add(intervals[i])
                if (i == intervals.lastIndex) {
                    ans.add(newInterval)
                }
            } else if (intervals[i][0] > newInterval[1]) {
                ans.add(newInterval)
                for (j in i until intervals.size) {
                    ans.add(intervals[j])
                }
                break
            } else {
                newInterval[0] = Math.min(newInterval[0], intervals[i][0])
                newInterval[1] = Math.max(newInterval[1], intervals[i][1])
                if (i == intervals.lastIndex) {
                    ans.add(newInterval)
                }
            }
        }
        if (ans.isEmpty()) {
            ans.add(newInterval)
        }
        return ans.toTypedArray()
    }

    fun intervalIntersection(A: Array<IntArray>, B: Array<IntArray>): Array<IntArray> {//986
        val ans = mutableListOf<IntArray>()
        var i = 0
        var j = 0
        var range: IntArray?
        while (i < A.size && j < B.size) {
            val l = Math.max(A[i][0], B[j][0])
            val h = Math.min(A[i][1], B[j][1])
            if (l <= h) {
                range = IntArray(2)
                range[0] = l
                range[1] = h
                ans.add(range)
            }
            if (A[i][1] < B[j][1]) {
                i++
            } else {
                j++
            }
        }
        return ans.toTypedArray()
    }

    fun canJump(nums: IntArray): Boolean {//55
        var rightMost = 0
        for (i in nums.indices) {
            if (i <= rightMost) {
                rightMost = Math.max(rightMost, i + nums[i])
                if (rightMost >= nums.lastIndex) {
                    return true
                }
            }
        }
        return false
    }

    fun canReach(arr: IntArray, start: Int): Boolean {//1306
        return canReach(arr, start, mutableSetOf())
    }

    fun canReach(arr: IntArray, start: Int, set: MutableSet<Int>): Boolean {//1306
        if (start < 0 || start > arr.lastIndex) {
            return false
        } else if (arr[start] == 0) {
            return true
        } else {
            if (set.contains(start)) {
                return false
            }
            set.add(start)
            return canReach(arr, start - arr[start], set) or canReach(arr, start + arr[start], set)
        }
    }

    fun jump(nums: IntArray): Int {//45
        var end = 0
        var maxPos = 0
        var steps = 0
        for (i in 0 until nums.lastIndex) {
            maxPos = Math.max(maxPos, i + nums[i])
            if (i == end) {
                end = maxPos
                steps++
            }
        }
        return steps
    }

    fun numIslands(grid: Array<CharArray>): Int {//200
        if (grid.isEmpty() || grid[0].isEmpty()) {
            return 0
        }
        var islands = 0
        val row = grid.size
        val column = grid[0].size
        for (i in 0 until row) {
            for (j in 0 until column) {
                if (grid[i][j] == '1') {
                    dfs(grid, i, j)
                    islands++
                }
            }
        }
        return islands
    }

    private fun dfs(grid: Array<CharArray>, row: Int, column: Int) {
        if (row < 0 || column < 0 || row > grid.lastIndex || column > grid[0].lastIndex || grid[row][column] == '0') {
            return
        }
        grid[row][column] = '0'
        dfs(grid, row - 1, column)
        dfs(grid, row + 1, column)
        dfs(grid, row, column - 1)
        dfs(grid, row, column + 1)
    }

    fun minCount(coins: IntArray): Int {
        var count = 0
        coins.forEach {
            if (it and 1 == 1) {
                count += (it + 1) / 2
            } else {
                count += it / 2
            }
        }
        return count
    }

    var count = 0
    fun numWays(n: Int, relation: Array<IntArray>, k: Int): Int {
        count = 0
        val map = mutableMapOf<Int, MutableList<Int>>()
        relation.forEach {
            map.compute(it[0]) { _, v ->
                if (v == null) {
                    val e = mutableListOf<Int>()
                    e.add(it[1])
                    e
                } else {
                    v.add(it[1])
                    v
                }
            }
        }
        ways(0, n, map, k)
        return count
    }

    private fun ways(index: Int, n: Int, map: Map<Int, MutableList<Int>>, k: Int) {
        if (k < 1) {
            return
        } else if (k == 1) {
            if (map.entries.filter { it.key == index }.map { it.value }.flatMap { it.asIterable() }.contains(n - 1)) {
                count++
            }
        } else {
            map.entries.filter { it.key == index }.map { it.value }.flatMap { it.asIterable() }.forEach { ways(it, n, map, k - 1) }
        }
    }

    fun getTriggerTime(increase: Array<IntArray>, requirements: Array<IntArray>): IntArray {
        val ans = IntArray(requirements.size) { -1 }
        val increases = IntArray(3) { 0 }
        val set = mutableSetOf<Int>()
        for (i in requirements.indices) {
            set.add(i)
        }
        for (i in increase.indices) {
            increases[0] += increase[i][0]
            increases[1] += increase[i][1]
            increases[2] += increase[i][2]
            val tmp = mutableSetOf<Int>()
            for (j in set) {
                if (requirements[j][0] == 0 && requirements[j][1] == 0 && requirements[j][2] == 0) {
                    ans[j] = 0
                    tmp.add(j)
                } else if (increases[0] >= requirements[j][0] && increases[1] >= requirements[j][1] && increases[2] >= requirements[j][2]) {
                    ans[j] = i + 1
                    tmp.add(j)
                } else {
                    continue
                }
            }
            if (tmp.isNotEmpty()) {
                set.removeAll(tmp)
            }
        }
        return ans
    }

    fun minJump(jump: IntArray): Int {
        return minJump(0, jump, 0)
    }

    private fun minJump(index: Int, jump: IntArray, count: Int): Int {
        if (index > jump.lastIndex) {
            return count
        } else if (index + jump[index] >= jump.size) {
            return count + 1
        } else {
            var next = 0
            for (i in 0 until index) {
                if (i + jump[i] > next) {
                    next = i + jump[i]
                }
            }
            if (next >= jump.size) {
                return count + 2
            }
            return Math.min(minJump(next, jump, count + 2), minJump(index + jump[index], jump, count + 1))
        }
    }

    fun getTriggerTime1(increase: Array<IntArray>, requirements: Array<IntArray>): IntArray {
        val ans = IntArray(requirements.size) { -1 }
        val increases = IntArray(3) { 0 }
        for (i in increase.indices) {
            increases[0] += increase[i][0]
            increases[1] += increase[i][1]
            increases[2] += increase[i][2]
        }
        val tmp = IntArray(3) { 0 }
        requirements.forEachIndexed { index, requirement ->
            if (requirement[0] == 0 && requirement[1] == 0 && requirement[2] == 0) {
                ans[index] = 0
            } else {
                if (increases[0] < requirement[0] || increases[1] < requirement[1] || increases[2] < requirement[2]) {

                } else {
                    tmp[0] = 0
                    tmp[1] = 0
                    tmp[2] = 0
                    for (i in increase.indices) {
                        tmp[0] += increase[i][0]
                        tmp[1] += increase[i][1]
                        tmp[2] += increase[i][2]
                        if (tmp[0] >= requirement[0] || tmp[1] >= requirement[1] || tmp[2] >= requirement[2]) {
                            ans[index] = i + 1
                            break
                        }
                    }
                }
            }
        }
        return ans
    }

    fun minJump1(jump: IntArray): Int {//45
        var end = 0
        var maxPos = 0
        var steps = 0
        for (i in 0 until jump.lastIndex) {
            maxPos = Math.max(maxPos, i + jump[i])
            if (i == end) {
                end = maxPos
                steps++
            }
        }
        return steps
    }

    fun reformat(s: String): String {//5388
        if (s.length < 2) {
            return s
        }
        val letters = StringBuilder()
        val digits = StringBuilder()
        s.forEach {
            if (it in 'a'..'z') {
                letters.append(it)
            } else {
                digits.append(it)
            }
        }
        if (Math.abs(letters.length - digits.length) > 1) {
            return ""
        }
        val ans = StringBuilder()
        if (letters.length > digits.length) {
            for (i in digits.indices) {
                ans.append(letters[i])
                ans.append(digits[i])
            }
            ans.append(letters.last())
        } else if (letters.length == digits.length) {
            for (i in digits.indices) {
                ans.append(letters[i])
                ans.append(digits[i])
            }
        } else {
            for (i in letters.indices) {
                ans.append(digits[i])
                ans.append(letters[i])
            }
            ans.append(digits.last())
        }
        return ans.toString()
    }

    fun displayTable(orders: List<List<String>>): List<List<String>> {//5389
        val ans = mutableListOf<MutableList<String>>()
        val tables = orders.map { it[1].toInt() }.distinct().sorted()
        val foods = orders.map { it[2] }.distinct().sorted()
        val firstRow = mutableListOf<String>()
        firstRow.add("Table")
        firstRow.addAll(foods)
        ans.add(firstRow)
        tables.forEach { table ->
            val tableFoods = orders.filter { it[1] == table.toString() }.map { it[2] }.groupBy { it }
            val row = mutableListOf<String>()
            row.add(table.toString())
            foods.forEach { food ->
                if (tableFoods.containsKey(food)) {
                    row.add(tableFoods[food]!!.size.toString())
                } else {
                    row.add("0")
                }
            }
            ans.add(row)
        }
        return ans
    }

    fun minNumberOfFrogs(croakOfFrogs: String): Int {//4390
        val croat = "croak"
        var croaked = 0
        val croatings = mutableListOf<String>()
        croakOfFrogs.forEach { ch ->
            if (ch in croat) {
                if (ch == 'c') {
                    croatings.add(ch.toString())
                    if (croatings.contains(croat)) {
                        croaked++
                    }
                } else {
                    val croating = croat.substring(0, croat.indexOf(ch))
                    if (croatings.contains(croating)) {
                        croatings[croatings.indexOf(croating)] = "$croating$ch"
                    } else {
                        croatings.add(ch.toString())
                    }
                }
            } else {
                return -1
            }
        }
        if (croatings.all { it == croat }) {
            return croatings.size - croaked
        } else {
            return -1
        }
    }

}