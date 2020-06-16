package me.bytebeats.algorithms.kt.design

import me.bytebeats.algorithms.meta.TreeNode

class BinaryTreeCodec { //297
    // Encodes a URL to a shortened URL.
    fun serialize(root: TreeNode?): String {
        if (root == null){
            return "null,"
        }
        val left = serialize(root.left)
        val right = serialize(root.right)
        return "${root.`val`},$left$right"
    }

    // Decodes your encoded data to tree.
    fun deserialize(data: String): TreeNode? {
        val element = data.split(",")
        return buildTree(element.toMutableList())
    }

    private fun buildTree(mutableList: MutableList<String>): TreeNode? {
        if (mutableList.isEmpty()) {
            return null
        }
        val e = mutableList.removeAt(0)
        if (e == "null") {
            return null
        }
        val root = TreeNode(e.toInt())
        root.left = buildTree(mutableList)
        root.right = buildTree(mutableList)
        return root
    }
}