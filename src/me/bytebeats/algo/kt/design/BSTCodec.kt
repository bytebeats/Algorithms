package me.bytebeats.algo.kt.design

import me.bytebeats.algs.ds.TreeNode

class BSTCodec { //449

    private fun postTraversal(root: TreeNode?, sb: StringBuilder): StringBuilder {
        if (root == null) return sb
        postTraversal(root.left, sb)
        postTraversal(root.right, sb)
        sb.append(root.`val`)
        sb.append(" ")
        return sb
    }

    // Encodes a URL to a shortened URL.
    fun serialize(root: TreeNode?): String {
        val postSeq = postTraversal(root, StringBuilder())
        if (postSeq.isNotEmpty()) {
            postSeq.deleteCharAt(postSeq.lastIndex)
        }
        return postSeq.toString()
    }

    // Decodes your encoded data to tree.
    fun deserialize(data: String): TreeNode? {
        if (data.isEmpty()) return null
        val nums = mutableListOf<Int>()
        for (s in data.split(Regex("\\s+"))) {
            nums.add(s.toInt())
        }
        return helper(Int.MIN_VALUE, Int.MAX_VALUE, nums)
    }

    private fun helper(lower: Int, higher: Int, nums: MutableList<Int>): TreeNode? {
        if (nums.isEmpty()) return null
        val last = nums.last()
        if (last < lower || last > higher) return null
        nums.removeAt(nums.lastIndex)
        val root = TreeNode(last)
        root.right = helper(last, higher, nums)
        root.left = helper(lower, last, nums)
        return root
    }
}