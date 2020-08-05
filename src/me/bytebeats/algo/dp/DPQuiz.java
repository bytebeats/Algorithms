package me.bytebeats.algo.dp;

public class DPQuiz {
    public int findPeakElement(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        if (nums.length == 1) {
            return 0;
        }
        if (nums.length == 2) {
            return nums[0] > nums[1] ? 0 : 1;
        }
        int index = -1;
        for (int i = 1; i < nums.length - 1; i++) {
            if (nums[i - 1] < nums[i] && nums[i] > nums[i + 1]) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            if (nums[0] < nums[1]) {
                index = nums.length - 1;
            } else if (nums[nums.length - 1] < nums[nums.length - 1]) {
                index = 0;
            } else {
                index = 0;
            }
        }
        return index;
    }

    public boolean repeatedSubstringPattern(String s) {
        if (s == null) {
            return false;
        }
        if ("".equals(s)) {
            return true;
        }
        int size = s.length();
        for (int i = 1; i <= size; i++) {
            String sub = s.substring(0, i);
            int subSize = sub.length();
            if (size == subSize) {
                return false;
            }
            if (size % subSize != 0) {
                continue;
            }
            if (isSubEquals(sub, s)) {
                return true;
            } else {
                continue;
            }
        }
        return false;
    }

    boolean isSubEquals(String sub, String s) {
        int subSize = sub.length();
        int count = s.length() / subSize;
        for (int j = 1; j < count; j++) {
            int start = subSize * j;
            int end = (j + 1) * subSize;
            if (!sub.equals(s.substring(start, end))) {
                return false;
            } else {
                continue;
            }
        }
        return true;
    }

    public int searchInsert(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums[0] > target) {
            return 0;
        }
        if (nums[nums.length - 1] < target) {
            return nums.length;
        }
        int index = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < target) {
                continue;
            } else if (nums[i] == target) {
                return i;
            } else {
                index = i;
                break;
            }
        }
        return index - 1;
    }

    public int searchInsert1(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums[0] > target) {
            return 0;
        }
        if (nums[nums.length - 1] < target) {
            return nums.length;
        }
        int i = 0, j = nums.length - 1;
        int mid;
        while (i < j) {
            mid = i + (j - i) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                if (mid > 0 && nums[mid - 1] < target) {
                    return mid;
                } else {
                    j = mid - 1;
                }
            } else {
                if (mid < nums.length -1 && nums[mid + 1] > target) {
                    return mid + 1;
                } else {
                    i = mid + 1;
                }
            }
        }
        return 0;
    }
}
