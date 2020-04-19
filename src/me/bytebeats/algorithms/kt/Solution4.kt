package me.bytebeats.algorithms.kt

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
}