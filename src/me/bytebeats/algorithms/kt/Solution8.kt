package me.bytebeats.algorithms.kt

import me.bytebeats.algorithms.kt.design.CustomFunction
import me.bytebeats.algorithms.meta.TreeNode

class Solution8 {

    fun pathSum(root: TreeNode?, sum: Int): Int {//面试题04.12
        if (root == null) {
            return 0
        }
        return pathSum(root, 0, sum) + pathSum(root?.left, sum) + pathSum(root?.right, sum)
    }

    private fun pathSum(root: TreeNode?, subSum: Int, sum: Int): Int {
        if (root == null) {
            return 0
        }
        var count = 0
        val nextSum = subSum + root.`val`
        if (nextSum == sum) {
            count++
        }
        count += pathSum(root.left, nextSum, sum)
        count += pathSum(root.right, nextSum, sum)
        return count
    }

    fun twoCitySchedCost(costs: Array<IntArray>): Int {//1029
        var ans = 0
        val half = costs.size / 2
        costs.sortBy { it[0] - it[1] }
        for (i in costs.indices) {
            if (i < half) {
                ans += costs[i][0]
            } else {
                ans += costs[i][1]
            }
        }
        return ans
    }

    fun reverseString(s: CharArray): Unit {//344
        var left = 0
        var right = s.lastIndex
        var ch = ' '
        while (left < right) {
            ch = s[left]
            s[left] = s[right]
            s[right] = ch
            left++
            right--
        }
    }

    fun reverseVowels(s: String): String {//557
        val chArr = s.toCharArray()
        var i = 0
        var j = s.lastIndex
        var ch = ' '
        while (i < j) {
            while (i < j && !isVowel(chArr[i])) {
                i++
            }
            while (i < j && !isVowel(chArr[j])) {
                j--
            }
            if (i < j) {
                ch = chArr[i]
                chArr[i] = chArr[j]
                chArr[j] = ch
                i++
                j--
            }
        }
        return String(chArr)
    }

    private fun isVowel(ch: Char): Boolean {
        return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u' || ch == 'A' || ch == 'E' || ch == 'I' || ch == 'O' || ch == 'U'
    }

    fun removeVowels(S: String): String {//1119
        val ans = StringBuilder()
        for (c in S) {
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                continue
            } else {
                ans.append(c)
            }
        }
        return ans.toString()
    }

    fun reverseStr(s: String, k: Int): String {//541
        val ans = s.toCharArray()
        var left = 0
        while (left < s.length) {
            if (s.length - left <= k) {
                reverse(ans, left, s.length - 1)
            } else {
                reverse(ans, left, left + k - 1)
            }
            left += 2 * k
        }
        return String(ans)
    }

    private fun reverse(charArray: CharArray, left: Int, right: Int) {
        var i = left
        var j = right
        var ch = ' '
        while (i < j) {
            ch = charArray[i]
            charArray[i] = charArray[j]
            charArray[j] = ch
            i++
            j--
        }
    }

    fun countLetters(S: String): Int {//1180
        var ans = 0
        var i = 0
        var j = 0
        while (i < S.length) {
            j = i
            while (j < S.length && S[j] == S[i]) {
                j++
            }
            ans += count(j - i)
            i = j
        }
        return ans
    }

    private fun count(n: Int): Int = n * (n + 1) / 2

    fun customSortString(S: String, T: String): String {//791
        val map = mutableMapOf<Char, Int>()
        for (i in S.indices) {
            map[S[i]] = i
        }
        return String(T.toList().sortedBy { map[it] ?: -1 }.toCharArray())
    }

    fun complexNumberMultiply(a: String, b: String): String {//537
        val ans = StringBuilder()
        val cn1 = extract(a)
        val cn2 = extract(b)
        val r = cn1[0] * cn2[0] - cn1[1] * cn2[1]
        val v = cn1[1] * cn2[0] + cn1[0] * cn2[1]
        ans.append(r)
        ans.append('+')
        ans.append(v)
        ans.append('i')
        return ans.toString()
    }

    private fun extract(cn: String): List<Int> = cn.split("+").map {
        if (it.endsWith('i')) {
            it.substring(0, it.lastIndex)
        } else {
            it
        }
    }.map { it.toInt() }

    fun maximumSwap(num: Int): Int {//670
        val ans = num.toString().toCharArray()
        var max = '0'
        var tmp = '0'
        for (i in 0 until ans.lastIndex) {
            max = ans.drop(i + 1).max() ?: '0'
            if (ans[i] < max) {
                for (j in ans.lastIndex downTo i + 1) {
                    if (ans[j] == max) {
                        tmp = ans[i]
                        ans[i] = ans[j]
                        ans[j] = tmp
                        return String(ans).toInt()
                    }
                }
            }
        }
        return num
    }

    fun longestConsecutive(nums: IntArray): Int {//128
        val set = mutableSetOf<Int>()
        for (num in nums) {
            set.add(num)
        }
        var ans = 0
        var curNum = 0
        var curStreak = 0
        for (num in set) {
            if (!set.contains(num - 1)) {
                curNum = num
                curStreak = 1
                while (set.contains(curNum + 1)) {
                    curNum++
                    curStreak++
                }
                ans = ans.coerceAtLeast(curStreak)
            }
        }
        return ans
    }

    fun reconstructQueue(people: Array<IntArray>): Array<IntArray> {//406
        val queue = people.sortedWith(Comparator { o1, o2 ->
            if (o1[0] == o2[0])
                o1[1] - o2[1]
            else
                o2[0] - o1[0]
        })
        val list = mutableListOf<IntArray>()
        for (q in queue) {
            list.add(q[1], q)
        }
        return list.toTypedArray()
    }

    fun findSolution(customfunction: CustomFunction, z: Int): List<List<Int>> {//1237
        val ans = mutableListOf<List<Int>>()
        var i = 1
        var j = 1000
        var res = 0
        while (i <= z && j >= 1) {
            res = customfunction.f(i, j)
            if (res < z) {
                i++
            } else if (res > z) {
                j--
            } else {
                ans.add(listOf(i, j))
                i++
                j++
            }
        }
        return ans
    }

    fun shuffle(nums: IntArray, n: Int): IntArray {//5428
        val list = mutableListOf<Int>()
        for (i in 0 until n) {
            list.add(nums[i])
            list.add(nums[i + n])
        }
        return list.toIntArray()
    }

    fun getStrongest(arr: IntArray, k: Int): IntArray {//5429
        val ans = IntArray(k)
        arr.sort()
        val median = arr[(arr.size - 1) / 2]
        val sorted = arr.reversed().sortedByDescending { Math.abs(it - median) }
        sorted.forEach { println("$it") }
        for (i in 0 until k) {
            ans[i] = sorted[i]
        }
        return ans
    }

    fun change(amount: Int, coins: IntArray): Int {//512
        val dp = IntArray(amount + 1)
        dp[0] = 1
        for (coin in coins) {
            for (i in coin..amount) {
                dp[i] += dp[i - coin]
            }
        }
        return dp[amount]
    }

    fun equationsPossible(equations: Array<String>): Boolean {//990
        val parent = IntArray(26) { it }
        for (e in equations) {
            if (e[1] == '=') {
                union(parent, e[0] - 'a', e[3] - 'a')
            }
        }
        for (e in equations) {
            if (e[1] == '!') {
                if (find(parent, e[0] - 'a') == find(parent, e[3] - 'a')) {
                    return false
                }
            }
        }
        return true
    }

    private fun union(parent: IntArray, index1: Int, index2: Int) {
        parent[find(parent, index1)] = parent[find(parent, index2)]
    }

    private fun find(parent: IntArray, index: Int): Int {
        var i = index
        while (parent[i] != i) {
            parent[i] = parent[parent[i]]
            i = parent[i]
        }
        return i
    }

    fun isPowerOfTwo(n: Int): Boolean {//231
        if (n < 1) {
            return false
        }
        var num = n
        var has1 = false
        while (num > 1) {
            if (has1) {
                return false
            }
            if (num and 1 == 1) {
                has1 = true
            }
            num = num shr 1
        }
        return true
    }

    fun translateNum(num: Int): Int {//面试题46
        var ans = 1
        val src = num.toString()
        var p = 0
        var q = 0
        var pre = ""
        for (i in src.indices) {
            p = q
            q = ans
            ans = 0
            ans += q
            if (i == 0) {
                continue
            }
            pre = src.substring(i - 1, i + 1)
            if (pre <= "25" && pre >= "10") {
                ans += p
            }
        }
        return ans
    }

    fun isSubsequence(s: String, t: String): Boolean {//392
        var count = 0
        var i = 0
        var j = 0
        while (i < s.length && j < t.length) {
            if (s[i] == t[j]) {
                count++
                i++
                j++
            } else {
                j++
            }
        }
        return count == s.length
    }

    fun getLonelyNodes(root: TreeNode?): List<Int> {//1469
        val ans = mutableListOf<Int>()
        dfs(root, ans)
        return ans
    }

    fun dfs(node: TreeNode?, ans: MutableList<Int>) {
        if (node == null || node.left == null && node.right == null) {
            return
        }
        if (node.left != null && node.right == null) {
            ans.add(node.left.`val`)
            dfs(node.left, ans)
        } else if (node.left == null && node.right != null) {
            ans.add(node.right.`val`)
            dfs(node.right, ans)
        } else {
            dfs(node.left, ans)
            dfs(node.right, ans)
        }
    }

}