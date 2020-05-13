package me.bytebeats.algorithms.trees;

import me.bytebeats.algorithms.meta.ListNode;
import me.bytebeats.algorithms.meta.TreeNode;
import me.bytebeats.algorithms.meta.TreeNode2;

import java.util.*;

public class TreesQuiz {
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        return sortedArrayToBST(nums, 0, nums.length - 1);
    }

    TreeNode sortedArrayToBST(int[] nums, int s, int e) {
        if (s > e) {
            return null;
        }
        int mid = s + (e - s) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = sortedArrayToBST(nums, s, mid - 1);
        root.right = sortedArrayToBST(nums, mid + 1, e);
        return root;
    }

    /**
     * https://leetcode.com/problems/convert-sorted-list-to-binary-search-tree/
     *
     * @param head
     * @return
     */
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
        }
        return sortedListToBST(head, tail);
    }

    TreeNode sortedListToBST(ListNode head, ListNode tail) {
        int d = 0;
        ListNode tmp = head;
        while (tmp != tail) {
            d++;
            tmp = tmp.next;
        }
        int mid = d / 2;
        ListNode midNode = head;
        while (mid-- > 0) {
            midNode = midNode.next;
        }
        TreeNode root = new TreeNode(midNode.val);
        root.left = sortedListToBST(head, midNode);
        root.right = sortedListToBST(midNode.next, tail);
        return root;
    }

    public List<String> binaryTreePaths(TreeNode root) {
        List<String> list = new ArrayList<>();
        bfs(list, "", root);
        return list;
    }

    void bfs(List<String> list, String s, TreeNode node) {
        if (node == null) {
            return;
        }
        s += "->" + node.val;
        if (node.left == null && node.right == null) {
            list.add(s.substring(2));
        } else {
            bfs(list, s, node.left);
            bfs(list, s, node.right);
        }
    }

    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> list = new ArrayList<>();
        bfs(list, Collections.EMPTY_LIST, 0, sum, root);
        return list;
    }

    void bfs(List<List<Integer>> list, List<Integer> subList, int subSum, int sum, TreeNode node) {
        if (node == null) {
            return;
        }
        int newSubSum = subSum + node.val;
        if (node.left == null && node.right == null && newSubSum == sum) {
            List<Integer> tmp = new ArrayList<>(subList);
            tmp.add(node.val);
            list.add(tmp);
        } else {
            List<Integer> tmp = new ArrayList<>(subList);
            tmp.add(node.val);
            if (node.left != null) {
                bfs(list, tmp, newSubSum, sum, node.left);
            }
            if (node.right != null) {
                bfs(list, tmp, newSubSum, sum, node.right);
            }
        }
    }

    public boolean hasPathSum(TreeNode root, int sum) {
        return hasPathSum(root, 0, sum);
    }

    boolean hasPathSum(TreeNode node, int subSum, int sum) {
        if (node == null) {
            return false;
        }
        int newSum = subSum + node.val;
        return node.left == null && node.right == null && newSum == sum || hasPathSum(node.left, newSum, sum) || hasPathSum(node.right, newSum, sum);
    }

    public int sumNumbers(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        strNumbers(list, 0, root);
        return list.stream().mapToInt(it -> it).sum();
    }

    int convertStr2Num(String str) {
        int num = 0;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            num *= 10;
            num += (ch - '0');
        }
        return num;
    }

    void strNumbers(List<String> list, String str, TreeNode node) {
        if (node == null) {
            return;
        }
        str += node.val;
        if (node.left == null && node.right == null) {
            list.add(str);
        } else {
            strNumbers(list, str, node.left);
            strNumbers(list, str, node.right);
        }
    }

    void strNumbers(List<Integer> list, int sum, TreeNode node) {
        if (node == null) {
            return;
        }
        sum = sum * 10 + node.val;
        if (node.left == null && node.right == null) {
            list.add(sum);
        } else {
            strNumbers(list, sum, node.left);
            strNumbers(list, sum, node.right);
        }
    }

    public TreeNode trimBST(TreeNode root, int L, int R) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        List<Integer> res = new ArrayList<>();
        while (!queue.isEmpty()) {
            TreeNode node = queue.remove();
            if (node == null) {
                break;
            } else {
                if (node.val <= R && node.val >= L) {
                    res.add(node.val);
                }
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }
        return createTree(res);
    }

    TreeNode createTree(List<Integer> elements) {
        TreeNode root = null;
        for (int e : elements) {
            if (root == null) {
                root = new TreeNode(e);
            } else {
                add(root, e);
            }
        }
        return root;
    }

    void add(TreeNode root, int val) {
        if (root == null) {
            return;
        }
        TreeNode p = root;
        if (p.val < val) {
            if (p.right == null) {
                p.right = new TreeNode(val);
            } else {
                add(p.right, val);
            }
        } else if (p.val > val) {
            if (p.left == null) {
                p.left = new TreeNode(val);
            } else {
                add(p.left, val);
            }
        }
    }

    /**
     * 分层打印二叉树
     *
     * @param root
     */
    public void printTree(TreeNode root) {
        if (root == null) {
            return;
        }
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        int current = 1;
        int next = 0;
        while (!q.isEmpty()) {
            TreeNode node = q.remove();
            System.out.print(node.val);
            System.out.print(", ");
            current--;
            if (node.left != null) {
                q.add(node.left);
                next++;
            }
            if (node.right != null) {
                q.add(node.right);
                next++;
            }
            if (current == 0) {
                System.out.println();
                current = next;
                next = 0;
            }
        }
    }

    /**
     * 分层打印二叉树
     *
     * @param root
     */
    public void printLevelAvg(TreeNode root) {
        if (root == null) {
            return;
        }
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        int current = 1;
        int next = 0;
        int levelCount = current;
        double levelSum = 0.0;
        while (!q.isEmpty()) {
            TreeNode node = q.remove();
            levelSum += node.val;
            current--;
            if (node.left != null) {
                q.add(node.left);
                next++;
            }
            if (node.right != null) {
                q.add(node.right);
                next++;
            }
            if (current == 0) {
                System.out.println(levelSum / levelCount);
                current = next;
                next = 0;
                levelCount = current;
                levelSum = 0.0;
            }
        }
    }

    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> res = new ArrayList<>();
        if (root != null) {
            Queue<TreeNode> q = new LinkedList<>();
            q.add(root);
            int current = 1;
            int next = 0;
            int levelCount = current;
            double levelSum = 0.0;
            while (!q.isEmpty()) {
                TreeNode node = q.remove();
                levelSum += node.val;
                current--;
                if (node.left != null) {
                    q.add(node.left);
                    next++;
                }
                if (node.right != null) {
                    q.add(node.right);
                    next++;
                }
                if (current == 0) {
                    res.add(levelSum / levelCount);
                    current = next;
                    next = 0;
                    levelCount = current;
                    levelSum = 0.0;
                }
            }
        }
        return res;
    }

    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root != null) {
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            int current = 1;
            int next = 0;
            List<Integer> sub = new ArrayList<>();
            while (!queue.isEmpty()) {
                TreeNode node = queue.remove();
                sub.add(node.val);
                current--;
                if (node.left != null) {
                    queue.add(node.left);
                    next++;
                }
                if (node.right != null) {
                    queue.add(node.right);
                    next++;
                }
                if (current == 0) {
                    res.add(sub);
                    sub = new ArrayList<>();
                    current = next;
                    next = 0;
                }
            }
        }
        Collections.reverse(res);
        return res;
    }

    public List<List<Integer>> levelOrder(TreeNode root) {//102
        List<List<Integer>> res = new ArrayList<>();
        if (root != null) {
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            int current = 1;
            int next = 0;
            List<Integer> sub = new ArrayList<>();
            while (!queue.isEmpty()) {
                TreeNode node = queue.remove();
                sub.add(node.val);
                current--;
                if (node.left != null) {
                    queue.add(node.left);
                    next++;
                }
                if (node.right != null) {
                    queue.add(node.right);
                    next++;
                }
                if (current == 0) {
                    res.add(new ArrayList<>(sub));
                    sub.clear();
                    current = next;
                    next = 0;
                }
            }
        }
        return res;
    }

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root != null) {
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            int current = 1;
            int next = 0;
            List<Integer> sub = new ArrayList<>();
            while (!queue.isEmpty()) {
                TreeNode node = queue.remove();
                sub.add(node.val);
                current--;
                if (node.left != null) {
                    queue.add(node.left);
                    next++;
                }
                if (node.right != null) {
                    queue.add(node.right);
                    next++;
                }
                if (current == 0) {
                    res.add(sub);
                    sub = new ArrayList<>();
                    current = next;
                    next = 0;
                }
            }
        }
        for (int i = 0; i < res.size(); i++) {
            if (i % 2 == 1) {
                Collections.reverse(res.get(i));
            } else {
                continue;
            }
        }
        return res;
    }

    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
        }
    }

    public int minDepth(TreeNode root) {
        int minDepth = 0;
        if (root != null) {
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            int current = 1;
            int next = 0;
            minDepth++;
            while (!queue.isEmpty()) {
                TreeNode node = queue.remove();
                if (node.left == null && node.right == null) {
                    return minDepth;
                }
                current--;
                if (node.left != null) {
                    queue.add(node.left);
                    next++;
                }
                if (node.right != null) {
                    queue.add(node.right);
                    next++;
                }
                if (current == 0) {
                    minDepth++;
                    current = next;
                    next = 0;
                }
            }
        }
        return minDepth;
    }

    public List<Integer> rightSideView(TreeNode root) {//199
        List<Integer> res = new ArrayList<>();
        if (root != null) {
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            int current = 1;
            int next = 0;
            while (!queue.isEmpty()) {
                TreeNode node = queue.remove();
                current--;
                if (node.left != null) {
                    queue.add(node.left);
                    next++;
                }
                if (node.right != null) {
                    queue.add(node.right);
                    next++;
                }
                if (current == 0) {
                    res.add(node.val);
                    current = next;
                    next = 0;
                }
            }
        }
        return res;
    }

    public boolean isSymmetric(TreeNode root) {
        if (root != null) {

        }
        return true;
    }

    public int minDiffInBST(TreeNode root) {
        if (root == null || root.left == null && root.right == null) {
            return 0;
        }
        Stack<TreeNode> stack = new Stack<>();
        List<Integer> mid = new ArrayList<>();
        TreeNode p = root;
        while (p != null || !stack.isEmpty()) {
            if (p != null) {
                stack.push(p);
                p = p.left;
            } else {
                p = stack.pop();
                mid.add(p.val);
                p = p.right;
            }
        }
        int minDiff = Integer.MAX_VALUE;
        for (int i = 0; i < mid.size() - 1; i++) {
            int diff = mid.get(i + 1) - mid.get(i);
            if (diff < minDiff) {
                minDiff = diff;
            }
        }
        return minDiff;
    }

    public boolean lemonadeChange(int[] bills) {
        Map<Integer, Integer> billCount = new HashMap<>();
        if (bills != null && bills.length > 0) {
            for (int bill : bills) {
                if (!billCount.containsKey(bill)) {
                    billCount.put(bill, 1);
                } else {
                    billCount.put(bill, billCount.get(bill) + 1);
                }
                if (bill == 10) {
                    if (billCount.containsKey(5) && billCount.get(5) > 0) {
                        billCount.put(5, billCount.get(5) - 1);
                    } else {
                        return false;
                    }
                } else if (bill == 20) {
                    bill -= 5;
                    if (billCount.containsKey(10) && billCount.get(10) > 0) {
                        billCount.put(10, billCount.get(10) - 1);
                        bill -= 10;
                    }
                    while (bill > 0) {
                        if (billCount.containsKey(5) && billCount.get(5) > 0) {
                            billCount.put(5, billCount.get(5) - 1);
                            bill -= 5;
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public TreeNode removeLeafNodes(TreeNode root, int target) {
        hasRemovedLeafNodes(root, target);
        if (root != null && root.left == null && root.right == null && root.val == target) {
            return null;
        }
        return root;
    }

    public boolean hasRemovedLeafNodes(TreeNode root, int target) {
        if (root == null) {
            return false;
        }
        if (root != null && root.left == null && root.right == null && root.val == target) {
            root = null;
            return true;
        }
        boolean res = false;
        if (root.left != null && root.left.left == null && root.left.right == null && root.left.val == target) {
            root.left = null;
            return hasRemovedLeafNodes(root, target);
        } else if (root.right != null && root.right.left == null && root.right.right == null && root.right.val == target) {
            root.right = null;
            return hasRemovedLeafNodes(root, target);
        } else {
            if (hasRemovedLeafNodes(root.left, target)) {
                return hasRemovedLeafNodes(root, target);
            }
            if (hasRemovedLeafNodes(root.right, target)) {
                return hasRemovedLeafNodes(root, target);
            }
            return false;
        }
    }

    public ListNode oddEvenList(ListNode head) {
        ListNode p = head;
        ListNode oddHead = null;
        ListNode q = null;
        while (p != null) {
            if (p.next != null) {
                if (oddHead == null) {
                    oddHead = p.next;
                    q = oddHead;
                } else {
                    q.next = p.next;
                    q = q.next;
                }
                p = p.next.next;
            } else {
                p.next = oddHead;
                break;
            }
        }
        return head;
    }

    public boolean findTarget(TreeNode root, int k) {//1214
        if (root == null || root.left == null && root.right == null) {
            return false;
        }
        List<Integer> midReversal = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode p = root;
        while (p != null || !stack.isEmpty()) {
            if (p != null) {
                stack.push(p);
                p = p.left;
            } else {
                p = stack.pop();
                midReversal.add(p.val);
                p = p.right;
            }
        }
        int i = 0, j = midReversal.size() - 1;
        int tmp = 0;
        while (i < j) {
            tmp = midReversal.get(i) + midReversal.get(j);
            if (tmp > k) {
                j--;
            } else if (tmp < k) {
                i++;
            } else {
                return true;
            }
        }
        return false;
    }

    public boolean twoSumBSTs(TreeNode root1, TreeNode root2, int target) {
        if (root1 == null || root2 == null) {
            return false;
        }
        List<Integer> midReversal1 = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode p = root1;
        while (p != null || !stack.isEmpty()) {
            if (p != null) {
                stack.push(p);
                p = p.left;
            } else {
                p = stack.pop();
                midReversal1.add(p.val);
                p = p.right;
            }
        }
        p = root2;
        List<Integer> midReversal2 = new ArrayList<>();
        while (p != null || !stack.isEmpty()) {
            if (p != null) {
                stack.push(p);
                p = p.left;
            } else {
                p = stack.pop();
                midReversal2.add(p.val);
                p = p.right;
            }
        }
        int i = midReversal1.size() - 1, j = 0;
        int tmp = 0;
        while (i > -1 && j < midReversal2.size()) {
            tmp = midReversal1.get(i) + midReversal2.get(j);
            if (tmp > target) {
                i--;
            } else if (tmp < target) {
                j++;
            } else {
                return true;
            }
        }
        return false;
    }


    public int getMinimumDifference(TreeNode root) {//530
        int minDiff = Integer.MAX_VALUE;
        if (root != null) {
            Stack<TreeNode> s = new Stack<>();
            TreeNode p = root;
            TreeNode pre = null;
            while (p != null || !s.isEmpty()) {
                if (p != null) {
                    s.push(p);
                    p = p.left;
                } else {
                    p = s.pop();
                    if (pre != null) {
                        if (p.val - pre.val < minDiff) {
                            minDiff = p.val - pre.val;
                        }
                    }
                    pre = p;
                    p = p.right;
                }
            }
        }
        return minDiff;
    }

    public int[] findMode(TreeNode root) {
        Map<Integer, Integer> map = new HashMap<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode p = root;
        while (p != null || !stack.isEmpty()) {
            if (p != null) {
                stack.push(p);
                p = p.left;
            } else {
                p = stack.pop();
                map.compute(p.val, (k, v) -> v == null ? 1 : v + 1);
                p = p.right;
            }
        }
        int maxCount = -1;
        for (Integer count : map.values()) {
            if (count > maxCount) {
                maxCount = count;
            }
        }
        List<Integer> list = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() == maxCount) {
                list.add(entry.getKey());
            }
        }
        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
    }

    public LinkedList<TreeNode> generate_trees(int start, int end) {
        LinkedList<TreeNode> all_trees = new LinkedList<TreeNode>();
        if (start > end) {
            all_trees.add(null);
            return all_trees;
        }

        // pick up a root
        for (int i = start; i <= end; i++) {
            // all possible left subtrees if i is choosen to be a root
            LinkedList<TreeNode> left_trees = generate_trees(start, i - 1);

            // all possible right subtrees if i is choosen to be a root
            LinkedList<TreeNode> right_trees = generate_trees(i + 1, end);

            // connect left and right trees to the root i
            for (TreeNode l : left_trees) {
                for (TreeNode r : right_trees) {
                    TreeNode current_tree = new TreeNode(i);
                    current_tree.left = l;
                    current_tree.right = r;
                    all_trees.add(current_tree);
                }
            }
        }
        return all_trees;
    }

    public List<TreeNode> generateTrees(int n) {
        if (n == 0) {
            return new LinkedList<TreeNode>();
        }
        return generate_trees(1, n);
    }

    public TreeNode increasingBST(TreeNode root) {
        if (root == null || root.left == null && root.right == null) {
            return root;
        }
        List<TreeNode> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode p = root;
        while (p != null || !stack.isEmpty()) {
            if (p != null) {
                stack.push(p);
                p = p.left;
            } else {
                p = stack.pop();
                list.add(p);
                p = p.right;
            }
        }
        p = null;
        TreeNode tmp = null;
        for (int i = list.size() - 1; i > -1; i--) {
            tmp = list.get(i);
            tmp.right = p;
            tmp.left = null;
            p = tmp;
        }
        return p;
    }

    public TreeNode balanceBST(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        List<Integer> res = new ArrayList<>();
        while (!queue.isEmpty()) {
            TreeNode node = queue.remove();
            if (node == null) {
                break;
            } else {
                res.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }
        Collections.sort(res);
        return createBalancedTree(res, 0, res.size() - 1);
    }

    private TreeNode createBalancedTree(List<Integer> list, int start, int end) {
        if (start < 0 || end >= list.size() || start > end) {
            return null;
        }
        int mid = (start + end) / 2;
        TreeNode root = new TreeNode(list.get(mid));
        root.left = createBalancedTree(list, start, mid - 1);
        root.right = createBalancedTree(list, mid + 1, end);
        return root;
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode p = headA;
        int sizeA = 0;
        while (p != null) {
            sizeA++;
            p = p.next;
        }
        ListNode q = headB;
        int sizeB = 0;
        while (q != null) {
            sizeB++;
            q = q.next;
        }
        p = headA;
        q = headB;
        int diff = 0;
        if (sizeA > sizeB) {
            diff = sizeA - sizeB;
            while (diff-- > 0) {
                p = p.next;
            }
        } else if (sizeA < sizeB) {
            diff = sizeB - sizeA;
            while (diff-- > 0) {
                q = q.next;
            }
        }
        while (p != null && q != null && p != q) {
            p = p.next;
            q = q.next;
        }
        return p;
    }

    public int deepestLeavesSum(TreeNode root) {
        if (root != null) {
            Queue<TreeNode> q = new LinkedList<>();
            q.add(root);
            int countDown = 1;
            int count = 0;
            TreeNode tmp = null;
            int sum = 0;
            int ans = 0;
            while (!q.isEmpty()) {
                tmp = q.remove();
                sum += tmp.val;
                countDown--;
                if (tmp.left != null) {
                    q.add(tmp.left);
                    count++;
                }
                if (tmp.right != null) {
                    q.add(tmp.right);
                    count++;
                }
                if (countDown == 0) {
                    ans = sum;
                    countDown = count;
                    count = 0;
                    sum = 0;
                }
            }
            return ans;
        }
        return 0;
    }

    public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
        List<Integer> ans1 = new ArrayList<>();
        Stack<TreeNode> s = new Stack<>();
        TreeNode p = root1;
        while (p != null || !s.isEmpty()) {
            if (p != null) {
                s.push(p);
                p = p.left;
            } else {
                p = s.pop();
                ans1.add(p.val);
                p = p.right;
            }
        }
        p = root2;
        List<Integer> ans2 = new ArrayList<>();
        while (p != null || !s.isEmpty()) {
            if (p != null) {
                s.push(p);
                p = p.left;
            } else {
                p = s.pop();
                ans2.add(p.val);
                p = p.right;
            }
        }
        List<Integer> ans = new ArrayList<>(ans1.size() + ans2.size());
        int i = 0;
        int j = 0;
        while (i < ans1.size() && j < ans2.size()) {
            if (ans1.get(i) < ans2.get(j)) {
                ans.add(ans1.get(i++));
            } else {
                ans.add(ans2.get(j++));
            }
        }
        while (i < ans1.size()) {
            ans.add(ans1.get(i++));
        }
        while (j < ans2.size()) {
            ans.add(ans2.get(j++));
        }
        return ans;
    }

    public int[] levelOrder2(TreeNode root) {
        if (root == null) {
            return null;
        }
        Queue<TreeNode> q = new LinkedList<>();
        List<Integer> list = new ArrayList<>();
        q.add(root);
        TreeNode p = null;
        while (!q.isEmpty()) {
            p = q.poll();
            list.add(p.val);
            if (p.left != null) {
                q.offer(p.left);

            }
            if (p.right != null) {
                q.offer(p.right);

            }
        }
        int[] ans = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ans[i] = list.get(i);
        }
        return ans;
    }

    public ListNode removeDuplicateNodes(ListNode head) {
        if (head != null && head.next != null) {
            Set<Integer> set = new HashSet<>();
            set.add(head.val);
            ListNode p = head;
            while (p.next != null) {
                if (set.contains(p.next.val)) {
                    p = p.next.next;
                } else {
                    set.add(p.next.val);
                    p = p.next;
                }
            }
        }
        return head;
    }

    public TreeNode searchBST(TreeNode root, int val) {//700
        if (root == null) {
            return null;
        }
        Deque<TreeNode> q = new LinkedList<>();
        TreeNode p = root;
        q.offer(p);
        while (!q.isEmpty()) {
            p = q.poll();
            if (p.val == val) {
                return p;
            }
            if (p.left != null) {
                q.offer(p.left);
            }
            if (p.right != null) {
                q.offer(p.right);
            }
        }
        return null;
    }

    public TreeNode2 connect(TreeNode2 root) {//117
        if (root != null) {
            Queue<TreeNode2> queue = new LinkedList<>();
            TreeNode2 p = null;
            queue.offer(root);
            int countDown = 1;
            int count = 0;
            List<TreeNode2> list = new LinkedList<>();
            while (!queue.isEmpty()) {
                p = queue.poll();
                list.add(p);
                countDown--;
                if (p.left != null) {
                    count++;
                    queue.offer(p.left);
                }
                if (p.right != null) {
                    count++;
                    queue.offer(p.right);
                }
                if (countDown == 0) {
                    countDown = count;
                    count = 0;
                    for (int i = 1; i < list.size(); i++) {
                        list.get(i - 1).next = list.get(i);
                    }
                    list.clear();
                }
            }
        }
        return root;
    }

    public boolean isCousins(TreeNode root, int x, int y) {//993
        if (root != null) {
            Queue<TreeNode> queue = new LinkedList<>();
            TreeNode p = null;
            queue.offer(root);
            int countDown = 1;
            int count = 0;
            int depth = 0;
            int xDepth = 0;
            int yDepth = 0;
            while (!queue.isEmpty()) {
                p = queue.poll();
                countDown--;
                if (p.left != null && p.right != null) {
                    if (p.left.val == x && p.right.val == y || p.left.val == y && p.right.val == x) {
                        return false;
                    }
                }
                if (p.val == x) {
                    xDepth = depth;
                }
                if (p.val == y) {
                    yDepth = depth;
                }
                if (p.left != null) {
                    queue.offer(p.left);
                    count++;
                }
                if (p.right != null) {
                    queue.offer(p.right);
                    count++;
                }
                if (countDown == 0) {
                    countDown = count;
                    count = 0;
                    depth++;
                }
            }
            return xDepth == yDepth;
        }
        return false;
    }

    public List<Integer> largestValues(TreeNode root) {//515
        List<Integer> ans = new ArrayList<>();
        if (root != null) {
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            TreeNode p = null;
            int countDown = 1;
            int count = 0;
            int max = Integer.MIN_VALUE;
            while (!queue.isEmpty()) {
                p = queue.poll();
                if (p.val > max) {
                    max = p.val;
                }
                countDown--;
                if (p.left != null) {
                    count++;
                    queue.offer(p.left);
                }
                if (p.right != null) {
                    count++;
                    queue.offer(p.right);
                }
                if (countDown == 0) {
                    ans.add(max);
                    countDown = count;
                    count = 0;
                    max = Integer.MIN_VALUE;
                }
            }
        }
        return ans;
    }

    public List<List<Integer>> verticalOrder(TreeNode root) {//314
        List<List<Integer>> ans = new ArrayList<>();
        if (root != null) {
            Map<Integer, List<Integer>> map = new TreeMap<>();
            Queue<TreeNode> nodeQueue = new LinkedList<>();
            Queue<Integer> indexQueue = new LinkedList<>();
            nodeQueue.offer(root);
            indexQueue.offer(0);
            TreeNode node = null;
            int index = 0;
            while (!nodeQueue.isEmpty()) {
                node = nodeQueue.poll();
                index = indexQueue.poll();
                if (map.containsKey(index)) {
                    map.get(index).add(node.val);
                } else {
                    List<Integer> e = new ArrayList<>();
                    e.add(node.val);
                    map.put(index, e);
                }
                if (node.left != null) {
                    nodeQueue.offer(node.left);
                    indexQueue.offer(index - 1);
                }
                if (node.right != null) {
                    nodeQueue.offer(node.right);
                    indexQueue.offer(index + 1);
                }
            }
            if (!map.isEmpty()) {
                for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
                    ans.add(entry.getValue());
                }
            }
        }
        return ans;
    }
}
