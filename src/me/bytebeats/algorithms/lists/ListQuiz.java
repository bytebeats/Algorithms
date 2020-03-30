package me.bytebeats.algorithms.lists;

import me.bytebeats.algorithms.meta.ListNode;

import java.util.Stack;

public class ListQuiz {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode longList = l1;
        ListNode shortList = l2;
        int size1 = 0;
        int size2 = 0;
        while (longList != null || shortList != null) {
            if (longList != null) {
                size1++;
                longList = longList.next;
            }
            if (shortList != null) {
                size2++;
                shortList = shortList.next;
            }
        }
        if (size1 >= size2) {
            longList = l1;
            shortList = l2;
        } else {
            longList = l2;
            shortList = l1;
        }

        longList = reverseList(longList);
        shortList = reverseList(shortList);
        int d = 0;
        ListNode tmpS = shortList;
        ListNode tmpL = longList;
        while (tmpS != null) {
            tmpL.val += tmpS.val + d;
            if (tmpL.val > 9) {
                tmpL.val -= 10;
                d = 1;
            } else {
                d = 0;
            }
            tmpS = tmpS.next;
            tmpL = tmpL.next;
        }
        while (d > 0) {
            if (tmpL == null) {
                tmpL = new ListNode(d);
                d = 0;
            } else {
                tmpL.val += d;
                if (tmpL.val > 9) {
                    tmpL.val -= 10;
                    d = 1;
                } else {
                    d = 0;
                }
            }
        }
        return reverseList(longList);
    }

    public ListNode reverseList(ListNode listNode) {
        ListNode head = null;
        ListNode p = listNode;
        while (p != null) {
            if (head == null) {
                head = new ListNode(p.val);
            } else {
                ListNode tmp = new ListNode(p.val);
                ListNode next = head;
                head = tmp;
                tmp.next = next;
            }
            p = p.next;
        }
        return head;
    }

    public void reverseList1(ListNode dummy, ListNode listNode) {
        if (listNode == null) {
            return;
        }
        ListNode next = dummy.next;
        ListNode newNode = new ListNode(listNode.val);
        newNode.next = next;
        dummy.next = newNode;
        reverseList1(newNode, listNode.next);
    }

    public ListNode createList(int[] elements) {
        ListNode head = null;
        ListNode p = null;
        for (int e : elements) {
            if (head == null) {
                head = new ListNode(e);
                p = head;
            } else {
                p.next = new ListNode(e);
                p = p.next;
            }
        }
        return head;
    }

    public void printList(ListNode head) {
        ListNode p = head;
        while (p != null) {
            System.out.print(p.val + ", ");
            p = p.next;
        }
    }

    public ListNode reverseBetween(ListNode head, int m, int n) {
        ListNode start = null;
        ListNode end = null;
        ListNode preStart = null;
        int i = 0;
        ListNode p = head;
        while (p != null) {
            i++;
            if (i <= m) {
                preStart = start;
                start = p;
                System.out.println(start.val);
            }
            if (i == n) {
                end = p;
                System.out.println(end.val);
                break;
            }
            p = p.next;
        }
        if (start != end) {
            if (preStart == null) {
                ListNode res = reverseBetween(head, end);
                return res;
            } else {
                reverseBetween(preStart, start, end);
            }
        }
        return head;
    }

    ListNode reverseBetween(ListNode head, ListNode end) {
        ListNode p = head;
        ListNode afterEnd = end.next;
        ListNode dummy = new ListNode(0);
        while (p != afterEnd) {
            ListNode tmp = p;
            p = p.next;
            tmp.next = dummy.next;
            dummy.next = tmp;
        }
        p = dummy.next;
        while (p.next != null) {
            p = p.next;
        }
        p.next = afterEnd;
        return dummy.next;
    }

    void reverseBetween(ListNode preStart, ListNode start, ListNode end) {
        ListNode p = start;
        while (p != end) {
            ListNode tmp = p;
            p = p.next;
            preStart.next = tmp.next;
            tmp.next = end.next;
            end.next = tmp;
        }
    }

    public ListNode deleteDuplicates(ListNode head) {
        ListNode pre = null;
        ListNode cur = null;
        ListNode p = head;
        while (p != null) {
            pre = cur;
            cur = p;
            p = p.next;
            while (pre != null && pre.val == cur.val) {
                cur.next = null;
                cur = p;
                p = p.next;
                pre.next = cur;
            }
        }
        return head;
    }

    public ListNode deleteDuplicates1(ListNode head) {
        Stack<Integer> stack = new Stack<>();
        int prePop = Integer.MIN_VALUE;
        while (head != null) {
            if (stack.isEmpty()) {
                if (prePop == Integer.MIN_VALUE || prePop != head.val) {
                    stack.push(head.val);
                }
            } else if (stack.peek() == head.val) {
                prePop = stack.pop();
            } else {
                if (prePop != head.val) {
                    stack.push(head.val);
                }
            }
            head = head.next;
        }
        ListNode dummy = new ListNode(0);
        while (!stack.isEmpty()) {
            ListNode tmp = dummy.next;
            ListNode newNode = new ListNode(stack.pop());
            newNode.next = tmp;
            dummy.next = newNode;
        }
        return dummy.next;
    }

    /**
     * K 个一组逆序链表
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode reverseKGroup(ListNode head, int k) {//1->2->3->4->5 ==> 3->2->1->4->5
        if (head == null || k < 2) {
            return head;
        }
        ListNode p = head;
        ListNode pre = null;
        ListNode start = null;
        ListNode end = null;
        int count = 0;
        while (p != null) {
            if (count == 0) {
                pre = start;
                start = p;
            } else if (count == k - 1) {
                end = p;

            }

            count++;
            p = p.next;
        }
        return null;
    }

    public void reverse(ListNode pre, ListNode start, ListNode end) {
        if (pre == null) {
            pre = new ListNode(0);
            pre.next = start;
        }
        ListNode p = start;
        ListNode next = null;
        while (start != end) {
            start = start.next;
            next = end.next;
            end.next = p;
            p.next = next;
            p = start;
        }
    }

    public boolean hasCycle(ListNode head) {//是否存在环形链表
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }

    public ListNode detectCycle(ListNode head) {//环形链表的起始点
        if (head == null || head.next == null) {
            return null;
        }
        ListNode intersect = getIntersect(head);
        if (intersect == null) {
            return null;
        }
        ListNode p1 = head;
        ListNode p2 = intersect;
        while (p1 != p2) {
            p1 = p1.next;
            p2 = p2.next;
        }
        return p1;
    }

    private ListNode getIntersect(ListNode head) {
        ListNode tortoise = head;
        ListNode hare = head;
        while (hare != null && hare.next != null) {
            tortoise = tortoise.next;
            hare = hare.next.next;
            if (tortoise == hare) {
                return tortoise;
            }
        }
        return null;
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        int sizeA = 0;
        ListNode p = headA;
        while (p != null) {
            p = p.next;
            sizeA++;
        }
        int sizeB = 0;
        ListNode q = headB;
        while (q != null) {
            q = q.next;
            sizeB++;
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
        while (p != null && q != null) {
            if (p == q) {
                return p;
            }
            p = p.next;
            q = q.next;
        }
        return null;
    }

    public ListNode oddEvenList(ListNode head) {
        if (head == null) return null;
        ListNode odd = head, even = head.next, evenHead = even;
        while (even != null && even.next != null) {
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }
        odd.next = evenHead;
        return head;
    }
}
