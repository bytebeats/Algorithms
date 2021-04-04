package me.bytebeats.algo.kt.design

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/4/4 19:36
 * @Version 1.0
 * @Description TO-DO
 */

class MyCircularDeque(val k: Int) {//641
    private val head = Node(-1)
    private val tail = Node(-1)
    private var count = 0

    /** Initialize your data structure here. Set the size of the deque to be k. */
    init {
        head.next = tail
        tail.pre = head
    }

    /** Adds an item at the front of Deque. Return true if the operation is successful. */
    fun insertFront(value: Int): Boolean {
        if (isFull()) return false
        val node = Node(value)
        val next = head.next
        head.next = node
        node.next = next
        node.pre = head
        next?.pre = node
        count++
        return true
    }

    /** Adds an item at the rear of Deque. Return true if the operation is successful. */
    fun insertLast(value: Int): Boolean {
        if (isFull()) return false
        val node = Node(value)
        val pre = tail.pre
        pre?.next = node
        node.next = tail
        tail.pre = node
        node.pre = pre
        count++
        return true
    }

    /** Deletes an item from the front of Deque. Return true if the operation is successful. */
    fun deleteFront(): Boolean {
        if (isEmpty()) return false
        val nextNext = head.next?.next
        head.next = nextNext
        nextNext?.pre = head
        count--
        return true
    }

    /** Deletes an item from the rear of Deque. Return true if the operation is successful. */
    fun deleteLast(): Boolean {
        if (isEmpty()) return false
        val prePre = tail.pre?.pre
        prePre?.next = tail
        tail.pre = prePre
        count--
        return true
    }

    /** Get the front item from the deque. */
    fun getFront(): Int {
        if (isEmpty()) return -1
        return head.next?.value ?: -1
    }

    /** Get the last item from the deque. */
    fun getRear(): Int {
        if (isEmpty()) return -1
        return tail.pre?.value ?: -1
    }

    /** Checks whether the circular deque is empty or not. */
    fun isEmpty(): Boolean {
        return count == 0
    }

    /** Checks whether the circular deque is full or not. */
    fun isFull(): Boolean {
        return count == k
    }

    private class Node(val value: Int) {
        var next: Node? = null
        var pre: Node? = null
    }

}