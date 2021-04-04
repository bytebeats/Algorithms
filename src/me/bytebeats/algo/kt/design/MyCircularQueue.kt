package me.bytebeats.algo.kt.design

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/4/4 19:11
 * @Version 1.0
 * @Description TO-DO
 */

class MyCircularQueue1(val k: Int) {//622
    private var headIdx = 0
    private var count = 0
    private val queue = IntArray(k)

    fun enQueue(value: Int): Boolean {
        if (count == k) {
            return false
        }
        queue[(headIdx + count) % k] = value
        count++
        return true
    }

    fun deQueue(): Boolean {
        if (count == 0) {
            return false
        }
        headIdx = (headIdx + 1) % k
        count--
        return true
    }

    fun Front(): Int {
        if (count == 0) return -1
        return queue[headIdx]
    }

    fun Rear(): Int {
        if (count == 0) return -1
        return queue[(headIdx + count - 1) % k]
    }

    fun isEmpty(): Boolean {
        return count == 0
    }

    fun isFull(): Boolean {
        return count == k
    }

}

class MyCircularQueue2(val k: Int) {//622
    private var count = 0
    private var head: Node? = null
    private var tail: Node? = null

    fun enQueue(value: Int): Boolean {
        if (count == k) {
            return false
        }
        val node = Node(value)
        if (count == 0) {
            head = node
            tail = node
        } else {
            tail?.next = node
            tail = node
        }
        count++
        return true
    }

    fun deQueue(): Boolean {
        if (count == 0) {
            return false
        }
        head = head?.next
        count--
        return true
    }

    fun Front(): Int {
        if (count == 0) return -1
        return head?.value ?: -1
    }

    fun Rear(): Int {
        if (count == 0) return -1
        return tail?.value ?: -1
    }

    fun isEmpty(): Boolean {
        return count == 0
    }

    fun isFull(): Boolean {
        return count == k
    }

    private class Node(val value: Int) {
        var next: Node? = null
    }

}