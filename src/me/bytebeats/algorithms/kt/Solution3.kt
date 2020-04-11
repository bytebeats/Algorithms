package me.bytebeats.algorithms.kt

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
}