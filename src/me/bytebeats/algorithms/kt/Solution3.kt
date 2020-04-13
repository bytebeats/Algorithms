package me.bytebeats.algorithms.kt

import com.sun.org.apache.xpath.internal.operations.Bool
import me.bytebeats.algorithms.meta.ListNode

class Solution3 {
    fun removeDuplicates(nums: IntArray): Int {
        var size = nums.size
        var k = 0
        var j = 0
        var num = 0
        while (k < size) {
            num = nums[k]
            j = k + 1
            while (j < size && num == nums[j]) {
                j++
            }
            if (j - k > 1) {
                for (h in j until size) {
                    nums[h - j + k + 2] = nums[h]
                }
                size -= (j - k - 2)
                k += 2
            } else {
                k = j
            }
        }
        return size
    }

    fun countLargestGroup(n: Int): Int {
        val map = HashMap<Int, ArrayList<Int>>()
        for (i in 1..n) {
            map.compute(getSum(i)) { k, v ->
                if (v == null) {
                    val e = ArrayList<Int>()
                    e.add(i)
                    e
                } else {
                    v.add(i)
                    v
                }
            }
        }
        val max = map.values.map { it.size }.max()
        return map.values.filter { it.size == max }.count()
    }

    private fun getSum(num: Int): Int {
        var sum = 0
        var n = num
        while (n != 0) {
            sum += n % 10
            n /= 10
        }
        return sum
    }

    fun canConstruct(s: String, k: Int): Boolean {
        if (s.length < k) {
            return false
        }
        if (s.length == k) {
            return true
        }
        val counts = IntArray(26)
        for (c in s) {
            counts[c - 'a']++
        }
        var oddCount = 0
        for (ch in counts) {
            if (ch > 0) {
                if (ch and 1 == 1) {
                    oddCount++
                }
            }
        }
        return oddCount <= k
    }

    fun checkOverlap(radius: Int, x_center: Int, y_center: Int, x1: Int, y1: Int, x2: Int, y2: Int): Boolean {
        val x = (x1.toDouble() + x2) / 2
        val y = (y1.toDouble() + y2) / 2
        val p = Math.sqrt(Math.pow((x1.toDouble() - x2) / 2, 2.0) + Math.pow((y1.toDouble() - y2) / 2, 2.0))
        val q = Math.sqrt(Math.pow((x - x_center), 2.0) + Math.pow((y - y_center), 2.0))
        return q - p <= radius
    }

    fun groupAnagrams(strs: Array<String>): List<List<String>> {
        val map = mutableMapOf<String, MutableList<String>>()
        strs.forEach {
            val key = key(it)
            if (map.containsKey(key)) {
                map[key]?.add(it)
            } else {
                val list = arrayListOf<String>()
                list.add(it)
                map[key] = list
            }
        }
        return map.values.toList()
    }

    private fun key(str: String): String {
        val chars = IntArray(26)
        str.forEach { chars[it - 'a'] += 1 }
        val ans = StringBuilder()
        chars.forEach {
            ans.append(it)
            ans.append(',')
        }
        return ans.toString()
    }

    fun containsNearbyAlmostDuplicate(nums: IntArray, k: Int, t: Int): Boolean {
        if (nums.size == 0 || k < 1) {
            return false
        }
        var count = k
        while (count > 0) {
            for (i in count until nums.size) {
                if (Math.abs(nums[i] - nums[i - count]) <= t) {
                    return true
                }
            }
            count--
        }
        return false
    }

    fun containsNearbyAlmostDuplicate1(nums: IntArray, k: Int, t: Int): Boolean {
        val set = sortedSetOf<Int>()
        for (i in nums.indices) {
            val s = set.ceiling(nums[i])
            if (s != null && s <= nums[i] + t) {
                return true
            }
            val g = set.floor(nums[i])
            if (g != null && nums[i] <= g + t) {
                return true
            }
            set.add(nums[i])
            if (set.size > k) {
                set.remove(nums[i - k])
            }
        }
        return false
    }

    fun deleteNode(head: ListNode?, `val`: Int): ListNode? {// 面试题18,  删除链表节点
        if (head == null) {
            return null
        }
        if (head.`val` == `val`) {
            return head.next
        }
        var pre = head
        var cur = head.next
        while (cur != null && cur.`val` != `val`) {
            pre = cur
            cur = cur.next
        }
        pre?.next = cur?.next
        return head
    }

    fun rotateRight(head: ListNode?, k: Int): ListNode? {//61
        var size = 0
        var p = head
        while (p != null) {
            size++
            p = p.next
        }
        if (size == 0 || k % size == 0) {
            return head
        }
        var d = size - k % size
        p = head
        var pre: ListNode? = null
        while (d-- > 0) {
            pre = p
            p = p?.next
        }
        pre?.next = null
        pre = p
        while (p != null && p.next != null) {
            p = p.next
        }
        p?.next = head
        return pre
    }

    fun rotateRight1(head: ListNode?, k: Int): ListNode? {//61, 连接成环, 移动后再断开
        if (head?.next == null || k == 0) {
            return head
        }
        var size = 0
        var p = head
        var pre: ListNode? = null
        while (p != null) {
            size++
            pre = p
            p = p.next
        }
        if (k % size == 0) {
            return head
        }
        pre?.next = head
        var d = size - k % size
        p = head
        pre = null
        while (d-- > 0) {
            pre = p
            p = p?.next
        }
        pre?.next = null
        return p
    }

    fun superEggDrop(K: Int, N: Int): Int {//887
        // Right now, dp[i] represents dp(1, i)
        var dp = IntArray(N + 1)
        for (i in 0..N) {
            dp[i] = i
        }
        for (k in 2..K) {
            // Now, we will develop dp2[i] = dp(k, i)
            val dp2 = IntArray(N + 1)
            var x = 1
            for (n in 1..N) {
                // Let's find dp2[n] = dp(k, n)
                // Increase our optimal x while we can make our answer better.
                // Notice max(dp[x-1], dp2[n-x]) > max(dp[x], dp2[n-x-1])
                // is simply max(T1(x-1), T2(x-1)) > max(T1(x), T2(x)).
                while (x < n && Math.max(dp[x - 1], dp2[n - x]) > Math.max(dp[x], dp2[n - x - 1])) {
                    x++
                }
                // The final answer happens at this x.
                dp2[n] = 1 + Math.max(dp[x - 1], dp2[n - x])
            }
            dp = dp2
        }
        return dp[N]
    }

    fun wordPattern(pattern: String, str: String): Boolean {//290
        if (pattern.isEmpty() || str.isEmpty() || pattern.length != str.split(" ").size) {
            return false
        }
        val map = mutableMapOf<Char, String>()
        val words = str.split(" ")
        pattern.forEachIndexed { index, c ->
            if (map.containsKey(c)) {
                if (map[c] != words[index]) {
                    return false
                }
            } else if (map.containsValue(words[index])) {
                return false
            } else {
                map[c] = words[index]
            }
        }
        return true
    }

    fun suggestedProducts(products: Array<String>, searchWord: String): List<List<String>> {//1268
        val ans = mutableListOf<List<String>>()
        var pre = StringBuilder()
        searchWord.forEach {
            pre.append(it)
            ans.add(products.filter { it.startsWith(pre) }.sorted().take(3))
        }
        return ans
    }

    fun stringMatching(words: Array<String>): List<String> {
        val set = mutableSetOf<String>()
        val ans = mutableListOf<String>()
        set.addAll(words)
        words.forEach { word ->
            if (set.filter { it.contains(word) && it != word }.count() > 0) {
                ans.add(word)
            }
        }
        return ans
    }

    fun processQueries(queries: IntArray, m: Int): IntArray {
        val p = IntArray(m)
        for (i in 1..m) {
            p[i - 1] = i
        }
        queries.forEachIndexed { index, i ->
            val position = p.indexOf(i)
            for (j in position downTo 1) {
                p[j] = p[j - 1]
            }
            p[0] = i
            queries[index] = position
        }
        return queries
    }

    fun entityParser(text: String): String {
        val map = mapOf(
            "&quot;" to "\"",
            "&apos;" to "'",
            "&amp;" to "&",
            "&gt;" to ">",
            "&lt;" to "<",
            "&frasl;" to "/"
        )
        var ans = text
        map.forEach { key, value -> ans = ans.replace(key, value) }
        return ans
    }

    fun numOfWays(n: Int): Int {
        var ans = 0
        return ans
    }

    fun intersection(start1: IntArray, end1: IntArray, start2: IntArray, end2: IntArray): DoubleArray {//面试题16.03 相交
        val ans = mutableListOf<Double>()
        val x1 = start1[0]
        val y1 = start1[1]
        val x2 = end1[0]
        val y2 = end1[1]
        val x3 = start2[0]
        val y3 = start2[1]
        val x4 = end2[0]
        val y4 = end2[1]
        // 判断 (x1, y1)~(x2, y2) 和 (x3, y3)~(x4, y3) 是否平行
        if ((y4 - y3) * (x2 - x1) == (y2 - y1) * (x4 - x3)) {
            // 若平行，则判断 (x3, y3) 是否在「直线」(x1, y1)~(x2, y2) 上
            if ((y2 - y1) * (x3 - x1) == (y3 - y1) * (x2 - x1)) {
                // 判断 (x3, y3) 是否在「线段」(x1, y1)~(x2, y2) 上
                if (inside(x1, y1, x2, y2, x3, y3)) {
                    update(ans, x3.toDouble(), y3.toDouble())
                }
                // 判断 (x4, y4) 是否在「线段」(x1, y1)~(x2, y2) 上
                if (inside(x1, y1, x2, y2, x4, y4)) {
                    update(ans, x4.toDouble(), y4.toDouble())
                }
                // 判断 (x1, y1) 是否在「线段」(x3, y3)~(x4, y4) 上
                if (inside(x3, y3, x4, y4, x1, y1)) {
                    update(ans, x1.toDouble(), y1.toDouble())
                }
                // 判断 (x2, y2) 是否在「线段」(x3, y3)~(x4, y4) 上
                if (inside(x3, y3, x4, y4, x2, y2)) {
                    update(ans, x2.toDouble(), y2.toDouble())
                }
            }
            // 在平行时，其余的所有情况都不会有交点
        } else {
            // 联立方程得到 t1 和 t2 的值
            val t1 =
                (x3.toDouble() * (y4 - y3) + y1 * (x4 - x3) - y3 * (x4 - x3) - x1 * (y4 - y3)) / ((x2 - x1) * (y4 - y3) - (x4 - x3) * (y2 - y1));
            val t2 =
                (x1.toDouble() * (y2 - y1) + y3 * (x2 - x1) - y1 * (x2 - x1) - x3 * (y2 - y1)) / ((x4 - x3) * (y2 - y1) - (x2 - x1) * (y4 - y3));
            // 判断 t1 和 t2 是否均在 [0, 1] 之间
            if (t1 in 0.0..1.0 && t2 in 0.0..1.0) {
                ans.add(x1 + t1 * (x2 - x1))
                ans.add(y1 + t1 * (y2 - y1))
            }
        }
        return ans.toDoubleArray()
    }

    // 判断 (xk, yk) 是否在「线段」(x1, y1)~(x2, y2) 上
    // 这里的前提是 (xk, yk) 一定在「直线」(x1, y1)~(x2, y2) 上
    private fun inside(x1: Int, y1: Int, x2: Int, y2: Int, xk: Int, yk: Int): Boolean {
        // 若与 x 轴平行，只需要判断 x 的部分
        // 若与 y 轴平行，只需要判断 y 的部分
        // 若为普通线段，则都要判断
        return (x1 == x2 || xk >= Math.min(x1, x2) && xk <= Math.max(x1, x2))
                && (y1 == y2 || yk >= Math.min(y1, y2) && yk <= Math.max(y1, y2))
    }

    private fun update(ans: MutableList<Double>, xk: Double, yk: Double) {
        if (ans.isEmpty()) {
            ans.add(xk)
            ans.add(yk)
        } else if (xk < ans[0]) {
            ans[0] = xk
            ans[1] = yk
        } else if (xk == ans[0] && yk < ans[1]) {
            ans[0] = xk
            ans[1] = yk
        }
    }

    fun fizzBuzz(n: Int): List<String> {//412
        val ans = mutableListOf<String>()
        for (i in 1..n) {
            if (i % 15 == 0) {
                ans.add("FizzBuzz")
            } else if (i % 5 == 0) {
                ans.add("Buzz")
            } else if (i % 3 == 0) {
                ans.add("Fizz")
            } else {
                ans.add("$i")
            }
        }
        return ans
    }

    fun thirdMax(nums: IntArray): Int {//414
        var max = Long.MIN_VALUE
        nums.forEach {
            if (max < it) {
                max = it.toLong()
            }
        }
        var max2 = Long.MIN_VALUE
        nums.forEach {
            if (it < max && max2 < it) {
                max2 = it.toLong()
            }
        }
        var max3 = Long.MIN_VALUE
        nums.forEach {
            if (it < max2 && max3 < it) {
                max3 = it.toLong()
            }
        }
        if (max3 > Long.MIN_VALUE) {
            return max3.toInt()
        } else {
            return max.toInt()
        }
    }

    fun findMaxLength(nums: IntArray): Int {//525
        val counts = IntArray(nums.size * 2 + 1) { -2 }
        counts[nums.size] = -1
        var max = 0
        var count = 0
        for (i in nums.indices) {
            count += if (nums[i] == 0) -1 else 1
            if (counts[count + nums.size] >= -1) {
                if (max < i - counts[count + nums.size]) {
                    max = i - counts[count + nums.size]
                }
            } else {
                counts[count + nums.size] = i
            }
        }
        return max
    }
}