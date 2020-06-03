package me.bytebeats.algorithms.kt

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


}