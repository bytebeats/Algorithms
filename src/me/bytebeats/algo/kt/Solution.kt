package me.bytebeats.algo.kt

import me.bytebeats.algo.designs.Trie
import me.bytebeats.algs.ds.ListNode
import me.bytebeats.algs.ds.TreeNode
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class Solution {
    fun findDuplicate(nums: IntArray): Int {//287
        var fast = 0
        var slow = 0
        while (true) {
            fast = nums[nums[fast]]
            slow = nums[slow]
            if (slow == fast) {
                fast = 0
                while (nums[slow] != nums[fast]) {
                    fast = nums[fast]
                    slow = nums[slow]
                }
                return nums[slow]
            }
        }
        return 0
    }

    fun invertTree(root: TreeNode?): TreeNode? {//226
        root?.apply {
            val tmp = left
            left = right
            right = tmp
            left?.apply { invertTree(root.left) }
            right?.apply { invertTree(root.right) }
        }
        return root
    }

    fun myPow(x: Double, n: Int): Double {//50, 面试题16
        var xVal = x
        if (n < 0) {
            xVal = 1 / x
        }
        return recursivePow(xVal, n)
    }

    fun recursivePow(x: Double, n: Int): Double {
        if (n == 0) {
            return 1.0
        }
        val halfP = recursivePow(x, n / 2)
        return if (n and 1 == 1) halfP * halfP * x else halfP * halfP
    }

    fun mySqrt(x: Int): Int {//69
        if (x == 0) {
            return 0
        } else {
            return binarySearch(1, x.toLong(), x.toLong()).toInt()
        }
    }

    private fun binarySearch(low: Long, high: Long, x: Long): Long {
        var mid = 0L
        var l = low
        var h = high
        while (l < h) {
            mid = l + (h - l) / 2L
            if (mid * mid == x || l == mid) {
                return mid
            } else if (mid * mid > x) {
                h = mid
            } else {
                l = mid
            }
        }
        return 1L
    }

    fun isPerfectSquare(num: Int): Boolean {//367, binary search
        if (num < 1) {
            return false
        } else if (num == 1) {
            return true
        } else {
            val n = num.toLong()
            var l = 1L
            var h: Long = (num / 2).toLong()
            var mid = 0L
            while (l <= h) {
                mid = l + (h - l) / 2L
                if (mid * mid == n) {
                    return true
                } else if (mid * mid > n) {
                    h = mid - 1
                } else {
                    l = mid + 1
                }
            }
            return false
        }
    }

    fun judgeSquareSum(c: Int): Boolean {
        if (c < 3) {
            return true
        } else {
            var sum = 0L
            var d = 0L
            var n = 0.0
            for (i in 0..Math.sqrt(c.toDouble()).toInt()) {
                sum = (i * i).toLong()
                d = c - sum
                if (d < 0) {
                    return false
                } else {
                    n = Math.sqrt(d.toDouble())
                    var dd = n - n.toInt()
                    if (dd == 0.0) {
                        return true
                    }
                }
            }
            return false
        }
    }

    fun isPowerOfTwo(n: Int): Boolean {//is n equal to 2^n
        if (n < 1) {
            return false
        }
        var num = n
        var has1 = false
        while (num != 0) {
            if (has1) {
                return false
            }
            if (num % 2 != 0) {
                has1 = true
            }
            num = num shr 1
        }
        return true
    }

    fun isPowerOfThree(n: Int): Boolean {
        if (n < 1) {
            return false
        } else if (n == 1) {
            return true
        } else if (n % 2 == 0) {
            return false
        } else {
            var num = n
            while (num != 1) {
                var d = num / 3.0
                var dd = d - d.toInt()
                if (dd != 0.0) {
                    return false
                }
                num = d.toInt()
            }
            return true
        }
    }

    fun isPowerOfFour(num: Int): Boolean {
        if (num <= 0) {
            return false
        } else if (num == 1) {
            return true
        } else {
            if (num % 10 != 4 && num % 10 != 6) {
                return false
            }
            var n = num
            while (n != 1) {
                var d = n / 4.0
                if (d - d.toInt() != 0.0) {
                    return false
                }
                n = d.toInt()
            }
            return true
        }
    }

    fun findShortestSubArray(nums: IntArray): Int {
        val count = HashMap<Int, Int>()
        for (i in nums.indices) {
            count[nums[i]] = count.getOrDefault(nums[i], 0) + 1
        }
        val degree = count.values.max() ?: 0
        var slow = 0
        var minLength = nums.size
        val windowCount = HashMap<Int, Int>()
        var windowDegree = 0
        for (fast in nums.indices) {
            windowCount[nums[fast]] = windowCount.getOrDefault(nums[fast], 0) + 1
            windowDegree = Math.max(windowDegree, windowCount[nums[fast]]!!)
            while (windowDegree == degree) {
                minLength = Math.min(minLength, fast - slow + 1)
                windowCount[nums[slow]] = windowCount[nums[slow]]!! - 1
                slow++
                if (nums[slow - 1] == nums[fast]) {
                    windowDegree--
                }
            }
        }
        return minLength
    }

    fun isSymmetric(root: TreeNode?): Boolean {
        if (root == null) {
            return false
        }
        return isSymmetric(root.left, root.right)
    }

    private fun isSymmetric(left: TreeNode?, right: TreeNode?): Boolean {
        if (left == null && right == null) {
            return true
        } else if (left == null || right == null) {
            return false
        } else if (left.`val` != right.`val`) {
            return false
        } else {
            return isSymmetric(left.left, right.right) && isSymmetric(left.right, right.left)
        }
    }

    fun getMinimumDifference(root: TreeNode?): Int {
        var minVal = Int.MAX_VALUE
        if (root != null) {
            val s = Stack<TreeNode>()
            val output = Stack<TreeNode>()
            var p = root
            while (p != null || !s.isEmpty()) {
                if (p != null) {
                    s.push(p)
                    p = p.left
                } else {
                    p = s.pop()
                    output.push(p)
                    p = p.right
                }
            }
            var top = s.pop()
            var diff = 0
            while (!s.isEmpty()) {
                diff = top.`val` - s.peek().`val`
                if (diff < minVal) {
                    minVal = diff
                }
                top = s.pop()
            }
        }
        return minVal
    }

    fun findPairs(nums: IntArray, k: Int): Int {
        var count = 0
        if (nums.isNotEmpty() && k >= 0) {
            val set = HashSet<Int>()
            for (num in nums) {
                if (set.isEmpty()) {
                    set.add(num)
                } else {
                    val num1 = num - k
                    if (set.contains(num1)) {
                        count++
                    }
                    val num2 = num + k
                    if (set.contains(num2)) {
                        count++
                    }
                    set.add(num)
                }
            }
            count /= 2
        }
        return count
    }

    fun tree2str(t: TreeNode?): String {
        var res = concat(t)
        if (res.length > 3) {
            res = res.substring(1, res.length - 1)
        }
        return res
    }

    private fun concat(t: TreeNode?): String {
        var res = ""
        if (t != null) {
            if (t.left == null && t.right == null) {
                res = "(${t.`val`})"
            } else {
                res = "${t.`val`}"
                if (t.left != null) {
                    res += "${concat(t.left)}"
                } else {
                    res += "()"
                }
                if (t.right != null) {
                    res += "${concat(t.right)}"
                }
                res = "($res)"
            }
        }
        return res
    }

    fun str2tree(s: String): TreeNode? {
        return createTree("($s)")
    }

    private fun createTree(s: String): TreeNode? {
        var str = s.substring(1, s.length - 1)
        if (str.isEmpty()) {
            return null
        } else {
            val numEndIndex = str.indexOfFirst { it == '(' }
            if (numEndIndex <= 0) {
                return TreeNode(str.toInt())
            } else {
                val num = str.subSequence(0, numEndIndex).toString().toInt()
                val splitIndex = str.lastIndexOf(")(")
                val newTreeNode = TreeNode(num)
                if (splitIndex > 0) {
                    newTreeNode.left = createTree(str.substring(numEndIndex, splitIndex + 1))
                    newTreeNode.right = createTree(str.substring(splitIndex + 1))
                } else {
                    newTreeNode.left = createTree(str.substring(numEndIndex))
                }
                return newTreeNode
            }
        }
    }

    fun longestWord(words: Array<String>): String {
        var res = ""
        if (words.isNotEmpty()) {
            val trie = Trie()
            words.sortBy { it.length }
            words.forEach {
                if (it.length > 0) {
//                    if (trie.) {
//
//                    } else {
                    trie.insert(it)
//                    }
                }
            }
        }
        return res
    }

    fun searchInsert(nums: IntArray, target: Int): Int {
        if (nums.isEmpty() || target < nums.first()) {
            return 0
        }
        if (target > nums.last()) {
            return nums.size
        }
        var index = -1
        for (i in 0 until nums.size) {
            println(i)
            if (nums[i] == target) {
                return i
            } else if (nums[i] > target) {
                index = i
                break
            }
        }
        return index
    }

    fun findClosestElements(arr: IntArray, k: Int, x: Int): List<Int> {
        val res = ArrayList<Int>(k)
        if (x < arr.first()) {
            for (i in 0 until k) {
                res.add(arr[i])
            }
        } else if (x > arr.last()) {
            for (i in arr.size - k until arr.size) {
                res.add(arr[i])
            }
        } else {
            var low = 0
            var high = arr.size - 1
            var mid = 0
            while (low < high) {
                mid = low + (high - low) / 2
                if (arr[mid] < x) {
                    low = mid + 1
                } else {
                    high = mid
                }
            }
            var newK = 0
            if (arr[low] == x) {
                res.add(arr[low])
                high = low + 1
                low--
                newK = k - 1
            } else {
                high = low
                low--
                newK = k
            }
            while (newK > 0) {
                if (low > -1 && high < arr.size) {
                    if (Math.abs(arr[low] - x) <= Math.abs(arr[high] - x)) {
                        res.add(0, arr[low])
                        low--
                    } else {
                        res.add(arr[high])
                        high++
                    }
                } else if (low > -1) {
                    res.add(0, arr[low])
                    low--
                } else if (high < arr.size) {
                    res.add(arr[high])
                    high++
                }
                newK--
            }
        }
        return res
    }

    fun generate(numRows: Int): List<List<Int>> {
        val triangle = ArrayList<List<Int>>(numRows)
        var pre: List<Int>? = null
        for (i in 0 until numRows) {
            val row = ArrayList<Int>(i + 1)
            if (i > 0) {
                pre = triangle[i - 1]
            }
            for (j in 0..i) {
                if (j == 0) {
                    row.add(1)
                } else if (j == i) {
                    row.add(1)
                } else {
                    if (j > 0 && pre != null) {
                        row.add(pre[j] + pre[j - 1])
                    }
                }
            }
            triangle.add(row)
        }
        return triangle
    }

    fun getRow(rowIndex: Int): List<Int> {
        val row = ArrayList<Int>(rowIndex + 1)
        for (i in 0..rowIndex) {
            row.add(1)
            if (i >= 2) {
                for (j in i - 1 downTo 1) {
                    row[j] += row[j - 1]
                }
            }
        }
        return row
    }

    fun largestUniqueNumber(A: IntArray): Int {
        if (A.size == 1) {
            return A[0]
        }
        A.sort()
        var res = -1
        var index = A.lastIndex
        while (index > -1) {
            if (index == A.lastIndex) {
                if (A[index] != A[index - 1]) {
                    return A.last()
                } else {
                    index--
                }
            } else if (index == 0) {
                if (A[index] != A[index + 1]) {
                    return A.first()
                } else {
                    break
                }
            } else {
                if (A[index] != A[index - 1] && A[index] != A[index + 1]) {
                    return A[index]
                } else {
                    index--
                }
            }
        }
        return res
    }

    fun dietPlanPerformance(calories: IntArray, k: Int, lower: Int, upper: Int): Int {
        var grade = 0
        var calorie = 0
        if (k > 1) {
            for (i in 0..k - 2) {
                calorie += calories[i]
            }
        }
        for (i in k - 1 until calories.size) {
            calorie += calories[i]
            if (calorie < lower) {
                grade--
            } else if (calorie > upper) {
                grade++
            }
            calorie -= calories[i - k + 1]
        }
        return grade
    }

    fun rotate(nums: IntArray, k: Int): Unit {//189
        if (nums.isEmpty() || nums.size == 1 || k < 1) {
            return
        }
        var j = 0
        val size = nums.size
        val newK = k % size
        var tmp = 0
        for (i in 0 until newK) {
            j = i + size - newK
            tmp = nums[j]
            for (h in j downTo i + 1) {
                nums[h] = nums[h - 1]
            }
            nums[i] = tmp
        }
    }

    fun rotate2(nums: IntArray, k: Int): Unit {//189
        if (nums.isEmpty() || nums.size == 1 || k < 1) {
            return
        }
        val size = nums.size
        val newK = k % size
        reverse(nums, 0, size - newK - 1)
        reverse(nums, size - newK, size - 1)
        reverse(nums, 0, size - 1)
    }

    private fun reverse(nums: IntArray, start: Int, end: Int) {
        var s = start
        var e = end
        var tmp = 0
        while (s < e) {
            tmp = nums[s]
            nums[s] = nums[e]
            nums[e] = tmp
            s++
            e--
        }
    }

    fun canReorderDoubled(A: IntArray): Boolean {//954
        val map = mutableMapOf<Int, Int>()
        A.forEach { map.compute(it) { _, v -> if (v == null) 1 else v + 1 } }
        for (num in A.sortedBy { Math.abs(it) }) {
            if (map[num]!! == 0) continue
            if (map[num * 2] ?: 0 <= 0) return false
            map[num] = map[num]!! - 1
            map[num * 2] = map[num * 2]!! - 1
        }
        return true
    }

    fun countAndSay(n: Int): String {
        if (n <= 1) {
            return "1"
        } else {
            var res = ""
            val pre = countAndSay(n - 1)
            var count = 0
            var ch = pre[0]
            for (i in 0 until pre.length) {
                if (ch == pre[i]) {
                    count++
                } else {
                    res += "$count$ch"
                    count = 1
                    ch = pre[i]
                }
            }
            res += "$count$ch"
            return res
        }
    }

    fun countAndSay2(n: Int): String {
        var res = "1"
        var k = n
        var count = 0
        var ch = ' '
        var tmp = ""
        while (k > 0) {
            count = 0
            ch = res[0]
            tmp = ""
            for (i in 0 until res.length) {
                if (ch == res[i]) {
                    count++
                } else {
                    tmp += "$count$ch"
                    count = 1
                    ch = res[i]
                }
            }
            tmp += "$count$ch"
            res = tmp
            k--
        }
        return res
    }

    fun compress(chars: CharArray): Int {
        var res = 0
        if (chars.isNotEmpty()) {
            var ch = chars[0]
            var count = 0
            for (i in 0 until chars.size) {
                if (ch == chars[i]) {
                    count++
                } else {
                    chars[res++] = ch
                    if (count > 1) {
                        count.toString().forEach { chars[res++] = it }
                    }
                    count = 1
                    ch = chars[i]
                }
            }
            chars[res++] = ch
            if (count > 1) {
                count.toString().forEach { chars[res++] = it }
            }
        }
        return res
    }

    fun smallerNumbersThanCurrent(nums: IntArray): IntArray {//1365
        val copy = nums.copyOfRange(0, nums.size)
        for (i in nums.indices) {
            nums[i] = copy.filter { it < nums[i] }.count()
        }
        return nums
    }

    fun rankTeams(votes: Array<String>): String {
        val res = StringBuilder()
        if (votes.isNotEmpty()) {
            val map = HashMap<Int, HashMap<Char, Int>>()
            votes.forEach { vote ->
                for (i in 0 until vote.length) {
                    map.compute(i) { _, v ->
                        if (v == null) {
                            val newV = HashMap<Char, Int>()
                            newV[vote[i]] = 1
                            newV
                        } else {
                            val key = vote[i]
                            if (v.containsKey(key)) {
                                v[key] = v?.getValue(key)?.plus(1)
                            } else {
                                v[key] = 1
                            }
                            v
                        }
                    }
                }
            }
            val count = votes[0].length
            for (i in 0 until count) {
                var ch: Char? = null
                var j = i
                while (j < count) {
                    val e = map[j]
                    val maxCount = e!!.values.max()
                    val keys = e.filter { it -> it.value == maxCount }.keys
                    if (keys.size == 1) {
                        ch = keys.first()
                        res.append(ch)
                    } else {
                        j++
                    }
                }
            }
            if (res.length != count) {
                res.append(votes[0].substring(res.length))
            }
        }
        return res.toString()
    }

    var found = false
    var treeHead: TreeNode? = null

    fun isSubPath(head: ListNode?, root: TreeNode?): Boolean {
        if (head == null || root == null) {
            return false
        }
        found = false
        treeHead = null
        findSubPathHead(root, head.`val`)
        if (!found || treeHead == null) {
            return false
        }
        var p = treeHead
        var q = head
        while (q != null) {
            if (p?.`val` != q?.`val`) {
                return false
            } else {
                if (p.left != null && p.left.`val` == q.`val`) {
                    p = p.left
                    q = q.next
                }
                if (p?.right != null && p.right.`val` == q?.`val`) {
                    p = p.right
                    q = q.next
                }
            }
        }
        return true
    }

    private fun findSubPathHead(root: TreeNode?, listHeadValue: Int) {
        if (found) {
            return
        }
        if (root != null) {
            if (root.`val` == listHeadValue) {
                treeHead = root
                found = true
            } else {
                findSubPathHead(root.left, listHeadValue)
                findSubPathHead(root.right, listHeadValue)
            }
        }
    }

    fun merge(A: IntArray, m: Int, B: IntArray, n: Int): Unit {
        for (i in 0 until n) {
            A[m + i] = B[i]
        }
        A.sort(0, m + n)
    }

    fun isBalanced(root: TreeNode?): Boolean {//110
        if (root == null) {
            return true
        } else if (root.left == null && root.right == null) {
            return true
        } else if (Math.abs(height(root.left) - height(root.right)) > 1) {
            return false
        } else {
            return isBalanced(root.left) && isBalanced(root.right)
        }
    }

    private fun height(root: TreeNode?): Int {
        if (root == null) {
            return 0
        } else {
            return 1 + Math.max(height(root.left), height(root.right))
        }
    }

    fun singleNumber(nums: IntArray): Int {
        var res = 0
        for (i in nums.indices) {
            res = res xor nums[i]
        }
        nums.forEach { res = res xor it }
        return res
    }

    fun singleNumber2(nums: IntArray): IntArray {
        var bitmask = 0
        nums.forEach { bitmask = bitmask xor it }
        val diff = bitmask and (-bitmask)
        var x = 0
        nums.forEach {
            if (it and diff != 0) {
                x = x xor it
            }
        }
        return intArrayOf(x, bitmask xor x)
    }

    fun majorityElement(nums: IntArray): Int {
        if (nums.isEmpty()) {
            return -1
        }
        var count = 1
        var tmp = nums.first()
        for (i in nums.indices) {
            if (tmp == nums[i]) {
                count++
            } else {
                count--
            }
            if (count == 0) {
                count = 1
                tmp = nums[i]
            }
        }
        val halfSize = nums.size / 2 + 1
        count = 0
        for (i in nums.indices) {
            if (tmp == nums[i]) {
                count++
            }
            if (count == halfSize) {
                return tmp
            }
        }
        return -1
    }

    val dr = intArrayOf(-1, 0, 1, 0)
    val dc = intArrayOf(0, -1, 0, 1)

    fun orangesRotting(grid: Array<IntArray>): Int {
        val R = grid.size
        val C: Int = grid[0].size
        // queue : all starting cells with rotten oranges
        val queue = ArrayDeque<Int>()
        val depth = HashMap<Int?, Int?>()
        for (r in 0 until R) for (c in 0 until C) if (grid[r][c] == 2) {
            val code = r * C + c
            queue.add(code)
            depth[code] = 0
        }
        var ans = 0
        while (!queue.isEmpty()) {
            val code = queue.remove()
            val r = code / C
            val c = code % C
            for (k in 0..3) {
                val nr = r + dr[k]
                val nc = c + dc[k]
                if (nr in 0 until R && nc in 0 until C && grid[nr][nc] == 1) {
                    grid[nr][nc] = 2
                    val ncode = nr * C + nc
                    queue.add(ncode)
                    depth[ncode] = depth[code]!! + 1
                    ans = depth[ncode]!!
                }
            }
        }
        for (row in grid) for (v in row) if (v == 1) return -1
        return ans
    }

    fun distributeCandies(candies: Int, num_people: Int): IntArray {//1103
        val arr = IntArray(num_people)
        var left = candies
        var index = 0
        var candy = 0
        while (left > 0) {
            candy++
            index = (candy - 1) % num_people
            if (candy <= left) {
                arr[index] += candy
                left -= candy
            } else {
                arr[index] += left
                left = 0
            }
        }
        return arr
    }

    fun findContinuousSequence(target: Int): Array<IntArray> {
        val res = ArrayList<IntArray>()
        var sum = 0
        val end = (target + 1) / 2
        for (i in 1..end) {
            sum = 0
            for (j in i..end) {
                sum += j
                if (sum >= target) {
                    if (sum == target) {
                        val sub = IntArray(j - i + 1)
                        for (k in i..j) {
                            sub[k - i] = k
                        }
                        res.add(sub)
                    }
                    break
                }
            }
        }
        return res.toTypedArray()
    }

    fun levelOrder(root: TreeNode?): List<List<Int>> {
        val res = ArrayList<List<Int>>()
        if (root != null) {
            val q = LinkedList<TreeNode>()
            var levelRes = ArrayList<Int>()
            q.add(root)
            var countDown = 1
            var count = 0
            var p: TreeNode? = null
            while (!q.isEmpty()) {
                p = q.remove()
                levelRes.add(p.`val`)
                countDown--
                if (root.left != null) {
                    count++
                    q.add(root.left)
                }
                if (root.right != null) {
                    count++
                    q.add(root.right)
                }
                if (countDown == 0) {
                    res.add(ArrayList<Int>(levelRes))
                    countDown = count
                    count = 0
                    levelRes.clear()
                }
            }
        }
        return res
    }

    fun plusOne(digits: IntArray): IntArray {
        var b = 1
        for (i in digits.lastIndex downTo 0) {
            digits[i] += b
            b = digits[i] / 10
            digits[i] %= 10
        }
        if (b == 0) {
            return digits
        } else {
            val newDigits = IntArray(digits.size + 1)
            newDigits[0] = b
            digits.forEachIndexed { index, i -> newDigits[index + 1] = i }
            return newDigits
        }
    }

    fun addBinary(a: String, b: String): String {//67
        val res = StringBuilder()
        var i = a.lastIndex
        var j = b.lastIndex
        var subSum = 0
        while (i > -1 && j > -1) {
            subSum += (a[i] - '0') + (b[j] - '0')
            res.append(subSum % 2)
            println(res)
            subSum /= 2
            i--
            j--
        }
        while (i > -1) {
            subSum += (a[i] - '0')
            res.append(subSum % 2)
            println(res)
            subSum /= 2
            i--
        }
        while (j > -1) {
            subSum += (b[j] - '0')
            res.append(subSum % 2)
            println(res)
            subSum /= 2
            j--
        }
        if (subSum != 0) {
            res.append(subSum)
            println(res)
        }
        return res.reversed().toString()
    }

    fun addToArrayForm(A: IntArray, K: Int): List<Int> {
        val sum = ArrayList<Int>()
        var i = A.lastIndex
        var j = K
        var d = 0
        while (i > -1 && j != 0) {
            d += A[i] + j % 10
            sum.add(d % 10)
            d /= 10
            i--
            j /= 10
        }
        while (i > -1) {
            d += A[i]
            sum.add(d % 10)
            d /= 10
            i--
        }
        while (j != 0) {
            d += j % 10
            sum.add(d % 10)
            d /= 10
            j /= 10
        }
        if (d != 0) {
            sum.add(d)
        }
        return sum.reversed()
    }

    fun addStrings(num1: String, num2: String): String {
        if (num1.isEmpty()) {
            return num2
        } else if (num2.isEmpty()) {
            return num1
        } else {
            val sum = StringBuilder()
            var i = num1.lastIndex
            var j = num2.lastIndex
            var d = 0
            while (i > -1 && j > -1) {
                d += (num1[i] - '0') + (num2[j] - '0')
                sum.append(d % 10)
                d /= 10
                i--
                j--
            }
            while (i > -1) {
                d += (num1[i] - '0')
                sum.append(d % 10)
                d /= 10
                i--
            }
            while (j > -1) {
                d += (num2[j] - '0')
                sum.append(d % 10)
                d /= 10
                j--
            }
            if (d != 0) {
                sum.append(d)
            }
            return sum.reverse().toString()
        }
    }

    fun multiply(num1: String, num2: String): String {
        var sum = ""
        if (num1.isNotEmpty() && num2.isNotEmpty()) {
            for (i in num1.lastIndex downTo 0) {
                sum = addStrings(sum, multiply(num2, num1[i], num1.lastIndex - i))
            }
        }
        while (sum.startsWith('0') && sum.length > 1) {
            sum = sum.substring(1)
        }
        return sum
    }

    fun multiply(num1: String, num2: Char, count: Int): String {
        val times = num2 - '0'
        if (times == 0) {
            return "0"
        }
        var sum = StringBuilder()
        var d = 0
        for (i in num1.lastIndex downTo 0) {
            d += (num1[i] - '0') * times
            sum.append(d % 10)
            d /= 10
        }
        if (d != 0) {
            sum.append(d)
        }
        sum.reverse()
        var zeroCount = count
        while (zeroCount > 0) {
            sum.append(0)
            zeroCount--
        }
        return sum.toString()
    }

    fun plusOne(head: ListNode?): ListNode? {
        if (head == null) {
            return head
        }
        var reverseList = reverse(head)
        var p = reverseList
        var d = 1
        while (p != null) {
            d += p.`val`
            p.`val` = d % 10
            d /= 10
            p = p.next
        }
        if (d != 0) {
            p = reverseList
            while (p?.next != null) {
                p = p.next
            }
            p?.next = ListNode(d)
        }
        return reverse(reverseList)
    }

    fun reverse(head: ListNode?): ListNode? {
        val dummy = ListNode(-1)
        var dummyNext = dummy.next
        var p = head
        var tmp: ListNode? = null
        while (p != null) {
            tmp = p
            p = p.next
            dummyNext = dummy.next
            dummy.next = tmp
            tmp.next = dummyNext
        }
        return dummy.next
    }

    fun sortString(s: String): String {
        val chCounts = IntArray(26)
        s.forEach { chCounts[it - 'a']++ }
        val res = StringBuilder()
        while (chCounts.sum() > 0) {
            for (i in chCounts.indices) {
                if (chCounts[i] > 0) {
                    res.append('a' + i)
                    chCounts[i]--
                }
            }
            for (i in 25 downTo 0) {
                if (chCounts[i] > 0) {
                    res.append('a' + i)
                    chCounts[i]--
                }
            }
        }
        return res.toString()
    }

    fun sortString2(s: String): String {
        val chCounts = IntArray(26)
        var count = 0
        s.forEach {
            chCounts[it - 'a']++
            count++
        }
        val res = StringBuilder()
        while (count > 0) {
            for (i in chCounts.indices) {
                if (chCounts[i] > 0) {
                    res.append('a' + i)
                    chCounts[i]--
                    count--
                }
            }
            for (i in 25 downTo 0) {
                if (chCounts[i] > 0) {
                    res.append('a' + i)
                    chCounts[i]--
                    count--
                }
            }
        }
        return res.toString()
    }

    fun findTheLongestSubstring(s: String): Int {
        val map = HashMap<Char, Int>(5)
        s.forEach {
            if (isVowel(it)) {
                map.compute(it) { _, value ->
                    if (value == null) {
                        1
                    } else {
                        value + 1
                    }
                }
            }
        }
        var start = 0
        var end = s.lastIndex
        while (map.values.sum() % 2 != 0) {
            for (i in s.indices) {
                if (isVowel(s[i])) {
                    start = i
                    break
                }
            }
            for (i in s.lastIndex downTo 0) {
                if (isVowel(s[i])) {
                    end = i
                    break
                }
            }
        }
        return 0
    }

    private fun isVowel(char: Char): Boolean = char == 'a' || char == 'e' || char == 'i' || char == 'o' || char == 'u'

    fun longestPalindromeSubseq(s: String): Int {
        val mnchStr = StringBuilder()
        s.forEach {
            mnchStr.append('#')
            mnchStr.append(it)
        }
        mnchStr.append('#')
        val radius = IntArray(mnchStr.length)
        var R = -1
        var C = -1
        var max = Int.MIN_VALUE
        for (i in mnchStr.indices) {
            radius[i] = if (R < i) 1 else Math.min(R - i, radius[2 * C - i])
            while (i - radius[i] > -1 && i + radius[i] < mnchStr.length && mnchStr[i - radius[i]] == mnchStr[i + radius[i]]) {
                radius[i]++
            }
            if (i + radius[i] > R) {
                R = i + radius[i] - 1
                C = i
            }
            if (max < radius[i]) {
                max = radius[i]
            }
        }
        return max - 1
    }

    fun longestPalindromeSubseq2(s: String): Int {
        val n = s.length
        val f = Array(n) { IntArray(n) }
        for (i in n - 1 downTo 0) {
            f[i][i] = 1
            for (j in i + 1 until n) {
                if (s[i] == s[j]) {
                    f[i][j] = f[i + 1][j - 1] + 2
                } else {
                    f[i][j] = Math.max(f[i + 1][j], f[i][j - 1])
                }
            }
        }
        return f[0][n - 1]
    }

    fun generateTheString(n: Int): String {//1374
        val res = StringBuilder()
        var k = n
        if (k % 2 == 0) {
            for (i in 0 until k - 1) {
                res.append('a')
            }
            res.append('b')
        } else {
            for (i in 0 until k) {
                res.append('a')
            }
        }
        return res.toString()
    }

    fun numTimesAllBlue(light: IntArray): Int {
        var count = 0
        val states = IntArray(light.size) { -1 }
        var index = 0
        var maxIndex = -1
        for (i in light.indices) {
            index = light[i] - 1
            if (maxIndex < index) {
                maxIndex = index
            }
            states[index] = 0
            if (isAllAtLeatOnBefore(states, maxIndex)) {
                setAllBlue(states, maxIndex)
                if (isAllOnBlue(states)) {
                    count++
                }
            }
        }
        return count
    }

    fun isAllOnBlue(states: IntArray): Boolean {
        for (i in states.indices) {
            if (states[i] == 0) {
                return false
            }
        }
        return true
    }

    fun setAllBlue(states: IntArray, n: Int) {
        for (i in 0..n) {
            states[i] = 1
        }
    }

    fun isAllAtLeatOnBefore(states: IntArray, n: Int): Boolean {
        for (i in 0..n) {
            if (states[i] < 0) {
                return false
            }
        }
        return true
    }

    fun numOfMinutes(n: Int, headID: Int, manager: IntArray, informTime: IntArray): Int {
        var k = n
        var minutes = 0
        val list = ArrayList<Int>()
        var minVal = 0
        manager[headID] = Int.MAX_VALUE
        minutes += informTime[headID]
        k -= 1
        while (k > 0) {
            list.clear()
            minVal = manager.min() ?: Int.MAX_VALUE
            if (minVal != Int.MAX_VALUE) {
                for (i in manager.indices) {
                    if (minVal == manager[i]) {
                        list.add(i)
                    } else {
                        continue
                    }
                }
                for (i in list.indices) {
                    minutes += informTime[i]
                    manager[i] = Int.MAX_VALUE
                }
            }
            k -= list.size
        }
        return minutes
    }

    fun coinChange(coins: IntArray, amount: Int): Int {
        if (amount <= 0) {
            return 0
        }
        if (coins.isEmpty()) {
            return -1
        }
        coins.sort()
        val matrix = Array(amount + 1) { IntArray(coins.size + 1) { -1 } }
        var preAmount = 0
        for (i in 1..amount) {
            for (j in 0..coins.lastIndex) {
                preAmount = i - coins[j]
                if (preAmount > -1) {
                    if (preAmount == 0) {
                        matrix[i][j] = 1
                    } else if (matrix[preAmount][coins.size] > 0) {
                        matrix[i][j] = matrix[preAmount][coins.size] + 1
                    }
                    if (matrix[i][coins.size] == -1 || matrix[i][j] < matrix[i][coins.size]) {
                        matrix[i][coins.size] = matrix[i][j]
                    }
                }
            }
        }
        return matrix[amount][coins.size]
    }

    fun maxProfit(prices: IntArray): Int {
        var maxProfit = 0
        if (prices.isNotEmpty()) {
            var minPrice = prices[0]
            for (i in prices.indices) {
                if (prices[i] - minPrice > maxProfit) {
                    maxProfit = prices[i] - minPrice
                }
                if (minPrice > prices[i]) {
                    minPrice = prices[i]
                }
            }
        }
        return maxProfit
    }

    fun minFlips(a: Int, b: Int, c: Int): Int {
        var flips = 0
        var bitA = 0
        var bitB = 0
        var bitC = 0
        for (i in 0..31) {
            bitA = (a shr i) and 1
            bitB = (b shr i) and 1
            bitC = (c shr i) and 1
            if (bitC == 0) {
                flips += bitA + bitB
            } else {
                flips += if (bitA + bitB == 0) 1 else 0
            }
        }
        return flips
    }

    fun maximum(a: Int, b: Int): Int {
        val sum = a.toLong() + b.toLong()
        val diff = a.toLong() - b.toLong()
        val absDiff = (diff xor (diff shr 63)) - (diff shr 63)
        return ((sum + absDiff) / 2).toInt()
    }

    fun findComplement(num: Int): Int {//476
        var tmp = 1L
        while (num >= tmp) {
            tmp = tmp shl 1
        }
        return (tmp - 1 - num).toInt()
    }

    fun numberOfSteps(num: Int): Int {
        var steps = 0
        var k = num
        while (k != 0) {
            if (k and 1 == 1) {
                k--
            } else {
                k /= 2
            }
            steps++
        }
        return steps
    }

    var d = 0
    fun diameterOfBinaryTree(root: TreeNode?): Int {
        d = 1
        depth(root)
        return d - 1
    }

    private fun depth(root: TreeNode?): Int {
        if (root == null) {
            return 0
        } else {
            val left = depth(root.left)
            val right = depth(root.right)
            d = Math.max(d, left + right + 1)
            return Math.max(left, right) + 1
        }
    }

    fun isBalanced2(root: TreeNode?): Boolean {//110
        if (root == null) {
            return true
        } else if (root.left == null && root.right == null) {
            return true
        } else {
            val left = height(root.left)
            val right = height(root.right)
            if (Math.abs(left - right) <= 1) {
                return isBalanced2(root.left) && isBalanced2(root.right)
            } else {
                return false
            }
        }
    }

    fun increasingBST(root: TreeNode?): TreeNode? {
        if (root == null || root.left == null && root.right == null) {
            return root
        }
        val list = ArrayList<TreeNode>()
        val stack = Stack<TreeNode>()
        var p = root
        while (p != null || stack.isNotEmpty()) {
            if (p != null) {
                stack.push(p)
                p = p.left
            } else {
                p = stack.pop()
                list.add(p)
                p = p.right
            }
        }
        list.forEach { print(it.`val`) }
        return root
    }

    fun pivotIndex(nums: IntArray): Int {
        var leftSum = 0L
        var rightSum = nums.sum().toLong()
        for (i in 0..nums.lastIndex) {
            rightSum -= nums[i]
            if (leftSum == rightSum) {
                return i
            }
            leftSum += nums[i]
        }
        return -1
    }

    fun canThreePartsEqualSum(A: IntArray): Boolean {
        if (A.size > 2) {
            val sum = A.sum().toLong()
            if (sum % 3 != 0L) {
                return false
            }
            var left = -1
            var mid = -1
            var target = sum / 3
            var subSum = 0L
            for (i in A.indices) {
                subSum += A[i]
                if (subSum == target) {
                    left = i
                    break
                }
            }
            if (left > A.size - 3) {
                return false
            }
            subSum = 0
            for (i in left + 1..A.lastIndex) {
                subSum += A[i]
                if (subSum == target) {
                    mid = i
                    break
                }
            }
            if (mid > A.size - 2) {
                return false
            }
            subSum = 0L
            if (left < mid && mid < A.lastIndex) {
                subSum = A.drop(mid + 1).sum().toLong()
                if (subSum == target) {
                    return true
                }
            }
        }
        return false
    }

    fun dayOfTheWeek(day: Int, month: Int, year: Int): String { //1185, Zeller's Formula
        var myDay = day
        var myMonth = month
        var myYear = year
        if (month == 1) {
            myMonth = 13
            myYear--
        }
        if (month == 2) {
            myMonth = 14
            myYear--
        }
        val week = (myDay + myMonth * 2 + 3 * (myMonth + 1) / 5 + myYear + myYear / 4 - myYear / 100 + myYear / 400) % 7
        return when (week) {
            0 -> "Monday"
            1 -> "Tuesday"
            2 -> "Wednesday"
            3 -> "Thursday"
            4 -> "Friday"
            5 -> "Saturday"
            else -> "Sunday"
        }
    }

    fun daysBetweenDates(date1: String, date2: String): Int {//1360
        return Math.abs(daysOfDate(date1) - daysOfDate(date2))
    }

    private fun daysOfDate(date: String): Int {//Zeller formula
        val arr = date.split("-")
        var day = arr[2].toInt()
        var month = arr[1].toInt()
        var year = arr[0].toInt()
        if (month <= 2) {
            month += 10
            year--
        } else {
            month -= 2
        }
        return day + month * 30 + (3 * month - 1) / 5 + 365 * year + year / 4 - year / 100 + year / 400
    }

    fun gcdOfStrings(str1: String, str2: String): String {
        val l1 = str1.length
        val l2 = str2.length
        val gcd = if (l1 >= l2) {
            gcd(l1, l2)
        } else {
            gcd(l2, l1)
        }
        val gcdStr = str1.substring(0, gcd)
        for (i in 1 until str1.length / gcd) {
            if (gcdStr != str1.substring(i * gcd, (i + 1) * gcd)) {
                return ""
            }
        }
        for (i in 0 until str2.length / gcd) {
            if (gcdStr != str2.substring(i * gcd, (i + 1) * gcd)) {
                return ""
            }
        }
        return gcdStr
    }

    private fun gcd(m: Int, n: Int): Int {
        var j = m
        var k = n
        var rem = 0
        while (k != 0) {
            rem = j % k
            j = k
            k = rem
        }
        return j
    }

    fun rotatedDigits(N: Int): Int {
        var count = 0
        for (i in 2..N) {
            if (isGoodNumber(i)) {
                count++
            }
        }
        return count
    }

    private fun isGoodNumber(num: Int): Boolean {
        var n = num
        var rem = 0
        var isDifferentAfterRotated = false
        while (n != 0) {
            rem = n % 10
            if (isValidDigit(rem)) {
                if (isDifferentAfterRotated(rem)) {
                    isDifferentAfterRotated = true
                }
            } else {
                return false
            }
            n /= 10
        }
        return isDifferentAfterRotated
    }

    private fun isValidDigit(i: Int): Boolean = i == 1 || i == 0 || i == 8 || i == 2 || i == 5 || i == 6 || i == 9
    private fun isDifferentAfterRotated(i: Int): Boolean = i == 2 || i == 5 || i == 6 || i == 9

    fun primePalindrome(N: Int): Int {
        var n = N
        while (!isNumPalindrome(n) || !isPrime(n)) {
            n++
        }
        return -1
    }

    private fun isNumPalindrome(n: Int): Boolean {
        if (n < 10) {
            return true
        } else {
            val numStr = n.toString()
            val count = numStr.length / 2
            for (i in 0..count) {
                if (numStr[i] != numStr[numStr.lastIndex - i]) {
                    return false
                }
            }
            return true
        }
    }

    private fun isPrime(n: Int): Boolean {
        if (n < 6) {
            return n != 4
        } else {
            val sqrt = Math.sqrt(n.toDouble()).toInt()
            for (i in 2..sqrt) {
                if (n % i == 0) {
                    return false
                }
            }
            return true
        }
    }

    fun majorityElement2(nums: IntArray): Int {
        val map = HashMap<Int, Int>()
        nums.forEach { map.compute(it) { _, v -> if (v == null) 1 else v + 1 } }
        var key = 0
        var maxCount = 0
        map.forEach { t, u ->
            if (u > maxCount) {
                maxCount = u
                key = t
            }
        }
        if (maxCount > nums.size / 2) {
            return key
        } else {
            return 0
        }
    }

    fun isMajorityElement(nums: IntArray, target: Int): Boolean {
        val count = nums.filter { it > target }.count()
        return count > nums.size / 2
    }

    fun sumZero(n: Int): IntArray {
        val nums = IntArray(n)
        for (i in 0 until n / 2) {
            nums[i * 2] = i + 1
            nums[i * 2 + 1] = -i - 1
        }
        if (n % 2 != 0) {
            nums[n - 1] = 0
        }
        return nums
    }

    fun decompressRLElist(nums: IntArray): IntArray {
        var newSize = nums.filterIndexed { index, i -> index and 1 == 0 }.sum()
        val res = IntArray(newSize)
        var index = 0
        var count = 0
        for (i in 0 until nums.size / 2) {
            count = nums[i * 2]
            while (count-- > 0) {
                res[index++] = nums[i * 2 + 1]
            }
        }
        return res
    }

    fun numSmallerByFrequency(queries: Array<String>, words: Array<String>): IntArray {
        val res = IntArray(queries.size)
        val wordsFrequency = IntArray(words.size)
        words.forEachIndexed { index, s -> wordsFrequency[index] = getFrequency(s) }
        queries.forEachIndexed { index, s ->
            val freq = getFrequency(s)
            res[index] = wordsFrequency.filter { it > freq }.count()
        }
        return res
    }

    private fun getFrequency(word: String): Int {
        var frequency = 0
        var ch = 'z'
        word.forEach {
            if (it == ch) {
                frequency++
            } else if (ch > it) {
                ch = it
                frequency = 1
            }
        }
        println(frequency)
        return frequency
    }

    fun lengthOfLIS(nums: IntArray): Int {
        if (nums.isEmpty()) {
            return 0
        }
        val dp = IntArray(nums.size)
        dp[0] = 1
        var maxAns = 1
        for (i in 1..nums.lastIndex) {
            var maxVal = 0
            for (j in 0..i) {
                if (nums[i] > nums[j]) {
                    maxVal = Math.max(maxVal, dp[j])
                }
            }
            dp[i] = maxVal + 1
            maxAns = Math.max(maxAns, dp[i])
        }
        return maxAns
    }

    fun luckyNumbers(matrix: Array<IntArray>): List<Int> {//1380
        val res = ArrayList<Int>()
        outer@ for (i in matrix.indices) {
            var minVal = Int.MAX_VALUE
            var minIndex = -1
            for (j in matrix[i].indices) {
                if (matrix[i][j] < minVal) {
                    minVal = matrix[i][j]
                    minIndex = j
                }
            }
            for (k in matrix.indices) {
                if (matrix[k][minIndex] > minVal) {
                    continue@outer
                }
            }
            res.add(matrix[i][minIndex])
        }
        return res
    }

    fun isPalindrome(head: ListNode?): Boolean {
        if (head?.next == null) {
            return true
        }
        var p = head
        var count = 0
        while (p != null) {
            count++
            p = p.next
        }
        var half = count / 2
        val list = IntArray(half)
        p = head
        while (half-- > 0) {
            list[half] = p?.`val` ?: 0
            p = p?.next
        }
        if (count and 1 == 1) {
            p = p?.next
        }
        half = 0
        while (half < count / 2) {
            if (p?.`val` != list[half]) {
                return false
            }
            half++
            p = p.next
        }
        return true
    }

    fun compressString(S: String): String {
        val ans = StringBuilder()
        var ch = ' '
        var count = 0
        for (i in S.indices) {
            if (ch == S[i]) {
                count++
            } else {
                if (ch == ' ') {
                    ch = S[i]
                    count++
                } else {
                    ans.append(ch)
                    ans.append(count)
                    ch = S[i]
                    count = 1
                }
            }
            if (ans.length >= S.length) {
                return S
            }
        }
        if (ch != ' ') {
            ans.append(ch)
            ans.append(count)
        }
        if (ans.length >= S.length) {
            return S
        }
        return ans.toString()
    }

    fun validPalindrome(s: String): Boolean {
        var start = 0
        var end = s.lastIndex
        while (start < end) {
            if (s[start] == s[end]) {
                start++
                end--
            } else {
                return validPalindrome(s, start + 1, end) || validPalindrome(s, start, end - 1)
            }
        }
        return true
    }

    fun validPalindrome(str: String, start: Int, end: Int): Boolean {
        var s = start
        var e = end
        while (s < e) {
            if (str[s] == str[e]) {
                s++
                e--
            } else {
                return false
            }
        }
        return true
    }

    fun isPalindrome(s: String): Boolean {//125
        var start = 0
        var end = s.lastIndex
        while (start < end) {
            while (!s[start].isLetterOrDigit()) {
                start++
                if (start > end) {
                    return true
                }
            }
            while (!s[end].isLetterOrDigit()) {
                end--
                if (start > end) {
                    return true
                }
            }
            if (!s[start++].equals(s[end--], true)) {
                return false
            }
        }
        return true
    }

    fun countCharacters(words: Array<String>, chars: String): Int {
        val charCounts = IntArray(26)
        chars.forEach { charCounts[it - 'a']++ }
        var sum = 0
        val wordCounts = IntArray(26)
        words.forEach {
            wordCounts.fill(0)
            inflate(wordCounts, it)
            if (contains(wordCounts, charCounts)) {
                sum += it.length
            }
        }
        return sum
    }

    private fun inflate(arr: IntArray, word: String) {
        word?.forEach { arr[it - 'a']++ }
    }

    private fun contains(arr: IntArray, chars: IntArray): Boolean {
        for (i in arr.indices) {
            if (arr[i] > chars[i]) {
                return false
            }
        }
        return true
    }

    fun shortestDistance(words: Array<String>, word1: String, word2: String): Int {
        val indices1 = findIndex(words, word1)
        val indices2 = findIndex(words, word2)
        return minDistance(indices1, indices2)
    }

    private fun findIndex(words: Array<String>, word: String): List<Int> {
        val list = ArrayList<Int>()
        words.forEachIndexed { index, s ->
            if (word == s) {
                list.add(index)
            }
        }
        return list
    }

    private fun minDistance(indices1: List<Int>, indices2: List<Int>): Int {
        var minDistance = Int.MAX_VALUE
        for (i in indices1.indices) {
            for (j in indices2.indices) {
                if (Math.abs(indices1[i] - indices2[j]) < minDistance) {
                    minDistance = Math.abs(indices1[i] - indices2[j])
                    if (minDistance == 1) {
                        return minDistance
                    }
                }
            }
        }
        return minDistance
    }

    private fun minDistance2(indices1: List<Int>, indices2: List<Int>): Int {
        var minDistance = Int.MAX_VALUE
        for (i in indices1.indices) {
            for (j in indices2.indices) {
                if (indices1[i] == indices2[j]) {
                    continue
                }
                if (Math.abs(indices1[i] - indices2[j]) < minDistance) {
                    minDistance = Math.abs(indices1[i] - indices2[j])
                    if (minDistance == 1) {
                        return minDistance
                    }
                }
            }
        }
        return minDistance
    }

    fun maxSlidingWindow(nums: IntArray, k: Int): IntArray {
        if (nums.isEmpty() || k <= 0 || k > nums.size) {
            return IntArray(0)
        }
        val ans = IntArray(nums.size - k + 1)
        var pre = 0
        var j = 0
        var maxVal = Int.MIN_VALUE
        val window = ArrayList<Int>(k)
        for (i in 0 until k - 1) {
            if (nums[i] > maxVal) {
                maxVal = nums[i]
            }
            window.add(nums[i])
        }
        for (i in k - 1 until nums.size) {
            if (nums[i] > maxVal) {
                maxVal = nums[i]
            }
            window.add(nums[i])
            ans[j++] = maxVal
            pre = nums[i - k + 1]
            window.removeAt(window.indexOf(pre))
            if (pre == maxVal) {
                maxVal = window.max() ?: Int.MIN_VALUE
            }
        }
        return ans
    }
}

class Trie() {

    /** Initialize your data structure here. */
    val root = TrieNode()

    /** Inserts a word into the trie. */
    fun insert(word: String) {
        word?.apply {
            var node = root
            forEach { ch: Char ->
                if (!node.children.containsKey(ch)) {
                    node.children[ch] = TrieNode()
                }
                node = node.children[ch]!!
            }
            node.wordEnd = true
        }
    }

    /** Returns if the word is in the trie. */
    fun search(word: String): Boolean {
        word?.apply {
            var node = root
            forEach { ch ->
                if (!node.children.containsKey(ch)) {
                    return false
                }
                node = node.children[ch]!!
            }
            return node.wordEnd
        }
        return false
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    fun startsWith(prefix: String): Boolean {
        prefix?.apply {
            var node = root
            forEach { ch ->
                if (!node.children.containsKey(ch)) {
                    return false
                }
                node = node.children[ch]!!
            }
            return true
        }
        return false
    }

    class TrieNode {
        val children = HashMap<Char, TrieNode>()
        var wordEnd = false
    }
}
