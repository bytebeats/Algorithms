package me.bytebeats.algorithms.kt

import me.bytebeats.algorithms.kt.design.BinaryMatrix
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

    fun numberOfSubarrays(nums: IntArray, k: Int): Int {//1248
        var ans = 0
        val oddIndexes = mutableListOf<Int>()
        for (i in nums.indices) {
            if (nums[i] and 1 == 1) {
                oddIndexes.add(i)
            }
        }
        oddIndexes.add(0, -1)
        oddIndexes.add(nums.size)
        for (i in 1 until oddIndexes.size - k) {
            ans += (oddIndexes[i] - oddIndexes[i - 1]) * (oddIndexes[i + k] - oddIndexes[i + k - 1])
        }
        return ans
    }

    fun isLongPressedName(name: String, typed: String): Boolean {//925
        if (name.length > typed.length) {
            return false
        }
        if (name == typed) {
            return true
        }

        return true
    }

    fun leftMostColumnWithOne(binaryMatrix: BinaryMatrix): Int {
        val row = binaryMatrix.dimensions()[0]
        val column = binaryMatrix.dimensions()[1]
        println("$row, $column")
        var x = 0
        var y = column - 1
        while (x < row && y > -1) {
            if (binaryMatrix.get(x, y) == 1) {
                y--
            } else {
                x++
            }
        }
        return if (y == column - 1) -1 else y + 1
    }

    fun findMaxConsecutiveOnes(nums: IntArray): Int {//485
        var ans = Int.MIN_VALUE
        val zeros = mutableListOf<Int>()
        nums.forEachIndexed { index, i ->
            if (i == 0) {
                zeros.add(index)
            }
        }
        zeros.add(0, -1)
        zeros.add(nums.size)
        for (i in 1..zeros.lastIndex) {
            ans = Math.max(zeros[i] - zeros[i - 1] - 1, ans)
        }
        return ans
    }

    fun findNumbers(nums: IntArray): Int {//1295
        var ans = 0
        nums.forEach {
            if (getBitCount(it) and 1 == 0) {
                ans++
            }
        }
        return ans
    }

    private fun getBitCount(num: Int): Int {
        var ans = 0
        var k = num
        while (k != 0) {
            ans++
            k /= 10
        }
        return ans
    }

    fun findLengthOfLCIS(nums: IntArray): Int {//674
        var max = 0
        if (nums.isNotEmpty()) {
            var count = 1
            max = 1
            var num = nums[0]
            for (i in 1..nums.lastIndex) {
                if (nums[i] > num) {
                    num = nums[i]
                    count++
                    if (count > max) {
                        max = count
                    }
                } else {
                    num = nums[i]
                    count = 1
                }
            }
        }
        return max
    }

    fun findMaxConsecutiveOnes1(nums: IntArray): Int {//487
        var ans = 0
        val zeros = mutableListOf<Int>()
        nums.forEachIndexed { index, i ->
            if (i and 1 == 0) {
                zeros.add(index)//records all index of 0
            }
        }
        if (zeros.isEmpty()) {
            return nums.size
        }
        zeros.forEach {
            var k = it + 1
            while (k < nums.size && nums[k] == 1) {//count 0s in left of current 0
                k++
            }
            var j = it - 1
            while (j > -1 && nums[j] == 1) {//count 0s in right of current 0
                j--
            }
            ans = Math.max(ans, k - j - 1)//count 1s, and get the max
        }
        return ans
    }

    fun longestOnes(A: IntArray, K: Int): Int {//1004
        var ans = 0
        val zeros = mutableListOf<Int>()
        A.forEachIndexed { index, i ->
            if (i and 1 == 0) {
                zeros.add(index)//records all index of 0
            }
        }
        if (zeros.size <= K) {
            return A.size
        }
        var pre = -1
        for (i in K..zeros.size) {
            if (i == K) {
                if (A[i - K] > 0) {

                }
            } else if (i == zeros.size) {
            } else {
            }
        }
        return ans
    }

    fun subarraySum(nums: IntArray, k: Int): Int {//560
        var count = 0
        var sum = 0
        val map = mutableMapOf<Int, Int>()
        map[0] = 1
        nums.forEach {
            sum += it
            if (map.containsKey(sum - k)) {
                count += map[sum - k] ?: 0
            }
            map.compute(sum) { _, v -> if (v == null) 1 else v + 1 }
        }
        return count
    }

    fun numSubmatrixSumTarget(matrix: Array<IntArray>, target: Int): Int {//1074
        var count = 0
        if (matrix.isNotEmpty() && matrix[0].isNotEmpty()) {
        }
        return count
    }

    fun checkSubarraySum(nums: IntArray, k: Int): Boolean {//523
        for (i in 0 until nums.lastIndex) {
            var sum = nums[i]
            for (j in i + 1 until nums.size) {
                sum += nums[j]
                if (k == 0) {
                    if (sum == 0) {
                        return true
                    }
                } else if (sum % k == 0) {
                    return true
                }
            }
        }
        return false
    }

    fun superPow(a: Int, b: IntArray): Int {//372
        return superPow(a, b, b.lastIndex)
    }

    fun superPow(a: Int, b: IntArray, index: Int): Int {//372
        if (index < 0) {
            return 1
        }
        val part1 = myPow(a, b[index])
        val part2 = myPow(superPow(a, b, index - 1), 10)
        return part1 * part2 % 1337
    }

    private fun myPow(a: Int, b: Int): Int {
        var ans = 1
        var aa = a % 1337
        for (i in 0 until b) {
            ans *= aa
            ans %= 1337
        }
        return ans
    }

    fun waysToChange(n: Int): Int {//面试题 08.11
        val coins = arrayOf(25, 10, 5, 1)
        val mod = 1_000_000_007
        val ans = IntArray(n + 1)
        ans[0] = 1
        for (i in coins.indices) {
            for (j in coins[i]..n) {
                ans[j] = (ans[j] + ans[j - coins[i]]) % mod
            }
        }
        return ans[n]
    }

    fun numWays(n: Int, k: Int): Int {//276
        if (n == 0 || k == 0) {
            return 0
        }
        if (n == 1) {
            return k
        }
        var diffColor = k * (k - 1)
        var sameColor = k
        var tmp = 0
        for (i in 2 until n) {
            tmp = diffColor
            diffColor = (diffColor + sameColor) * (k - 1)
            sameColor = tmp
        }
        return diffColor + sameColor
    }

    fun minCost(costs: Array<IntArray>): Int {//256
        var preR = 0
        var preG = 0
        var preB = 0
        var curR = 0
        var curG = 0
        var curB = 0
        for (i in costs.indices) {
            curR = Math.min(preG, preB) + costs[i][0]
            curG = Math.min(preR, preB) + costs[i][1]
            curB = Math.min(preG, preR) + costs[i][2]
            preR = curR
            preG = curG
            preB = curB
        }
        return Math.min(Math.min(curR, curG), curB)
    }

    fun minCostII(costs: Array<IntArray>): Int {//265
        if (costs.isEmpty()) {
            return 0
        } else if (costs[0].size == 1) {
            return costs[0][0]
        }
        var minColor = -1
        var minCost = 0
        var secondMinCost = 0
        costs.forEach { cost ->
            var tmpMinColor = -1
            var tmpMinCost = Int.MAX_VALUE
            var tmpSecondMinCost = Int.MAX_VALUE
            for (i in cost.indices) {
                val thisMinCost = (if (i == minColor) secondMinCost else minCost) + cost[i]
                if (thisMinCost < tmpMinCost) {
                    tmpSecondMinCost = tmpMinCost
                    tmpMinCost = thisMinCost
                    tmpMinColor = i
                } else if (thisMinCost < tmpSecondMinCost) {
                    tmpSecondMinCost = thisMinCost
                }
            }
            minCost = tmpMinCost
            minColor = tmpMinColor
            secondMinCost = tmpSecondMinCost
        }
        return minCost
    }

    fun duplicateZeros(arr: IntArray): Unit {//1089
        var i = 0
        while (i < arr.size) {
            if (arr[i] == 0) {
                for (j in arr.lastIndex - 1 downTo i) {
                    arr[j + 1] = arr[j]
                }
                i += 2
            } else {
                i++
            }
        }
    }

    fun removeElement(nums: IntArray, `val`: Int): Int {//27
        var size = nums.size
        var i = 0
        while (i < size) {
            if (nums[i] != `val`) {
                i++
            } else {
                for (j in i until size - 1) {
                    nums[j] = nums[j + 1]
                }
                size--
            }
        }
        return size
    }

    fun removeDuplicates(nums: IntArray): Int {//26
        if (nums.size < 2) {
            return nums.size
        }
        var size = nums.size
        var i = -1
        var j = 1
        while (j < nums.size) {
            if (nums[j] == nums[j - 1]) {
                if (i < 0) {
                    i = j
                }
                j++
                size--
            } else {
                if (i > -1) {
                    nums[i++] = nums[j++]
                } else {
                    j++
                }
            }
        }
        return size
    }

    fun reversePairs(nums: IntArray): Int {//面试题51
        var ans = 0
        if (nums.size > 1) {
            for (i in 0 until nums.lastIndex) {
                for (j in i + 1 until nums.size) {
                    if (nums[i] > nums[j]) {
                        ans++
                    }
                }
            }
        }
        return ans
    }

    fun reversePairs1(nums: IntArray): Int {//面试题51
        val size = nums.size
        val tmp = IntArray(size)
        return reversePairs(nums, tmp, 0, size - 1)
    }

    private fun reversePairs(nums: IntArray, tmp: IntArray, l: Int, r: Int): Int {//面试题51
        if (l >= r) {
            return 0
        }
        val mid = l + (r - l) / 2
        var pairCount = reversePairs(nums, tmp, l, mid) + reversePairs(nums, tmp, mid + 1, r)
        var i = l
        var j = mid + 1
        var pos = l
        while (i <= mid && j <= r) {
            if (nums[i] <= nums[j]) {
                tmp[pos++] = nums[i++]
                pairCount += (j - (mid + 1))
            } else {
                tmp[pos++] = nums[j++]
            }
        }
        for (k in i..mid) {
            tmp[pos++] = nums[k]
            pairCount += (j - (mid + 1))
        }
        for (k in j..r) {
            tmp[pos++] = nums[k]
        }
        for (i in l..r) {
            nums[i] = tmp[i]
        }
        return pairCount
    }

    fun fib(n: Int): Int {//面试题10-I
        if (n < 2) {
            return n
        } else {
            var fib1 = 0
            var fib2 = 1
            var tmp = 0
            var N = n
            while (N-- > 1) {
                tmp = fib2
                fib2 = (fib1 + fib2) % 1_000_000_007
                fib1 = tmp
            }
            return fib2
        }
    }

    fun singleNumbers(nums: IntArray): IntArray {//面试题56-I
        var xorVal = 0
        nums.forEach {
            xorVal = xorVal xor it
        }
        var bigEnd = 1
        while (bigEnd and xorVal == 0) {
            bigEnd = bigEnd shl 1
        }
        var a = 0
        var b = 0
        nums.forEach {
            if (it and bigEnd == 0) {
                a = a xor it
            } else {
                b = b xor it
            }
        }
        return intArrayOf(a, b)
    }

    fun sortArrayByParity(A: IntArray): IntArray {//905
        var i = 0
        var j = A.lastIndex
        var tmp = 0
        while (i < j) {
            while (i < j && A[i] and 1 == 0) {
                i++
            }
            while (i < j && A[j] and 1 == 1) {
                j--
            }
            tmp = A[i]
            A[i] = A[j]
            A[j] = tmp
            i++
            j--
        }
        return A
    }

    fun replaceElements(arr: IntArray): IntArray {//1299
        for (i in 0 until arr.lastIndex) {
            var max = Int.MIN_VALUE
            for (j in i + 1 until arr.size) {
                if (arr[j] > max) {
                    max = arr[j]
                }
            }
            arr[i] = max
        }
        arr[arr.size - 1] = -1
        return arr
    }
}