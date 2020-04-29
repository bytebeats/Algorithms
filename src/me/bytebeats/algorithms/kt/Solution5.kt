package me.bytebeats.algorithms.kt

import me.bytebeats.algorithms.meta.TreeNode

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

}