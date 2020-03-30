package me.bytebeats.algorithms.kt.design;

import java.util.Stack;

public class MyQueue {
    private Stack<Integer> push;
    private Stack<Integer> pop;
    private boolean isPushing = false;

    /**
     * Initialize your data structure here.
     */
    public MyQueue() {
        push = new Stack<>();
        pop = new Stack<>();
    }

    /**
     * Push element x to the back of queue.
     */
    public void push(int x) {
        if (!isPushing) {
            while (!pop.isEmpty()) {
                push.push(pop.pop());
            }
            isPushing = true;
        }
        push.push(x);
    }

    /**
     * Removes the element from in front of queue and returns that element.
     */
    public int pop() {
        if (isPushing) {
            isPushing = false;
            while (!push.isEmpty()) {
                pop.push(push.pop());
            }
        }
        return pop.pop();
    }

    /**
     * Get the front element.
     */
    public int peek() {
        if (isPushing) {
            isPushing = false;
            while (!push.isEmpty()) {
                pop.push(push.pop());
            }
        }
        return pop.peek();
    }

    /**
     * Returns whether the queue is empty.
     */
    public boolean empty() {
        if (isPushing) {
            return push.isEmpty();
        } else {
            return pop.isEmpty();
        }
    }
}
