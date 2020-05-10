package me.bytebeats.algorithms.kt

import me.bytebeats.algorithms.kt.design.Employee
import me.bytebeats.algorithms.kt.design.NestedInteger
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

    fun isSubtree(s: TreeNode?, t: TreeNode?): Boolean {//572
        if (s == null) {
            return t == null
        } else {
            var flag = isSameTree(s, t)
            if (s.left != null) {
                flag = flag or isSubtree(s?.left, t)
            }
            if (s.right != null) {
                flag = flag or isSubtree(s?.right, t)
            }
            return flag
        }
    }

    private fun isSameTree(s: TreeNode?, t: TreeNode?): Boolean {
        if (s == null && t == null) {
            return true
        } else if (s != null && t != null) {
            return s.`val` == t.`val` && isSameTree(s.left, t.left) && isSameTree(s.right, t.right)
        } else {
            return false
        }
    }

    fun getImportance(employees: List<Employee?>, id: Int): Int {//690
        var count = 0
        val set = mutableSetOf<Int>()
        set.add(id)
        val sub = mutableSetOf<Int>()
        while (set.isNotEmpty()) {
            set.forEach { id ->
                employees.first { it?.id == id }?.apply {
                    count += importance
                    subordinates?.apply { sub.addAll(this) }
                }
            }
            set.clear()
            set.addAll(sub)
            sub.clear()
        }
        return count
    }

    fun countUnivalSubtrees(root: TreeNode?): Int {//250
        val map = mutableMapOf<Int, Int>()
        traverse(root, map)
        for (key in map.keys) {
            count(root, key, map)
        }
        map.forEach { t, u -> println("$t, $u") }
        return map.entries.filter { it.value > 1 }.map { it.value }.sum()
    }

    private fun count(node: TreeNode?, `val`: Int, map: MutableMap<Int, Int>) {
        node?.apply {
            if (isUnivalSubtree(this, `val`)) {
                map.compute(`val`) { _, v -> if (v == null) 1 else v + 1 }
            }
            if (left != null) {
                count(left, `val`, map)
            }
            if (right != null) {
                count(right, `val`, map)
            }
        }
    }

    private fun isUnivalSubtree(root: TreeNode?, `val`: Int): Boolean {
        if (root == null) {
            return true
        }
        return root.`val` == `val` && isUnivalSubtree(root.left, `val`) && isUnivalSubtree(root.right, `val`)
    }

    private fun traverse(root: TreeNode?, map: MutableMap<Int, Int>) {
        if (root == null) {
            return
        }
        if (!map.containsKey(root.`val`)) {
            map[root.`val`] = 0
        }
        if (root.left != null) {
            traverse(root.left, map)
        }
        if (root.right != null) {
            traverse(root.right, map)
        }
    }

    fun constructMaximumBinaryTree(nums: IntArray): TreeNode? {//654
        return constructMaximumBinaryTree(nums, 0, nums.lastIndex)
    }

    fun constructMaximumBinaryTree(nums: IntArray, left: Int, right: Int): TreeNode? {//654
        if (left > right) {
            return null
        }
        var max = nums[left]
        var index = left
        for (i in left..right) {
            if (nums[i] > max) {
                max = nums[i]
                index = i
            }
        }
        val root = TreeNode(max)
        root.left = constructMaximumBinaryTree(nums, left, index - 1)
        root.right = constructMaximumBinaryTree(nums, index + 1, right)
        return root
    }

    fun insertIntoMaxTree(root: TreeNode?, `val`: Int): TreeNode? {//998
        if (root == null) {
            return TreeNode(`val`)
        } else {
            if (root.`val` > `val`) {
                root.right = insertIntoMaxTree(root.right, `val`)
                return root
            } else {
                val node = TreeNode(`val`)
                node.left = root
                return node
            }
        }
    }

    fun depthSum(nestedList: List<NestedInteger>): Int {// 339
        var sum = 0
        val list = mutableListOf<NestedInteger>()
        list.addAll(nestedList)
        var depth = 1
        val sub = mutableListOf<NestedInteger>()
        while (list.isNotEmpty()) {
            list.forEach {
                if (it.isInteger()) {
                    sum += depth * (it.getInteger() ?: 0)
                }
                it.getList()?.apply {
                    sub.addAll(this)
                }
            }
            depth++
            list.clear()
            list.addAll(sub)
            sub.clear()
        }
        return sum
    }

    fun depthSumInverse(nestedList: List<NestedInteger>): Int {//364
        var sum = 0
        val list = mutableListOf<NestedInteger>()
        list.addAll(nestedList)
        var depth = 1
        val sub = mutableListOf<NestedInteger>()
        while (list.isNotEmpty()) {//computing max depth
            list.forEach {
                it.getList()?.apply {
                    sub.addAll(this)
                }
            }
            depth++
            list.clear()
            list.addAll(sub)
            sub.clear()
        }
        list.addAll(nestedList)
        sub.clear()
        while (list.isNotEmpty()) {
            depth--
            list.forEach {
                if (it.isInteger()) {
                    sum += depth * (it.getInteger() ?: 0)
                }
                it.getList()?.apply {
                    sub.addAll(this)
                }
            }
            list.clear()
            list.addAll(sub)
            sub.clear()
        }
        return sum
    }

    fun arrayNesting(nums: IntArray): Int {//565
        var ans = 0
        val visited = BooleanArray(nums.size) { false }
        var start = 0
        var count = 0
        for (i in nums.indices) {
            if (!visited[i]) {
                start = nums[i]
                count = 0
                do {
                    start = nums[start]
                    count++
                    visited[start] = true
                } while (start != nums[i])
                if (count > ans) {
                    ans = count
                }
            }
        }
        return ans
    }

    fun findPairs(nums: IntArray, k: Int): Int {//532
        var count = 0
        nums.sort()
        var left = 0
        var right = 1
        var diff = 0
        while (right < nums.size) {
            diff = nums[right] - nums[left]
            if (diff < k) {
                right++
            } else if (diff > k) {
                left++
                while (left >= right) {
                    right++
                }
            } else {
                count++
                while (right < nums.lastIndex && nums[right] == nums[right + 1]) {
                    right++
                }
                while (left < right && nums[left] == nums[left + 1]) {
                    left++
                }
                left++
                do {
                    right++
                } while (left >= right)
            }
        }
        return count
    }

    fun convertBST(root: TreeNode?): TreeNode? {//538
        val list = mutableListOf<TreeNode>()
        convertBST2List(root, list)
        var sum = 0
        var value = 0
        for (i in list.lastIndex downTo 0) {
            value = list[i].`val`
            list[i].`val` += sum
            sum += value
        }
        return root
    }

    fun bstToGst(root: TreeNode?): TreeNode? {//1038
        val list = mutableListOf<TreeNode>()
        convertBST2List(root, list)
        var sum = 0
        var value = 0
        for (i in list.lastIndex downTo 0) {
            value = list[i].`val`
            list[i].`val` += sum
            sum += value
        }
        return root
    }

    private fun convertBST2List(root: TreeNode?, list: MutableList<TreeNode>) {
        if (root == null) {
            return
        }
        convertBST2List(root.left, list)
        list.add(root)
        convertBST2List(root.right, list)
    }

    fun convertBiNode(root: TreeNode?): TreeNode? {//面试题17.12
        if (root == null) {
            return null
        }
        val list = mutableListOf<TreeNode>()
        convertBST2List(root, list)
        for (i in list.indices) {
            list[i].left = null
            if (i < list.lastIndex) {
                list[i].right = list[i + 1]
            } else {
                list[i].right = null
            }
        }
        return list[0]
    }

    fun matrixReshape(nums: Array<IntArray>, r: Int, c: Int): Array<IntArray> {//566
        val row = nums.size
        val column = nums[0].size
        if (row * column != r * c) {
            return nums
        }
        val ans = Array(r) { IntArray(c) }
        for (i in 0 until r * c) {
            ans[i / c][i % c] = nums[i / column][i % column]
        }
        return ans
    }

    fun canThreePartsEqualSum(A: IntArray): Boolean {//1013
        if (A.size > 2) {
            var sum = A.sum()
            if (sum % 3 != 0) {
                return false
            }
            val third = sum / 3
            var pivot1 = 0
            sum = 0
            for (i in A.indices) {
                sum += A[i]
                if (sum == third) {
                    pivot1 = i
                    break
                }
            }
            if (pivot1 < A.lastIndex - 1) {
                var pivot2 = 0
                sum = 0
                for (i in pivot1 + 1 until A.lastIndex) {
                    sum += A[i]
                    if (sum == third) {
                        pivot2 = i
                        break
                    }
                }
                return pivot2 in pivot1 + 1 until A.lastIndex && A.drop(pivot2 + 1).sum() == third
            } else {
                return false
            }
        }
        return false
    }

    fun checkStraightLine(coordinates: Array<IntArray>): Boolean {//1232
        val point1 = coordinates.first()
        val point2 = coordinates.last()
        val a = point1[1] - point2[1]
        val b = point2[0] - point1[0]
        val c = point1[0] * point2[1] - point2[0] * point1[1]
        for (i in 1 until coordinates.lastIndex) {
            if (a * coordinates[i][0] + b * coordinates[i][1] + c != 0) {
                return false
            }
        }
        return true
    }

    fun balanceBST(root: TreeNode?): TreeNode? {//1382
        val list = mutableListOf<Int>()
        midReversal(root, list)
        return createBST(list, 0, list.lastIndex)
    }

    private fun createBST(list: MutableList<Int>, left: Int, right: Int): TreeNode? {
        if (left < 0 || left > right || right > list.lastIndex) {
            return null
        }
        val mid = left + (right - left) / 2
        val root = TreeNode(list[mid])
        root.left = createBST(list, left, mid - 1)
        root.right = createBST(list, mid + 1, right)
        return root
    }

    private fun midReversal(root: TreeNode?, list: MutableList<Int>) {
        if (root == null) {
            return
        }
        midReversal(root.left, list)
        list.add(root.`val`)
        midReversal(root.right, list)
    }

    fun twoSumBSTs(root1: TreeNode?, root2: TreeNode?, target: Int): Boolean {//1214
        if (root1 == null) {
            return false
        }
        return sumBSTs(root1.`val`, root2, target) || twoSumBSTs(root1.left, root2, target) || twoSumBSTs(
                root1.right,
                root2,
                target
        )
    }

    private fun sumBSTs(`val`: Int, root2: TreeNode?, target: Int): Boolean {
        if (root2 == null) {
            return false
        }
        return `val` + root2.`val` == target || sumBSTs(`val`, root2.left, target) || sumBSTs(
                `val`,
                root2.right,
                target
        )
    }

    var maxSum: Int? = null
    fun maxSumBST(root: TreeNode?): Int {//1373
        maxSum = null
        reverse(root)
        return if (maxSum ?: 0 < 0) 0 else maxSum!!
    }

    private fun reverse(root: TreeNode?) {
        if (root == null) {
            return
        }
        if (isBST(root)) {
            sumBST(root)
        }
        reverse(root.left)
        reverse(root.right)
    }

    private fun isBST(root: TreeNode?): Boolean {
        if (root == null || root.left == null && root.right == null) {
            return true
        } else if (root.left != null && root.right != null) {
            return root.`val` > root.left.`val` && root.`val` < root.right.`val` && isBST(root.left) && isBST(root.right)
        } else if (root.left != null) {
            return root.`val` > root.left.`val` && isBST(root.left)
        } else {
            return root.`val` < root.right.`val` && isBST(root.right)
        }
    }

    private fun sumBST(root: TreeNode?): Int {
        if (root == null) {
            return 0
        } else {
            var sum = root.`val`
            if (root.left != null) {
                sum += sumBST(root.left)
            }
            if (root.right != null) {
                sum += sumBST(root.right)
            }
            if (maxSum == null) {
                maxSum = sum
            } else if (maxSum!! < sum) {
                maxSum = sum
            }
            return sum
        }
    }

    fun findMode(root: TreeNode?): IntArray {//501
        val map = mutableMapOf<Int, Int>()
        countMode(root, map)
        val max = map.values.max()
        return map.entries.filter { it.value == max }.map { it.key }.toIntArray()
    }

    private fun countMode(root: TreeNode?, map: MutableMap<Int, Int>) {
        root?.apply {
            map.compute(`val`) { _, v -> if (v == null) 1 else v + 1 }
            left?.apply { countMode(this@apply, map) }
            right?.apply { countMode(this@apply, map) }
        }
    }

    fun generateAbbreviations(word: String): List<String> {//320
        val ans = mutableListOf<String>()
        for (i in 1..word.length) {

        }
        return ans
    }

    //1035, equals to longest common sequences, which is not longest common substring
    fun maxUncrossedLines1(A: IntArray, B: IntArray): Int {
        val row = A.size
        val column = B.size
        val dp = Array(row + 1) { IntArray(column + 1) }
        for (i in 0 until row) {
            for (j in 0 until column) {
                if (A[i] == B[j]) {
                    dp[i + 1][j + 1] = dp[i][j] + 1
                } else {
                    dp[i + 1][j + 1] = Math.max(dp[i + 1][j], dp[i][j + 1])
                }
            }
        }
        return dp[row][column]
    }

    fun maxUncrossedLines(A: IntArray, B: IntArray): Int {//1035
        return maxUncrossedLines(A, 0, B, 0)
    }

    fun maxUncrossedLines(A: IntArray, a: Int, B: IntArray, b: Int): Int {
        if (a > A.lastIndex || b > B.lastIndex) {
            return 0
        } else {
            if (A[a] == B[b]) {
                return 1 + maxUncrossedLines(A, a + 1, B, b + 1)
            } else {
                return Math.max(maxUncrossedLines(A, a + 1, B, b), maxUncrossedLines(A, a, B, b + 1))
            }
        }
    }

    fun findUnsortedSubarray(nums: IntArray): Int {//581
        var ans = 0
        val copy = nums.copyOfRange(0, nums.size)
        copy.sort()
        var start = 0
        while (start < nums.size && copy[start] == nums[start]) {
            start++
        }
        var end = nums.lastIndex
        while (end >= start && copy[end] == nums[end]) {
            end--
        }
        if (end >= start) {
            return end - start + 1
        } else {
            return 0
        }
    }

    fun pairSums(nums: IntArray, target: Int): List<List<Int>> {//面试题16.24
        val ans = mutableListOf<MutableList<Int>>()
        nums.sort()
        var i = 0
        var j = nums.lastIndex
        var sum = 0
        while (i < j) {
            sum = nums[i] + nums[j]
            if (sum > target) {
                j--
            } else if (sum < target) {
                i++
            } else {
                val pair = mutableListOf<Int>()
                pair.add(nums[i])
                pair.add(nums[j])
                ans.add(pair)
                i++
                j--
            }
        }
        return ans
    }

    var ans: TreeNode? = null
    fun lowestCommonAncestor(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {//236
        ans = null
        dfs(root, p, q)
        return ans
    }

    private fun dfs(root: TreeNode?, p: TreeNode?, q: TreeNode?): Boolean {
        if (root == null) {
            return false
        }
        val lson = dfs(root.left, p, q)
        val rson = dfs(root.right, p, q)
        if (lson && rson || (lson || rson) && (root.`val` == p?.`val` || root.`val` == q?.`val`)) {
            ans = root
        }
        return lson || rson || (root.`val` == p?.`val` || root.`val` == q?.`val`)
    }

    fun buildArray(target: IntArray, n: Int): List<String> {
        val ans = mutableListOf<String>()
        var count = 1
        for (i in target.indices) {
            for (j in count..n) {
                if (target[i] > j) {
                    ans.add("Push")
                    ans.add("Pop")
                } else if (target[i] == j) {
                    ans.add("Push")
                }
                count++
            }
        }
        return ans
    }

    fun countTriplets(arr: IntArray): Int {
        var ans = 0
        var jxor = 0
        var kxor = 0
        for (i in 0..arr.size - 2) {
            jxor = arr[i]
            for (j in i + 1 until arr.size) {
                if (j > i + 1) {
                    jxor = jxor xor arr[j - 1]
                }
                kxor = 0
                for (k in j until arr.size) {
                    kxor = kxor xor arr[k]
                    if (jxor == kxor) {
                        ans++
                    }
                }
            }
        }
        return ans
    }
}