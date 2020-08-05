package me.bytebeats.algo.designs;

import java.util.*;

public class TwoSum {
    private List<Integer> list;

    /**
     * Initialize your data structure here.
     */
    public TwoSum() {
        list = new ArrayList<>();
    }

    /**
     * Add the number to an internal data structure..
     */
    public void add(int number) {
        list.add(number);
    }

    /**
     * Find if there exists any pair of numbers which sum is equal to the value.
     */
    public boolean find(int value) {
        Collections.sort(list);
        int i = 0, j = list.size() - 1;
        while (i < j) {
            int tmp = list.get(i) + list.get(j);
            if (tmp > value) {
                j--;
            } else if (tmp < value) {
                i++;
            } else {
                return true;
            }
        }
        return false;
    }
}

/**
 * Your TwoSum object will be instantiated and called as such:
 * TwoSum obj = new TwoSum();
 * obj.add(number);
 * boolean param_2 = obj.find(value);
 */