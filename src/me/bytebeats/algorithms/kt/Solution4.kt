package me.bytebeats.algorithms.kt

import me.bytebeats.algorithms.meta.TreeNode

class Solution4 {
    fun minPathSum(grid: Array<IntArray>): Int {
        if (grid.isEmpty() || grid[0].isEmpty()) {
            return 0
        }
        return minPathSum(grid, grid.lastIndex, grid[0].lastIndex)
    }

    fun minPathSum(grid: Array<IntArray>, x: Int, y: Int): Int {
        if (x < 0 || y < 0) {
            return 0
        } else if (y == 0) {
            return grid[x][y] + minPathSum(grid, x - 1, y)
        } else if (x == 0) {
            return grid[x][y] + minPathSum(grid, x, y - 1)
        } else {
            return grid[x][y] + Math.min(minPathSum(grid, x - 1, y), minPathSum(grid, x, y - 1))
        }
    }

    fun minPathSum1(grid: Array<IntArray>): Int {
        if (grid.isEmpty() || grid[0].isEmpty()) {
            return 0
        }
        val row = grid.size
        val column = grid[0].size
        for (i in 1 until row) {
            grid[i][0] += grid[i - 1][0]
        }
        for (j in 1 until column) {
            grid[0][j] += grid[0][j - 1]
        }
        for (i in 1 until row) {
            for (j in 1 until column) {
                grid[i][j] += Math.min(grid[i - 1][j], grid[i][j - 1])
            }
        }
        return grid[row - 1][column - 1]
    }

    fun sumSubarrayMins(A: IntArray): Int {//907
        var count = 0L
        for (i in 1..A.size) {
            val sub = mutableListOf<Int>()
            for (j in 0 until i - 1) {
                sub.add(A[j])
            }
            for (j in i - 1 until A.size) {
                sub.add(A[j])
                sub.forEach { print(", $it") }
                count += sub.min() ?: 0
                count %= Int.MAX_VALUE
                sub.removeAt(0)
            }
        }
        return count.toInt()
    }

    fun minStartValue(nums: IntArray): Int {//5372
        var min = Int.MAX_VALUE
        var sum = 0
        nums.forEach {
            sum += it
            if (sum < min) {
                min = sum
            }
        }
        if (min > 0) {
            return 1
        } else {
            return 1 - min
        }
    }

    fun findMinFibonacciNumbers(k: Int): Int {//5373
        var ans = 0
        var c = k
        while (c > 0) {
            c -= find1stFLessOrEqualK(c)
            ans++
        }
        return ans
    }

    private fun find1stFLessOrEqualK(k: Int): Int {
        if (k < 3) {
            return 1
        }
        var f1 = 1
        var f2 = 1
        var tmp = 0
        while (true) {
            tmp = f1
            f1 = f2
            f2 = tmp + f1
            if (f2 > k) {
                break
            }
        }
        return f1
    }

    fun getHappyString(n: Int, k: Int): String {//5374
        var count = 3
        var newN = n - 1
        while (newN-- > 0) {
            count *= 2
        }
        if (count < k) {
            return ""
        }
        val ans = StringBuilder()
        newN = n
        while (newN-- > 0) {
            if (ans.isEmpty()) {
                ans.append('a')
            } else {
                if (ans.endsWith('a')) {
                    ans.append('b')
                } else {
                    ans.append('a')
                }
            }
        }
        newN = k
        while (newN-- > 1) {

        }
        return ans.toString()
    }

    fun findMin(nums: IntArray): Int {//153
        if (nums.isEmpty()) {
            return -1
        }
        if (nums.first() <= nums.last()) {//in ascending order, not rotated at some pivot, or only one element
            return nums.first()
        }
        var pivot = -1
        var s = 0
        var e = nums.lastIndex
        while (s <= e) {
            pivot = s + (e - s) / 2
            if (pivot > 0 && nums[pivot - 1] > nums[pivot]) {
                return nums[pivot]
            } else if (nums.first() <= nums[pivot]) {// "=" means pivoted element is the first one
                s = pivot + 1
            } else if (nums[pivot] <= nums.last()) {// "=" means pivoted element is the last one
                e = pivot - 1
            }
        }
        return -1
    }

    fun findMin1(nums: IntArray): Int {//154
        var low = 0
        var high = nums.lastIndex
        var pivot = 0
        while (low < high) {
            pivot = low + (high - low) / 2
            if (nums[pivot] < nums[high]) {
                high = pivot
            } else if (nums[pivot] > nums[high]) {
                low = pivot + 1
            } else {
                high--
            }
        }
        return nums[low]
    }

    fun search(nums: IntArray, target: Int): Int {//33
        if (nums.isEmpty()) {
            return -1
        }
        var pivot = -1
        var s = 0
        var e = nums.lastIndex
        if (nums.first() > nums.last()) {//ascending array is rotated
            while (s <= e) {
                pivot = s + (e - s) / 2
                if (pivot > 0 && nums[pivot - 1] > nums[pivot]) {//find the rotated index, e.g.: [2,3,1] -> nums[2] = 1, find 2
                    break
                } else if (nums.first() <= nums[pivot]) {
                    s = pivot + 1
                } else if (nums[pivot] <= nums.last()) {
                    e = pivot - 1
                }
            }
            if (target <= nums.last()) {
                s = pivot
                e = nums.lastIndex
            } else if (target >= nums.first()) {
                s = 0
                e = pivot - 1
            } else {
                return -1
            }
        }
        while (s <= e) {
            pivot = s + (e - s) / 2
            if (nums[pivot] < target) {
                s = pivot + 1
            } else if (nums[pivot] > target) {
                e = pivot - 1
            } else {
                return pivot//binary search
            }
        }
        return -1
    }

    fun search1(nums: IntArray, target: Int): Boolean {//81
        if (nums.isEmpty()) {
            return false
        }
        var low = 0
        var high = nums.lastIndex
        var mid = 0
        while (low < high) {
            mid = low + (high - low) / 2
            if (nums[mid] > nums[high]) {
                low = high + 1
            } else if (nums[mid] < nums[high]) {
                high = mid
            } else {
                high--
            }
        }
        val pivot = low
        low = 0
        high = pivot - 1
        while (low <= high) {
            mid = low + (high - low) / 2
            if (nums[mid] > target) {
                high = mid - 1
            } else if (nums[mid] < target) {
                low = mid + 1
            } else {
                return true
            }
        }
        low = pivot
        high = nums.lastIndex
        while (low <= high) {
            mid = low + (high - low) / 2
            if (nums[mid] > target) {
                high = mid - 1
            } else if (nums[mid] < target) {
                low = mid + 1
            } else {
                return true
            }
        }
        return false
    }

    fun searchRange(nums: IntArray, target: Int): IntArray {//34
        var ans = IntArray(2) { -1 }
        var low = 0
        var high = nums.lastIndex
        var mid = 0
        while (low <= high) {
            mid = low + (high - low) / 2
            if (nums[mid] > target) {
                high = mid - 1
            } else if (nums[mid] < target) {
                low = mid + 1
            } else {
                var start = mid
                while (start - 1 > -1 && nums[mid] == nums[start - 1]) {
                    start--
                }
                var end = mid
                while (end + 1 < nums.size && nums[mid] == nums[end + 1]) {
                    end++
                }
                ans[0] = start
                ans[1] = end
                break
            }
        }
        return ans
    }

    fun numPairsDivisibleBy60(time: IntArray): Int {//1010
        var ans = 0
        val seconds = IntArray(60)
        time.forEach { seconds[it % 60] += 1 }
        ans += combination(seconds[30], 2)
        ans += combination(seconds[0], 2)
        var i = 1
        var j = 59
        while (i < j) {
            ans += seconds[i++] * seconds[j--]
        }
        return ans
    }

    private fun combination(n: Int, k: Int): Int {
        var ans = 1L
        for (i in 1..k) {
            ans = ans * (n - i + 1) / i
        }
        return ans.toInt()
    }

    fun rankTeams(votes: Array<String>): String {//1366
        val map = sortedMapOf<Char, IntArray>()
        votes[0].forEach {
            map[it] = IntArray(votes[0].length)
        }
        votes.forEach { vote ->
            vote.forEachIndexed { index, c ->
                map[c]!![index]++
            }
        }
        return String(map.entries.sortedWith(Comparator { t1, t2 ->//sorting really matters
            for (i in votes[0].indices) {
                if (t1.value[i] != t2.value[i]) {
                    return@Comparator t2.value[i] - t1.value[i]
                }
            }
            return@Comparator t1.key - t2.key
        }).map { it.key }.toCharArray())
    }

    fun bstFromPreorder(preorder: IntArray): TreeNode? {//1008
        if (preorder.isNotEmpty()) {
            return bstFromPreorder(preorder, 0, preorder.lastIndex)
        }
        return null
    }

    fun bstFromPreorder(preorder: IntArray, start: Int, end: Int): TreeNode? {//
        if (start > end || start < 0 || end > preorder.lastIndex) {
            return null
        }
        val root = TreeNode(preorder[start])
        var pivot = -1
        for (i in start + 1..end) {
            if (preorder[i] > preorder[start]) {
                pivot = i
                break
            }
        }
        if (pivot == -1) {
            root.left = bstFromPreorder(preorder, start + 1, end)
        } else {
            if (pivot - 1 >= start + 1) {
                root.left = bstFromPreorder(preorder, start + 1, pivot - 1)
            }
            if (pivot <= end) {
                root.right = bstFromPreorder(preorder, pivot, end)
            }
        }
        return root
    }

    var trees: MutableMap<String, Int>? = null
    var count: MutableMap<Int, Int>? = null
    var ans: MutableList<TreeNode>? = null
    var t = 0
    fun findDuplicateSubtrees(root: TreeNode?): List<TreeNode?> {//652
        t = 1
        trees = mutableMapOf()
        count = mutableMapOf()
        ans = mutableListOf()
        lookup(root)
        return ans!!
    }

    private fun lookup(node: TreeNode?): Int {
        if (node == null) {
            return 0
        }
        val serial = "${node.`val`}${lookup(node.left)}${lookup(node.right)}"
        val uid = trees!!.computeIfAbsent(serial) { t++ }
        count!![uid] = (count!![uid] ?: 0) + 1
        if (count!![uid] == 2) {
            ans!!.add(node)
        }
        return uid
    }
}