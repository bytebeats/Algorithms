package me.bytebeats.algorithms.cellectors;


import java.util.ArrayDeque;
import java.util.Deque;

class MyStack {

    private int[] table;
    private int top = -1;

    /**
     * Initialize your data structure here.
     */
    public MyStack() {
        table = new int[16];
        top = 0;
    }

    /**
     * Push element x onto stack.
     */
    public void push(int x) {
        table[top] = x;
        top++;
        ensureCapacity();
    }

    /**
     * Removes the element on top of the stack and returns that element.
     */
    public int pop() {
        int pop = table[top - 1];
        top--;
        return pop;
    }

    /**
     * Get the top element.
     */
    public int top() {
        return table[top - 1];
    }

    /**
     * Returns whether the stack is empty.
     */
    public boolean empty() {
        return top <= 0;
    }

    private void ensureCapacity() {
        if (top >= table.length) {
            int[] tmp = table;
            int length = table.length * 3 / 2;
            table = new int[length];
            System.arraycopy(tmp, 0, table, 0, top);
        }
    }
}
