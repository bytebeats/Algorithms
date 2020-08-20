package me.bytebeats.algo.arrays;

import java.util.*;

import me.bytebeats.algs.ds.TreeNode;

public class ArraysQuiz {

    public int findPeakElement(int[] nums) {
        int index = -1;
        if (nums != null && nums.length > 0) {
            if (nums.length == 1) {
                return 0;
            } else if (nums.length == 2) {
                if (nums[0] > nums[1]) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                for (int i = 2; i < nums.length; i++) {
                    if (nums[i - 1] > nums[i] && nums[i - 1] > nums[i - 2]) {
                        return i - 1;
                    }
                }
                if (index == -1) {
                    if (nums[0] > nums[nums.length - 1]) {
                        return 0;
                    } else {
                        return nums.length - 1;
                    }
                }
            }
        }
        return index;
    }

    /**
     * recursive solution
     *
     * @param A
     * @param B
     * @return
     */
    public int maxUncrossedLines(int[] A, int[] B) {
        int count = 0;
        return count;
    }

    public int smallestDistancePair(int[] nums, int k) {
        int d = -1;
        TreeMap<Integer, Integer> map = new TreeMap<>();//<distance, count>
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                int absV = Math.abs(nums[i] - nums[j]);
                if (map.containsKey(absV)) {
                    map.put(absV, map.get(absV) + 1);
                } else {
                    map.put(absV, 1);
                }
            }
        }
        int count = 0;
        for (int key : map.keySet()) {
            count += map.get(key);
            if (k <= count) {
                d = key;
                break;
            }
        }
        return d;
    }

    public int smallestDistancePair1(int[] nums, int k) {
        int[] distances = new int[nums.length * (nums.length - 1) / 2];
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                int absV = Math.abs(nums[i] - nums[j]);
                distances[count] = absV;
                count++;
            }
        }
        quickSort(distances, 0, distances.length - 1);
        return distances[k - 1];
    }

    public int smallestDistancePair2(int[] nums, int k) {
        quickSort(nums, 0, nums.length - 1);
        int left = 0;
        int right = nums[nums.length - 1] - nums[0];
        int mid;
        while (left < right) {
            mid = left + (right - left) / 2;
            if (possible(mid, k, nums)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    boolean possible(int val, int k, int[] nums) {
        int i = 0, count = 0;
        for (int j = 1; j < nums.length; j++) {
            while (i < j && nums[j] - nums[i] > val) {
                i++;
            }
            count += j - i;
        }
        return count >= k;
    }

    public int kthSmallest(int[][] matrix, int k) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0 || k < 1 || k > matrix.length * matrix[0].length) {
            return Integer.MIN_VALUE;
        }
        int l = matrix.length;
        quickSort1(matrix, 0, l * l - 1);
        return elementAt(matrix, k - 1);
    }

    public void printMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return;
        }
        for (int[] arr : matrix) {
            for (int e : arr) {
                System.out.print(e);
                System.out.print(", ");
            }
            System.out.println("");
        }
    }

    public int adjustMatrix(int[][] matrix, int s, int e) {
        int i = s;
        int j = e;
        int tmp = elementAt(matrix, s);
        while (i < j) {
            while (j > s && elementAt(matrix, j) >= tmp) {
                j--;
            }
            if (i < j) {
                setElementAt(matrix, i, elementAt(matrix, j));
                i++;
            }
            while (i < e && elementAt(matrix, i) < tmp) {
                i++;
            }
            if (i < j) {
                setElementAt(matrix, j, elementAt(matrix, i));
                j--;
            }
        }
        setElementAt(matrix, i, tmp);
        return i;
    }

    public void quickSort(int[][] matrix, int s, int e) {
        if (s < e) {
            int i = adjustMatrix(matrix, s, e);
            quickSort(matrix, s, i - 1);
            quickSort(matrix, i + 1, e);
        }
    }

    public void quickSort1(int[][] matrix, int start, int end) {
        if (start >= 0 && end <= matrix.length * matrix.length - 1 && start < end) {
            int low = start;
            int high = end;
            int spiltKey = elementAt(matrix, start);
            while (start < end) {
                while (start < end && elementAt(matrix, end) >= spiltKey) end--;
                setElementAt(matrix, start, elementAt(matrix, end));
                while (start < end && elementAt(matrix, start) <= spiltKey) start++;
                setElementAt(matrix, end, elementAt(matrix, start));
            }

            setElementAt(matrix, end, spiltKey);
            quickSort1(matrix, low, start - 1);
            quickSort1(matrix, start + 1, high);
        }
    }

    void setElementAt(int[][] matrix, int index, int value) {
        int x = getX(matrix, index);
        int y = getY(matrix, index);
        matrix[x][y] = value;
    }

    int getX(int[][] matrix, int index) {
        int n = matrix.length;
        return index / n;
    }

    int getY(int[][] matrix, int index) {
        int n = matrix[0].length;
        return index % n;
    }

    int elementAt(int[][] matrix, int index) {
        int x = getX(matrix, index);
        int y = getY(matrix, index);
        return matrix[x][y];
    }

    public int adjustArray(int[] array, int start, int end) {
        int i = start, j = end;
        int v = array[start];//Math.max(Math.max(array[start], array[end]), array[(start + end) / 2]);
        while (i < j) {
            while (j > start && v <= array[j]) {
                j--;
            }
            if (i < j) {
                array[i] = array[j];
                i++;
            }
            while (i < end && v > array[i]) {
                i++;
            }
            if (i < j) {
                array[j] = array[i];
                j--;
            }
        }
        array[i] = v;
        return i;
    }

    public void quickSort(int[] array, int start, int end) {
        if (start < end) {
            int index = adjustArray(array, start, end);
            quickSort(array, start, index - 1);
            quickSort(array, index + 1, end);
        }
    }

    public int sumOfLeftLeaves(TreeNode root) {
        if (root != null) {
            if (root.left != null) {
                if (root.left.left == null && root.left.right == null) {
                    return root.left.val + sumOfLeftLeaves(root.right);
                } else {
                    return sumOfLeftLeaves(root.left) + sumOfLeftLeaves(root.right);
                }
            } else {
                return sumOfLeftLeaves(root.right);
            }
        }
        return 0;
    }

    public void QKSort(int[] a, int start, int end) {
        if (a.length < 0) {
            return;
        }
        if (start >= end) {
            return;
        }
        int left = start;
        int right = end;
        int temp = a[left];
        while (left < right) {
            while (left < right && a[right] > temp) {
                right--;
            }
            a[left] = a[right];
            while (left < right && a[left] < temp) {
                left++;
            }
            a[right] = a[left];
        }
        a[left] = temp;
        QKSort(a, start, left - 1);
        QKSort(a, left + 1, end);
    }

    public void quickSort1(int a[], int start, int end) {
        if (start >= 0 && end <= a.length - 1 && start < end) {
            int low = start;
            int high = end;
            //记录一个关键值 spiltKey
            int spiltKey = a[start];

            //遍历数组
            while (start < end) {
                //从后向前比较，直到遇到比spiltKey小的数
                while (start < end && a[end] >= spiltKey) end--;
                //交换位置
                a[start] = a[end];
                //从前向后比较，直到遇到有比spiltKey大的数
                while (start < end && a[start] <= spiltKey) start++;
                //交换位置
                a[end] = a[start];
            }

            /**
             * 此时第一次循环比较结束，关键值的位置已经确定了。
             * 左边的值都比关键值小，右边的值都比关键值大，但是两边的顺序还有可能是不一样的，进行下面的递归调用
             */
            a[end] = spiltKey;
            //递归，再对左半部分排序
            quickSort1(a, low, start - 1);
            //递归，再对右半部分排序
            quickSort1(a, start + 1, high);
        }
    }

    public int sumSubarrayMins(int[] A) {
        int sum = 0;
        int modulo = power(10, 9) + 7;
        if (A != null && A.length > 0) {
            for (int i = 1; i <= A.length; i++) {//subarray length;
                for (int j = 0; j <= A.length - i; j++) {
                    sum += getMinE(A, j, j + i);
                    sum %= modulo;
                }
            }
        }
        return sum;
    }

    int power(int p, int i) {
        int r = 1;
        while (i-- > 0) {
            r *= p;
        }
        return r;
    }

    int getMinE(int[] A, int s, int e) {
        int minE = Integer.MAX_VALUE;
        for (int i = s; i < e; i++) {
            if (minE > A[i]) {
                minE = A[i];
            }
        }
        return minE;
    }

    public List<Integer> majorityElement(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if (nums != null || nums.length > 0) {
            Map<Integer, Integer> map = new HashMap<>();
            for (int i : nums) {
                if (map.containsKey(i)) {
                    map.put(i, map.get(i) + 1);
                } else {
                    map.put(i, 1);
                }
            }
            int splittor = nums.length / 3;
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                if (entry.getValue() > splittor) {
                    res.add(entry.getKey());
                }
            }
        }
        return res;
    }

    public int totalFruit(int[] tree) {
        if (tree == null || tree.length == 0) {
            return 0;
        }
        if (tree.length == 1) {
            return 1;
        }
        int count = 0;
        int count1 = 0;
        int count2 = 0;
        int type1 = -1;
        int type2 = -1;
        for (int type : tree) {
            if (type1 == -1) {
                type1 = type;
                count1++;
            } else if (type2 == -1) {
                if (type1 != type) {
                    type2 = type;
                    count2++;
                } else {
                    count1++;
                }
            } else {
                if (type == type1) {
                    count1++;
                } else if (type == type2) {
                    count2++;
                } else {
                    if (count < count1 + count2) {
                        count = count1 + count2;
                    } else if (count == 0) {
                        count = count1 + count2;
                    }
                    type1 = type2;
                    type2 = type;
                    count1 = count2;
                    count2 = 1;
                }
            }
        }
        return count;
    }

    public int[] twoSum(int[] nums, int target) {
        int[] indices = new int[2];
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int n = target - nums[i];
            if (map.containsKey(n)) {
                indices[0] = map.get(n);
                indices[1] = i;
                break;
            } else {
                map.put(nums[i], i);
            }
        }
        return indices;
    }

    public int twoSumLessThanK(int[] A, int K) {
        int sum = -1;
        if (A != null || A.length < 2) {
            System.out.println(Arrays.toString(A));
            Arrays.sort(A);
            System.out.println(Arrays.toString(A));
            int i = 0, j = A.length - 1;
            int tmp = 0;
            while (i < j) {
                tmp = A[i] + A[j];
                if (tmp >= K) {
                    j--;
                } else {
                    if (tmp > sum) {
                        sum = tmp;
                    }
                    i++;
                }
            }
        }
        return sum;
    }

    public int[] twoSum2(int[] numbers, int target) {//numbers are ascending
        int[] indices = new int[2];
        int i = 0, j = numbers.length - 1;
        int sum = 0;
        while (i < j) {
            sum = numbers[i] + numbers[j];
            if (sum == target) {
                indices[0] = i;
                indices[1] = j;
                break;
            } else if (sum > target) {
                j--;
            } else {
                i++;
            }
        }
        return indices;
    }

    public int[] prisonAfterNDays(int[] cells, int N) {
        if (cells == null || cells.length == 0 || N < 1) {
            return null;
        }
        int[] copy = new int[cells.length];
        for (int i = 0; i < N; i++) {
            if (i % 2 == 0) {
                for (int j = 0; j < cells.length; j++) {
                    if (j == 0 || j == cells.length - 1) {
                        copy[j] = 0;
                    } else {
                        if (cells[j - 1] == cells[j + 1]) {
                            copy[j] = 1;
                        } else {
                            copy[j] = 0;
                        }
                    }
                }
            } else {
                for (int j = 0; j < copy.length; j++) {
                    if (j == 0 || j == copy.length - 1) {
                        cells[j] = 0;
                    } else {
                        if (copy[j - 1] == copy[j + 1]) {
                            cells[j] = 1;
                        } else {
                            cells[j] = 0;
                        }
                    }
                }
            }
        }
        if (N % 2 == 0) {
            return copy;
        } else {
            return cells;
        }
    }

    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> res = new ArrayList<>(numRows);
        for (int i = 0; i < numRows; i++) {
            if (i == 0) {
                List<Integer> item = new ArrayList<>(1);
                item.add(1);
                res.add(item);
            } else if (i == 1) {
                List<Integer> item = new ArrayList<>(2);
                item.add(1);
                item.add(1);
                res.add(item);
            } else {
                List<Integer> pre = res.get(i - 1);
                List<Integer> cur = new ArrayList<>(i + 1);
                for (int j = 0; j < pre.size(); j++) {
                    if (j == 0) {
                        cur.add(1);
                    } else {
                        cur.add(pre.get(j - 1) + pre.get(j));
                        if (j == pre.size() - 1) {
                            cur.add(1);
                        }
                    }
                }
                res.add(cur);
            }
        }
        return res;
    }

    public int maxSubArray(int[] nums) {//面试题16.17
        int sum = Integer.MIN_VALUE;
        if (nums != null) {
            int tmp = 0;
            for (int i = 0; i < nums.length; i++) {
                if (tmp < 0) {
                    tmp = nums[i];
                } else {
                    tmp += nums[i];
                }
                if (sum < tmp) {
                    sum = tmp;
                }
            }
        }
        return sum;
    }

    public int removeDuplicates(int[] nums) {
        if (nums == null) {
            return -1;
        }
        if (nums.length < 2) {
            return nums.length;
        }
        int size = nums.length;
        //dp solution, two points, j represents the starting index to place latter num, i represents the loop index and compare the consecutive elements.
        int j = -1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                if (j < 0) {
                    j = i;
                }
                size--;
            } else {
                if (j > -1) {
                    nums[j] = nums[i];
                    j++;
                }
            }
        }
        return size;
    }

//    public int removeDuplicates(int[] nums) {
//        if (nums == null) {
//            return -1;
//        }
//        if (nums.length < 2) {
//            return nums.length;
//        }
//        List<Integer> elements = new ArrayList<>();
//        for (int i = 0; i < nums.length; i++) {
//            if (elements.isEmpty() || elements.get(elements.size() - 1) != nums[i]) {
//                elements.add(nums[i]);
//            } else {
//                continue;
//            }
//        }
//        for (int i = 0; i < elements.size(); i++) {
//            nums[i] = elements.get(i);
//        }
//        return elements.size();
//    }

    public int removeElement(int[] nums, int val) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int size = nums.length;
        for (int i = -1, j = 0; j < nums.length; j++) {
            if (nums[j] == val) {
                if (i < 0) {
                    i = j;
                }
                size--;
            } else {
                if (i > -1) {
                    nums[i] = nums[j];
                    i++;
                }
            }
        }
        return size;
    }

    public int maxSumAfterPartitioning(int[] A, int K) {
        if (A == null || A.length == 0) {
            return 0;
        }
        int maxSum = Integer.MIN_VALUE;
        int count = A.length / K;
        int[] a1 = new int[A.length];
        for (int i = 0; i < count; i++) {
            int s = i * K;
            int e = s + K;
            int max = Integer.MIN_VALUE;
            for (int j = s; j < e; j++) {
                if (A[j] > max) {
                    max = A[j];
                }
            }
            for (int j = s; j < e; j++) {
                a1[j] = max;
            }
        }
        if (count * K < a1.length) {
            int max = Integer.MIN_VALUE;
            for (int j = count * K; j < a1.length; j++) {
                if (A[j] > max) {
                    max = A[j];
                }
            }
            for (int j = count * K; j < a1.length; j++) {
                a1[j] = max;
            }
        }
        int sum1 = Arrays.stream(a1).sum();
        if (sum1 > maxSum) {
            maxSum = sum1;
        }
        int[] a2 = new int[A.length];
        for (int i = 0; i < count; i++) {
            int s = A.length - i * K - 1;
            int e = s - K;
            int max = Integer.MIN_VALUE;
            for (int j = e; j > s; j--) {
                if (A[j] > max) {
                    max = A[j];
                }
            }
            for (int j = e; j > s; j--) {
                a2[j] = max;
            }
        }
        if (count * K < a2.length) {
            int max = Integer.MIN_VALUE;
            for (int j = count * K; j < a2.length; j++) {
                if (A[A.length - j - 1] > max) {
                    max = A[A.length - j - 1];
                }
            }
            for (int j = count * K; j < a2.length; j++) {
                a2[A.length - j - 1] = max;
            }
        }
        int sum2 = Arrays.stream(a2).sum();
        if (sum2 > maxSum) {
            maxSum = sum2;
        }

        int[] a3 = new int[A.length];
        int i = 0, j = A.length - 1;
        while (i + K - 1 < j - K + 1) {

        }
        int sum3 = Arrays.stream(a3).sum();
        if (sum3 > maxSum) {
            maxSum = sum3;
        }

        return maxSum;
    }

    public boolean increasingTriplet(int[] nums) {
        if (nums == null || nums.length < 3) {
            return false;
        }
        List<List<Integer>> is = new ArrayList<>();
        for (int num : nums) {
            if (is.isEmpty()) {
                List<Integer> sub = new ArrayList<>();
                sub.add(num);
                is.add(sub);
            } else {
                List<List<Integer>> tmp = new ArrayList<>(is);
                for (List<Integer> tmpSub : tmp) {
                    if (tmpSub.get(tmpSub.size() - 1) < num) {
                        List<Integer> sub = new ArrayList<>(tmpSub);
                        sub.add(num);
                        if (sub.size() == 3) {
                            return true;
                        }
                        is.add(sub);
                    }
                }
                List<Integer> sub = new ArrayList<>();
                sub.add(num);
                is.add(sub);
            }
        }
        System.out.println(Arrays.toString(is.toArray()));
        return false;
    }

    public String numberToWords(int num) {
        if (num == 0) {
            return "Zero";
        }
        List<String> res = new ArrayList<>();
        int unit = 0;
        while (num > 0) {
            StringBuilder sb = new StringBuilder();
            int c = num % 1000;
            sb.append(tripletToString(c));
            sb.append(" ");
            if (c != 0) {
                sb.append(numToUnit(unit));
            }
            num /= 1000;
            unit++;
            res.add(sb.toString());
        }
        Collections.reverse(res);
        StringBuilder sb = new StringBuilder();
        for (String str : res) {
            sb.append(str);
            sb.append(" ");
        }
        return sb.toString().trim().replaceAll(" +", "");
    }

    String tripletToString(int num) {
        StringBuilder sb = new StringBuilder();
        if (num > 99) {
            sb.append(numTo1(num / 100));
            sb.append(" ");
            sb.append("Hundred");
            num %= 100;
        }
        if (num > 20) {
            sb.append(numTo10(num / 10));
            sb.append(" ");
            sb.append(numTo1(num % 10));
        } else {
            sb.append(numTo1(num));
        }
        return sb.toString();
    }

    String numTo1(int num) {
        if (num == 1) {
            return "One";
        } else if (num == 2) {
            return "Two";
        } else if (num == 3) {
            return "Three";
        } else if (num == 4) {
            return "Four";
        } else if (num == 5) {
            return "Five";
        } else if (num == 6) {
            return "Six";
        } else if (num == 7) {
            return "Seven";
        } else if (num == 8) {
            return "Eight";
        } else if (num == 9) {
            return "Nine";
        } else if (num == 10) {
            return "Ten";
        } else if (num == 11) {
            return "Eleven";
        } else if (num == 12) {
            return "Twelve";
        } else if (num == 13) {
            return "Thirteen";
        } else if (num == 14) {
            return "Fourteen";
        } else if (num == 15) {
            return "Fifteen";
        } else if (num == 16) {
            return "Sixteen";
        } else if (num == 17) {
            return "Seventeen";
        } else if (num == 18) {
            return "Eighteen";
        } else if (num == 19) {
            return "Nineteen";
        } else {
            return "";
        }
    }

    String numTo10(int num) {
        if (num == 2) {
            return "Twenty";
        } else if (num == 3) {
            return "Thirty";
        } else if (num == 4) {
            return "Forty";
        } else if (num == 5) {
            return "Fifty";
        } else if (num == 6) {
            return "Sixty";
        } else if (num == 7) {
            return "Seventy";
        } else if (num == 8) {
            return "Eighty";
        } else if (num == 9) {
            return "Ninety";
        } else {
            return "";
        }
    }

    String numToUnit(int num) {
        if (num == 1) {
            return "Thousand";
        } else if (num == 2) {
            return "Million";
        } else if (num == 3) {
            return "Billion";
        } else {
            return "";
        }
    }

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums != null && nums.length >= 3) {
            Arrays.sort(nums);
            int left = 0, right = 0;
            int sum = 0;
            for (int i = 0; i < nums.length - 2; i++) {
                left = i + 1;
                right = nums.length - 1;
                while (left < right) {
                    sum = nums[i] + nums[left] + nums[right];
                    if (sum > 0) {
                        right--;
                    } else if (sum < 0) {
                        left++;
                    } else {
                        List<Integer> e = new ArrayList<>();
                        e.add(nums[i]);
                        e.add(nums[left]);
                        e.add(nums[right]);
                        res.add(e);
                        while (left < right && nums[left] == nums[left + 1]) {
                            left++;
                        }
                        while (left < right && nums[right] == nums[right - 1]) {
                            right--;
                        }
                        left++;
                        right--;
                    }
                }
                if (i < nums.length - 1 && nums[i] == nums[i + 1]) {
                    i++;
                }
            }
        }
        return res;
    }

    public int threeSumSmaller(int[] nums, int target) {
        int count = 0;
        if (nums != null && nums.length >= 3) {
            Arrays.sort(nums);
            int left = -1, right = -1, sum = -1;
            for (int i = 0; i < nums.length - 2; i++) {
                left = i + 1;
                right = nums.length - 1;
                while (left < right) {
                    sum = nums[i] + nums[left] + nums[right];
                    if (sum >= target) {
                        right--;
                    } else {
                        count += right - left;
                        /**
                         * if any element has duplicate, remove comments below.
                         */
//                        while (left < right && nums[left] == nums[left + 1]) {
//                            left++;
//                        }
//                        while (left < right && nums[right] == nums[right - 1]) {
//                            right--;
//                        }
                        left++;
                    }
                }
//                while (i < nums.length - 1 && nums[i] == nums[i + 1]) {
//                    i++;
//                }
            }
        }
        return count;
    }

    public int threeSumClosest(int[] nums, int target) {
        int res = 0;
        if (nums != null && nums.length > 2) {
            Arrays.sort(nums);
            int abs = Integer.MAX_VALUE;
            int left = 0, right = 0, sum;
            for (int i = 0; i < nums.length - 2; i++) {
                left = i + 1;
                right = nums.length - 1;
                while (left < right) {
                    sum = nums[i] + nums[left] + nums[right];
                    if (Math.abs(sum - target) < abs) {
                        abs = Math.abs(sum - target);
                        res = sum;
                    }
                    if (sum > target) {
                        right--;
                    } else if (sum < target) {
                        left++;
                    } else {
                        break;
                    }
                }
            }
        }
        return res;
    }

    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums != null && nums.length > 3) {
            Arrays.sort(nums);
            int left = 0, right = 0, sum = 0, newTarget = 0;
            for (int i = 0; i < nums.length - 3; i++) {
                newTarget = target - nums[i];
                for (int j = i + 1; j < nums.length - 2; j++) {
                    left = j + 1;
                    right = nums.length - 1;
                    while (left < right) {
                        sum = nums[j] + nums[left] + nums[right];
                        if (sum > newTarget) {
                            right--;
                        } else if (sum < newTarget) {
                            left++;
                        } else {
                            List<Integer> e = new ArrayList<>(3);
                            e.add(nums[i]);
                            e.add(nums[j]);
                            e.add(nums[left]);
                            e.add(nums[right]);
                            res.add(e);
                            while (left < right && nums[left] == nums[left + 1]) {//skip duplicate element.
                                left++;
                            }
                            while (left < right && nums[right] == nums[right - 1]) {//skip duplicate element.
                                right--;
                            }
                            left++;
                            right--;
                        }
                    }
                    while (j < nums.length - 2 && nums[j] == nums[j + 1]) {//skip duplicate element.
                        j++;
                    }
                }
                while (i < nums.length - 2 && nums[i] == nums[i + 1]) {//skip duplicate element.
                    i++;
                }
            }
        }
        return res;
    }

    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        int count = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B.length; j++) {
                map.compute(A[i] + B[j], (k, v) -> v == null ? 1 : v + 1);
            }
        }
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < D.length; j++) {
                int key = C[i] + D[j];
                if (map.containsKey(-key)) {
                    count += map.get(-key);
                }
            }
        }
        return count;
    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new LinkedList<>();
        if (nums != null && nums.length > 0) {
            permute(nums, new boolean[nums.length], res, new ArrayList<>());
        }
        return res;
    }

    void permute(int[] nums, boolean[] used, List<List<Integer>> res, List<Integer> tmp) {
        if (tmp.size() == nums.length) {
            res.add(new ArrayList<>(tmp));
        } else {
            for (int i = 0; i < nums.length; i++) {
                if (used[i]) continue;
                used[i] = true;
                tmp.add(nums[i]);
                permute(nums, used, res, tmp);
                tmp.remove(tmp.size() - 1);
                used[i] = false;
            }
        }
    }

    public int countNegatives(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
            return 0;
        }
        int count = 0;
        int x = grid.length;
        int y = grid[0].length;
        if (grid[x - 1][y - 1] < 0) {
            for (int i = x - 1; i > -1; i--) {
                if (grid[i][y - 1] >= 0) {
                    break;
                }
                for (int j = y - 1; j > -1; j--) {
                    if (grid[i][y] < 0) {
                        count++;
                    } else {
                        break;
                    }
                }
            }
        }
        return count;
    }

    public int maxEvents(int[][] events) {
        int count = 0;
        if (events != null && events.length != 0 && events[0] != null && events[0].length != 0) {
            System.out.println(Arrays.toString(events));
            Arrays.sort(events, new Comparator<int[]>() {
                @Override
                public int compare(int[] o1, int[] o2) {
                    if (o1[0] < o2[0]) {
                        return 1;
                    } else if (o1[0] == o2[0]) {
                        if (o1[1] < o2[1]) {
                            return 1;
                        } else {
                            return -1;
                        }
                    } else {
                        return -1;
                    }
                }
            });
            int[] pre = events[0];
            int[] cur = null;
            count++;
            for (int i = 1; i < events.length; i++) {
                cur = events[i];
                if (pre[0] < cur[0]) {
                    count++;
                } else if (pre[0] == cur[0]) {

                }

                pre = cur;
            }
        }
        return count;
    }

    public int findDuplicate(int[] nums) {

        for (int i = 0; i < nums.length; i++) {
            int j = Math.abs(nums[i]) - 1;
            if (nums[j] < 0) {
                return Math.abs(nums[i]);
            } else {
                nums[j] = -nums[j];
            }
        }

        return -1;
    }

    public int findShortestSubArray(int[] nums) {
        Map<Integer, Integer> count = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            count.put(nums[i], count.getOrDefault(nums[i], 0) + 1);
        }
        int degree = Collections.max(count.values());

        int slow = 0;
        int minLength = nums.length;
        Map<Integer, Integer> windowCount = new HashMap<>();
        int windowDegree = 0;

        for (int fast = 0; fast < nums.length; fast++) {
            windowCount.put(nums[fast], windowCount.getOrDefault(nums[fast], 0) + 1);
            windowDegree = Math.max(windowDegree, windowCount.get(nums[fast]));
            while (windowDegree == degree) {
                minLength = Math.min(minLength, fast - slow + 1);

                windowCount.put(nums[slow], windowCount.get(nums[slow]) - 1);
                slow++;
                if (nums[slow - 1] == nums[fast]) {
                    windowDegree--;
                }
            }
        }
        return minLength;
    }

    public int coinChange2(int[] coins, int amount) {
        int max = amount + 1;
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, max);
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            for (int j = 0; j < coins.length; j++) {
                if (coins[j] <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

    public int coinChange(int[] coins, int amount) {
        if (amount < 1) return 0;
        return coinChange(coins, amount, new int[amount]);
    }

    private int coinChange(int[] coins, int rem, int[] count) {
        if (rem < 0) return -1;
        if (rem == 0) return 0;
        if (count[rem - 1] != 0) return count[rem - 1];
        int min = Integer.MAX_VALUE;
        for (int coin : coins) {
            int res = coinChange(coins, rem - coin, count);
            if (res >= 0 && res < min)
                min = 1 + res;
        }
        count[rem - 1] = (min == Integer.MAX_VALUE) ? -1 : min;
        return count[rem - 1];
    }

    int[] dr = new int[]{-1, 0, 1, 0};
    int[] dc = new int[]{0, -1, 0, 1};

    public int orangesRotting(int[][] grid) {
        int R = grid.length, C = grid[0].length;

        // queue : all starting cells with rotten oranges
        Queue<Integer> queue = new ArrayDeque();
        Map<Integer, Integer> depth = new HashMap();
        for (int r = 0; r < R; ++r)
            for (int c = 0; c < C; ++c)
                if (grid[r][c] == 2) {
                    int code = r * C + c;
                    queue.add(code);
                    depth.put(code, 0);
                }

        int ans = 0;
        while (!queue.isEmpty()) {
            int code = queue.remove();
            int r = code / C, c = code % C;
            for (int k = 0; k < 4; ++k) {
                int nr = r + dr[k];
                int nc = c + dc[k];
                if (0 <= nr && nr < R && 0 <= nc && nc < C && grid[nr][nc] == 1) {
                    grid[nr][nc] = 2;
                    int ncode = nr * C + nc;
                    queue.add(ncode);
                    depth.put(ncode, depth.get(code) + 1);
                    ans = depth.get(ncode);
                }
            }
        }

        for (int[] row : grid)
            for (int v : row)
                if (v == 1)
                    return -1;
        return ans;

    }

    public int[] prisonAfterNDays1(int[] cells, int N) {//957
        Map<Integer, Integer> seen = new HashMap();

        // state  = integer representing state of prison
        int state = 0;
        for (int i = 0; i < 8; ++i) {
            if (cells[i] > 0)
                state ^= 1 << i;
        }

        // While days remaining, simulate a day
        while (N > 0) {
            // If this is a cycle, fast forward by
            // seen.get(state) - N, the period of the cycle.
            if (seen.containsKey(state)) {
                N %= seen.get(state) - N;
            }
            seen.put(state, N);

            if (N >= 1) {
                N--;
                state = nextDay(state);
            }
        }

        // Convert the state back to the required answer.
        int[] ans = new int[8];
        for (int i = 0; i < 8; ++i) {
            if (((state >> i) & 1) > 0) {
                ans[i] = 1;
            }
        }

        return ans;
    }

    public int nextDay(int state) {
        int ans = 0;

        // We only loop from 1 to 6 because 0 and 7 are impossible,
        // as those cells only have one neighbor.
        for (int i = 1; i <= 6; ++i) {
            if (((state >> (i - 1)) & 1) == ((state >> (i + 1)) & 1)) {
                ans ^= 1 << i;
            }
        }

        return ans;
    }

    public boolean canPlaceFlowers(int[] flowerbed, int n) {//605
        int i = 0, count = 0;
        while (i < flowerbed.length) {
            if (flowerbed[i] == 0 && (i == 0 || flowerbed[i - 1] == 0) && (i == flowerbed.length - 1 || flowerbed[i + 1] == 0)) {
                flowerbed[i++] = 1;
                count++;
            }
            if (count >= n)
                return true;
            i++;
        }
        return false;
    }

    public int maxProfit(int[] prices) {//309
        if (prices.length == 0) {
            return 0;
        }

        int n = prices.length;
        // f[i][0]: 手上持有股票的最大收益
        // f[i][1]: 手上不持有股票，并且处于冷冻期中的累计最大收益
        // f[i][2]: 手上不持有股票，并且不在冷冻期中的累计最大收益
        int[][] f = new int[n][3];
        f[0][0] = -prices[0];
        for (int i = 1; i < n; ++i) {
            f[i][0] = Math.max(f[i - 1][0], f[i - 1][2] - prices[i]);
            f[i][1] = f[i - 1][0] + prices[i];
            f[i][2] = Math.max(f[i - 1][1], f[i - 1][2]);
        }
        return Math.max(f[n - 1][1], f[n - 1][2]);
    }

    public int[] fraction(int[] cont) {//LCP 02
        int n = cont.length;
        int[] cp = new int[n + 1];
        System.arraycopy(cont, 0, cp, 0, n);
        cp[n] = 1;
        int i = cp.length - 3;
        while (i != -1) {
            cp[i] = cp[i] * cp[i + 1] + cp[i + 2];
            i--;
        }
        return new int[]{cp[0], cp[1]};
    }

    public int maxProfit1(int[] prices) {//123
        // 特判
        int len = prices.length;
        if (len < 2) {
            return 0;
        }

        // [0, left] 包括 left 这个区间完成一次交易能够获得的最大利润
        int[] left = new int[len];
        int minVal = prices[0];
        for (int i = 1; i < len; i++) {
            left[i] = Math.max(left[i - 1], prices[i] - minVal);
            minVal = Math.min(minVal, prices[i]);
        }

        // [right, len - 1] 包括 left 这个区间完成一次交易能够获得的最大利润
        int[] right = new int[len];
        int maxVal = prices[len - 1];
        for (int i = len - 2; i >= 0; i--) {
            right[i] = Math.max(right[i + 1], maxVal - prices[i]);
            maxVal = Math.max(maxVal, prices[i]);
        }

        // 枚举间隙
        // [0, 1[, 2, 3,] 4, 5]
        // 这里有一个坑，有可能是只交易一次的场景
        int res = Math.max(left[len - 1], right[0]);
        for (int i = 1; i < len - 2; i++) {
            res = Math.max(res, left[i] + right[i + 1]);
        }
        return res;
    }

    int[] dirX = {0, 1, 0, -1, 1, 1, -1, -1};
    int[] dirY = {1, 0, -1, 0, 1, -1, 1, -1};

    public char[][] updateBoard(char[][] board, int[] click) {//529
        int x = click[0], y = click[1];
        if (board[x][y] == 'M') {
            // 规则 1
            board[x][y] = 'X';
        } else {
            bfs(board, x, y);
        }
        return board;
    }

    public void bfs(char[][] board, int sx, int sy) {
        Queue<int[]> queue = new LinkedList<int[]>();
        boolean[][] vis = new boolean[board.length][board[0].length];
        queue.offer(new int[]{sx, sy});
        vis[sx][sy] = true;
        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            int cnt = 0, x = pos[0], y = pos[1];
            for (int i = 0; i < 8; ++i) {
                int tx = x + dirX[i];
                int ty = y + dirY[i];
                if (tx < 0 || tx >= board.length || ty < 0 || ty >= board[0].length) {
                    continue;
                }
                // 不用判断 M，因为如果有 M 的话游戏已经结束了
                if (board[tx][ty] == 'M') {
                    ++cnt;
                }
            }
            if (cnt > 0) {
                // 规则 3
                board[x][y] = (char) (cnt + '0');
            } else {
                // 规则 2
                board[x][y] = 'B';
                for (int i = 0; i < 8; ++i) {
                    int tx = x + dirX[i];
                    int ty = y + dirY[i];
                    // 这里不需要在存在 B 的时候继续扩展，因为 B 之前被点击的时候已经被扩展过了
                    if (tx < 0 || tx >= board.length || ty < 0 || ty >= board[0].length || board[tx][ty] != 'E' || vis[tx][ty]) {
                        continue;
                    }
                    queue.offer(new int[]{tx, ty});
                    vis[tx][ty] = true;
                }
            }
        }
    }
}
