package me.bytebeats.algo.kt.design

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/7/30 16:52
 * @Version 1.0
 * @Description TO-DO
 */

class MapSum() {//677
    /** Initialize your data structure here. */
    private val root = Node(' ')

    fun insert(key: String, `val`: Int) {
        var node = root
        for (c in key) {
            if (!node.followers.containsKey(c)) {
                node.followers[c] = Node(c)
            }
            node = node.followers[c]!!
        }
        node.isEnd = true
        node.value = `val`
    }

    fun sum(prefix: String): Int {
        var node = root
        for (c in prefix) {
            if (!node.followers.containsKey(c)) {
                node.followers[c] = Node(c)
            }
            node = node.followers[c]!!
        }
        return sum(node)
    }

    fun sum(node: Node): Int {
        var sum = 0
        if (node.isEnd) {
            sum += node.value
        }
        for (n in node.followers.values) {
            sum += sum(n)
        }
        return sum
    }

    class Node(val prefix: Char) {
        var isEnd = false
        var value = 0
        val followers = mutableMapOf<Char, Node>()
    }
}