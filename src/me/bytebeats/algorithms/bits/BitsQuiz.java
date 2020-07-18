package me.bytebeats.algorithms.bits;

import java.util.*;

public class BitsQuiz {

    public int rotatedDigits(int N) {
        int count = 0;
        for (int i = 0; i < N + 1; i++) {
            if (isValidNumber(i)) {
                count++;
            }
        }
        return count;
    }

    boolean isValidNumber(int num) {
        if (num == 0) {
            return false;
        }
        int res = 0;
        int power = 0;
        int n = num;
        while (num > 0) {
            int d = num % 10;
            if (isValidDigit(d)) {
                res += getRotatedDigit(d) * power(power);
            } else if (isSameAfterRotated(d)) {
                res += d * power(power);
            } else {
                return false;
            }
            num /= 10;
            power++;
        }
        return res != n;
    }

    int power(int y) {
        int res = 1;
        while (y-- > 0) {
            res *= 10;
        }
        return res;
    }

    int getRotatedDigit(int d) {
        if (d == 2) {
            return 5;
        } else if (d == 5) {
            return 2;
        } else if (d == 9) {
            return 6;
        } else {
            return 9;
        }
    }

    boolean isValidDigit(int d) {
        return d == 2 || d == 5 || d == 6 || d == 9;
    }

    boolean isSameAfterRotated(int d) {
        return d == 0 || d == 1 || d == 8;
    }

    public int maxDistToClosest(int[] seats) {
        int startD = 0;
        int startIndex = 0;
        for (int i = 0; i < seats.length; i++) {
            if (seats[i] == 0) {
                startD++;
            } else {
                startIndex = i;
                break;
            }
        }
        int endD = 0;
        int endIndex = 0;
        for (int i = seats.length - 1; i > -1; i--) {
            if (seats[i] == 0) {
                endD++;
            } else {
                endIndex = i;
                break;
            }
        }
        int maxD = 0;
        int tmp = 0;
        for (int i = startIndex + 1; i <= endIndex - 1; i++) {
            if (seats[i] == 0) {
                tmp++;
            } else {
                tmp = 0;
            }
            if (tmp > maxD) {
                maxD = tmp;
            }
        }
        if (maxD % 2 == 0) {
            maxD = maxD / 2 - 1;
        } else {
            maxD = maxD / 2;
        }
        maxD = Math.max(maxD, startD);
        maxD = Math.max(maxD, endD);
        return maxD;
    }

    public int fib(int N) {
        if (N == 0) {
            return 0;
        } else if (N == 1) {
            return 1;
        } else {
            return fib(N - 1) + fib(N - 2);
        }
    }

    public int distributeCandies(int[] candies) {
        if (candies == null || candies.length % 2 != 0) {
            return 0;
        }
        int half = candies.length / 2;
        int kindCount = (int) Arrays.stream(candies).distinct().count();
        if (kindCount > half) {
            return half;
        } else {
            return kindCount;
        }
    }

    public int[] getNoZeroIntegers(int n) {
        int[] res = new int[2];
        for (int i = 1; i < n; i++) {
            int d = n - i;
            if (containsNo0(i) && containsNo0(d)) {
                res[0] = i;
                res[1] = d;
                break;
            }
        }
        return res;
    }

    boolean containsNo0(int num) {
        while (num > 0) {
            if (num % 10 == 0) {
                return false;
            }
            num /= 10;
        }
        return true;
    }

    public int minFlips(int a, int b, int c) {
        String aBinaryStr = Integer.toBinaryString(a);
        String bBinaryStr = Integer.toBinaryString(b);
        String cBinaryStr = Integer.toBinaryString(c);
        System.out.println(aBinaryStr + ", " + bBinaryStr + ", " + cBinaryStr);
        while (bBinaryStr.length() < aBinaryStr.length()) {
            bBinaryStr = '0' + bBinaryStr;
        }
        while (aBinaryStr.length() < bBinaryStr.length()) {
            aBinaryStr = '0' + aBinaryStr;
        }
        while (cBinaryStr.length() < bBinaryStr.length()) {
            cBinaryStr = '0' + cBinaryStr;
        }
        System.out.println(aBinaryStr + ", " + bBinaryStr + ", " + cBinaryStr);
        int flips = 0;
        for (int i = 0; i < cBinaryStr.length(); i++) {
            if (cBinaryStr.charAt(i) == '1') {//'1'
                if (aBinaryStr.charAt(i) == '0' && bBinaryStr.charAt(i) == '0') {
                    flips += 1;
                } else {
                    continue;
                }
            } else {//'0'
                if (aBinaryStr.charAt(i) == '1' && bBinaryStr.charAt(i) == '1') {
                    flips += 2;
                } else if (aBinaryStr.charAt(i) == '0' && bBinaryStr.charAt(i) == '0') {
                    continue;
                } else {
                    flips += 1;
                }
            }
        }
        return flips;
    }

    public int maximum69Number(int num) {//1323
        String s = Integer.toString(num);
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '6') {
                chars[i] = '9';
                break;
            }
        }
        return Integer.parseInt(String.valueOf(chars));
    }

    public boolean hasAlternatingBits(int n) {
        int pre = -1;
        int cur = -1;
        while (n > 0) {
            pre = cur;
            cur = n % 2;
            if (pre == cur) {
                return false;
            }
            n >>= 1;
        }
        return true;
    }

    public int[] fraction(int[] cont) {
        int[] res = new int[2];
        double tmp = compute(cont, 0);
        System.out.println(tmp);
        int m = 1;
        double n = 0.0;
        while (true) {
            n = m * tmp;
            int aInt = (int) n;
            double aD = n - aInt;
            if (aD == 0.0) {
                res[0] = aInt;
                res[1] = m;
                break;
            }
            m++;
        }
        int gcd = equalGCD(res[0], res[1]);
        res[0] /= gcd;
        res[1] /= gcd;
        return res;
    }

    double compute(int[] cont, int i) {
        if (i == cont.length - 1) {
            return cont[i];
        } else {
            return cont[i] + 1.0 / compute(cont, i + 1);
        }
    }

    int equalGCD(int m, int n) {
        while (m != n) {
            if (m > n) {
                m -= n;
            } else {
                n -= m;
            }
        }
        return m;
    }

    public int reverse(int x) {
        if (x > Math.pow(10, 9)) {
            if (x % 10 > 2) {
                return 0;
            } else if (x % 10 == 2 && x / 10 % 10 > 1) {
                return 0;
            } else if (x / 10 % 10 == 1 && x / 100 % 10 > 4) {
                return 0;
            } else if (x / 100 % 10 == 4 && x / 1000 % 10 > 7) {
                return 0;
            } else if (x / 1000 % 10 == 7 && x / 10000 % 10 > 4) {
                return 0;
            }
        } else if (x < -Math.pow(10, 9)) {
            if (x == Integer.MIN_VALUE) {
                return 0;
            }
            int tmp = -x;
            if (tmp % 10 > 2) {
                return 0;
            } else if (tmp % 10 == 2 && tmp / 10 % 10 > 1) {
                return 0;
            } else if (tmp / 10 % 10 == 1 && tmp / 100 % 10 > 4) {
                return 0;
            } else if (tmp / 100 % 10 == 4 && tmp / 1000 % 10 > 7) {
                return 0;
            } else if (tmp / 1000 % 10 == 7 && tmp / 10000 % 10 > 4) {
                return 0;
            }
        }
        int res = 0;
        boolean isLessThanZero = x < 0;
        x = x < 0 ? -x : x;
        while (x > 0) {
            int d = x % 10;
            res *= 10;
            res += d;
            x /= 10;
        }
        if (isLessThanZero) {
            return -res;
        } else {
            return res;
        }
    }

    public int numberOfSteps(int num) {
        int count = 0;
        while (num != 0) {
            if (num % 2 == 0) {
                num /= 2;
            } else {
                num -= 1;
            }
            count++;
        }
        return count;
    }

    public int numOfSubarrays(int[] arr, int k, int threshold) {
        int count = 0;
        double sum = 0;
        for (int i = 0; i < arr.length - k + 1; i++) {
            if (i == 0) {
                for (int j = i; j < i + k; j++) {
                    sum += arr[j];
                }
            } else {
                sum -= arr[i];
                sum += arr[i + k - 1];
            }
            if (sum / k >= threshold) {

                count++;
            }
        }
        return count;
    }

    public double angleClock(int hour, int minutes) {
        double angle = 0.0;
        double mAngle = 360.0 * minutes / 60;
        double hAngle = 360.0 * ((hour + minutes / 60.0) % 12) / 12;
        angle = hAngle > mAngle ? hAngle - mAngle : mAngle - hAngle;
        if (angle > 180) {
            angle = 360 - angle;
        }
        return angle;
    }

    public boolean checkIfExist(int[] arr) {
        Set<Integer> set = new HashSet<>(arr.length);
        if (arr != null && arr.length >= 2) {
            for (int num : arr) {
                if (set.contains(num * 2)) {
                    return true;
                }
                if (num % 2 == 0 && set.contains(num / 2)) {
                    return true;
                }
                set.add(num);
            }
        }
        return false;
    }

    public int getSum(int a, int b) {//不使用+/-实现两数之和
        if (b == 0) {
            return a;
        } else {
            return getSum(a ^ b, (a & b) << 1);
        }
    }

    /**
     * only one num appears once, others appear exactly twice.
     * find the one.
     *
     * @param nums
     * @return
     */
    public int singleNumber(int[] nums) {
        int res = 0;
        if (nums != null && nums.length != 0) {
            for (int num : nums) {
                res ^= num;
            }
        }
        return res;
    }

    /**
     * only one num appears once, others appear exactly three times.
     * find the one.
     *
     * @param nums
     * @return
     */
    public int singleNumber1(int[] nums) {
        int seen1 = 0;
        int seen2 = 0;
        for (int num : nums) {
            seen1 = ~seen2 & (seen1 ^ num);
            seen2 = ~seen1 & (seen2 ^ num);
        }
        return seen1;
    }

    /**
     * [9,6,4,2,3,5,7,0,1] -> 8, which is missing from the contineous array.
     *
     * @param nums
     * @return
     */
    public int missingNumber(int[] nums) {
        int res = 0;
        if (nums != null) {
            for (int i = 0; i < nums.length; i++) {
                res ^= i;
                res ^= nums[i];
            }
            res ^= nums.length;
        }
        return res;
    }

    public int findDuplicate(int[] nums) {
        if (nums != null && nums.length >= 2) {
            int i = 0;
            int prepre = 0;
            int pre = 0;
            while (nums[i] != prepre) {
                prepre = pre;
                pre = i;
                i = nums[i];
            }
        }
        return 0;
    }

    public double myPow(double x, int n) {
        if (n < 0) {
            x = 1 / x;
        }
        return solv(x, n);
    }

    double solv(double x, int n) {
        if (n == 0) {
            return 1.0;
        }
        double value = solv(x, n / 2);
        if (n % 2 == 0) {
            return value * value;
        }
        return value * value * x;
    }


    public int[] sortByBits(int[] arr) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int num : arr) {
            int count = count(num);
            map.compute(count, (k, v) -> {
                if (v == null) {
                    List<Integer> e = new ArrayList<>();
                    e.add(num);
                    return e;
                } else {
                    v.add(num);
                    return v;
                }
            });
        }
        int index = 0;
        for (Integer key : map.keySet()) {
            Collections.sort(map.get(key));
            for (Integer num : map.get(key)) {
                arr[index++] = num;
            }
        }
        return arr;
    }

    public void quickSort(int[] arr, int counts[], int start, int end) {
        if (start >= 0 && end <= counts.length - 1 && start < end) {
            int low = start;
            int high = end;
            //记录一个关键值 spiltKey
            int spiltKey = counts[start];
            int splitValue = arr[start];

            //遍历数组
            while (start < end) {
                //从后向前比较，直到遇到比spiltKey小的数
                while (start < end && counts[end] >= spiltKey) end--;
                //交换位置
                counts[start] = counts[end];
                arr[start] = arr[end];
                //从前向后比较，直到遇到有比spiltKey大的数
                while (start < end && counts[start] <= spiltKey) start++;
                //交换位置
                counts[end] = counts[start];
                arr[end] = arr[start];
            }

            /**
             * 此时第一次循环比较结束，关键值的位置已经确定了。
             * 左边的值都比关键值小，右边的值都比关键值大，但是两边的顺序还有可能是不一样的，进行下面的递归调用
             */
            counts[end] = spiltKey;
            arr[end] = splitValue;
            //递归，再对左半部分排序
            quickSort(arr, counts, low, start - 1);
            //递归，再对右半部分排序
            quickSort(arr, counts, start + 1, high);
        }
    }

    int count(int num) {
        int count = 0;
        while (num != 0) {
            if ((num & 1) != 0) {
                count++;
            }
            num >>= 1;
        }
        return count;
    }

    public int kthGrammar(int N, int K) {
        return Integer.bitCount(K - 1) % 2;
    }

    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {//面试题15
        int ans = 0;
        while (n != 0) {
            ans += n & 1;
            n >>>= 1;
        }
        return ans;
    }

    public int hammingWeight1(int n) {//面试题15
        int ans = 0;
        while (n != 0) {
            ans++;
            n &= n - 1;
        }
        return ans;
    }
}
