package me.bytebeats.algorithms.kt

import me.bytebeats.algorithms.meta.Node

class Solution6 {

    fun nextGreatestLetter(letters: CharArray, target: Char): Char {//744, O(n)
        var index = -1
        for (i in letters.indices) {
            if (letters[i] > target) {
                index = i
                break
            }
        }
        return if (index > -1) letters[index] else letters[0]
    }

    fun nextGreatestLetter1(letters: CharArray, target: Char): Char {//744, O(logn)
        var low = 0
        var high = letters.size
        var mid = 0
        while (low < high) {
            mid = low + (high - low) / 2
            if (letters[mid] <= target) low = mid + 1
            else high = mid
        }
        return letters[low % letters.size]
    }

    fun isToeplitzMatrix(matrix: Array<IntArray>): Boolean {//766
        return false
    }

    private val visited = mutableMapOf<Node, Node>()
    fun cloneGraph(node: Node?): Node? {//133
        if (node == null) {
            return null
        }
        if (visited.containsKey(node)) {
            return visited[node]
        }
        val cloned = Node(node.`val`)
        cloned.neighbors = ArrayList()
        visited[node] = cloned
        if (node.neighbors != null) {
            for (neighbor in node.neighbors!!) {
                cloned?.neighbors?.add(cloneGraph(neighbor))
            }
        }
        return cloned
    }
}