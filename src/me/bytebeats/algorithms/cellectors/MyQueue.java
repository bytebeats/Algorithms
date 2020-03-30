package me.bytebeats.algorithms.cellectors;

class MyQueue {

    private int[] table = null;
    private int head = -1, tail = -1;

    /**
     * Initialize your data structure here.
     */
    public MyQueue() {
        table = new int[16];
        head = tail = 0;
    }

    /**
     * Push element x to the back of queue.
     */
    public void push(int x) {
        table[tail] = x;
        tail++;
        ensureCapacity();
    }

    /**
     * Removes the element from in front of queue and returns that element.
     */
    public int pop() {
        if (tail > head) {
            int res = table[head];
            head++;
            return res;
        } else {
            throw new RuntimeException("MyQueue is Empty!");
        }
    }

    /**
     * Get the front element.
     */
    public int peek() {
        if (tail > head) {
            return table[head];
        } else {
            throw new RuntimeException("MyQueue is Empty!");
        }
    }

    /**
     * Returns whether the queue is empty.
     */
    public boolean empty() {
        return tail - head <= 0;
    }

    private void ensureCapacity() {
        if (tail >= table.length) {
            int[] tmp = table;
            int length = (tail - head) * 3 / 2;
            table = new int[length];
            System.arraycopy(tmp, head, table, 0, tail - head);
            tail -= head;
            head = 0;
        }
    }
}
