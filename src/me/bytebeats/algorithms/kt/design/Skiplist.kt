package me.bytebeats.algorithms.kt.design

import java.util.*

class Skiplist() {//1206

    private var head = Node(-1, null, null)
    private val stack = Array<Node?>(64) { null }
    private val random = Random()

    fun search(target: Int): Boolean {
        var p: Node? = head
        while (p != null) {
            while (p?.next != null && p!!.next!!.value < target) {
                p = p.next
            }
            if (p?.next != null && p!!.next!!.value == target) {
                return true
            }
            p = p?.child
        }
        return false
    }

    fun add(num: Int) {
        var lv = -1
        var p: Node? = head
        while (p != null) {
            while (p!!.next != null && p.next!!.value < num) {
                p = p.next
            }
            stack[++lv] = p
            p = p?.child
        }
        var insertUp = true
        var child: Node? = null
        while (insertUp && lv >= 0) {
            val insert = stack[lv--]
            insert?.next = Node(num, insert?.next, child)
            child = insert?.next
            insertUp = random.nextInt() and 1 == 0
        }
        if (insertUp) {
            head = Node(0, Node(num, null, child), head)
        }
    }

    fun erase(num: Int): Boolean {
        var exist = false
        var p: Node? = head
        while (p != null) {
            while (p!!.next != null && p!!.next!!.value < num) {
                p = p.next
            }
            if (p.next != null && p!!.next!!.value <= num) {
                exist = true
                p.next = p?.next?.next
            }
            p = p?.child
        }
        return exist
    }

    private class Node(val value: Int, var next: Node?, var child: Node?)

}