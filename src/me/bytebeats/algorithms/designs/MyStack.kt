package me.bytebeats.algorithms.designs

import java.util.*

class MyStack() {

    /** Initialize your data structure here. */
    private val q = LinkedList<Int>()

    /** Push element x onto stack. */
    fun push(x: Int) {
        q.addLast(x)
    }

    /** Removes the element on top of the stack and returns that element. */
    fun pop(): Int = q.removeLast()


    /** Get the top element. */
    fun top(): Int = q.last

    /** Returns whether the stack is empty. */
    fun empty(): Boolean = q.isEmpty()

}