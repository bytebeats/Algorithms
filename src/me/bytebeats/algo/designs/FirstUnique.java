package me.bytebeats.algo.designs;

import java.util.*;

class FirstUnique {
    Set<Integer> unrepeated = new LinkedHashSet<>();//why this did not TLE ?
    Set<Integer> repeated = new HashSet<>();

    public FirstUnique(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            add(nums[i]);
        }
    }

    public int showFirstUnique() {
        if (unrepeated.isEmpty()) {
            return -1;
        } else {
            return unrepeated.iterator().next();
        }
    }

    public void add(int value) {
        if (repeated.contains(value)) {
            return;
        } else if (unrepeated.contains(value)) {
            unrepeated.remove(value);
            repeated.add(value);
        } else {
            unrepeated.add(value);
        }
    }
}
