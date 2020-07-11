package me.bytebeats.algorithms.kt

import me.bytebeats.algorithms.meta.TreeNode

class Solution10 {
    class Node(val `val`: Int) {
        var prev: Node? = null
        var next: Node? = null
        var child: Node? = null
    }

    fun flatten(root: Node?): Node? {//430, recursive
        if (root == null) return root
        val dummy = Node(-1)
        dummy.next = root
        flattenDFS(dummy, root)
        dummy.next?.prev = null
        return dummy.next
    }

    private fun flattenDFS(pre: Node?, cur: Node?): Node? {
        if (cur == null) return pre
        pre?.next = cur
        cur?.prev = pre

        val next = cur.next
        val tail = flattenDFS(cur, cur?.child)
        cur?.child = null
        return flattenDFS(tail, next)
    }

    fun flatten2(root: Node?): Node? {//430, loop
        if (root == null) return root
        val dummy = Node(-1)
        dummy.next = root
        var pre: Node? = dummy
        var cur: Node? = null
        val stack = mutableListOf<Node>()
        stack.add(root)
        while (stack.isNotEmpty()) {
            cur = stack.removeAt(stack.lastIndex)
            pre?.next = cur
            cur?.prev = pre

            if (cur?.next != null) stack.add(cur?.next!!)
            if (cur?.child != null) {
                stack.add(cur?.child!!)
                cur?.child = null
            }
            pre = cur
        }
        dummy.next?.prev = null
        return dummy.next
    }

    fun countSmaller(nums: IntArray): List<Int> {//315
        val ans = mutableListOf<Int>()
        val set = mutableSetOf<Int>()
        set.addAll(nums.toTypedArray())
        val a = set.toList().sorted()
        val c = IntArray(nums.size + 5) { 0 }
        var id = 0
        for (i in nums.lastIndex downTo 0) {
            id = a.binarySearch(nums[i]) + 1
            var ret = 0
            var pos = id - 1
            while (pos > 0) {
                ret += c[pos]
                pos -= pos and (-pos)
            }
            ans.add(ret)
            pos = id
            while (pos < c.size) {
                c[pos] += 1
                pos += pos and -pos
            }
        }
        return ans.reversed()
    }

    fun countSmaller1(nums: IntArray): List<Int> {//315
        val ans = mutableListOf<Int>()
        if (nums.isNotEmpty()) {
            ans.add(0)
            val size = nums.size
            for (i in size - 2 downTo 0) {
                nums.sort(i + 1)
                if (nums.last() < nums[i]) {
                    ans.add(size - i - 1)
                } else if (nums[i] <= nums[i + 1]) {
                    ans.add(0)
                } else {
                    var left = i + 1
                    var right = size - 1
                    var mid = 0
                    while (left < right) {
                        mid = left + (right - left) / 2
                        if (nums[mid] >= nums[i]) {
                            right = mid
                        } else {
                            left = mid + 1
                        }
                    }
                    ans.add(left - i - 1)
                }
            }
        }
        return ans.reversed()
    }

    fun flatten(root: TreeNode?): Unit {//114
        if (root == null) return
        if (root.left != null) {
            val right = root.right//获取右子树
            root.right = root.left//右子树的位置放置左子树
            root.left = null
            var p = root
            while (p?.right != null) {
                p = p?.right
            }
            p?.right = right//右子树放在原先左子树的(右子树的)右子树
        }
        flatten(root.right)
    }

    fun subsets(nums: IntArray): List<List<Int>> {//78
        val ans = mutableListOf<MutableList<Int>>()
        var pow = 1 shl nums.size
        for (i in 0 until pow) {
            val list = mutableListOf<Int>()
            var nn = i
            var j = 0
            while (nn > 0) {
                if (nn and 1 == 1) {
                    list.add(nums[j])
                }
                j += 1
                nn /= 2
            }
            ans.add(list)
        }
        return ans
    }

}