package me.bytebeats.algo.kt.design

import me.bytebeats.algs.ds.TreeNode

class TreeCodec() {
    // Encodes a URL to a shortened URL.
    fun serialize(root: TreeNode?): String {
        val ans = StringBuilder()
        return ans.toString()
    }

    private fun depth(root: TreeNode?): Int {
        if (root == null) {
            return 0
        } else if (root.left == null && root.right == null) {
            return 1
        } else if (root.left == null) {
            return 1 + depth(root.right)
        } else if (root.right == null) {
            return 1 + depth(root.left)
        } else {
            return 1 + Math.max(depth(root.left), depth(root.right))
        }
    }

    // Decodes your encoded data to tree.
    fun deserialize(data: String): TreeNode? {
        return null
    }
}