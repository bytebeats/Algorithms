package me.bytebeats.algorithms.strings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

public class StringsQuiz {

    public int numMatchingSubseq(String S, String[] words) {
        int count = 0;
        if (words != null) {
            for (String srt : words) {
                if (isSubsequence(srt, S)) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean isSubsequence(String s, String t) { // s is shorter than t
        if (s == null || t == null) {
            return false;
        }
        if (s.isEmpty()) {
            return true;
        }
        int count = 0;
        int sI = 0, tI = 0;
        while (sI < s.length() && tI < t.length()) {
            char sCh = s.charAt(sI);
            char tCh = t.charAt(tI);
            if (sCh == tCh) {
                count++;
                sI++;
                tI++;
            } else {
                tI++;
            }
        }
        return count == s.length();
    }

    public boolean rotateString(String A, String B) {
        if (A == null || B == null || A.isEmpty() || B.isEmpty() || A.length() != B.length()) {
            return false;
        }
        int length = A.length();

        for (int i = 1; i < length; i++) {
            String pre = A.substring(0, i);
            String suf = A.substring(i, length);
            if (B.endsWith(pre) && B.startsWith(suf) && B.indexOf(suf) == 0) {
                return true;
            }
        }
        return false;
    }

    public int numUniqueEmails(String[] emails) {
        if (emails == null || emails.length == 0) {
            return 0;
        }
        Set<String> set = new HashSet<>();
        for (String email : emails) {
            if (email.contains("@") && email.indexOf("@") > 0) {
                int atIndex = email.indexOf("@");
                String localName = email.substring(0, atIndex);
                String domainName = email.substring(atIndex);
                System.out.println("1" + localName + ", " + domainName);
                if (localName.contains("+") && localName.indexOf("+") > 0) {
                    int plusIndex = localName.indexOf("+");
                    localName = localName.substring(0, plusIndex);
                    System.out.println("2" + localName + ", " + domainName);
                }
                if (localName.contains(".")) {
                    localName = localName.replaceAll("\\.", "");
                    System.out.println("3" + localName + ", " + domainName);
                }
                System.out.println("4" + localName + ", " + domainName);
                System.out.println("-----");
                set.add(localName + domainName);
            }
        }
        System.out.println(set.size());
        return set.size();
    }

    public String mostCommonWord(String paragraph, String[] banned) {
        if (paragraph == null || paragraph.length() == 0 || banned == null || banned.length == 0) {
            System.out.println("null");
            return null;
        }
        System.out.println(paragraph);
        paragraph = paragraph.replaceAll("!", " ").replaceAll("\\?", " ").replaceAll("'", " ").replaceAll(",", " ").replaceAll(";", " ").replaceAll("\\.", " ");
        System.out.println(paragraph);
        String[] words = paragraph.split(" ");
        Map<String, Integer> map = new HashMap<>();
        Set<String> keys = Arrays.stream(banned).map(it -> it.toLowerCase()).collect(Collectors.toSet());
        for (String word : words) {
            System.out.println(word);
            if (word == null || word.isEmpty() || keys.contains(word.toLowerCase())) {
                continue;
            } else {
                String key = word.toLowerCase();
                if (map.containsKey(key)) {
                    map.put(key, map.get(key) + 1);
                } else {
                    map.put(key, 1);
                }
            }
        }
        int count = 0;
        String word = null;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
            if (entry.getValue() > count) {
                count = entry.getValue();
                word = entry.getKey();
            }
        }
        return word;
    }

    public List<String> invalidTransactions(String[] transactions) {
        if (transactions == null || transactions.length == 0) {
            return null;
        }
        Map<Boolean, List<String>> group = Arrays.stream(transactions).collect(Collectors.groupingBy(it -> {
            String[] split = it.split(",");
            return split[2].length() > 3;
        }, Collectors.toList()));
        if (!group.get(false).isEmpty()) {

        }
        for (int i = 0; i < group.get(false).size(); i++) {
            String transI = group.get(false).get(i);
            String[] transISplitted = transI.split(",");
            if (transISplitted == null || transISplitted.length != 4) {
                continue;
            }
            for (int j = i + 1; j < group.get(false).size(); j++) {
                String transJ = group.get(false).get(i);
                String[] transJSplitted = transJ.split(",");
                if (transJSplitted == null || transJSplitted.length != 4) {
                    continue;
                }
                if (transISplitted[0].equals(transJSplitted[0]) && !transISplitted[3].equals(transJSplitted[3]) && Math.abs(Double.parseDouble(transISplitted[1]) - Double.parseDouble(transJSplitted[1])) < 60) {
                    group.get(true).add(transI);
                }
            }
        }
        return group.get(true);
    }

    public String removeKdigits(String num, int k) {
        if (num == null || num.length() <= k) {
            return "0";
        }
        if (k <= 0) {
            return num;
        }
        while (k-- > 0) {
            int index = -1;
            for (int i = 0; i < num.length() - 1; i++) {
                if (num.charAt(i) > num.charAt(i + 1)) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                num = num.substring(0, num.length() - 1);
            } else {
                num = num.substring(0, index) + num.substring(index + 1);
            }
        }
        while (num.startsWith("0") && num.length() > 1) {
            num = num.substring(1);
        }
        return num;
    }

    public int strStr(String haystack, String needle) {
        if (haystack == null || needle == null) {
            return -1;
        }
        if (needle.length() == 0) {
            return 0;
        }
        return kmp(haystack, needle, next(needle));
    }

    int kmp(String haystack, String needle, int[] next) {
        for (int i = 0, j = 0; i < haystack.length(); i++) {
            while (j > 0 && haystack.charAt(i) != needle.charAt(j)) {
                j = next[j - 1];
            }
            if (haystack.charAt(i) == needle.charAt(j)) {
                j++;
            }
            if (j == needle.length()) {
                return i - j + 1;
            }
        }
        return -1;
    }

    int[] next(String needle) {
        int length = needle.length();
        int[] next = new int[length];
        int j = 0;
        for (int i = 1; i < length; i++) {
            while (j > 0 && needle.charAt(i) != needle.charAt(j)) {
                j = next[j - 1];
            }
            if (needle.charAt(i) == needle.charAt(j)) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }

    public int firstUniqChar(String s) {
        int index = -1;
        if (s != null && s.length() > 0) {
            Map<Character, Integer> nonRepeatingMap = new HashMap<>();
            Set<Character> repeatingSet = new HashSet<>();
            for (int i = 0; i < s.length(); i++) {
                char ch = s.charAt(i);
                if (repeatingSet.contains(ch)) {
                    continue;
                } else {
                    if (nonRepeatingMap.containsKey(ch)) {
                        nonRepeatingMap.remove(ch);
                        repeatingSet.add(ch);
                    } else {
                        nonRepeatingMap.put(ch, i);
                    }
                }
            }
            if (!nonRepeatingMap.isEmpty()) {
                List<Integer> indices = new ArrayList<>(nonRepeatingMap.values());
                Collections.sort(indices);
                return indices.get(0);
            }
        }
        return index;
    }

    public String frequencySort(String s) {
        if (s != null && s.length() > 0) {
            Map<Character, Integer> char2Frequency = new HashMap<>();
            for (char c : s.toCharArray()) {
                //check c contained in char2Frequency, if so, value +1; if not, set 1;
                char2Frequency.compute(c, (k, v) -> v == null ? 1 : v + 1);
            }
            StringBuilder sb = new StringBuilder();
            char2Frequency.entrySet()//entry set
                    .stream()//Stream<Entry>
                    .sorted((entry1, entry2) -> entry2.getValue() - entry1.getValue())//sort entry by value, descending order
                    .flatMap(entry -> Collections.nCopies(entry.getValue(), entry.getKey()).stream())//convert Stream<Entry> -> Stream<List<Character>> -> Stream<Character>
                    .collect(Collectors.toList())//collect characters into List
                    .forEach(ch -> sb.append(ch));//add each character into StringBuilder
            return sb.toString();
        }
        return s;
    }

    public List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> intFrequency = new HashMap<>();
        for (int num : nums) {
            intFrequency.compute(num, (key, value) -> value == null ? 1 : value + 1);
        }
        return intFrequency.entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue() - entry1.getValue())//sort entry stream by value, in descending order
                .map(entry -> entry.getKey())//convert entry stream to key stream
                .limit(k)//at most first k elements
                .collect(Collectors.toList());
    }

    public int findKthLargest(int[] nums, int k) {
//        return Arrays.stream(nums).sorted().skip(nums.length - k).findFirst().getAsInt();
        Arrays.sort(nums);
        return nums[nums.length - k - 1];
    }

    public List<String> topKFrequent(String[] words, int k) {
        if (words == null || words.length == 0) {
            return null;
        }
        Map<String, Integer> map = new TreeMap<>();
        for (String word : words) {
            if (word != null && !word.isEmpty()) {
                map.compute(word, (key, value) -> value == null ? 1 : value + 1);
            }
        }
        return map.entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue() - entry1.getValue())
                .map(entry -> entry.getKey())
                .limit(k)
                .collect(Collectors.toList());
    }

    void quickSort(char[] chars, int start, int end) {
        if (start >= 0 && end <= chars.length - 1 && start < end) {
            int low = start;
            int high = end;
            char spiltKey = chars[start];
            while (start < end) {
                while (start < end && chars[end] >= spiltKey) end--;
                chars[start] = chars[end];
                while (start < end && chars[start] <= spiltKey) start++;
                chars[end] = chars[start];
            }
            chars[end] = spiltKey;
            quickSort(chars, low, start - 1);
            quickSort(chars, start + 1, high);
        }
    }

    public boolean isPossible(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        List<List<Integer>> seqs = new ArrayList<>();
        for (int num : nums) {
            if (seqs.isEmpty()) {
                List<Integer> e = new ArrayList<>();
                e.add(num);
                seqs.add(e);
            } else {
                boolean added = false;
                for (int i = seqs.size() - 1; i > -1; i--) {
                    List<Integer> seq = seqs.get(i);
                    if (seq.get(seq.size() - 1) + 1 == num) {
                        seq.add(num);
                        added = true;
                        break;
                    }
                }
                if (!added) {
                    List<Integer> e = new ArrayList<>();
                    e.add(num);
                    seqs.add(e);
                }
            }
        }
        Map<Boolean, List<List<Integer>>> group = seqs.stream()
                .collect(Collectors.partitioningBy(it -> it.size() >= 3));
        return group.get(false).size() > 0;
    }

    public boolean isPossibleDivide(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0 || nums.length % k != 0) {
            return false;
        }
        List<List<Integer>> seqs = new ArrayList<>();
        Arrays.stream(nums).sorted().forEach(num -> {
            if (seqs.isEmpty()) {
                List<Integer> e = new ArrayList<>();
                e.add(num);
                seqs.add(e);
            } else {
                boolean added = false;
                for (List<Integer> seq : seqs) {
                    if (seq.size() >= k) {
                        continue;
                    } else if (seq.get(seq.size() - 1) + 1 == num) {
                        seq.add(num);
                        added = true;
                        break;
                    }
                }
                if (!added) {
                    List<Integer> e = new ArrayList<>();
                    e.add(num);
                    seqs.add(e);
                }
            }
        });
        Map<Boolean, List<List<Integer>>> group = seqs.stream()
                .collect(Collectors.partitioningBy(it -> it.size() == k));
        return group.get(false).size() == 0;
    }

    public int[][] kClosest(int[][] points, int K) {
        List<int[]> tail = Arrays.stream(points)
                .sorted((point1, point2) -> p(point1) - p(point2))
                .limit(K)
                .collect(Collectors.toList());
        return tail.toArray(new int[tail.size()][]);
    }

    int p(int[] point) {
        return point[0] * point[0] + point[1] * point[1];
    }

    public boolean canConstruct(String ransomNote, String magazine) {
        if (ransomNote == null || magazine == null) {
            return false;
        }
//        int[] abcCount = new int[26];
//        for (int i = 0; i < magazine.length(); i++) {
//            abcCount[magazine.charAt(i) - 'a']++;
//        }
//        for (int i = 0; i < ransomNote.length(); i++) {
//            if (--abcCount[ransomNote.charAt(i) - 'a'] < 0) {
//                return false;
//            }
//        }
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < magazine.length(); i++) {
            map.compute(magazine.charAt(i), (k, v) -> v == null ? 1 : v + 1);
        }
        for (int i = 0; i < ransomNote.length(); i++) {
            Character key = ransomNote.charAt(i);
            if (!map.containsKey(key) || map.get(key) < 1) {
                return false;
            } else {
                map.put(key, map.get(key) - 1);
            }
        }
        return true;
    }

    public int numberOfBoomerangs(int[][] points) {
        if (points == null || points.length == 0) {
            return 0;
        }
        int sum = 0;
        Map<Integer, Integer> distanceCount = new HashMap<>();
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                if (i != j) {
                    int d = distance(points[i], points[j]);
                    distanceCount.compute(d, (key, value) -> value == null ? 1 : value + 1);
                }
            }
            sum += distanceCount.values()
                    .stream()
                    .filter(v -> v > 1)
                    .mapToInt(v -> combination(v, 2))
                    .sum();
            distanceCount.clear();
        }
        return sum;
    }

    int combination(int n, int m) {
        int sum = 1;
        for (int i = n - m + 1; i <= n; i++) {
            sum *= i;
        }
        return sum;
    }

    int distance(int[] p1, int[] p2) {
        return (p1[0] - p2[0]) * (p1[0] - p2[0]) + (p1[1] - p2[1]) * (p1[1] - p2[1]);
    }

    public int lengthOfLongestSubstring(String s) {//3
        List<Set<Character>> subs = new ArrayList<>();
        Set<Character> element = new HashSet<>();
        subs.add(element);
        int start = 0;
        int max = 0;
        if (s != null && s.length() > 0) {
            for (int i = 0; i < s.length(); i++) {
                char ch = s.charAt(i);
                boolean addToLast = true;
                for (int j = start; j < subs.size(); j++) {
                    Set<Character> tmp = subs.get(j);
                    if (tmp.isEmpty()) {
                        tmp.add(ch);
                        addToLast = false;
                    } else if (tmp.contains(ch)) {
                        start = j + 1;
                        addToLast = true;
                    } else {
                        addToLast = true;
                        tmp.add(ch);
                    }
                    if (tmp.size() > max) {
                        max = tmp.size();
                    }
                }
                if (!addToLast) {
                    continue;
                }
                Set<Character> newE = new HashSet<>();
                newE.add(ch);
                subs.add(newE);
            }
        }
        return max;
    }

    public String longestPalindrome(String s) {//5, 最长回文子串
        String res = "";
        if (s != null && s.length() > 0) {
            String tmp = "";
            for (int i = 0; i < s.length(); ) {
                int j = i;
                while (j < s.length() && s.charAt(i) == s.charAt(j)) {
                    j++;
                }
                tmp = s.substring(i, j);
                int k = 1;
                while (i - k > -1 && j + k - 1 < s.length()) {
                    if (s.charAt(i - k) == s.charAt(j + k - 1)) {
                        tmp = s.substring(i - k, j + k);
                        k++;
                    } else {
                        break;
                    }
                }
                if (tmp.length() > res.length()) {
                    res = tmp;
                }
                i = j;
            }
        }
        return res;
    }

    public int longestPalindrome1(String s) {//最长回文子串长度
        if (s == null || s.length() == 0) {
            return 0;
        }
        return maxPalindromeLength(manacherString(s));
    }

    String manacherString(String src) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < src.length(); i++) {
            sb.append('#');
            sb.append(src.charAt(i));
        }
        sb.append('#');
        System.out.println(sb.toString());
        return sb.toString();
    }

    int maxPalindromeLength(String mnchr) {
        int[] radius = new int[mnchr.length()];
        int max = Integer.MIN_VALUE;
        int R = -1;
        int C = -1;
        for (int i = 0; i < mnchr.length(); i++) {
            radius[i] = R < i ? 1 : Math.min(R - i, radius[2 * C - i]);
            while (i + radius[i] < mnchr.length() && i - radius[i] > -1 && mnchr.charAt(i + radius[i]) == mnchr.charAt(i - radius[i])) {
                radius[i]++;
            }
            if (i + radius[i] > R) {
                R = i + radius[i] - 1;
                C = i;
            }
            max = Math.max(max, radius[i]);
        }
        return max - 1;
    }

    public int longestPalindrome2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        Map<Character, Integer> charCount = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            charCount.compute(s.charAt(i), (k, v) -> v == null ? 1 : v + 1);
        }
        int radius = 0;
        radius += charCount.values().stream().filter(it -> it > 1).mapToInt(it -> {
            if (it % 2 == 0) {
                return it;
            } else {
                return it - 1;
            }
        }).sum();
        radius += charCount.values().stream().filter(it -> it % 2 == 1).count() > 0 ? 1 : 0;
        return radius;
    }

    public int longestPalindromeSubseq(String s) {
        int n = s.length();
        int[][] f = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            f[i][i] = 1;
            for (int j = i + 1; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    f[i][j] = f[i + 1][j - 1] + 2;
                } else {
                    f[i][j] = Math.max(f[i + 1][j], f[i][j - 1]);
                }
            }
        }
        return f[0][n - 1];
    }

    boolean isPalindrome(List<Character> chs) {
        for (int i = 0; i < chs.size() / 1; i++) {
            if (chs.get(i) != chs.get(chs.size() - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    public List<String> printVertically(String s) {
        List<String> res = new ArrayList<>();
        if (s != null && s.length() > 0) {
            String[] words = s.trim().split(" ");
            int maxLength = 0;
            for (String word : words) {
                if (word != null && word.length() > maxLength) {
                    maxLength = word.length();
                }
            }
            for (int i = 0; i < maxLength; i++) {
                StringBuilder stringBuilder = new StringBuilder();
                for (String word : words) {
                    if (word != null && word.length() > i) {
                        stringBuilder.append(word.charAt(i));
                    } else {
                        stringBuilder.append(" ");
                    }
                }
                String sub = stringBuilder.toString();
                while (sub.endsWith(" ")) {
                    sub = sub.substring(0, sub.length() - 1);
                }
                res.add(sub);

            }
        }
        return res;
    }

    public int minSteps(String s, String t) {
        int count = 0;
        if (!s.equals(t)) {
            int[] sArr = new int[26];
            int[] tArr = new int[26];
            for (int i = 0; i < s.length(); i++) {
                sArr[s.charAt(i) - 'a']++;
                tArr[t.charAt(i) - 'a']++;
            }
            for (int i = 0; i < 26; i++) {
                count += Math.abs(sArr[i] - tArr[i]);
            }
            count /= 2;
        }
        return count;
    }

    public boolean isAnagram(String s, String t) {
        if (s == null || t == null || s.length() != t.length()) {
            return false;
        }
        int[] sArr = new int[26];
        int[] tArr = new int[26];
        for (int i = 0; i < s.length(); i++) {
            sArr[s.charAt(i) - 'a']++;
            tArr[t.charAt(i) - 'a']++;
        }
        for (int i = 0; i < 26; i++) {
            if (sArr[i] != tArr[i]) {
                return false;
            }
        }
        return true;
    }

    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        if (strs != null && strs.length > 0) {
            for (String str : strs) {
                int[] arr = arr(str);
                String key = key(arr);
                if (map.containsKey(key)) {
                    map.get(key).add(str);
                } else {
                    List<String> e = new ArrayList<>();
                    e.add(str);
                    map.put(key, e);
                }
            }
        }
        return new ArrayList<>(map.values());
    }

    int[] arr(String str) {
        int[] arr = new int[26];
        if (str != null) {
            for (int i = 0; i < str.length(); i++) {
                arr[str.charAt(i) - 'a']++;
            }
        }
        return arr;
    }

    String key(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(i);
            sb.append('=');
            sb.append(arr[i]);
            sb.append(',');
        }
        return sb.toString();
    }

    public List<Integer> findAnagrams(String s, String p) {//438
        List<Integer> res = new ArrayList<>();
        if (s != null && s.length() >= p.length()) {
            int pSize = p.length();
            int sSize = s.length();
            int[] pArr = new int[26];
            for (int i = 0; i < pSize; i++) {
                pArr[p.charAt(i) - 'a']++;
            }
            int[] sArr = new int[26];
            for (int i = 0; i < pSize - 1; i++) {
                sArr[s.charAt(i) - 'a']++;
            }
            for (int i = pSize - 1; i < sSize; i++) {
                sArr[s.charAt(i) - 'a']++;
                if (isEqualArray(pArr, sArr)) {
                    res.add(i - pSize + 1);
                }
                sArr[s.charAt(i - pSize + 1) - 'a']--;
            }
        }
        return res;
    }

    boolean isEqualArray(int[] a, int[] b) {
        if (a.length != b.length) {
            return false;
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    public boolean checkInclusion(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() > s2.length()) {
            return false;
        } else {
            int size1 = s1.length();
            int size2 = s2.length();
            int[] arr1 = new int[26];
            for (int i = 0; i < size1; i++) {
                arr1[s1.charAt(i) - 'a']++;
            }
            int[] arr2 = new int[26];
            for (int i = 0; i < size1 - 1; i++) {
                arr2[s2.charAt(i) - 'a']++;
            }
            for (int i = size1 - 1; i < size2; i++) {
                arr2[s2.charAt(i) - 'a']++;
                if (isEqualArray(arr1, arr2)) {
                    return true;
                }
                arr2[s2.charAt(i - size1 + 1) - 'a']--;
            }
            return false;
        }
    }

    /**
     * Sliding Window Approach
     *
     * @param s
     * @param t
     * @return
     */
    public String minWindow(String s, String t) {
        String ans = "";
        Set<Character> tSet = new HashSet<>();
        int[] tCount = new int[256];
        for (int i = 0; i < t.length(); i++) {
            tSet.add(t.charAt(i));
            tCount[t.charAt(i)]++;
        }
        int i = 0, j = 0;
        int[] count = new int[256];
        int size = 0;
        while (i < s.length() && j < s.length()) {
            char c = s.charAt(j);
            if (tSet.contains(c)) {
                count[c]++;
                if (count[c] == tCount[c]) {
                    size++;
                }
                if (size == tSet.size()) {
                    int len = j - i + 1;
                    if (ans.equals("") || len < ans.length()) {
                        ans = s.substring(i, j + 1);
                    }
                    while (size == tSet.size()) {
                        char ci = s.charAt(i);
                        count[ci]--;
                        if (size == tSet.size()) {
                            len = j - i + 1;
                            if (ans.equals("") || len < ans.length()) {
                                ans = s.substring(i, j + 1);
                            }
                        }
                        if (tSet.contains(ci) && count[ci] < tCount[ci]) {
                            size--;
                        }
                        i++;
                    }
                }
                j++;
            } else {
                j++;
                count[c]++;
            }
        }
        return ans;
    }

    public String convert(String s, int numRows) {
        if (s == null || s.length() == 0 || numRows < 2) {
            return s;
        }
        StringBuilder[] sbs = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            sbs[i] = new StringBuilder();
        }
        int row = 0;
        boolean down = true;
        for (int i = 0; i < s.length(); i++) {
            sbs[row].append(s.charAt(i));
            if (row == 0) {
                down = true;
            } else if (row == numRows - 1) {
                down = false;
            }
            if (down) {
                row++;
            } else {
                row--;
            }
        }
        StringBuilder res = new StringBuilder();
        for (StringBuilder sb : sbs) {
            res.append(sb);
        }
        return res.toString();
    }

    /**
     * s = "abcd"
     * t = "adecb"
     * find char 'e'
     *
     * @param s
     * @param t
     * @return
     */
    public char findTheDifference(String s, String t) {
        if (s == null || t == null || s.length() + 1 != t.length()) {
            return (char) 0;
        }
        int[] counts = new int[26];
        for (int i = 0; i < s.length(); i++) {
            counts[s.charAt(i) - 'a']++;
            counts[t.charAt(i) - 'a']--;
        }
        counts[t.charAt(t.length() - 1) - 'a']--;
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] == -1) {
                return (char) ('a' + i);
            }
        }
        return (char) 0;
    }

    public int numberOfSubstrings(String s) {
        int count = 0;
        if (s != null && s.length() >= 3) {
            int[] counts = new int[3];
            for (int i = 3; i <= s.length(); i++) {
                for (int j = 0; j < s.length() - i + 1; j++) {
                    count(counts, s, j, j + i);
                    if (valid(counts)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    void count(int[] counts, String str, int s, int e) {
        for (int i = 0; i < counts.length; i++) {
            counts[i] = 0;
        }
        for (int i = s; i < e; i++) {
            counts[str.charAt(i) - 'a']++;
        }
    }

    boolean valid(int[] counts) {
        return counts[0] > 0 && counts[1] > 0 && counts[2] > 0;
    }

    public int countOrders(int n) {
        if (n < 1) {
            return 1;
        } else {
            return c(n, n * 2) / (n + 1);
        }
    }

    int a(int n) {
        int a = 1;
        if (n > 1) {
            while (n != 0) {
                a *= a - 1;
                a %= Integer.MAX_VALUE;
            }
        }
        return a;
    }

    int c(int m, int n) {
        return a(n) / a(m) / a((n - m));
    }

    public int daysBetweenDates(String date1, String date2) {
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");
        try {
            Date localDate1 = formatter.parse(date1);
            Date localDate2 = formatter.parse(date2);
            return (int) Math.abs((localDate2.getTime() - localDate1.getTime()) / (1000 * 86400));
        } catch (ParseException e) {

        }
        return 0;
    }

    public String removeDuplicates(String S) {
        StringBuilder sb = new StringBuilder();
        int sbLength = 0;
        for (char character : S.toCharArray()) {
            if (sbLength != 0 && character == sb.charAt(sbLength - 1))
                sb.deleteCharAt(sbLength-- - 1);
            else {
                sb.append(character);
                sbLength++;
            }
        }
        return sb.toString();
    }

    public boolean backspaceCompare(String S, String T) {
        Stack<Character> sStack = new Stack<>();
        for (int i = 0; i < S.length(); i++) {
            if (S.charAt(i) == '#') {
                if (!sStack.isEmpty()) {
                    sStack.pop();
                }
            } else {
                sStack.push(S.charAt(i));
            }
        }
        Stack<Character> tStack = new Stack<>();
        for (int i = 0; i < T.length(); i++) {
            if (T.charAt(i) == '#') {
                if (!tStack.isEmpty()) {
                    tStack.pop();
                }
            } else {
                tStack.push(T.charAt(i));
            }
        }
        if (sStack.size() != tStack.size()) {
            return false;
        }
        while (!sStack.isEmpty()) {
            if (sStack.pop() != tStack.pop()) {
                return false;
            }
        }
        return true;
    }

    public int[] maxDepthAfterSplit(String seq) {
        int[] ans = new int[seq.length()];
        int d = 0;
        int k = 0;
        for (int i = 0; i < seq.length(); i++) {
            if (seq.charAt(i) == '(') {
                d++;
                ans[k++] = d % 2;
            } else {
                ans[k++] = d % 2;
                d--;
            }
        }
        return ans;
    }

    public boolean checkValidString(String s) {//678
        Stack<Integer> left = new Stack<>();
        Stack<Integer> star = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(') {
                left.push(i);
            } else if (ch == '*') {
                star.push(i);
            } else {
                if (!left.isEmpty()) {
                    left.pop();
                } else if (!star.isEmpty()) {
                    star.pop();
                } else {
                    return false;
                }
            }
        }
        while (!left.isEmpty()) {
            if (star.isEmpty()) {
                return false;
            } else {
                if (left.peek() < star.peek()) {
                    left.pop();
                    star.pop();
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public int getMaxRepetitions(String s1, int n1, String s2, int n2) {
        if (n1 == 0) return 0;
        char[] chars1 = s1.toCharArray();
        char[] chars2 = s2.toCharArray();
        int l1 = s1.length();
        int l2 = s2.length();
        int count1 = 0;//经历多少s1
        int count2 = 0;//经历多少s2
        int p = 0;//当前在s2的位置
        Map<Integer, int[]> map = new HashMap<>();//记录每一次s1扫描结束后当前的状态，寻找循环
        while (count1 < n1) {
            for (int i = 0; i < l1; i++) {
                if (chars1[i] == chars2[p]) {//往前
                    p++;
                    if (p == l2) {//s2扫描结束从头开始循环
                        p = 0;
                        count2++;
                    }
                }
            }
            count1++;
            if (!map.containsKey(p)) {
                map.put(p, new int[]{count1, count2});//记录当前状态

            } else {//出现了循环 这次结束后p的位置和以前某一次一样，就是循环
                int[] last = map.get(p);
                int circle1 = count1 - last[0];
                int circle2 = count2 - last[1];
                count2 += circle2 * ((n1 - count1) / circle1);
                count1 = count1 + ((n1 - count1) / circle1) * circle1;//更新新他们
            }
        }
        return count2 / n2;
    }

    public List<String> findRepeatedDnaSequences(String s) {//187
        int L = 10, n = s.length();
        if (n <= L) return new ArrayList();

        // rolling hash parameters: base a
        int a = 4, aL = (int) Math.pow(a, L);

        // convert string to array of integers
        Map<Character, Integer> toInt = new HashMap();
        toInt.put('A', 0);
        toInt.put('C', 1);
        toInt.put('G', 2);
        toInt.put('T', 3);
        int[] nums = new int[n];
        for (int i = 0; i < n; ++i) nums[i] = toInt.get(s.charAt(i));

        int h = 0;
        Set<Integer> seen = new HashSet();
        Set<String> output = new HashSet();
        // iterate over all sequences of length L
        for (int start = 0; start < n - L + 1; ++start) {
            // compute hash of the current sequence in O(1) time
            if (start != 0)
                h = h * a - nums[start - 1] * aL + nums[start + L - 1];
                // compute hash of the first sequence in O(L) time
            else
                for (int i = 0; i < L; ++i) h = h * a + nums[i];
            // update output and hashset of seen sequences
            if (seen.contains(h)) output.add(s.substring(start, start + L));
            seen.add(h);
        }
        return new ArrayList(output);
    }

    public boolean areSentencesSimilarTwo(String[] words1, String[] words2, List<List<String>> pairs) {//737
        if (words1.length != words2.length) return false;
        Map<String, List<String>> graph = new HashMap();
        for (List<String> pair : pairs) {
            for (String p : pair)
                if (!graph.containsKey(p)) {
                    graph.put(p, new ArrayList());
                }
            graph.get(pair.get(0)).add(pair.get(1));
            graph.get(pair.get(1)).add(pair.get(0));
        }

        for (int i = 0; i < words1.length; ++i) {
            String w1 = words1[i], w2 = words2[i];
            Stack<String> stack = new Stack();
            Set<String> seen = new HashSet();
            stack.push(w1);
            seen.add(w1);
            search:
            {
                while (!stack.isEmpty()) {
                    String word = stack.pop();
                    if (word.equals(w2)) break search;
                    if (graph.containsKey(word)) {
                        for (String nei : graph.get(word)) {
                            if (!seen.contains(nei)) {
                                stack.push(nei);
                                seen.add(nei);
                            }
                        }
                    }
                }
                return false;
            }
        }
        return true;
    }
}
