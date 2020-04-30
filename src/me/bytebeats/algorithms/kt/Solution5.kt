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
}