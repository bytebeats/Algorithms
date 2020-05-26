package me.bytebeats.algorithms.kt

import me.bytebeats.algorithms.meta.TreeNode

class Solution7 {

    fun verticalTraversal(root: TreeNode?): List<List<Int>> {//987
        val ans = mutableMapOf<Int, MutableMap<Int, MutableList<Int>>>()
        if (root != null) {
            val queue = mutableListOf<TreeNode>()
            val xes = mutableListOf<Int>()
            val yes = mutableListOf<Int>()
            queue.add(root)
            xes.add(0)
            yes.add(0)
            var x = 0
            var y = 0
            var node: TreeNode? = null
            while (queue.isNotEmpty()) {
                node = queue.removeAt(0)
                x = xes.removeAt(0)
                y = yes.removeAt(0)
                ans.compute(x) { _, v ->
                    if (v == null) {
                        val map = mutableMapOf<Int, MutableList<Int>>()
                        map.compute(y) { _, c ->
                            if (c == null) {
                                val list = mutableListOf<Int>()
                                list.add(node.`val`)
                                list
                            } else {
                                c.add(node.`val`)
                                c
                            }
                        }
                        map
                    } else {
                        v.compute(y) { _, c ->
                            if (c == null) {
                                val list = mutableListOf<Int>()
                                list.add(node.`val`)
                                list
                            } else {
                                c.add(node.`val`)
                                c
                            }
                        }
                        v
                    }
                }
                if (node.left != null) {
                    queue.add(node.left)
                    xes.add(x - 1)
                    yes.add(y + 1)
                }
                if (node.right != null) {
                    queue.add(node.right)
                    xes.add(x + 1)
                    yes.add(y + 1)
                }
            }
        }
        return ans.entries
            .sortedBy { it.key }//ordered by x
            .map { x ->
                x.value
                    .entries
                    .sortedBy { it.key }//ordered by y
                    .flatMap { it.value.sorted() }//ordered by value
            }
    }

    var btnLeafVal = 0
    fun findBottomLeftValue(root: TreeNode?): Int {//513
        btnLeafVal = 0
        dfs(root, 0, mutableSetOf())
        return btnLeafVal
    }

    private fun dfs(root: TreeNode?, level: Int, set: MutableSet<Int>) {//mid order reversal
        if (root == null) {
            return
        }
        if (!set.contains(level)) {
            set.add(level)
            btnLeafVal = root.`val`
        }
        dfs(root.left, level + 1, set)
        dfs(root.right, level + 1, set)
    }

    fun pathInZigZagTree(label: Int): List<Int> {//1104
        val path = mutableListOf<Int>()
        var newLbl = label
        var level = (Math.log(newLbl.toDouble()) / Math.log(2.0)).toInt()
        while (newLbl > 1) {
            path.add(newLbl)
            newLbl = (3 * Math.pow(2.0, (--level).toDouble()) - newLbl / 2 - 1).toInt()
        }
        path.add(1)
        return path.reversed()
    }

    fun missingNumber(arr: IntArray): Int {//1228
        var pre = 0
        var post = 0
        for (i in 1 until arr.size - 1) {
            pre = arr[i] - arr[i - 1]
            post = arr[i + 1] - arr[i]
            if (pre != post) {
                if (pre / post == 2) {
                    return arr[i] - (arr[i + 1] - arr[i])
                } else {
                    return arr[i] + (arr[i] - arr[i - 1])
                }
            }
        }
        return arr[0]//in case of [1,1,1]
    }

    fun longestSubsequence(arr: IntArray, difference: Int): Int {//1218
        var ans = 0
        return 0
    }

    fun findErrorNums(nums: IntArray): IntArray {//645
        val ans = IntArray(2)
        var xorVal = 0
        for (i in nums.indices) {
            xorVal = xorVal xor (i + 1)
            xorVal = xorVal xor nums[i]
        }
        val rightMost = xorVal and (xorVal - 1).inv()
        var x0 = 0
        var x1 = 0
        for (i in nums.indices) {
            if ((i + 1) and rightMost != 0) {
                x1 = x1 xor (i + 1)
            } else {
                x0 = x0 xor (i + 1)
            }
            if (nums[i] and rightMost != 0) {
                x1 = x1 xor nums[i]
            } else {
                x0 = x0 xor nums[i]
            }
        }
        for (i in nums.indices) {
            if (nums[i] == x0) {
                ans[0] = x0
                ans[1] = x1
                return ans
            }
        }
        ans[0] = x1
        ans[1] = x0
        return ans
    }

    fun waysToStep(n: Int): Int {//面试题08.01
        val mod = 1000000007
        if (n < 2) {
            return 1
        } else if (n < 3) {
            return 2
        } else if (n < 4) {
            return 4
        } else {
            var nn = n
            var f1 = 1
            var f2 = 2
            var f3 = 4
            var tmp1 = 0
            var tmp2 = 0
            while (nn-- > 3) {
                tmp1 = f1
                tmp2 = f2
                f1 = f2
                f2 = f3
                f3 = ((f3 + tmp1) % mod + tmp2) % mod
            }
            return f3
        }
    }

    fun findString(words: Array<String>, s: String): Int {//面试题10.05
        var i = 0
        var j = words.lastIndex

        var mid = 0
        while (i <= j) {
            while (i < j && words[i].isEmpty()) {
                i++
            }
            while (i < j && words[j].isEmpty()) {
                j--
            }
            mid = i + (j - i) / 2
            while (mid > i && words[mid].isEmpty()) {
                mid--
            }
            if (words[mid] < s) {
                i = mid + 1
            } else if (words[mid] > s) {
                j = mid - 1
            } else {
                return mid
            }
        }
        return -1
    }

    fun findMagicIndex(nums: IntArray): Int {//面试题08.03
        var ans = -1
        for (i in nums.indices) {
            if (i == nums[i]) {
                ans = i
                break
            }
        }
        return ans
    }

    var firstIdx = -1
    fun findMagicIndex1(nums: IntArray): Int {//面试题08.03
        firstIdx = -1
        search(nums, 0, nums.lastIndex)
        return firstIdx
    }

    private fun search(nums: IntArray, left: Int, right: Int) {
        if (left > right) {
            return
        }
        val mid = left + (right - left) / 2
        if (mid == nums[mid]) {
            if (firstIdx == -1) {
                firstIdx = mid
            } else if (firstIdx > mid) {
                firstIdx = mid
            }
            search(nums, left, mid - 1)
        } else {
            search(nums, left, mid - 1)
            if (firstIdx == -1 || firstIdx > mid) {
                search(nums, mid + 1, right)
            }
        }
    }

}