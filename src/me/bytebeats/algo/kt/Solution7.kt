package me.bytebeats.algo.kt

import me.bytebeats.algs.ds.ListNode
import me.bytebeats.algs.ds.TreeNode

class Solution7 {

    fun verticalTraversal(root: TreeNode?): List<List<Int>> {//987
        val ans = mutableMapOf<Int, MutableMap<Int, MutableList<Int>>>()
        if (root != null) {
            val queue = mutableListOf<TreeNode>()
            val xes = mutableListOf<Int>()
            val yes = mutableListOf<Int>()
            queue.add(root)
            xes.add(0)
            yes.add(0)
            var x = 0
            var y = 0
            var node: TreeNode? = null
            while (queue.isNotEmpty()) {
                node = queue.removeAt(0)
                x = xes.removeAt(0)
                y = yes.removeAt(0)
                ans.compute(x) { _, v ->
                    if (v == null) {
                        val map = mutableMapOf<Int, MutableList<Int>>()
                        map.compute(y) { _, c ->
                            if (c == null) {
                                val list = mutableListOf<Int>()
                                list.add(node.`val`)
                                list
                            } else {
                                c.add(node.`val`)
                                c
                            }
                        }
                        map
                    } else {
                        v.compute(y) { _, c ->
                            if (c == null) {
                                val list = mutableListOf<Int>()
                                list.add(node.`val`)
                                list
                            } else {
                                c.add(node.`val`)
                                c
                            }
                        }
                        v
                    }
                }
                if (node.left != null) {
                    queue.add(node.left)
                    xes.add(x - 1)
                    yes.add(y + 1)
                }
                if (node.right != null) {
                    queue.add(node.right)
                    xes.add(x + 1)
                    yes.add(y + 1)
                }
            }
        }
        return ans.entries
            .sortedBy { it.key }//ordered by x
            .map { x ->
                x.value
                    .entries
                    .sortedBy { it.key }//ordered by y
                    .flatMap { it.value.sorted() }//ordered by value
            }
    }

    var btnLeafVal = 0
    fun findBottomLeftValue(root: TreeNode?): Int {//513
        btnLeafVal = 0
        dfs(root, 0, mutableSetOf())
        return btnLeafVal
    }

    private fun dfs(root: TreeNode?, level: Int, set: MutableSet<Int>) {//mid order reversal
        if (root == null) {
            return
        }
        if (!set.contains(level)) {
            set.add(level)
            btnLeafVal = root.`val`
        }
        dfs(root.left, level + 1, set)
        dfs(root.right, level + 1, set)
    }

    fun pathInZigZagTree(label: Int): List<Int> {//1104
        val path = mutableListOf<Int>()
        var newLbl = label
        var level = (Math.log(newLbl.toDouble()) / Math.log(2.0)).toInt()
        while (newLbl > 1) {
            path.add(newLbl)
            newLbl = (3 * Math.pow(2.0, (--level).toDouble()) - newLbl / 2 - 1).toInt()
        }
        path.add(1)
        return path.reversed()
    }

    fun missingNumber(arr: IntArray): Int {//1228
        var pre = 0
        var post = 0
        for (i in 1 until arr.size - 1) {
            pre = arr[i] - arr[i - 1]
            post = arr[i + 1] - arr[i]
            if (pre != post) {
                if (pre / post == 2) {
                    return arr[i] - (arr[i + 1] - arr[i])
                } else {
                    return arr[i] + (arr[i] - arr[i - 1])
                }
            }
        }
        return arr[0]//in case of [1,1,1]
    }

    fun longestSubsequence(arr: IntArray, difference: Int): Int {//1218
        var ans = 0
        return 0
    }

    fun findErrorNums(nums: IntArray): IntArray {//645
        val ans = IntArray(2)
        var xorVal = 0
        for (i in nums.indices) {
            xorVal = xorVal xor (i + 1)
            xorVal = xorVal xor nums[i]
        }
        val rightMost = xorVal and (xorVal - 1).inv()
        var x0 = 0
        var x1 = 0
        for (i in nums.indices) {
            if ((i + 1) and rightMost != 0) {
                x1 = x1 xor (i + 1)
            } else {
                x0 = x0 xor (i + 1)
            }
            if (nums[i] and rightMost != 0) {
                x1 = x1 xor nums[i]
            } else {
                x0 = x0 xor nums[i]
            }
        }
        for (i in nums.indices) {
            if (nums[i] == x0) {
                ans[0] = x0
                ans[1] = x1
                return ans
            }
        }
        ans[0] = x1
        ans[1] = x0
        return ans
    }

    fun waysToStep(n: Int): Int {//面试题08.01
        val mod = 1000000007
        if (n < 2) {
            return 1
        } else if (n < 3) {
            return 2
        } else if (n < 4) {
            return 4
        } else {
            var nn = n
            var f1 = 1
            var f2 = 2
            var f3 = 4
            var tmp1 = 0
            var tmp2 = 0
            while (nn-- > 3) {
                tmp1 = f1
                tmp2 = f2
                f1 = f2
                f2 = f3
                f3 = ((f3 + tmp1) % mod + tmp2) % mod
            }
            return f3
        }
    }

    fun findString(words: Array<String>, s: String): Int {//面试题10.05
        var i = 0
        var j = words.lastIndex

        var mid = 0
        while (i <= j) {
            while (i < j && words[i].isEmpty()) {
                i++
            }
            while (i < j && words[j].isEmpty()) {
                j--
            }
            mid = i + (j - i) / 2
            while (mid > i && words[mid].isEmpty()) {
                mid--
            }
            if (words[mid] < s) {
                i = mid + 1
            } else if (words[mid] > s) {
                j = mid - 1
            } else {
                return mid
            }
        }
        return -1
    }

    fun findMagicIndex(nums: IntArray): Int {//面试题08.03
        var ans = -1
        for (i in nums.indices) {
            if (i == nums[i]) {
                ans = i
                break
            }
        }
        return ans
    }

    var firstIdx = -1
    fun findMagicIndex1(nums: IntArray): Int {//面试题08.03
        firstIdx = -1
        search(nums, 0, nums.lastIndex)
        return firstIdx
    }

    private fun search(nums: IntArray, left: Int, right: Int) {
        if (left > right) {
            return
        }
        val mid = left + (right - left) / 2
        if (mid == nums[mid]) {
            if (firstIdx == -1) {
                firstIdx = mid
            } else if (firstIdx > mid) {
                firstIdx = mid
            }
            search(nums, left, mid - 1)
        } else {
            search(nums, left, mid - 1)
            if (firstIdx == -1 || firstIdx > mid) {
                search(nums, mid + 1, right)
            }
        }
    }

    fun validMountainArray(A: IntArray): Boolean {//941
        if (A.size >= 3) {
            var hasPeak = false
            for (i in 1 until A.size - 1) {
                if (A[i - 1] < A[i] && A[i] > A[i + 1]) {
                    if (hasPeak) {
                        return false
                    }
                    hasPeak = true
                } else if (A[i - 1] == A[i] || A[i] == A[i + 1]) {
                    return false
                } else if (A[i - 1] > A[i] && A[i] < A[i + 1]) {
                    return false
                }
            }
            return hasPeak
        }
        return false
    }

    fun validMountainArray1(A: IntArray): Boolean {//941
        if (A.size >= 3) {
            var i = 0
            while (i < A.size - 1 && A[i] < A[i + 1]) {
                i++
            }
            if (i == 0 || i == A.size - 1) {
                return false
            }
            while (i < A.size - 1 && A[i] > A[i + 1]) {
                i++
            }
            return i == A.size - 1
        }
        return false
    }

    fun longestSubarray(nums: IntArray, limit: Int): Int {//1438
        var ans = 0
        var i = 0
        var min = nums[i]
        var max = nums[i]
        for (j in nums.indices) {
            min = Math.min(min, nums[j])
            max = Math.max(max, nums[j])
            if (max - min <= limit) {
                ans = Math.max(ans, j - i + 1)
            } else {
                i++
                min = nums[i]
                max = nums[i]
                for (k in i + 1..j) {
                    min = Math.min(min, nums[k])
                    max = Math.max(max, nums[k])
                }
            }
        }
        return ans
    }

    fun possibleBipartition(N: Int, dislikes: Array<IntArray>): Boolean {//886
        val graph = Array<MutableList<Int>>(N + 1) { mutableListOf() }
        for (dislike in dislikes) {
            graph[dislike[0]].add(dislike[1])
            graph[dislike[1]].add(dislike[0])
        }
        val color = mutableMapOf<Int, Int>()
        for (i in 1..N) {
            if (!color.containsKey(i) && !dfs(i, 0, graph, color)) {
                return false
            }
        }
        return true
    }

    private fun dfs(n: Int, c: Int, graph: Array<MutableList<Int>>, color: MutableMap<Int, Int>): Boolean {
        if (color.containsKey(n)) {
            return color[n] == c
        }
        color[n] = c
        for (neighbour in graph[n]) {
            if (!dfs(neighbour, c xor 1, graph, color)) {
                return false
            }
        }
        return true
    }

    fun floodFill(image: Array<IntArray>, sr: Int, sc: Int, newColor: Int): Array<IntArray> {//面试题08.01
        if (image.isNotEmpty() && image[0].isNotEmpty()) {
            if (image[sr][sc] != newColor) {
                floodFill(image, sr, sc, newColor, image[sr][sc])
            }
        }
        return image
    }

    private fun floodFill(image: Array<IntArray>, sr: Int, sc: Int, newColor: Int, color: Int) {//面试题08.01
        if (sr < 0 || sc < 0 || sr >= image.size - 1 || sc >= image[0].size - 1 || image[sr][sc] != color) {
            return
        }
        image[sr][sc] = newColor
        floodFill(image, sr - 1, sc, newColor, color)
        floodFill(image, sr, sc - 1, newColor, color)
        floodFill(image, sr + 1, sc, newColor, color)
        floodFill(image, sr, sc + 1, newColor, color)
    }

    fun pathSum(root: TreeNode?, sum: Int): List<List<Int>> {//面试题34
        val ans = mutableListOf<MutableList<Int>>()
        pathSum(root, sum, 0, mutableListOf(), ans)
        return ans
    }

    fun pathSum(root: TreeNode?, sum: Int, subSum: Int, path: MutableList<Int>, ans: MutableList<MutableList<Int>>) {
        if (root == null) {
            return
        }
        if (root.left == null && root.right == null) {
            if (root.`val` + subSum == sum) {
                val list = mutableListOf<Int>()
                list.addAll(path)
                list.add(root.`val`)
                ans.add(list)
            }
        } else {
            if (root.left != null) {
                val list = mutableListOf<Int>()
                list.addAll(path)
                list.add(root.`val`)
                pathSum(root.left, sum, root.`val` + subSum, list, ans)
            }
            if (root.right != null) {
                val list = mutableListOf<Int>()
                list.addAll(path)
                list.add(root.`val`)
                pathSum(root.right, sum, root.`val` + subSum, list, ans)
            }
        }
    }

    /**
     * Forsaken by me.
     */
    fun minTime(n: Int, edges: Array<IntArray>, hasApple: List<Boolean>): Int {//1443
        val graph = Array(n) { mutableListOf<Int>() }
        for (edge in edges) {
            graph[edge[0]].add(edge[1])
            graph[edge[1]].add(edge[0])
        }
        val ans = collect(n, graph, 0, hasApple, mutableSetOf())
        if (ans > 0) {
            return (ans - 1) shr 1
        } else {
            return 0
        }
    }

    private fun collect(
        n: Int,
        graph: Array<MutableList<Int>>,
        i: Int,
        hasApple: List<Boolean>,
        visited: MutableSet<Int>
    ): Int {
        if (i >= n) {
            return 0
        }
        var sum = 0
        visited.add(i)
        for (nb in graph[i]) {
            if (!visited.contains(nb)) {
                sum += collect(n, graph, nb, hasApple, visited)
            }
        }
        if (sum > 0 || hasApple[i]) {
            sum += 1
        }
        return sum
    }


    fun deleteNode(root: TreeNode?, key: Int): TreeNode? {//450
        if (root != null) {
            if (root.`val` > key) {
                root.left = deleteNode(root.left, key)
            } else if (root.`val` < key) {
                root.right = deleteNode(root.right, key)
            } else {
                if (root.left == null) {
                    return root.right
                } else if (root.right == null) {
                    return root.left
                } else {
                    var node = root.right
                    while (node.left != null) {
                        node = node.left
                    }
                    node.left = root.left
                    return root.right
                }
            }
        }
        return root
    }

    fun splitBST(root: TreeNode?, V: Int): Array<TreeNode?> {//776
        if (root == null) {
            return Array(2) { null }
        } else if (root.`val` <= V) {
            val bns = splitBST(root.right, V)
            root.right = bns[0]
            bns[0] = root
            return bns
        } else {
            val bns = splitBST(root.left, V)
            root.left = bns[1]
            bns[1] = root
            return bns
        }
    }

    fun partition(head: ListNode?, x: Int): ListNode? {//86
        val dummyBefore = ListNode(-1)
        var before = dummyBefore
        val dummyAfter = ListNode(-1)
        var after = dummyAfter
        var p = head
        while (p != null) {
            if (p.`val` < x) {
                before.next = p
                before = before.next
            } else {
                after.next = p
                after = after.next
            }
            p = p.next
        }
        before.next = dummyAfter.next
        after.next = null
        return dummyBefore.next
    }

    var ptr = 0
    fun decodeString(s: String): String {//394
        ptr = 0
        val stack = mutableListOf<String>()
        while (ptr < s.length) {
            var ch = s[ptr]
            if (ch.isDigit()) {
                stack.add(getDigits(s))
            } else if (ch.isLetter() || ch == '[') {
                stack.add(ch.toString())
                ptr++
            } else {
                ptr++
                val sub = mutableListOf<String>()
                while (stack.last() != "[") {
                    sub.add(stack.last())
                    stack.removeAt(stack.lastIndex)
                }
                sub.reverse()
                stack.removeAt(stack.lastIndex)
                var count = stack.last().toInt()
                stack.removeAt(stack.lastIndex)
                val tmp = StringBuilder()
                val subStr = getString(sub)
                while (count-- > 0) {
                    tmp.append(subStr)
                }
                stack.add(tmp.toString())
            }
        }

        return getString(stack)
    }

    private fun getString(list: MutableList<String>): String {
        val str = StringBuilder()
        list.forEach { str.append(it) }
        return str.toString()
    }

    private fun getDigits(s: String): String {
        val digits = StringBuilder()
        while (s[ptr].isDigit()) {
            digits.append(s[ptr++])
        }
        return digits.toString()
    }

    fun tribonacci(n: Int): Int {//1137
        if (n <= 0) {
            return 0
        } else if (n == 1) {
            return 1
        } else if (n == 2) {
            return 1
        } else {
            var t0 = 0
            var t1 = 1
            var t2 = 1
            var nn = n
            var tmp1 = 0
            var tmp2 = 0
            while (nn-- > 2) {
                tmp1 = t0
                tmp2 = t1
                t0 = t1
                t1 = t2
                t2 += (tmp1 + tmp2)
            }
            return t2
        }
    }

    fun minimumAbsDifference(arr: IntArray): List<List<Int>> {//1200
        arr.sort()
        var minAbs = Int.MAX_VALUE
        for (i in 1 until arr.size) {
            minAbs = Math.min(minAbs, arr[i] - arr[i - 1])
        }
        val ans = mutableListOf<List<Int>>()
        for (i in 1 until arr.size) {
            if (arr[i] - arr[i - 1] == minAbs) {
                ans.add(listOf(arr[i - 1], arr[i]))
            }
        }
        return ans
    }

    fun nextGreaterElement(nums1: IntArray, nums2: IntArray): IntArray {//496
        for (k in nums1.indices) {
            val num = nums1[k]
            var i = 0
            while (nums2[i] != num) {
                i++
            }
            var idx = -1
            for (j in i + 1 until nums2.size) {
                if (nums2[j] > num) {
                    idx = j
                    break
                }
            }
            if (idx != -1) {
                nums1[k] = nums2[idx]
            } else {
                nums1[k] = -1
            }
        }
        return nums1
    }

    fun nextGreaterElement1(nums1: IntArray, nums2: IntArray): IntArray {//496, monotone stack
        val stack = mutableListOf<Int>()
        val map = mutableMapOf<Int, Int>()
        for (i in nums2.indices) {
            while (stack.isNotEmpty() && nums2[i] > stack.last()) {
                map[stack.removeAt(stack.lastIndex)] = nums2[i]
            }
            stack.add(nums2[i])
        }
        while (stack.isNotEmpty()) {
            map[stack.removeAt(stack.lastIndex)] = -1
        }
        for (i in nums1.indices) {
            nums1[i] = map[nums1[i]] ?: -1
        }
        return nums1
    }

    fun dailyTemperatures(T: IntArray): IntArray {//739
        val ans = IntArray(T.size)
        val stack = mutableListOf<Int>()
        for (i in T.lastIndex downTo 0) {
            while (stack.isNotEmpty() && T[i] >= T[stack.last()]) stack.removeAt(stack.lastIndex)
            ans[i] = if (stack.isEmpty()) 0 else stack.last() - i
            stack.add(i)
        }
        return ans
    }

    fun countDigitOne(n: Int): Int {//面试题43
        var ans = 0
        var digit = 1
        var high = n / 10
        var cur = n % 10
        var low = 0
        while (high != 0 || cur != 0) {
            if (cur == 0) {
                ans += digit * high
            } else if (cur == 1) {
                ans += (digit * high + low + 1)
            } else {
                ans += (high + 1) * digit
            }
            low += cur * digit
            cur = high % 10
            high /= 10
            digit *= 10
        }
        return ans
    }

    fun countDigitOne1(n: Int): Int {//233
        var ans = 0
        if (n > 0) {
            var digit = 1
            var high = n / 10
            var cur = n % 10
            var low = 0
            while (high != 0 || cur != 0) {
                if (cur == 0) {
                    ans += digit * high
                } else if (cur == 1) {
                    ans += (digit * high + low + 1)
                } else {
                    ans += (high + 1) * digit
                }
                low += cur * digit
                cur = high % 10
                high /= 10
                digit *= 10
            }
        }
        return ans
    }

    fun minCostClimbingStairs(cost: IntArray): Int {//746
        var dp1 = 0
        var dp2 = 0
        var tmp = 0
        for (i in cost.lastIndex downTo 0) {
            tmp = Math.min(dp1, dp2) + cost[i]
            dp2 = dp1
            dp1 = tmp
        }
        return Math.min(dp1, dp2)
    }

    fun largestRectangleArea(heights: IntArray): Int {//84
        val size = heights.size
        val left = IntArray(size)
        val right = IntArray(size)
        val stack = mutableListOf<Int>()// store index of heights
        for (i in heights.indices) {
            while (stack.isNotEmpty() && heights[stack.last()] >= heights[i]) {
                stack.removeAt(stack.lastIndex)
            }
            left[i] = if (stack.isEmpty()) -1 else stack.last()
            stack.add(i)
        }
        stack.clear()
        for (i in heights.lastIndex downTo 0) {
            while (stack.isNotEmpty() && heights[stack.last()] >= heights[i]) {
                stack.removeAt(stack.lastIndex)
            }
            right[i] = if (stack.isEmpty()) size else stack.last()
            stack.add(i)
        }
        var ans = 0
        for (i in heights.indices) {
            ans = Math.max(ans, (right[i] - left[i] - 1) * heights[i])
        }
        return ans
    }

    fun maximalRectangle(matrix: Array<CharArray>): Int {//85
        var ans = 0
        if (matrix.isNotEmpty()) {
            val m = matrix.size
            val n = matrix[0].size
            val left = IntArray(n)
            val right = IntArray(n) { n }
            val height = IntArray(n)
            for (i in 0 until m) {
                var curLeft = 0
                var curRight = n
                //update height
                for (j in 0 until n) {
                    if (matrix[i][j] == '1') {
                        height[j]++
                    } else {
                        height[j] = 0
                    }
                }
                //update left
                for (j in 0 until n) {
                    if (matrix[i][j] == '1') {
                        left[j] = Math.max(left[j], curLeft)
                    } else {
                        left[j] = 0
                        curLeft = j + 1
                    }
                }
                //update right
                for (j in n - 1 downTo 0) {
                    if (matrix[i][j] == '1') {
                        right[j] = Math.min(right[j], curRight)
                    } else {
                        right[j] = n
                        curRight = j
                    }
                }
                //update area
                for (j in 0 until n) {
                    ans = Math.max(ans, (right[j] - left[j]) * height[j])
                }
            }
        }
        return ans
    }

    fun findRestaurant(list1: Array<String>, list2: Array<String>): Array<String> {//599
        val intersection = list1.intersect(list2.asIterable())
        val min = intersection.map { list1.indexOf(it) + list2.indexOf(it) }.minOfOrNull { it }
        return intersection.filter { list1.indexOf(it) + list2.indexOf(it) == min }.toTypedArray()
    }

    fun maxScore(s: String): Int {//1422
        var ans = 0
        var ones = 0
        for (ch in s) {
            if (ch == '1') {
                ones++
            }
        }
        var zeros = 0
        for (i in 0 until s.lastIndex) {
            if (s[i] == '0') {
                zeros++
            } else {
                ones--
            }
            if (zeros + ones > ans) {
                ans = zeros + ones
            }
        }
        return ans
    }

    fun kClosest(points: Array<IntArray>, K: Int): Array<IntArray> {//973
        points.sortBy { it[0] * it[0] + it[1] * it[1] }
        return points.take(K).toTypedArray()
    }

    fun isUnivalTree(root: TreeNode?): Boolean {//965
        if (root == null) {
            return true
        }
        return isEqual(root.left, root.`val`) && isEqual(root.right, root.`val`)
    }

    private fun isEqual(node: TreeNode?, `val`: Int): Boolean {
        if (node == null) {
            return true
        }
        if (node.`val` != `val`) {
            return false
        } else {
            return isEqual(node.left, `val`) && isEqual(node.right, `val`)
        }
    }

    fun canBeEqual(target: IntArray, arr: IntArray): Boolean {//5408, 1460
        var xorVal = 0
        for (i in target.indices) {//元素各类及相应数目相同即可
            xorVal = xorVal xor target[i]
            xorVal = xorVal xor arr[i]
        }
        return xorVal == 0
    }

    fun hasAllCodes(s: String, k: Int): Boolean {//5409, 1461
        if (s.length <= k) {
            return false
        }
        val visited = BooleanArray(1 shl k) { false }
        var cur = 0
        for (i in 0 until k - 1) {//sliding window to compute digit to verify is in 0[k-2]0 ~ 1[k-2]1
            cur = cur * 2 + (s[i] - '0')
        }
        for (i in k - 1 until s.length) {
            cur = cur * 2 + (s[i] - '0')
            visited[cur] = true
            cur = cur and (1 shl (k - 1)).dec()
        }
        for (i in visited.indices) {
            if (!visited[i]) {
                return false
            }
        }
        return true
    }

    fun checkIfPrerequisite(n: Int, prerequisites: Array<IntArray>, queries: Array<IntArray>): BooleanArray {//5410
        val ans = BooleanArray(queries.size) { false }
        val arr = Array(n) { BooleanArray(n) { false } }
        for (pre in prerequisites) {
            for (i in arr.indices) {
                if (i == pre[0] || arr[i][pre[0]]) {
                    arr[i][pre[1]] = true
                    for (j in 0 until n) {
                        if (arr[pre[1]][j]) {
                            arr[i][j] = true
                        }
                    }
                }
            }
        }
        for (i in queries.indices) {
            ans[i] = arr[queries[i][0]][queries[i][1]]
        }
        return ans
    }

    fun maxProfit(prices: IntArray): Int {//面试题63
        var max = 0
        if (prices.isNotEmpty()) {
            var min = prices[0]
            for (i in prices.indices) {
                if (prices[i] - min > max) {
                    max = prices[i] - min
                }
                if (prices[i] < min) {
                    min = prices[i]
                }
            }
        }
        return max
    }

    fun maxProduct(nums: IntArray): Int {//5424, 1464
        nums.sort()
        return (nums.last() - 1) * (nums[nums.lastIndex - 1] - 1)
    }

    /**
     * x 轴的最大间隔 * y 轴的最大间隔
     */
    fun maxArea(h: Int, w: Int, horizontalCuts: IntArray, verticalCuts: IntArray): Int {//5425, 1465
        horizontalCuts.sort()
        verticalCuts.sort()
        var maxHSpan = 1L
        for (i in 1 until horizontalCuts.size) {
            maxHSpan = maxHSpan.coerceAtLeast((horizontalCuts[i] - horizontalCuts[i - 1]).toLong())
        }
        maxHSpan = maxHSpan.coerceAtLeast(horizontalCuts[0].toLong())
        maxHSpan = maxHSpan.coerceAtLeast((h - horizontalCuts.last()).toLong())
        var maxVSpan = 1L
        for (j in 1 until verticalCuts.size) {
            maxVSpan = maxVSpan.coerceAtLeast((verticalCuts[j] - verticalCuts[j - 1]).toLong())
        }
        maxVSpan = maxVSpan.coerceAtLeast((verticalCuts[0] - 0).toLong())
        maxVSpan = maxVSpan.coerceAtLeast((w - verticalCuts.last()).toLong())
        return (maxHSpan * maxVSpan % 1000000007).toInt()
    }

    /**
     * 不使用除法的除去本索引元素的数组乘积
     */
    fun constructArr(a: IntArray): IntArray {// 面试题66
        val ans = IntArray(a.size)
        if (a.isNotEmpty()) {
            ans[0] = 1
            for (i in 1 until a.size) {
                ans[i] = ans[i - 1] * a[i - 1]
            }
            var tmp = 1
            for (i in a.size - 2 downTo 0) {
                tmp *= a[i + 1]
                ans[i] *= tmp
            }
        }
        return ans
    }

    /**
     * a: 当所有绳段长度相等时，乘积最大。
     * b: 最优的绳段长度为 33
     */
    fun cuttingRope(n: Int): Int {//343, 面试题14-I
        if (n <= 3) {
            return n - 1
        }
        val a = n / 3
        val b = n % 3
        return if (b == 0) Math.pow(3.0, a.toDouble()).toInt()
        else if (b == 1) (Math.pow(3.0, (a - 1).toDouble()) * 4).toInt()
        else (Math.pow(3.0, a.toDouble()) * 2).toInt()
    }

    /**
     * 动态规划解法, 拆分数组
     */
    fun integerBreak(n: Int): Int {//343, 面试题14-I
        val dp = IntArray(n + 1)
        dp[2] = 1
        for (i in 3..n) {
            for (j in 1 until i) {
                dp[i] = Math.max(dp[i], Math.max(j * dp[i - j], j * (i - j)))
            }
        }
        return dp[n]
    }

    /**
     * 编辑距离
     */
    fun minDistance(word1: String, word2: String): Int {//72, edit distance
        val s1 = word1.length
        val s2 = word2.length
        val dp = Array(s1 + 1) { IntArray(s2 + 1) }
        for (i in 0..s1) {
            for (j in 0..s2) {
                if (i == 0) {
                    dp[i][j] = j
                } else if (j == 0) {
                    dp[i][j] = i
                } else {
                    if (word1[i - 1] == word2[j - 1]) {
                        dp[i][j] = dp[i - 1][j - 1]
                    } else {
                        dp[i][j] = 1 + Math.min(dp[i][j - 1], Math.min(dp[i - 1][j], dp[i - 1][j - 1]))
                    }
                }
            }
        }
        return dp[s1][s2]
    }

    fun minReorder(n: Int, connections: Array<IntArray>): Int {//5426, 1466
        val connIdx = Array(n) { mutableListOf<Int>() }
        for (i in connections.indices) {
            connIdx[connections[i][0]].add(i)
            connIdx[connections[i][1]].add(i)
        }
        val visited = BooleanArray(connections.size) { false }
        var ans = 0
        val queue = mutableListOf<Int>()
        queue.add(0)
        var p = 0
        while (queue.isNotEmpty()) {
            p = queue.removeAt(0)
            for (i in connIdx[p]) {
                if (visited[i]) continue
                visited[i] = true
                var a = connections[i][0]
                var b = connections[i][1]
                if (a == p) {
                    ans++
                    a = b
                }
                queue.add(a)
            }
        }
        return ans
    }

    fun kidsWithCandies(candies: IntArray, extraCandies: Int): BooleanArray {//1431
        val max = candies.maxOfOrNull { it } ?: 0
        val ans = BooleanArray(candies.size) { false }
        for (i in candies.indices) {
            ans[i] = candies[i] >= max - extraCandies
        }
        return ans
    }

    fun splitArray(nums: IntArray): Boolean {//548
        if (nums.size < 7) {
            return false
        }
        val sum = nums.sum()
        var sum1 = 0
        for (i in 1..nums.size - 6) {
            sum1 += nums[i - 1]
            var sum2 = 0
            for (j in i + 2..nums.size - 4) {
                sum2 += nums[j - 1]
                var sum3 = 0
                for (k in j + 2..nums.size - 2) {
                    sum3 += nums[k - 1]
                    if (sum1 == sum2 && sum2 == sum3 && sum3 == sum - sum1 - sum2 - sum3 - nums[i] - nums[j] - nums[k]) {
                        return true
                    }
                }
            }
        }
        return false
    }

    fun numSubarrayBoundedMax(A: IntArray, L: Int, R: Int): Int {//795
        return count(A, R) - count(A, L - 1)
    }

    private fun count(A: IntArray, maxVal: Int): Int {
        var ans = 0
        var cur = 0
        for (a in A) {
            cur = if (a <= maxVal) cur + 1 else 0
            ans += cur
        }
        return ans
    }

    fun hasGroupsSizeX(deck: IntArray): Boolean {//914
        val count = IntArray(1000)
        for (card in deck) {
            count[card]++
        }
        var gcd = -1
        for (c in count) {
            if (c > 0) {
                if (gcd == -1) {
                    gcd = c
                } else {
                    gcd = gcd(gcd, c)
                }
            }
        }
        return gcd >= 2
    }

    private fun gcd(x: Int, y: Int): Int {
        return if (x == 0) y else gcd(y % x, x)
    }

    fun arrayRankTransform(arr: IntArray): IntArray {//1331
        val ans = IntArray(arr.size)
        var min = Int.MAX_VALUE
        var max = Int.MIN_VALUE
        for (n in arr) {
            min = min.coerceAtMost(n)
            max = max.coerceAtLeast(n)
        }
        val count = IntArray(max - min + 1)
        for (n in arr) {
            count[n - min] = 1
        }
        val preSum = IntArray(count.size + 1)
        for (i in 1..count.size) {
            preSum[i] = preSum[i - 1] + count[i - 1]
        }
        var index = 0
        for (n in arr) {
            ans[index++] = preSum[n - min] + 1
        }
        return ans
    }

    var product = 0
    fun sumNums(n: Int): Int {//面试题64
        n > 1 && sumNums(n - 1) > 0 //短路特性, 如果前面为0, 则后面不再计算
        product += n
        return product
    }

    fun numberOfLines(widths: IntArray, S: String): IntArray {//806
        val ans = IntArray(2)
        var lineWidth = 0
        var curLine = 0
        for (c in S) {
            if (lineWidth + widths[c - 'a'] <= 100) {
                lineWidth += widths[c - 'a']
            } else {
                lineWidth = widths[c - 'a']
                curLine++
            }
        }
        ans[0] = curLine + 1
        ans[1] = lineWidth
        return ans
    }

    fun checkPossibility(nums: IntArray): Boolean {//665
        var n = 0
        for (i in 0..nums.size - 2) {
            if (nums[i] > nums[i + 1]) {
                if (n > 0) {
                    return false
                }
                n++
                if (i == 0) {
                    nums[i] = nums[i + 1]
                } else {
                    if (nums[i + 1] > nums[i - 1]) {
                        nums[i] = nums[i - 1]
                    } else {
                        nums[i + 1] = nums[i]
                    }
                }
            }
        }
        return true
    }

    fun subtractProductAndSum(n: Int): Int {//1281
        var product = 1
        var sum = 0
        var nn = n
        while (nn > 0) {
            val d = nn % 10
            product *= d
            sum += d
            nn /= 10
        }
        return product - sum
    }

    fun uniqueOccurrences(arr: IntArray): Boolean {//1207
        val map = mutableMapOf<Int, Int>()
        for (n in arr) {
            map.compute(n) { _, v -> if (v == null) 1 else v + 1 }
        }
        val list = map.values.distinct()
        return map.size == list.size
    }

    fun new21Game(N: Int, K: Int, W: Int): Double {//837, DP + Math, hehe, forsaken
        if (K == 0) return 1.toDouble()
        if (K == 1) return N.coerceAtMost(W) / W.toDouble()
        val dp = DoubleArray(N + 1)
        val preSum = DoubleArray(N + 1)
        dp[0] = 1.0
        var left = 0
        var right = 0
        for (n in 1..N) {
            left = 0.coerceAtLeast(n - W)
            right = (n - 1).coerceAtMost(K - 1)
            dp[n] = (preSum[right] - preSum[left] + dp[left]) / W.toDouble()
            preSum[n] = preSum[n - 1] + dp[n]
        }
        return preSum[N] - preSum[K - 1]
    }

    fun isValidBST(root: TreeNode?): Boolean {//面试题 04.05
        if (root == null) {
            return true
        }
        return isValidBSTNode(root.left, true, root.`val`) && isValidBSTNode(
            root.right,
            false,
            root.`val`
        ) && isValidBST(root.left) && isValidBST(root.right)
    }

    private fun isValidBSTNode(node: TreeNode?, isLeft: Boolean, `val`: Int): Boolean {
        if (node == null) {
            return true
        }
        if (isLeft) {
            return node.`val` < `val` && isValidBSTNode(node.left, isLeft, `val`) && isValidBSTNode(
                node.right,
                isLeft,
                `val`
            )
        } else {
            return node.`val` > `val` && isValidBSTNode(node.left, isLeft, `val`) && isValidBSTNode(
                node.right,
                isLeft,
                `val`
            )
        }
    }
}