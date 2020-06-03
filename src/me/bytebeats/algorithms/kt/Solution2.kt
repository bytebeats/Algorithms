package me.bytebeats.algorithms.kt

import me.bytebeats.algorithms.meta.ListNode
import me.bytebeats.algorithms.meta.TreeNode

class Solution2 {
    fun removeDuplicates(s: String, k: Int): String {
        if (k < 2 || s.isEmpty() || s.length < k) {
            return s
        }
        var start = -1
        var str = s
        start = startOfKDuplicates(str, k)
        while (start > -1) {
            str = str.substring(0, start) + str.substring(start + k)
            start = startOfKDuplicates(str, k)
        }
        return str
    }

    private fun startOfKDuplicates(s: String, k: Int): Int {
        var start = -1
        var has = true
        for (i in 0..s.length - k) {
            has = true
            for (j in i + 1 until i + k) {
                if (s[j] != s[i]) {
                    has = false
                    break
                }
            }
            if (has) {
                start = i
                return start
            }
        }
        return start
    }

    fun verifyPreorder(preorder: IntArray): Boolean {
        if (preorder.isEmpty() || preorder.size == 1) {
            return true
        }
        return verifyPreorder(preorder, 0, preorder.lastIndex)
    }

    fun verifyPreorder(preorder: IntArray, s: Int, e: Int): Boolean {
        if (s >= e) {
            return true
        }
        val rootVal = preorder[s]
        var rightIndex = -1
        for (i in s + 1..e) {
            if (rootVal < preorder[i]) {
                rightIndex = i
                break
            }
        }
        if (rightIndex == -1) {
            return verifyPreorder(preorder, s + 1, e)
        } else {
            for (i in rightIndex + 1..e) {
                if (rootVal > preorder[i]) {
                    return false
                }
            }
            return verifyPreorder(preorder, s + 1, rightIndex - 1) && verifyPreorder(preorder, rightIndex, e)
        }
    }

    fun sortedArrayToBST(nums: IntArray): TreeNode? {
        return createBST(nums, 0, nums.lastIndex)
    }

    fun createBST(nums: IntArray, s: Int, e: Int): TreeNode? {
        if (s > e) {
            return null
        }
        val mid = s + (e - s) / 2
        val root = TreeNode(nums[mid])
        root.left = createBST(nums, 0, mid - 1)
        root.right = createBST(nums, mid + 1, e)
        return root
    }

    fun isRectangleOverlap(rec1: IntArray, rec2: IntArray): Boolean {//矩形是否重叠
        return !(rec1[3] <= rec2[1] || rec1[1] >= rec2[3] || rec1[0] >= rec2[2] || rec1[2] <= rec2[0])
    }

    fun computeArea(A: Int, B: Int, C: Int, D: Int, E: Int, F: Int, G: Int, H: Int): Int {
        val s1 = (C - A) * (D - B)
        val s2 = (G - E) * (H - F)
        if (A >= G || C <= E || B >= H || D <= F) {
            return s1 + s2
        } else {
            val sX = Math.max(A, E)
            val sY = Math.max(B, F)
            val eX = Math.min(C, G)
            val eY = Math.min(D, H)
            return s1 - (eX - sX) * (eY - sY) + s2
        }
    }

    fun minMoves2(nums: IntArray): Int {
        if (nums.size < 2) {
            return 0
        }
        nums.sort()
        val mid = nums.size / 2
        if (nums.size and 1 == 0) {
            val sum2 = nums.map { if (nums[mid - 1] > it) nums[mid - 1] - it else it - nums[mid - 1] }.sum()
            if (nums[mid] == nums[mid - 1]) {
                return sum2
            }
            val sum1 = nums.map { if (nums[mid] > it) nums[mid] - it else it - nums[mid] }.sum()
            return if (sum1 > sum2) sum2 else sum1
        } else {
            return nums.map { if (nums[mid] > it) nums[mid] - it else it - nums[mid] }.sum()
        }
    }

    fun minMoves(nums: IntArray): Int {
        var count = 0
        val minVal = nums.min() ?: 0
        nums.forEach { count += it - minVal }
        return count
    }

    fun confusingNumber(N: Int): Boolean {
        var num = N
        var newNum = 0
        var rem = 0
        while (num != 0) {
            rem = num % 10
            if (isValidDigit(rem)) {
                newNum *= 10
                if (isDifferentAfterRotated(rem)) {
                    newNum += getRotatedDigit(rem)
                } else {
                    newNum += rem
                }
            } else {
                return false
            }
            num /= 10
        }
        return newNum != N
    }

    private fun isValidDigit(i: Int): Boolean = i == 1 || i == 0 || i == 8 || i == 6 || i == 9
    private fun isDifferentAfterRotated(i: Int): Boolean = i == 6 || i == 9
    fun getRotatedDigit(d: Int): Int = if (d == 9) 6 else 9

    fun insertBits(N: Int, M: Int, i: Int, j: Int): Int {
        var sbN = N.toString(2).reversed()
        println(sbN)
        val sbM = M.toString(2)
        println(sbM)
        while (sbN.length < j + 1) {
            sbN = "${sbN}0"
        }
        println(sbM)
        val ans = StringBuilder()
        for (k in sbN.indices) {
            if (k < i) {
                ans.append(sbN[k])
            } else if (k > i + sbM.length - 1) {
                ans.append(sbN[k])
            } else {
                ans.append(sbM[k - i])
            }
            println(ans.toString())
        }
        return ans.reverse().toString().toInt(2)
    }

    fun insertBits2(N: Int, M: Int, i: Int, j: Int): Int {
        var n = N
        for (k in i..j) {
            n = n and (1.inv() shl i)
        }
        return n or (M shl i)
    }

    fun longestPalindrome(s: String): Int {
        val map = HashMap<Char, Int>()
        s.forEach {
            map.compute(it) { k, v -> if (v == null) 1 else v + 1 }
        }
        var count = 0
        var oddCount = 0
        count += map.values.map {
            if (it % 2 == 0) {
                it
            } else {
                oddCount++
                it - 1
            }
        }.sum()
        if (oddCount > 0) {
            count += 1
        }
        return count
    }

    fun shiftingLetters(S: String, shifts: IntArray): String {
        val ans = StringBuilder(S)
        for (i in shifts.indices) {
            for (j in 0..i) {
                ans[j] = shift(ans[j], shifts[i])
            }
        }
        return ans.toString()
    }

    private fun shift(ch: Char, shift: Int): Char {
        val mod = shift % 26
        val d = ch - 'a'
        if (mod + d <= 26) {
            return ch + mod
        } else {
            return 'a' + (d + mod) % 26 - 1
        }
    }

    fun reverseWords(s: String): String {
        val ans = StringBuilder()
        s.trim().forEach {
            if (it == ' ') {
                if (ans.last() != ' ') {
                    ans.append(it)
                }
            } else {
                ans.append(it)
            }
        }
        var i = 0
        var j = 0
        for (k in ans.indices) {
            if (k == ans.lastIndex) {
                reverse(ans, i, k)
            } else if (ans[k] == ' ') {
                reverse(ans, i, k - 1)
                i = k + 1
            }
        }
        reverse(ans, 0, ans.lastIndex)
        return ans.toString()
    }

    fun reverse(sb: StringBuilder, s: Int, e: Int) {
        if (s >= e || e >= sb.length || s >= sb.length) {
            return
        }
        val m = s + (e - s) / 2
        var tmp = ' '
        for (i in s..m) {
            tmp = sb[i]
            sb[i] = sb[e - i + s]
            sb[e - i + s] = tmp
        }
    }

    fun reverseWords(s: CharArray): Unit {
        var i = 0
        var j = 0
        for (k in s.indices) {
            if (k == s.lastIndex) {
                reverse(s, i, k)
            } else if (s[k] == ' ') {
                reverse(s, i, k - 1)
                i = k + 1
            }
        }
        reverse(s, 0, s.lastIndex)
    }

    fun reverse(sb: CharArray, s: Int, e: Int) {
        if (s >= e || e >= sb.size || s >= sb.size) {
            return
        }
        val m = s + (e - s) / 2
        var tmp = ' '
        for (i in s..m) {
            tmp = sb[i]
            sb[i] = sb[e - i + s]
            sb[e - i + s] = tmp
        }
    }

    fun medianSlidingWindow(nums: IntArray, k: Int): DoubleArray {
        val ans = DoubleArray(k - nums.size + 1)

        return ans
    }

    fun canMeasureWater(x: Int, y: Int, z: Int): Boolean {
        if (x + y < z) {
            return false
        } else if (x == 0 || y == 0) {
            return z == 0 || x + y == z
        } else {
            return z % gcd(x, y) == 0
        }
    }

    private fun gcd(m: Int, n: Int): Int {
        if (n == 0) {
            return m
        } else {
            return gcd(m, n % m)
        }
    }

    fun buildTree(preorder: IntArray, inorder: IntArray): TreeNode? {//105, 面试题07
        if (preorder == null || inorder == null || preorder.size != inorder.size) {
            return null
        }
        return buildTree(preorder, 0, preorder.lastIndex, inorder, 0, inorder.lastIndex)
    }

    private fun buildTree(preorder: IntArray, preS: Int, preE: Int, inorder: IntArray, inS: Int, inE: Int): TreeNode? {
        if (preS < 0 || inS < 0 || preS > preE || inS > inE || preE > preorder.lastIndex || inE > inorder.lastIndex) {
            return null
        }
        val root = TreeNode(preorder[preS])
        var inSplit = -1
        for (i in inS..inE) {
            if (inorder[i] == preorder[preS]) {
                inSplit = i
                break
            }
        }
        if (inSplit != -1) {
            val leftSize = inSplit - inS
            val rightSize = inE - inSplit
            if (leftSize > 0) {
                root.left = buildTree(preorder, preS + 1, preS + leftSize, inorder, inSplit - leftSize, inSplit - 1)
            }
            if (rightSize > 0) {
                root.right = buildTree(preorder, preS + leftSize + 1, preE, inorder, inSplit + 1, inE)
            }
        }
        return root
    }

    fun buildTree2(inorder: IntArray, postorder: IntArray): TreeNode? {
        if (inorder == null || postorder == null || inorder.size != postorder.size) {
            return null
        }
        return buildTree2(inorder, 0, inorder.lastIndex, postorder, 0, postorder.lastIndex)
    }

    fun buildTree2(inorder: IntArray, inS: Int, inE: Int, postorder: IntArray, postS: Int, postE: Int): TreeNode? {
        if (inS > inE || postS > postE) {
            return null
        }
        val root = TreeNode(postorder[postE])
        var inSplit = -1
        for (i in inS..inE) {
            if (inorder[i] == postorder[postE]) {
                inSplit = i
                break
            }
        }
        if (inSplit != -1) {
            val leftSize = inSplit - inS
            val rightSize = inE - inSplit
            if (leftSize > 0) {
                root.left = buildTree2(inorder, inS, inSplit - 1, postorder, postS, postS + leftSize - 1)
            }
            if (rightSize > 0) {
                root.right = buildTree2(inorder, inSplit + 1, inE, postorder, postE - rightSize, postE - 1)
            }
        }
        return root
    }

    fun constructFromPrePost(pre: IntArray, post: IntArray): TreeNode? {//从先序和后序遍历生成二叉树
        if (pre == null || post == null || pre.size != post.size) {
            return null
        }
        return construct(pre, 0, pre.lastIndex, post, 0, post.lastIndex)
    }

    private fun construct(pre: IntArray, preS: Int, preE: Int, post: IntArray, postS: Int, postE: Int): TreeNode? {
        if (preS > preE || postS > postE || pre[preS] != post[postE]) {
            return null
        }
        val root = TreeNode(pre[preS])
        if (preS < preE) {
            var nextHeadIndex = -1
            for (i in postS..postE) {
                if (post[i] == pre[preS + 1]) {
                    nextHeadIndex = i
                    break
                }
            }
            if (nextHeadIndex != -1) {
                val leftSize = nextHeadIndex - postS + 1
                val rightSize = postE - nextHeadIndex - 1
                if (leftSize > 0) {
                    root.left = construct(pre, preS + 1, preS + leftSize, post, postS, postS + leftSize - 1)
                }
                if (rightSize > 0) {
                    root.right = construct(pre, preE - rightSize + 1, preE, post, nextHeadIndex + 1, postE - 1)
                }
            }
        }
        return root
    }

    fun verifyPostorder(postorder: IntArray): Boolean {
        return verifyPostorder(postorder, 0, postorder.lastIndex)
    }

    fun verifyPostorder(postorder: IntArray, s: Int, e: Int): Boolean {
        if (s >= e) {
            return true
        }
        var l = s
        while (postorder[l] < postorder[e]) {
            l++
        }
        val m = l
        while (postorder[l] > postorder[e]) {
            l++
        }
        return l == e && verifyPostorder(postorder, s, m - 1) && verifyPostorder(postorder, m, e - 1)
    }

    fun findTheDistanceValue(arr1: IntArray, arr2: IntArray, d: Int): Int {
        var count = 0
        var flag = false
        for (i in arr1.indices) {
            flag = false
            for (j in arr2.indices) {
                if (Math.abs(arr1[i] - arr2[j]) <= d) {
                    flag = true
                }
            }
            if (!flag) {
                count++
            }
        }
        return count
    }

    fun getKth(lo: Int, hi: Int, k: Int): Int {
        val weights = HashMap<Int, Int>(hi - lo + 1)
        for (i in lo..hi) {
            weights.put(i, getWeight(i))
        }
        weights.entries.sortedBy { it.key }.sortedBy { it.value }.forEach { println("${it.key}, ${it.value}") }
        return weights.entries.sortedBy { it.key }.sortedBy { it.value }.map { it.key }.take(k).last()
    }

    private fun getWeight(N: Int): Int {
        var weight = 0
        var n = N
        while (n != 1) {
            if (n and 1 == 0) {
                n /= 2
            } else {
                n = n * 3 + 1
            }
            weight++
        }
        return weight
    }

    fun maxNumberOfFamilies(n: Int, reservedSeats: Array<IntArray>): Int {
        val matrix = Array(reservedSeats.size) { IntArray(10) { 0 } }
        var x = 0
        var y = 0
        for (i in reservedSeats.indices) {
            x = reservedSeats[i][0] - 1
            y = reservedSeats[i][1] - 1
            matrix[x][y] = -1
        }
        var count = 0
        var a = true
        var b = true
        var c = true
        var d = true
        for (i in matrix.indices) {
            a = true
            b = true
            c = true
            d = true
            for (j in 0..9) {
                if (matrix[i][j] == -1) {
                    if (j in 1..2) {
                        a = false
                    } else if (j in 3..4) {
                        b = false
                    } else if (j in 5..6) {
                        c = false
                    } else if (j in 7..8) {
                        d = false
                    }
                }
            }
            println("$a, $b, $c, $d")
            if (a && b && c && d) {
                count += 2
                continue
            } else if (a && b && c) {
                count++
                continue
            } else if (b && c && d) {
                count++
                continue
            } else if (a && b) {
                continue
                count++
            } else if (b && c) {
                continue
                count++
            } else if (c && d) {
                continue
                count++
            }
        }
        return count
    }

    fun createTargetArray(nums: IntArray, index: IntArray): IntArray {
        val ans = ArrayList<Int>()
        for (i in nums.indices) {
            ans.add(index[i], nums[i])
        }
        return ans.toIntArray()
    }

    fun sumFourDivisors(nums: IntArray): Int {
        var sum = 0
        for (i in nums.indices) {
            if (divisors(nums[i]).size == 4) {
                sum += divisors(nums[i]).sum()
            }
        }
        return sum
    }

    private fun divisors(num: Int): Set<Int> {
        val set = HashSet<Int>()
        for (i in 1..Math.sqrt(num.toDouble()).toInt()) {
            if (num % i == 0) {
                set.add(i)
                set.add(num / i)
            }
        }
        return set
    }

    fun hasValidPath(grid: Array<IntArray>): Boolean {
        val m = grid.size
        val n = grid[0].size
        var x = 0
        var y = 0
        while (x > -1 && y > -1 && x < m && y < n) {
            if (grid[x][y] == 1) {
                if (y < m - 1) {
                    if (grid[x][y + 1] == 3 || grid[x][y + 1] == 4 || grid[x][y] == 6) {
                        y++
                    } else if (grid[x][y + 1] == 5) {
                        y--
                    } else {
                        break
                    }
                } else {
                    break
                }
            } else if (grid[x][y] == 2 || grid[x][y] == 3) {
                if (x < m - 1) {
                    if (grid[x + 1][y] == 5) {
                        x++
                    } else if (grid[x + 1][y] == 6) {
                        x++
                    } else {
                        break
                    }
                } else {
                    break
                }
            } else if (grid[x][y] == 5) {
                if (x > 1) {
                    if (grid[x - 1][y] in 2..4) {
                        x--
                    } else {
                        break
                    }
                } else {
                    break
                }
            } else {
                break
            }
        }
        return x == m - 1 && y == n - 1
    }

    fun longestPrefix(s: String): String {
        val prefixs = ArrayList<String>()
        var size = 1
        var prefix = ""
        while (size++ < s.length) {
            prefix = s.substring(0, size)
            if (prefix == s.substring(s.length - size)) {
                prefixs.add(prefix)
            }
        }
        prefixs.forEach { print(", $it") }
        if (prefixs.isEmpty()) {
            return ""
        } else {
            prefixs.sortBy { it.length }
            return prefixs.last()
        }
    }

    fun longestPrefix2(s: String): String {
        val next = IntArray(s.length)
        var max = 0
        var j = 0
        for (i in 1..s.lastIndex) {
            while (j > 0 && s[i] != s[j]) {
                j = next[j - 1]
            }
            if (s[i] == s[j]) {
                j++
            }
            next[i] = j
        }
        next.forEach { print(", $it") }
        return s.substring(0, next.last())
    }

    fun removeElements(head: ListNode?, `val`: Int): ListNode? {
        val dummy = ListNode(-1)
        dummy.next = head
        var p = dummy
        while (p != null && p.next != null) {
            if (p.next.`val` == `val`) {
                p.next = p.next.next
            } else {
                p = p.next
            }
        }
        return dummy.next
    }

    fun middleNode(head: ListNode?): ListNode? {
        if (head == null) {
            return null
        }
        var size = 0
        var p = head
        while (p != null) {
            p = p.next
            size++
        }
        var half = size / 2
        p = head
        while (half-- > 0) {
            p = p?.next
        }
        return p
    }

    fun oddEvenList(head: ListNode?): ListNode? {//328, 奇数索引节点在前, 偶数索引节点在后
        if (head == null) return null
        var odd: ListNode = head
        var even = head.next
        val evenHead = even
        while (even?.next != null) {
            odd.next = even.next
            odd = odd.next
            even.next = odd.next
            even = even.next
        }
        odd.next = evenHead
        return head
    }

    fun splitListToParts(root: ListNode?, k: Int): Array<ListNode?> {
        var count = 0
        var p = root
        while (p != null) {
            p = p.next
            count++
        }
        val ans = Array<ListNode?>(k) { null }
        return ans
    }

    fun partition(head: ListNode?, x: Int): ListNode? {
        val preXDummy = ListNode(-1)
        val postXDummy = ListNode(-1)
        var p = preXDummy
        var q = postXDummy
        var k = head
        while (k != null) {
//            if (k.`val` < x)
        }
        preXDummy.next
        return preXDummy.next
    }

    fun arrayPairSum(nums: IntArray): Int {//561
        nums.sort()
        return nums.filterIndexed { index, _ -> index and 1 == 0 }.sum()
    }

    fun thirdMax(nums: IntArray): Int {
        val map = HashMap<Int, Int>()
        nums.forEach { map.compute(it) { k, v -> 0 } }
        val ans = map.keys.sortedBy { it }
        ans.forEach { print(", ${it}") }
        if (ans.size >= 3) {
            return ans.drop(map.size - 3).first()
        } else {
            return ans.last()
        }
    }

    fun triangleNumber(nums: IntArray): Int {
        var count = 0
        for (i in 0..nums.size - 3) {
            for (j in i + 1..nums.size - 2) {
                for (k in j + 1..nums.size - 1) {
                    if (isTriangleValid(nums[i], nums[j], nums[k])) {
                        count++
                    }
                }
            }
        }
        return count
    }

    private fun isTriangleValid(a: Int, b: Int, c: Int): Boolean {
        return a * b * c != 0 && Math.abs(a + b) > c && Math.abs(b + c) > a && Math.abs(a + c) > b
    }

    fun shortestToChar(S: String, C: Char): IntArray {
        val ans = IntArray(S.length)
        val set = HashSet<Int>()
        S.forEachIndexed { index, ch ->
            if (ch == C) {
                set.add(index)
            }
        }
        val min = set.min() ?: 0
        val max = set.max() ?: 0
        S.forEachIndexed { index, c ->
            if (set.contains(index)) {
                ans[index] = 0
            } else {
                if (index < min) {
                    ans[index] = min - index
                } else if (index > max) {
                    ans[index] = index - max
                } else {
                    var minDiff = Int.MAX_VALUE
                    set.forEach {
                        if (Math.abs(it - index) < minDiff) {
                            minDiff = Math.abs((it - index))
                        }
                    }
                    ans[index] = minDiff
                }
            }
        }
        return ans
    }

    fun toGoatLatin(S: String): String {
        val ans = StringBuilder()
        val words = S.split(" ")
        words.forEachIndexed { index, s ->
            ans.append(" ")
            ans.append(toGoatLatin(s, index))
        }
        return ans.substring(1)
    }

    private fun toGoatLatin(s: String, index: Int): String {
        val ans = StringBuilder()
        if (startWithVowel(s)) {
            ans.append(s)
        } else {
            ans.append(s.substring(1))
            ans.append(s.first())
        }
        ans.append("ma")
        for (i in 0..index) {
            ans.append('a')
        }
        return ans.toString()
    }

    private fun startWithVowel(s: String): Boolean {
        val ch = s.first()
        return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u'
    }

    fun largeGroupPositions(S: String): List<List<Int>> {
        val ans = ArrayList<List<Int>>()
        var i = 0
        var ch = ' '
        var j = 0
        while (i < S.length) {
            ch = S[i]
            j = i + 1
            while (j < S.length && S[j] == ch) {
                j++
            }
            if (j - i >= 3) {
                val element = ArrayList<Int>(2)
                element.add(i)
                element.add(j - 1)
                ans.add(element)
            }
            i = j
        }
        return ans
    }

    fun removeElement(nums: IntArray, `val`: Int): Int {
        var ans = nums.size
        if (nums.isNotEmpty()) {
            var i = -1
            for (j in nums.indices) {
                if (nums[j] == `val`) {
                    if (i < 0) {
                        i = j
                    }
                    ans--
                } else {
                    if (i > -1) {
                        nums[i++] = nums[j]
                    }
                }
            }
        }
        return ans
    }

    fun minSubArrayLen(s: Int, nums: IntArray): Int {
        var sum = 0
        for (i in 1..nums.size) {
            sum = 0
            for (j in 0 until i - 1) {
                sum += nums[j]
            }
            for (k in i until nums.size) {
                sum += nums[k]
                if (sum >= s) {
                    return i
                }
                sum -= nums[k - i + 1]
            }
        }
        return 0
    }

    fun moveZeroes(nums: IntArray): Unit {
        var i = 0
        var j = nums.lastIndex
        while (i < j) {
            if (nums[i] != 0) {
                i++
            } else {
                for (k in i + 1..j) {
                    nums[k - 1] = nums[k]
                }
                nums[j] = 0
                j--
            }
        }
    }

    fun findDiagonalOrder(matrix: Array<IntArray>): IntArray {
        val m = matrix.size
        val n = matrix[0].size
        val ans = IntArray(m * n)
        var k = 0
        val min = if (m > n) n else m
        var up = true
        for (i in 0 until min) {
            for (j in 0..i) {
                if (up) {
                    ans[k++] = matrix[i - j][j]
                } else {
                    ans[k++] = matrix[j][i - j]
                }
            }
            up = !up
        }
        if (m != n) {
            if (m > n) {
                for (i in 0 until m - n) {
                    for (j in 0 until n) {
                        if (up) {
                            ans[k++] = matrix[n + i - j][j]
                        } else {
                            ans[k++] = matrix[n + j][n - j]
                        }
                    }
                    up = !up
                }
            } else {
                for (i in 0 until n - m) {
                    for (j in 0 until m) {
                        if (up) {
                            ans[k++] = matrix[m + i - j][j]
                        } else {
                            ans[k++] = matrix[m + j][m - j]
                        }
                    }
                    up = !up
                }
            }
        }

        for (i in 0 until min - 1) {
            for (j in min - 2 - i..0) {
                if (up) {
                    ans[k++] = matrix[m - 1][j + 1]
                } else {
                    ans[k++] = matrix[m - i + j - 1][min - j]
                }
            }
            up = !up
        }

        return ans
    }

    fun reverseOnlyLetters(S: String): String {
        val ans = StringBuilder(S)
        var i = 0
        var j = ans.lastIndex
        var tmp = ' '
        while (i < j) {
            if (ans[i].isLetter() && ans[j].isLetter()) {
                tmp = ans[i]
                ans[i] = ans[j]
                ans[j] = tmp
                i++
                j++
            } else if (!ans[i].isLetter() && !ans[j].isLetter()) {
                i++
                j--
            } else if (ans[i].isLetter()) {
                j--
            } else {
                i++
            }
        }
        return ans.toString()
    }

    fun CheckPermutation(s1: String, s2: String): Boolean {
        if (s1 == s2) {
            return true
        }
        if (s1.length != s2.length) {
            return false
        }
        val map1 = HashMap<Char, Int>()
        s1.forEach { map1.compute(it) { k, v -> if (v == null) 1 else v + 1 } }
        val map2 = HashMap<Char, Int>()
        s2.forEach { map2.compute(it) { k, v -> if (v == null) 1 else v + 1 } }
        if (map1.size != map2.size) {
            return false
        }
        var res = true
        map1.forEach { t, u ->
            if (!map2.containsKey(t) || map2[t] != u) {
                res = false
                return@forEach
            }
        }
        return res
    }

    fun replaceSpaces(S: String, length: Int): String {
        val ans = S.toCharArray()
        var i = length - 1
        var j = ans.lastIndex
        while (i > 0) {
            if (ans[i] != ' ') {
                ans[j--] = ans[i--]
            } else {
                i--
                ans[j--] = '0'
                ans[j--] = '2'
                ans[j--] = '%'
            }
        }
        return String(ans)
    }

    fun hasGroupsSizeX(deck: IntArray): Boolean {
        val map = HashMap<Int, Int>()
        deck.forEach { map.compute(it) { _, v -> if (v == null) 1 else v + 1 } }
        map.forEach { t, u -> println("$t = $u") }
        val min = map.values.min() ?: 0
        val max = map.values.max() ?: 0
        val d = gcd(min, max)
        if (d < 2) {
            return false
        }
        var ans = true
        map.values.forEach {
            if (it % d != 0) {
                ans = false
                return@forEach
            }
        }
        return ans
    }

    fun lastRemaining(n: Int, m: Int): Int {
        var ans = 0
        var i = 2
        while (i != n + 1) {
            ans = (m + ans) % i
            i++
        }
        return ans
    }

    fun gameOfLife(board: Array<IntArray>): Unit {
        val ans = Array(board.size) { IntArray(board[0].size) }
        for (i in board.indices) {
            for (j in board[i].indices) {
                ans[i][j] = compute(board, i, j)
            }
        }
        for (i in ans.indices) {
            for (j in ans[i].indices) {
                board[i][j] = ans[i][j]
            }
        }
    }

    private fun compute(board: Array<IntArray>, x: Int, y: Int): Int {
        var count = 0
        val src = board[x][y]
        var preX = x - 1
        var preY = y - 1
        var nextX = x + 1
        var nextY = y + 1
        if (preX > -1) {
            if (preY > -1 && board[preX][preY] == 1) {
                count++
            }
            if (board[preX][y] == 1) {
                count++
            }
            if (nextY < board[0].size && board[preX][nextY] == 1) {
                count++
            }
        }
        if (nextX < board.size) {
            if (preY > -1 && board[nextX][preY] == 1) {
                count++
            }
            if (board[nextX][y] == 1) {
                count++
            }
            if (nextY < board[0].size && board[nextX][nextY] == 1) {
                count++
            }
        }
        if (preY > -1 && board[x][preY] == 1) {
            count++
        }
        if (nextY < board[0].size && board[x][nextY] == 1) {
            count++
        }
        return if (src == 1) {
            if (count < 2) 0 else if (count in 2..3) 1 else if (count > 3) 0 else 0
        } else {
            if (count == 3) 1 else 0
        }
    }

    fun titleToNumber(s: String): Int {
        var ans = 0
        s.forEach {
            ans *= 26
            ans += it - 'A' + 1
        }
        return ans
    }

    fun myAtoi(str: String): Int {
        var ans = 0
        var s = str.trim()
        var negative = false
        if (s.isNotEmpty()) {
            var firstDigitIndex = 0
            for (i in s.indices) {
                if (s[i].isDigit()) {
                    firstDigitIndex = i
                    break
                }
            }
            if (firstDigitIndex > 1) {
                return 0
            }
            val firstChar = s.first()
            if (firstChar == '-') {
                negative = true
                s = s.substring(1)
            } else if (firstChar == '+') {
                negative = false
                s = s.substring(1)
            } else {
                negative = false
            }
            for (i in s.indices) {
                if (s[i].isDigit()) {
                    if (ans > Int.MAX_VALUE / 10 || ans == Int.MAX_VALUE / 10 && s[i] - '0' > 7) {
                        if (negative) {
                            return Int.MIN_VALUE
                        } else {
                            return Int.MAX_VALUE
                        }
                    } else {
                        ans *= 10
                        ans += s[i] - '0'
                    }
                } else if (s[i] == '+' || s[i] == '-') {
                    break
                } else {
                    break
                }
            }
        }
        return if (negative) -ans else ans
    }

    //dp[i] = max(dp[i - 1], nums[i] + dp[i - 2])
    fun rob(nums: IntArray): Int {//loop
        var tmp = 0
        var dp1 = 0
        var dp2 = 0
        for (i in 0..nums.lastIndex) {
            if (i == 0) {
                dp1 = nums[0]
            } else if (i == 1) {
                dp2 = Math.max(dp1, nums[1])
            } else {
                tmp = Math.max(dp2, nums[i] + dp1)
                dp1 = dp2
                dp2 = tmp
            }
        }
        return Math.max(dp1, dp2)
        // return rob(nums, nums.lastIndex)
    }

    // private fun rob(nums: IntArray, i: Int): Int{//recursive
    //     if(i == 0){
    //         return nums[0]
    //     } else if(i == 1){
    //         return Math.max(nums[0], nums[1])
    //     } else {
    //         return Math.max(rob(nums, i - 1), rob(nums, i - 2) + nums[i])
    //     }
    // }

    fun rob2(nums: IntArray): Int {
        if (nums.isEmpty()) {
            return 0
        }
        if (nums.size == 1) {
            return nums[0]
        }
        if (nums.size == 2) {
            return Math.max(nums[0], nums[1])
        }
        var tmp = 0
        var dp1 = 0
        var dp2 = 0
        for (i in 0..nums.lastIndex - 1) {
            if (i == 0) {
                dp1 = nums[0]
            } else if (i == 1) {
                dp2 = Math.max(dp1, nums[1])
            } else {
                tmp = Math.max(dp2, nums[i] + dp1)
                dp1 = dp2
                dp2 = tmp
            }
        }
        val p1 = Math.max(dp1, dp2)
        tmp = 0
        dp1 = 0
        dp2 = 0
        for (i in 1..nums.lastIndex) {
            if (i == 0) {
                dp1 = nums[0]
            } else if (i == 1) {
                dp2 = Math.max(dp1, nums[1])
            } else {
                tmp = Math.max(dp2, nums[i] + dp1)
                dp1 = dp2
                dp2 = tmp
            }
        }
        val p2 = Math.max(dp1, dp2)
        return Math.max(p1, p2)
    }

    fun rob(root: TreeNode?): Int {
        if (root == null) {
            return 0
        }
        var ans = root.`val`
        if (root.left != null) {
            ans += rob(root.left.left) + rob(root.left.right)
        }
        if (root.right != null) {
            ans += rob(root.right.left) + rob(root.right.right)
        }
        return Math.max(ans, rob(root.left) + rob(root.right))
    }

//    fun rob(root: TreeNode?): Int {
//        if (root == null) {
//            return 0
//        } else {
//            if (root.left == null && root.right == null) {
//                return root.`val`
//            } else if (root.left != null && root.right != null) {
//                return Math.max(
//                    root.`val` + rob(root.left.left) + rob(root.left.right) + rob(root.right.left) + rob(root.right.right),
//                    rob(root.left) + rob(root.right)
//                )
//            } else if (root.left != null) {
//                return Math.max(root.`val` + rob(root.left.left) + rob(root.left.right), rob(root.left))
//            } else {
//                return Math.max(root.`val` + rob(root.right.left) + rob(root.right.right), rob(root.right))
//            }
//        }
//    }


    fun maxDepthAfterSplit(seq: String): IntArray {
        return IntArray(0)
    }

    fun nthUglyNumber(n: Int): Int {
        val uglies = IntArray(1690)
        uglies[0] = 1
        var ugly = 0
        var i2 = 0
        var i3 = 0
        var i5 = 0
        for (i in 1 until 1690) {
            ugly = Math.min(Math.min(uglies[i2] * 2, uglies[i3] * 3), uglies[i5] * 5)
            uglies[i] = ugly
            if (ugly == uglies[i2] * 2) i2++
            if (ugly == uglies[i3] * 3) i3++
            if (ugly == uglies[i5] * 5) i5++
        }
        return uglies[n - 1]
    }

    fun rotate(matrix: Array<IntArray>): Unit {//顺时针旋转90 度
        val n = matrix.size
        var tmp = 0
        for (i in 0 until n / 2) {//y
            for (j in i until n - 1 - i) {//x
                tmp = matrix[j][i]
                matrix[j][i] = matrix[n - i - 1][j]
                matrix[n - i - 1][j] = matrix[n - j - 1][n - i - 1]
                matrix[n - j - 1][n - i - 1] = matrix[i][n - j - 1]
                matrix[i][n - j - 1] = tmp
            }
        }
    }

    fun findMissingRanges(nums: IntArray, lower: Int, upper: Int): List<String> {
        val ans = mutableListOf<String>()
        if (nums.isNotEmpty()) {
            if (lower < nums.first()) {
                if (nums.first() - lower.toLong() > 1L) {//in case result of minus greater than Int.MAX_VALUE
                    ans.add("$lower->${nums.first() - 1}")
                } else {
                    ans.add("$lower")
                }
            }
            for (i in 1..nums.lastIndex) {
                if (nums[i].toLong() - nums[i - 1] > 1) {
                    if (nums[i].toLong() - nums[i - 1] > 2) {//in case result of minus greater than Int.MAX_VALUE
                        ans.add("${nums[i - 1] + 1}->${nums[i] - 1}")
                    } else {
                        ans.add("${nums[i] - 1}")
                    }
                } else {
                    continue
                }
            }
            if (upper > nums.last()) {
                if (upper.toLong() - nums.last() > 1) {//in case result of minus greater than Int.MAX_VALUE
                    ans.add("${nums.last() + 1}->$upper")
                } else {
                    ans.add("$upper")
                }
            }
        } else {
            if (upper.toLong() - lower > 0) {//in case result of minus greater than Int.MAX_VALUE
                ans.add("$lower->$upper")
            } else {
                ans.add("$lower")
            }
        }
        return ans
    }

    //lowest common ancestor of a binary search tree
    fun lowestCommonAncestor(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
        if (root == null || p == null || q == null) {
            return null
        }
        var t = root
        while (t != null && t != p && t != q) {
            if (t?.`val` < p?.`val` && t?.`val` < q?.`val`) {
                t = t.right
            } else if (t?.`val` > p?.`val` && t?.`val` > q?.`val`) {
                t = t.left
            } else {
                break
            }
        }
        return t
    }

    fun countElements(arr: IntArray): Int {
        var count = 0
        if (arr.size > 1) {
            val set = mutableSetOf<Int>()
            arr.forEach {
                if (!set.contains(it)) {
                    set.add(it)
                }
            }
            for (i in arr.indices) {
                if (set.contains(arr[i] + 1)) {
                    count++
                }
            }
        }
        return count
    }

    fun movingCount(m: Int, n: Int, k: Int): Int {
        if (k == 0) {
            return 1
        }
        var ans = 0
        val matrix = Array(m) { IntArray(n) { 0 } }
        for (i in 0 until m) {
            for (j in 0 until n) {
                if (i == 0 && j == 0) {
                    matrix[i][j] = 1
                    ans = 1
                } else if (sum(i) + sum(j) > k) {
                    continue
                } else {
                    if (i > 0) {
                        matrix[i][j] = matrix[i][j] or matrix[i - 1][j]
                    }
                    if (j > 0) {
                        matrix[i][j] = matrix[i][j] or matrix[i][j - 1]
                    }
                    ans += matrix[i][j]
                }
            }
        }
        return ans
    }

    private fun sum(num: Int): Int {
        var ans = 0
        var n = num
        while (n != 0) {
            ans += n % 10
            n /= 10
        }
        return ans
    }

    fun reversePrint(head: ListNode?): IntArray {
        var p = head
        var count = 0
        while (p != null) {
            p = p.next
            count++
        }
        val ans = IntArray(count)
        p = head
        count = 0
        while (p != null) {
            ans[count++] = p.`val`
            p = p.next
        }
        ans.reverse()
        return ans
    }

    fun middleNode2(head: ListNode?): ListNode? {
        var p = head
        var q = head
        while (q != null && q.next != null) {
            p = p?.next
            q = q.next.next
        }
        if (q != null && q.next != null) {
            p = p?.next
        }
        return p
    }

    fun generateParenthesis(n: Int): List<String> {//22
        val ans = mutableListOf<String>()
        backtrack(ans, StringBuilder(), 0, 0, n)
        return ans
    }

    private fun backtrack(ans: MutableList<String>, sb: StringBuilder, open: Int, close: Int, max: Int) {
        if (sb.length == max * 2) {
            ans.add(sb.toString())
            return
        }
        if (open < max) {
            sb.append('(')
            backtrack(ans, sb, open + 1, close, max)
            sb.deleteCharAt(sb.lastIndex)
        }
        if (close < open) {
            sb.append(')')
            backtrack(ans, sb, open, close + 1, max)
            sb.deleteCharAt(sb.lastIndex)
        }
    }

    fun isStrobogrammatic(num: String): Boolean {//246
        for (i in 0 until (num.length + 1) / 2) {
            if (isRotatable(num[i]) && isRotatable(num[num.length - 1 - i]) &&
                isSameAfterRotated(num[num.length - 1 - i], num[i])
            ) {
                continue
            } else {
                return false
            }
        }
        return true
    }

    private fun isSameAfterRotated(ch1: Char, ch2: Char): Boolean {
        return ch1 == ch2 && ch2 == '1' || ch1 == ch2 && ch2 == '0' || ch1 == ch2 && ch2 == '8' || ch1 == '9' && ch2 == '6' || ch1 == '6' && ch2 == '9'
    }

    private fun isRotatable(ch: Char): Boolean = ch == '0' || ch == '1' || ch == '6' || ch == '8' || ch == '9'

    val map1 = mapOf('0' to '0')
    val map2 = mapOf('1' to '1', '8' to '8')
    val map3 = mapOf('6' to '9', '9' to '6')

    fun findStrobogrammatic(n: Int): List<String> {//247
        val ans = mutableListOf<String>()
        findStrobogrammatic(n, "", "", ans, n)
        ans.sort()
        return ans
    }

    private fun findStrobogrammatic(n: Int, left: String, right: String, ans: MutableList<String>, max: Int) {
        if (n < 0) {
            //nothing
        } else if (n == 0) {
            ans.add("$left$right")
        } else if (n == 1) {
            map1.keys.forEach {
                ans.add("$left$it$right")
            }
            map2.keys.forEach {
                ans.add("$left$it$right")
            }
        } else {
            if (n != 2 && n != 3) {// in case of "01..." & "...10"
                map1.keys.forEach {
                    findStrobogrammatic(n - 2, "$it$left", "$right$it", ans, max)
                }
            }
            map2.keys.forEach {
                findStrobogrammatic(n - 2, "$it$left", "$right$it", ans, max)
            }
            map3.keys.forEach {
                findStrobogrammatic(n - 2, "$it$left", "$right${map3[it]}", ans, max)
            }
        }
    }

    fun backspaceCompare(S: String, T: String): Boolean {//844
        return get(S) == get(T)
    }

    private fun get(s: String): String {
        val ans = StringBuilder()
        s.forEach {
            if (it == '#') {
                if (ans.isNotEmpty()) {
                    ans.deleteCharAt(ans.lastIndex)
                }
            } else {
                ans.append(it)
            }
        }
        return ans.toString()
    }

    fun addDigits(num: Int): Int {//258
        var n = num
        var tmp = 0
        while (n > 9) {
            tmp = 0
            while (n > 0) {
                tmp += n % 10
                n /= 10
            }
            n = tmp
        }
        return n
    }

    fun intersection(nums1: IntArray, nums2: IntArray): IntArray {//349
        return nums1.distinct().intersect(nums2.distinct()).toIntArray()
    }

    fun intersect(nums1: IntArray, nums2: IntArray): IntArray {//350
        nums1.sort()
        nums2.sort()
        val ans = mutableListOf<Int>()
        var i = 0
        var j = 0
        while (i < nums1.size && j < nums2.size) {
            if (nums1[i] > nums2[j]) {
                j++
            } else if (nums1[i] < nums2[j]) {
                i++
            } else {
                ans.add(nums1[i])
                i++
                j++
            }
        }
        return ans.toIntArray()
    }

    fun arraysIntersection(arr1: IntArray, arr2: IntArray, arr3: IntArray): List<Int> {//1213
        val ans = mutableListOf<Int>()
        val intersetList = mutableListOf<Int>()
        var i = 0
        var j = 0
        while (i < arr1.size && j < arr2.size) {
            if (arr1[i] > arr2[j]) {
                j++
            } else if (arr1[i] < arr2[j]) {
                i++
            } else {
                intersetList.add(arr1[i])
                i++
                j++
            }
        }
        if (intersetList.isNotEmpty()) {
            i = 0
            j = 0
            while (i < intersetList.size && j < arr3.size) {
                if (intersetList[i] > arr3[j]) {
                    j++
                } else if (intersetList[i] < arr3[j]) {
                    i++
                } else {
                    ans.add(intersetList[i])
                    i++
                    j++
                }
            }
        }
        return ans
    }

    fun commonChars(A: Array<String>): List<String> {//1002
        val chCounts = Array(A.size) { IntArray(26) }
        val ans = mutableListOf<String>()
        A.forEachIndexed { index, s -> count(chCounts[index], s) }
        for (i in 0..25) {
            var min = Int.MAX_VALUE
            for (j in A.indices) {
                if (chCounts[j][i] == 0) {
                    min = 0
                    break
                } else {
                    if (min > chCounts[j][i]) {
                        min = chCounts[j][i]
                    }
                }
            }
            while (min-- > 0) {
                ans.add(('a' + i).toString())
            }
        }
        return ans
    }

    private fun count(array: IntArray, s: String) {
        s.forEach { array[it - 'a']++ }
    }

    fun numberOfSubstrings(s: String): Int {//1358
        var ans = 0
        val counts = IntArray(3) { 0 }
        val size = s.length
        var r = -1
        var i = 0
        while (i < size) {
            while (r < size && !(counts[0] >= 1 && counts[1] >= 1 && counts[2] >= 1)) {
                if (++r == size) {
                    break
                }
                counts[s[r] - 'a']++
            }
            ans += size - r
            counts[s[i++] - 'a']--
        }
        return ans
    }

    fun compareVersion(version1: String, version2: String): Int {//165
        if (version1 == version2) {
            return 0
        }
        val dotCount1 = version1.count { it == '.' }
        val dotCount2 = version2.count { it == '.' }
        val s = (if (dotCount1 > dotCount2) dotCount1 else dotCount2) + 1
        val vNum1 = IntArray(s) { 0 }
        version1.split(".").forEachIndexed { index, s -> vNum1[index] = s.toInt() }
        val vNum2 = IntArray(s) { 0 }
        version2.split(".").forEachIndexed { index, s -> vNum2[index] = s.toInt() }
        for (i in 0 until s) {
            if (vNum1[i] > vNum2[i]) {
                return 1
            } else if (vNum1[i] < vNum2[i]) {
                return -1
            }
        }
        return 0
    }
}
