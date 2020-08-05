package me.bytebeats.algo.kt.design

class MyLinkedList() {//707

    /** Initialize your data structure here. */
    val head = ListNode(-1)

    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
    fun get(index: Int): Int {
        if (index < 0) {
            return -1
        }
        var count = 0
        var p = head.next
        while (count < index) {
            count++
            p = p?.next
        }
        return p?.`val` ?: -1
    }

    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    fun addAtHead(`val`: Int) {
        val node = ListNode(`val`)
        var p = head.next
        head.next = node
        node.next = p
    }

    /** Append a node of value val to the last element of the linked list. */
    fun addAtTail(`val`: Int) {
        var p: ListNode? = head
        while (p?.next != null) {
            p = p.next
        }
        p?.next = ListNode(`val`)
    }

    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    fun addAtIndex(index: Int, `val`: Int) {
        if (index < 0) {
            return
        }
        var count = 0
        var p: ListNode? = head
        while (count < index) {
            count++
            p = p?.next
        }
        if (p == null) {
            return
        }
        val q = p.next
        p.next = ListNode(`val`)
        p?.next?.next = q
    }

    /** Delete the index-th node in the linked list, if the index is valid. */
    fun deleteAtIndex(index: Int) {
        if (index < 0) {
            return
        }
        var count = 0
        var p: ListNode? = head
        while (count < index) {
            count++
            p = p?.next
        }
        if (p?.next == null) {
            return
        }
        p.next = p?.next?.next
    }

    class ListNode(val `val`: Int) {
        var next: ListNode? = null
    }

}