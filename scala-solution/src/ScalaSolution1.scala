import scala.util.control.Breaks.breakable
import scala.util.control.Breaks.break

object ScalaSolution1 {
    def judgeCircle(moves: String): Boolean = { //657
        var x = 0
        var y = 0
        moves.foreach { c =>
            if (c == 'U') {
                y += 1
            } else if (c == 'D') {
                y -= 1
            } else if (c == 'R') {
                x += 1
            } else if (c == 'L') {
                x -= 1
            }
        }
        x == 0 && y == 0
    }

    def rotateString(A: String, B: String): Boolean = { //796
        A.length == B.length && s"$A$A".contains(B)
    }

    def shiftGrid(grid: Array[Array[Int]], k: Int): List[List[Int]] = { //1260
        val m = grid.size
        val n = grid(0).size
        val ans = Array.fill(m) {
            Array.fill(n) {
                0
            }
        }
        val size = m * n
        val kk = k % size
        for (i <- 0 until size) {
            val ii = (i + kk) % size
            ans(ii / n)(ii % n) = grid(i / n)(i % n)
        }
        ans.map {
            _.toList
        }.toList
    }

    def removeOuterParentheses(S: String): String = { //1201
        var list = Array[String]()
        var left = 0
        var right = 0
        var start = 0
        for (i <- 0 until S.length) {
            if (S(i) == '(') {
                left += 1
            } else {
                right += 1
            }
            if (left == right) {
                left = 0
                right = 0
                list = list :+ S.substring(start, i + 1)
                start = i + 1
            }
        }
        list.foldLeft("") { (left, next) => s"$left${next.substring(1, next.size - 1)}" }
    }

    def isPalindrome(x: Int): Boolean = { //9
        val s = x.toString
        var left = 0
        var right = s.size - 1
        while (left < right) {
            if (s(left) == s(right)) {
                left += 1
                right -= 1
            } else {
                return false
            }
        }
        true
    }

    def isPalindrome1(x: Int): Boolean = { //9
        if (x < 0 || x % 10 == 0 && x != 0) {
            return false
        }
        var right = 0
        var xx = x
        while (xx > right) {
            right *= 10
            right += xx % 10
            xx /= 10
        }

        xx == right || xx == right / 10
    }

    def nthPersonGetsNthSeat(n: Int): Double = { //1227
        if (n == 1) 1.0 else 0.5
    }

    def isArmstrong(N: Int): Boolean = { //1134
        if (N < 0) {
            return false
        }
        var k = 0
        var nn = N
        while (nn != 0) {
            k += 1
            nn /= 10
        }
        var sum = 0
        nn = N
        while (nn != 0) {
            sum += Math.pow(nn % 10, k).toInt
            nn /= 10
        }
        N == sum
    }

    def defangIPaddr(address: String): String = { //1108
        val ans = new StringBuilder
        address.foreach(ch => if (ch == '.') {
            ans.append('[')
            ans.append(ch)
            ans.append(']')
        } else {
            ans.append(ch)
        }
        )
        ans.toString()
    }

    def searchInsert(nums: Array[Int], target: Int): Int = {
        if (nums.isEmpty) {
            return 0
        }
        if (target < nums(0)) {
            return 0
        }
        if (target > nums(nums.size - 1)) {
            return nums.size - 1
        }
        for (i <- 0 until nums.size) {
            if (target <= nums(i)) {
                return i
            }
        }
        0
    }

    def generatePossibleNextMoves(s: String): List[String] = { //293
        var ans = Array[String]()
        val chars = s.toCharArray
        for (i <- 0 until s.length - 1) {
            if (chars(i) == '+' && chars(i + 1) == '+') {
                chars(i) = '-'
                chars(i + 1) = '-'
                ans = ans :+ chars.mkString("")
                chars(i) = '+'
                chars(i + 1) = '+'
            }
        }
        ans.toList
    }

    //    def canWin(s: String): Boolean = {//294
    //
    //    }
    def fixedPoint(A: Array[Int]): Int = { //1064
        for (j <- A.indices) {
            if (j == A(j)) {
                return j
            }
        }
        -1
    }

    def findLucky(arr: Array[Int]): Int = { //1394
        val map = scala.collection.mutable.Map[Int, Int]()
        arr.foreach(num => if (map.contains(num)) {
            map(num) += 1
        } else {
            map += (num -> 1)
        }
        )
        var ans = -1
        for ((k, v) <- map) {
            if (k == v && v > ans) {
                ans = v
            }
        }
        ans
    }

    def fairCandySwap(A: Array[Int], B: Array[Int]): Array[Int] = { //888
        var ans = Array[Int]()
        val aSum = A.sum
        val bSum = B.sum
        for (a <- A; b <- B) {
            if (aSum - a + b == bSum + a - b) {
                ans = ans :+ a
                ans = ans :+ b
                return ans
            }
        }
        ans
    }

    def sortColors(nums: Array[Int]): Unit = { //75
        //        nums.sortInPlace()//quick sort
        var z = 0
        var o = 0
        var t = 0
        for (n <- nums) {
            if (n == 0) {
                z += 1
            } else if (n == 1) {
                o += 1
            } else {
                t += 1
            }
        }
        var i = 0
        while (z > 0) {
            nums(i) = 0
            z -= 1
            i += 1
        }
        while (o > 0) {
            nums(i) = 1
            o -= 1
            i += 1
        }
        while (t > 0) {
            nums(i) = 1
            t -= 1
            i += 1
        }
    }

    def maxScoreSightseeingPair(A: Array[Int]): Int = { //1014
        var ans = 0
        for (i <- 0 until A.size - 1) {
            for (j <- i + 1 until A.size) {
                ans = Math.max(ans, A(i) + A(j) + i - j)
            }
        }
        ans
    }

    def maxScoreSightseeingPair2(A: Array[Int]): Int = { //1014
        var ans = 0
        var mx = A(0) + 0
        for (j <- 1 until A.size) {
            ans = Math.max(ans, mx + A(j) - j)
            mx = Math.max(mx, A(j) + j)
        }
        ans
    }

    def kLengthApart(nums: Array[Int], k: Int): Boolean = { //1437
        var i = 0
        var j = 0
        while (i < nums.size) {
            while (i < nums.size && nums(i) == 0) {
                i += 1
            }
            j = i + 1
            while (j < nums.size && nums(j) == 0) {
                j += 1
            }
            if (j < nums.size && j - i - 1 < k) {
                return false
            }
            i = j
        }
        true
    }

    def isAlienSorted(words: Array[String], order: String): Boolean = { //953
        val map = scala.collection.mutable.Map[Char, Int]()
        for (i <- order.indices) {
            map += (order(i) -> i)
        }
        println()
        for ((k, v) <- map) print(s"$k=$v,")
        for (i <- 1 until words.length) {
            println(s"${words(i - 1)}, ${words(i - 1)}, ${isLess(words(i - 1), words(i), map)}")
            if (!isLess(words(i - 1), words(i), map)) {
                return false
            }
        }
        true
    }

    private def isLess(word1: String, word2: String, map: scala.collection.mutable.Map[Char, Int]): Boolean = {
        val size = Math.min(word1.length, word2.length)
        for (i <- 0 until size) {
            if (map(word1(i)) < map(word2(i))) {
                return true
            } else if (map(word1(i)) > map(word2(i))) {
                return false
            }
        }
        word1.length <= word2.length
    }

    def kWeakestRows(mat: Array[Array[Int]], k: Int): Array[Int] = { //1337
        val twoD = Array.ofDim[Int](mat.length, 2)
        for (i <- mat.indices) {
            twoD(i)(0) = i
            twoD(i)(1) = mat(i).sum
        }
        twoD.sortBy(arr => arr(1)).map(arr => arr(0)).take(k)
    }

    def threeSumClosest(nums: Array[Int], target: Int): Int = { //16
        var ans = 0
        if (!nums.isEmpty) {
            nums.sortInPlace()
            var left = 0
            var right = 0
            var abs = Int.MaxValue
            var sum = 0
            for (i <- 0 until nums.length - 2) {
                left = i + 1
                right = nums.length - 1
                while (left < right) {
                    sum = nums(i) + nums(left) + nums(right)
                    if (Math.abs(sum - target) < abs) {
                        abs = Math.abs(sum - target)
                        ans = sum
                    }
                    if (sum < target) {
                        left += 1
                    } else if (sum > target) {
                        right -= 1
                    } else {
                        return ans
                    }
                }
            }
        }
        ans
    }

    def wordBreak(s: String, wordDict: List[String]): Boolean = {//139, dp
        if (s.isEmpty) return true
        var flag = false
        wordDict.foreach(pre => if (s.startsWith(pre)) {
            flag |= wordBreak(s.substring(pre.length), wordDict)
        }
        )
        flag
    }

    def wordBreak1(s: String, wordDict: List[String]): Boolean = { //139, dp
        val set = scala.collection.mutable.Set[String]()
        set.addAll(wordDict)
        val size = s.length
        val dp = Array.ofDim[Boolean](size + 1)
        dp(0) = true//means "" is true
        for (i <- 1 to size) {
            breakable {
                for (j <- 0 until i) {
                    if (dp(j) && set.contains(s.substring(j, i))) {
                        dp(i) = true
                        break()
                    }
                }
            }
        }
        dp(size)
    }

    def findDuplicate(nums: Array[Int]): Int = {
        var fast = 0
        var slow = 0
        while (true) {
            fast = nums(nums(fast))
            slow = nums(slow)
            if (slow == fast) {
                fast = 0
                while (nums(slow) != nums(fast)) {
                    fast = nums(fast)
                    slow = nums(slow)
                }
                return nums(slow)
            }
        }
        0
    }

    def maxNumberOfBalloons(text: String): Int = { //1189
        val bln = "balloon"
        val balloon = Array.ofDim[Int](26)
        bln.foreach(ch => balloon(ch - 'a') += 1)
        val counts = Array.ofDim[Int](26)
        text.foreach(ch => counts(ch - 'a') += 1)
        var count = Int.MaxValue
        for (i <- 0 until 26) {
            if (balloon(i) > 0) {
                if (counts(i) > 0) {
                    count = Math.min(count, counts(i) / balloon(i))
                } else {
                    return 0
                }
            }
        }
        count
    }

    def findSpecialInteger(arr: Array[Int]): Int = { //1287
        var cnt = 0
        var tgt = arr.head
        for (i <- 0 until arr.size) {
            if (tgt == arr(i)) {
                cnt += 1
                if (cnt * 4 > arr.size) {
                    return tgt
                }
            } else {
                tgt = arr(i)
                cnt = 1
            }
        }
        -1
    }

    def largestPerimeter(A: Array[Int]): Int = {//976
        A.sortInPlace()
        for (i <- A.size - 1 to 2 by -1) {
            if (A(i - 2) + A(i - 1) > A(i)) {
                return A(i - 2) + A(i - 1) + A(i)
            }
        }
        0
    }

    def convertToBase7(num: Int): String = { //504
        if (num == 0) return "0"
        var ans = ""
        var positive = true
        var n = num
        if (num < 0) {
            n = -num
            positive = false
        }
        while (n != 0) {
            ans = s"${n % 7}$ans"
            n /= 7
        }
        if (positive) ans
        else s"-$ans"
    }

    def firstMissingPositive(nums: Array[Int]): Int = { //41
        val s = nums.size
        for (i <- 0 until s) {
            while (nums(i) > 0 && nums(i) <= s && nums(i) != nums(nums(i) - 1)) {
                val tmp = nums(nums(i) - 1)
                nums(nums(i) - 1) = nums(i)
                nums(i) = tmp
            }
        }
        for (i <- 0 until s) {
            if (nums(i) != i + 1) return i + 1
        }
        s + 1
    }
}
