package me.bytebeats.algorithms.kt

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
}