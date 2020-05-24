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
}