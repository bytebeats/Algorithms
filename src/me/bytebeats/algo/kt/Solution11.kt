package me.bytebeats.algo.kt

import me.bytebeats.algs.ds.TreeNode

/**
 * @author bytebeats
 * @email <happychinapc@gmail.com>
 * @github https://github.com/bytebeats
 * @created on 2020/9/3 11:07
 * @version 1.0
 * @description TO-DO
 */

class Solution11 {
    fun solveNQueens(n: Int): List<List<String>> {//51 N Queens
        val ans = mutableListOf<MutableList<String>>()
        val queens = IntArray(n) { -1 }
        val columns = mutableSetOf<Int>()
        val diagonals1 = mutableSetOf<Int>()
        val diagonals2 = mutableSetOf<Int>()
        backtrack(ans, queens, n, 0, columns, diagonals1, diagonals2)
        return ans
    }

    private fun backtrack(
        ans: MutableList<MutableList<String>>,
        queens: IntArray,
        n: Int,
        row: Int,
        columns: MutableSet<Int>,
        diagonals1: MutableSet<Int>,
        diagonals2: MutableSet<Int>
    ) {
        if (row == n) {
            val board = generateBoard(queens, n)
            ans.add(board)
        } else {
            for (i in 0 until n) {
                if (columns.contains(i)) continue
                val diagonal1 = row - i
                if (diagonals1.contains(diagonal1)) continue
                val diagonal2 = row + i
                if (diagonals2.contains(diagonal2)) continue
                queens[row] = i
                columns.add(i)
                diagonals1.add(diagonal1)
                diagonals2.add(diagonal2)
                backtrack(ans, queens, n, row + 1, columns, diagonals1, diagonals2)
                queens[row] = -1
                columns.remove(i)
                diagonals1.remove(diagonal1)
                diagonals2.remove(diagonal2)
            }
        }
    }

    private fun generateBoard(queens: IntArray, n: Int): MutableList<String> {
        val board = mutableListOf<String>()
        for (i in 0 until n) {
            val row = CharArray(n) { '.' }
            row[queens[i]] = 'Q'
            board.add(String(row))
        }
        return board
    }

    fun partitionLabels(S: String): List<Int> {//763
        val last = IntArray(26)
        for (i in S.indices) {
            last[S[i] - 'a'] = i
        }
        val ans = mutableListOf<Int>()
        var j = 0
        var anchor = 0
        for (i in S.indices) {
            j = j.coerceAtLeast(last[S[i] - 'a'])
            if (i == j) {
                ans.add(i - anchor + 1)
                anchor = i + 1
            }
        }
        return ans
    }

    fun getAllElements(root1: TreeNode?, root2: TreeNode?): List<Int> {//1305
        val list1 = mutableListOf<Int>()
        convertTree2List(root1, list1)
        val list2 = mutableListOf<Int>()
        convertTree2List(root2, list2)
        list1.addAll(list2)
        return list1.sorted()
    }

    private fun convertTree2List(root: TreeNode?, list: MutableList<Int>) {
        if (root == null) return
        if (root.left != null) {
            convertTree2List(root.left, list)
        }
        list.add(root.`val`)
        if (root.right != null) {
            convertTree2List(root.right, list)
        }
    }
}