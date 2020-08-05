package me.bytebeats.algo.kt.design

import me.bytebeats.algs.ds.TreeNode

class BSTIterator(var root: TreeNode?) {//173

    /** @return the next smallest number */
    fun next(): Int {
        var pre: TreeNode? = null
        var p = root
        while (p?.left != null) {
            pre = p
            p = p.left
        }
        var `val` = 0
        if (pre == null) {
            `val` = root?.`val` ?: -1
            root = root?.right
        } else {
            `val` = p?.`val` ?: -1
            pre.left = p?.right
        }
        return `val`
    }

    /** @return whether we have a next smallest number */
    fun hasNext(): Boolean = root != null

}