package me.bytebeats.algo.kt

import me.bytebeats.algs.ds.TreeNode
import kotlin.math.absoluteValue
import kotlin.math.min

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/9/7 19:30
 * @Version 1.0
 * @Description TO-DO
 */

class Solution13 {
    fun minimumSwitchingTimes(source: Array<IntArray>, target: Array<IntArray>): Int {
        val sm = map(source)
        val tm = map(target)
        sm.forEach { (t, u) ->
            if (tm.containsKey(t)) {
                tm[t] = (tm[t]!! - u).absoluteValue
            } else {
                tm[t] = u
            }
        }
        return tm.values.sum() / 2
    }

    private fun map(src: Array<IntArray>): MutableMap<Int, Int> {
        val map = mutableMapOf<Int, Int>()
        for (ints in src) {
            for (i in ints) {
                map.compute(i) { _, v -> if (v == null) 1 else v + 1 }
            }
        }
        return map
    }

    fun maxmiumScore(cards: IntArray, cnt: Int): Int {
        val evens = mutableListOf<Int>()
        val odds = mutableListOf<Int>()
        for (card in cards) {
            if (card and 1 == 0) {
                evens.add(card)
            } else {
                odds.add(card)
            }
        }
        evens.sort()
        odds.sort()
        if (cnt > evens.size && (cnt - evens.size) and 1 == 0 || evens.isEmpty() && cnt and 1 == 1) {
            println("$cnt, ${cnt - evens.size}")
            return 0
        }
        var sum = 0
        var i = 0
        while (i < cnt) {
            if (odds.size > 1) {
                val odd = odds.takeLast(2).sum()
                if (evens.isEmpty()) {
                    i += 2
                    sum += odd
                    odds.removeAt(odds.lastIndex)
                    odds.removeAt(odds.lastIndex)
                } else {
                    val even = evens.last()
                    if (even >= odd) {
                        sum += even
                        evens.removeAt(evens.lastIndex)
                        i++
                    } else {
                        i += 2
                        sum += odd
                        odds.removeAt(odds.lastIndex)
                        odds.removeAt(odds.lastIndex)
                    }
                }
            } else {
                sum += evens.removeAt(evens.lastIndex)
                i++
            }
        }
        return sum
    }

    fun numColor(root: TreeNode?): Int {
        val set = mutableSetOf<Int>()
        traversal(root, set)
        return set.size
    }

    private fun traversal(node: TreeNode?, colors: MutableSet<Int>) {
        if (node == null) return
        colors.add(node.`val`)
        if (node.left != null) {
            traversal(node.left, colors)
        }
        if (node.right != null) {
            traversal(node.right, colors)
        }
    }

    fun reformatNumber(number: String): String {//1694
        val num = number.replace(" ", "").replace("-", "")
        val count = num.length
        val sb = StringBuilder()
        val rem = num.length % 3
        val firstN = if (rem == 0) count - 3 else if (rem == 1) count - 4 else count - 2
        val lastM = count - firstN
        for (i in 0 until firstN) {
            sb.append(num[i])
            if (i % 3 == 2) {
                sb.append("-")
            }
        }
        sb.append(num.takeLast(lastM))
        if (lastM == 4) {
            sb.insert(sb.length - 2, "-")
        }
        return sb.toString()
    }

    fun countStudents(students: IntArray, sandwiches: IntArray): Int {//1700
        val counts = IntArray(2)
        for (student in students) {
            counts[student] += 1
        }
        val n = sandwiches.size
        for (i in 0 until n) {
            if (counts[sandwiches[i]] > 0) {
                counts[sandwiches[i]] -= 1
            } else {
                return n - i
            }
        }
        return 0
    }

    fun largestAltitude(gain: IntArray): Int {//1732
        var max = 0
        var h = 0
        for (i in gain.indices) {
            h += gain[i]
            if (h > max) {
                max = h
            }
        }
        return max
    }

    fun totalMoney(n: Int): Int {//1716
        val weeks = n / 7
        val mod = n % 7
        return 28 * weeks + 7 * weeks * (weeks - 1) / 2 + weeks * mod + mod * (mod + 1) / 2
    }

    private val memo = mutableMapOf<Int, Int>()
    fun integerReplacement(n: Int): Int {//397
        if (n == 1) return 0
        if (!memo.containsKey(n)) {
            if (n and 1 == 0) {
                memo[n] = 1 + integerReplacement(n / 2)
            } else {
                memo[n] = 2 + min(integerReplacement(n / 2), integerReplacement(n / 2 + 1))
            }
        }
        return memo[n]!!
    }

    fun mergeAlternately(word1: String, word2: String): String {//1768
        val result = StringBuilder()
        val S1 = word1.length
        val S2 = word2.length
        var i = 0
        var j = 0
        while (i < S1 && j < S2) {
            if (i > j) {
                result.append(word2[j])
                j++
            } else {
                result.append(word1[i])
                i++
            }
        }
        while (i < S1) {
            result.append(word1[i])
            i++
        }
        while (j < S2) {
            result.append(word2[j])
            j++
        }
        return result.toString()
    }

    fun maxAscendingSum(nums: IntArray): Int {//1800
        var maxSum = Int.MIN_VALUE
        val S = nums.size
        var i = 0
        while (i < S) {
            var subSum = nums[i]
            var j = i + 1
            while (j < S && nums[j - 1] < nums[j]) {
                subSum += nums[j]
                j++
            }

            if (maxSum < subSum) {
                maxSum = subSum
            }
            i = j
        }
        return maxSum
    }

    fun secondHighest(s: String): Int {//1796
        val digitals = s.filter { it.isDigit() }.toSortedSet().toList()
        return if (digitals.size > 1) {
            digitals[digitals.size - 2] - '0'
        } else {
            -1
        }
    }

    /**
     * 构建大顶堆的过程
     */
    fun buildMaxHeap(tree: IntArray, p: Int, size: Int) {
        //只有发生交换时才会继续循环, 以被交换的子结点作为父节点继续向下重构
        var tmp = 0
        var pp = p//完全二 X 树非叶节点的坐标,
        var i = p * 2 + 1//非叶结点左子结点的坐标
        while (i < size) {
            //找到左右子结点中值相对较大的子结点
            if (i + 1 < size && tree[i] < tree[i + 1]) {
                i += 1 //右子结点值更大
            }
            //如果子结点中较大的结点值大于父结点, 则交换
            if (tree[i] > tree[pp]) {
                tmp = tree[i]
                tree[i] = tree[pp]
                tree[pp] = tmp
                pp = i//被交换子结点作为父结点重复以上交换的过程
            } else {//如果父结点比子节点值大, 直接退出即可, 因为是从底层调整到上层的
                break
            }
            i = i * 2 + 1//处理被交换的子结点作为交结点之后的再交换
        }
    }

    fun heapSort(tree: IntArray) {
        //Step a: 将无序数组作为完全二 X 树处理, 构建大顶堆
        val size = tree.size
        //p指向父结点, 从最后一个非叶子结点, 调整到根结点
        var p = size / 2 - 1//size / 2 -1 是完全二 X 树的最后一个非叶子结点
        //将完全二 X 树的非叶节点从后往前进行调整交换, 构建最大堆
        while (p >= 0) {
            buildMaxHeap(tree, p, size)
            p--
        }

        //大顶堆在构建完成后进行堆排序
        //交换根和尾结点, 缩小堆尺寸, 重构大顶堆. 重复这个过程, 直到尺寸减少为1
        var tmp = 0
        var s = size
        while (s > 1) {
            //将头结点和最后结点进行交换, 此时最后的结点值即为最大值.
            tmp = tree[0]
            tree[0] = tree[s - 1]
            tree[s - 1] = tmp
            s--//缩小尺寸
            buildMaxHeap(tree, 0, s)
        }
    }
}