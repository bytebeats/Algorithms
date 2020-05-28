package me.bytebeats.algorithms.kt

import me.bytebeats.algorithms.meta.TreeNode
import me.bytebeats.algorithms.meta.ListNode

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


}