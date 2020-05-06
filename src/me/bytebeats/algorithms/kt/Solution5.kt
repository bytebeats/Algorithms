package me.bytebeats.algorithms.kt

import me.bytebeats.algorithms.meta.TreeNode
import me.bytebeats.algorithms.meta.TreeNode2

class Solution5 {

    var maxPathSum = Int.MIN_VALUE
    fun maxPathSum(root: TreeNode?): Int {//124
        maxGain(root)
        return maxPathSum
    }

    private fun maxGain(root: TreeNode?): Int {
        if (root == null) return 0
        val left = Math.max(maxGain(root.left), 0)
        val right = Math.max(maxGain(root.right), 0)
        maxPathSum = Math.max(maxPathSum, left + right + root.`val`)
        return root.`val` + Math.max(left, right)
    }

    fun findSecondMinimumValue(root: TreeNode?): Int {//671
        val list = mutableListOf<Int>()
        convert2List(root, list)
        if (list.size > 1) {
            list.sort()
            return list[1]
        } else {
            return -1
        }
    }

    private fun convert2List(root: TreeNode?, list: MutableList<Int>) {
        if (root == null) {
            return
        }
        if (!list.contains(root.`val`)) {
            list.add(root.`val`)
        }
        convert2List(root.left, list)
        convert2List(root.right, list)
    }

    fun kthLargest(root: TreeNode?, k: Int): Int {//面试题54
        val list = mutableListOf<Int>()
        convert2List(root, list)
        list.sort()
        return list[list.size - k]
    }

    fun connect(root: TreeNode2?): TreeNode2? {
        if (root != null) {
            connect(root.left, root.right)
        }
        return root
    }

    private fun connect(left: TreeNode2?, right: TreeNode2?) {//116
        if (left == null || right == null) {
            return
        }
        left.next = right
        connect(left.left, left.right)
        connect(right.left, right.right)
        connect(left.right, right.left)
    }

    fun isValidSequence(root: TreeNode?, arr: IntArray): Boolean {
        return isValidSequence(root, arr, 0)
    }

    fun isValidSequence(node: TreeNode?, arr: IntArray, index: Int): Boolean {
        if (node == null) {
            return arr.isEmpty()
        } else if (index > arr.lastIndex) {
            return false
        } else if (index == arr.lastIndex && node.left == null && node.right == null && arr[index] == node.`val`) {
            return true
        } else {
            return index < arr.size && node.`val` == arr[index] &&
                    (isValidSequence(node.left, arr, index + 1) || isValidSequence(node.right, arr, index + 1))
        }
    }

    fun singleNumber(nums: IntArray): IntArray {//260
        var xorV = 0
        for (num in nums) {
            xorV = xorV xor num
        }
        var d = 1
        while (d and xorV == 0) {
            d = d shl 1
        }
        var a = 0
        var b = 0
        for (num in nums) {
            if (num and d == 0) {
                a = a xor num
            } else {
                b = b xor num
            }
        }
        return intArrayOf(a, b)
    }

    fun mincostTickets(days: IntArray, costs: IntArray): Int {//983
        return dp(days, costs, 0, Array(days.size) { null })
    }

    private fun dp(
        days: IntArray,
        costs: IntArray,
        i: Int,
        memo: Array<Int?>,
        durations: IntArray = intArrayOf(1, 7, 30)
    ): Int {
        if (i >= days.size) {
            return 0
        }
        if (memo[i] != null) {
            return memo[i]!!
        }
        memo[i] = Int.MAX_VALUE
        var j = i
        for (k in durations.indices) {
            while (j < days.size && days[j] < days[i] + durations[k]) {
                j++
            }
            memo[i] = Math.min(memo[i]!!, dp(days, costs, j, memo) + costs[k])
        }
        return memo[i]!!
    }

    fun coinChange(coins: IntArray, amount: Int): Int {//322
        if (coins.isNotEmpty() && amount >= 0) {
            val matrix = Array(amount + 1) { -1 }
            matrix[0] = 0
            var diff = 0
            for (i in 1..amount) {
                for (j in coins.indices) {
                    diff = i - coins[j]
                    if (diff >= 0 && matrix[diff] > -1) {
                        if (matrix[i] == -1) {
                            matrix[i] = matrix[diff] + 1
                        } else {
                            matrix[i] = Math.min(matrix[i], matrix[diff] + 1)
                        }
                    }
                }
            }
            return matrix[amount]
        }
        return -1
    }

    fun majorityElement(nums: IntArray): Int {//169
        nums.sort()
        return nums[nums.size / 2]
    }

    fun majorityElement1(nums: IntArray): List<Int> {//229, Moore voting
        val ans = mutableListOf<Int>()
        if (nums.isNotEmpty()) {
            //initialize two candidates
            var candidate1 = nums.first()
            var voting1 = 0
            var candidate2 = nums.first()
            var voting2 = 0
            for (i in nums.indices) {//配对阶段
                //投票
                if (candidate1 == nums[i]) {
                    voting1++
                    continue
                }
                if (candidate2 == nums[i]) {
                    voting2++
                    continue
                }
                //第一个候选人配对
                if (voting1 == 0) {
                    candidate1 = nums[i]
                    voting1++
                    continue
                }
                if (voting2 == 0) {
                    candidate2 = nums[i]
                    voting2++
                    continue
                }
                voting1--
                voting2--
            }
            voting1 = 0
            voting2 = 0
            for (i in nums.indices) {//计数阶段, 找到两个候选人之后, 确定票数是否 >=3
                if (candidate1 == nums[i]) {
                    voting1++
                } else if (candidate2 == nums[i]) {
                    voting2++
                }
            }
            if (voting1 > nums.size / 3) {
                ans.add(candidate1)
            }
            if (voting2 > nums.size / 3) {
                ans.add(candidate2)
            }
        }
        return ans
    }

    fun isMajorityElement(nums: IntArray, target: Int): Boolean {//1150
        if (nums.isNotEmpty() && target == nums[nums.size / 2]) {
            var count = 1
            val half = nums.size / 2
            var i = half
            while (--i > -1 && nums[i] == target) {
                count++
            }
            i = half
            while (++i < nums.size && nums[i] == target) {
                count++
            }
            return count > half
        }
        return false
    }

    fun findMaxAverage(nums: IntArray, k: Int): Double {//643
        var sum = 0.0
        for (i in 0 until k - 1) {
            sum += nums[i]
        }
        var maxSum = sum + nums[k - 1]
        for (i in k - 1 until nums.size) {
            sum += nums[i]
            if (sum > maxSum) {
                maxSum = sum
            }
            sum -= nums[i - k + 1]
        }
        return maxSum / k
    }

    fun findMaxAverage1(nums: IntArray, k: Int): Double {//644
        var maxAvg = Double.MIN_VALUE
        var flag = false
        for (i in k..nums.size) {
            var sum = 0.0
            for (j in 0..i - 2) {
                sum += nums[j]
            }
            var maxSum = sum + nums[i - 1]
            for (j in i - 1 until nums.size) {
                sum += nums[j]
                maxSum = Math.max(maxSum, sum)
                sum -= nums[j - i + 1]
            }
            if (!flag) {
                maxAvg = maxSum / i
                flag = !flag
            } else {
                maxAvg = Math.max(maxAvg, maxSum / i)
            }
        }
        return maxAvg
    }
}