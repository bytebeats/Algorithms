package me.bytebeats.algorithms.kt.design

class MinStack {//155

    /**
     * initialize your data structure here.
     * */
    private var table = IntArray(16)
    private var top = 0

    /**
     * Push element x onto stack.
     */
    fun push(x: Int) {
        table[top] = x
        top++
        ensureCapacity()
    }

    /**
     * Removes the element on top of the stack and returns that element.
     */
    fun pop() {
        top--
    }

    /**
     * Get the top element.
     */
    fun top(): Int {
        return table[top - 1]
    }

    /**
     * Returns whether the stack is empty.
     */
    private fun empty(): Boolean {
        return top <= 0
    }

    fun getMin(): Int {
        if (empty()) {
            return -1
        }
        var min = Int.MAX_VALUE
        for (i in 0 until top) {
            if (min > table[i]) {
                min = table[i]
            }
        }
        return min
    }

    private fun ensureCapacity() {
        if (top >= table.size) {
            val tmp = table
            val length = table.size * 3 / 2
            table = IntArray(length)
            System.arraycopy(tmp, 0, table, 0, top)
        }
    }
}