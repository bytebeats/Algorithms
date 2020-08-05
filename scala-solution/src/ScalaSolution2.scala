import me.bytebeats.algs.ds.TreeNode

object ScalaSolution2 {

    def twoSum(numbers: Array[Int], target: Int): Array[Int] = { //167
        val ans = Array.ofDim[Int](2)
        var i = 0
        var j = numbers.size - 1
        while (i < j) {
            if (numbers(i) + numbers(j) > target) {
                j -= 1
            } else if (numbers(i) + numbers(j) < target) {
                i += 1
            } else {
                ans(0) = i + 1
                ans(1) = j + 1
                return ans
            }
        }
        ans
    }

    def minArray(numbers: Array[Int]): Int = { //剑指 offer 11
        val size = numbers.size
        var left = 0
        var right = size - 1
        var mid = 0
        while (left < right) {
            mid = left + (right - left) / 2
            if (numbers(mid) > numbers(right)) {
                left = mid + 1
            } else if (numbers(mid) < numbers(right)) {
                right = mid
            } else {
                right -= 1
            }
        }
        numbers(left)
    }

    def minPathSum(grid: Array[Array[Int]]): Int = {//64
        if (grid.isEmpty || grid(0).isEmpty) return 0
        val r = grid.size
        val c = grid(0).size
        val dp = Array.ofDim[Int](r, c)
        for (i <- 0 until r) {
            for (j <- 0 until c) {
                if (i == 0 && j == 0) {
                    dp(i)(j) = grid(i)(j)
                } else if (i == 0) {
                    dp(i)(j) = dp(i)(j - 1) + grid(i)(j)
                } else if (j == 0) {
                    dp(i)(j) = dp(i - 1)(j) + grid(i)(j)
                } else {
                    dp(i)(j) = grid(i)(j) + dp(i - 1)(j).min(dp(i)(j - 1))
                }
            }
        }
        dp(r - 1)(c - 1)
    }

    def singleNumber(nums: Array[Int]): Array[Int] = {
        var xor = 0
        for (elem <- nums) {
            xor ^= elem
        }
        val diff = xor & -xor
        var a = 0
        for (elem <- nums) {
            if ((diff & elem) != 0) {
                a ^= elem
            }
        }
        val ans = Array.ofDim[Int](2)
        ans(0) = a
        ans(1) = a ^ xor
        ans
    }

    def divisorGame(N: Int): Boolean = {//1025
        N % 2 == 0
    }

    def restoreString(s: String, indices: Array[Int]): String = { //1528
        val map = scala.collection.mutable.TreeMap[Int, Char]()
        for (i <- s.indices) {
            map += (indices(i) -> s(i))
        }
        map.toSeq.sortBy(_._1).map(_._2).mkString("")
    }

    def countOdds(low: Int, high: Int): Int = { //1523
        var ans = 0L
        if ((low & 1) == 0) {
            ans += (high - low + 1L) / 2
        } else {
            ans += (high - low) / 2 + 1L
        }
        ans.toInt
    }

    def shortestCompletingWord(licensePlate: String, words: Array[String]): String = { //748
        val chs = Array.ofDim[Int](26)
        licensePlate.foreach(ch => {
            if (ch.isLetter) {
                chs(ch.toLower - 'a') += 1
            }
        }
        )
        var ans = ""
        for (elem <- words) {
            if (isGreaterOrEquals(countChars(elem), chs)) {
                if (ans == "") ans = elem
                else if (ans.length > elem.length) ans = elem
            }
        }
        ans
    }

    private def countChars(word: String): Array[Int] = {
        val chs = Array.ofDim[Int](26)
        word.foreach(ch => {
            if (ch.isLetter) {
                chs(ch.toLower - 'a') += 1
            }
        }
        )
        chs
    }

    private def isGreaterOrEquals(chs1: Array[Int], chs2: Array[Int]): Boolean = {
        for (i <- 0 until 26) {
            if (chs1(i) < chs2(i)) {
                return false
            }
        }
        true
    }

    def addDigits(num: Int): Int = {//258
        (num - 1) % 9 + 1
    }

    def findMagicIndex(nums: Array[Int]): Int = { //08.03
        for (i <- nums.indices) {
            if (i == nums(i)) {
                return i
            }
        }
        -1
    }

    def tree(root: TreeNode): TreeNode = {//test
        null
    }
}
