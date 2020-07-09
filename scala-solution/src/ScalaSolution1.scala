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

    def numSquares(n: Int): Int = { //279
        val dp = Array.ofDim[Int](n + 1)
        for (i <- 1 to n) {
            dp(i) = i
            var j = 1
            while (j * j <= i) {
                dp(i) = Math.min(dp(i), dp(i - j * j) + 1)
                j += 1
            }
        }
        dp(n)
    }

    def minSubArrayLen(s: Int, nums: Array[Int]): Int = { //209
        if (nums.isEmpty) {
            return 0
        }
        var ans = Int.MaxValue
        var i = 0
        var j = 0
        var sum = 0
        while (j < nums.size) {
            sum += nums(j)
            while (i < nums.size && sum >= s) {
                ans = Math.min(ans, j - i + 1)
                sum -= nums(i)
                i += 1
            }
            j += 1
        }
        if (ans == Int.MaxValue) 0 else ans
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

    def findKthLargest(nums: Array[Int], k: Int): Int = {//215
        nums.sortInPlace()
        nums(nums.size - k)
    }

    def uniquePaths(m: Int, n: Int): Int = { //62
        val dp = Array.ofDim[Int](m + 1, n + 1)
        for (i <- 1 to m) {
            for (j <- 1 to n) {
                if (i == 1 || j == 1) {
                    dp(i)(j) = 1
                } else {
                    dp(i)(j) = dp(i - 1)(j) + dp(i)(j - 1)
                }
            }
        }
        dp(m)(n)
    }

    def uniquePathsWithObstacles(obstacleGrid: Array[Array[Int]]): Int = {//63
        if (obstacleGrid.isEmpty || obstacleGrid(0).isEmpty) return 0
        if (obstacleGrid(0)(0) == 1) return 0
        val m = obstacleGrid.size
        val n = obstacleGrid(0).size
        for (i <- 0 until m) {
            for (j <- 0 until n) {
                if (obstacleGrid(i)(j) == 1) {
                    obstacleGrid(i)(j) = 0
                } else if (i == 0 && j == 0) {
                    obstacleGrid(i)(j) = 1
                } else if (i == 0) {
                    obstacleGrid(i)(j) = obstacleGrid(i)(j - 1)
                } else if (j == 0) {
                    obstacleGrid(i)(j) = obstacleGrid(i - 1)(j)
                } else {
                    obstacleGrid(i)(j) = obstacleGrid(i - 1)(j) + obstacleGrid(i)(j - 1)
                }
            }
        }
        obstacleGrid(m - 1)(n - 1)
    }

    def oddCells(n: Int, m: Int, indices: Array[Array[Int]]): Int = { //1252
        val matrix = Array.ofDim[Int](n, m)
        for (elem <- indices) {
            val r = elem(0)
            for (j <- 0 until m) {
                matrix(r)(j) += 1
            }
            val c = elem(1)
            for (i <- 0 until n) {
                matrix(i)(c) += 1
            }
        }
        var ans = 0
        for (i <- 0 until n) {
            for (j <- 0 until m) {
                if (matrix(i)(j) % 2 == 1) {
                    ans += 1
                }
            }
        }
        ans
    }

    def findLength(A: Array[Int], B: Array[Int]): Int = { //718
        val row = A.size
        val column = B.size
        val dp = Array.ofDim[Int](row + 1, column + 1)
        var ans = 0
        for (i <- row - 1 to 0 by -1) {
            for (j <- column - 1 to 0 by -1) {
                if (A(i) == B(j)) {
                    dp(i)(j) = 1 + dp(i + 1)(j + 1)
                } else {
                    dp(i)(j) = 0
                }
                if (dp(i)(j) > ans) {
                    ans = dp(i)(j)
                }
            }
        }
        ans
    }

    def arrangeCoins(n: Int): Int = {
        var k = 1
        var nn = n
        while (nn >= k) {
            nn -= k
            k += 1
        }
        k - 1
    }

    def numTeams(rating: Array[Int]): Int = { //1395
        var ans = 0
        val size = rating.length
        for (i <- 0 until size - 2) {
            for (j <- i + 1 until size - 1) {
                for (k <- j + 1 until size) {
                    if (rating(i) < rating(j) && rating(j) < rating(k) || rating(i) > rating(j) && rating(j) > rating(k)) {
                        ans += 1
                    }
                }
            }
        }
        ans
    }

    def numTeams2(rating: Array[Int]): Int = { //1395
        var ans = 0
        val size = rating.length
        for (i <- 1 to size - 2) {
            var iLess = 0
            var iMore = 0
            for (j <- 0 until i) {
                if (rating(j) < rating(i)) {
                    iLess += 1
                } else if (rating(j) > rating(i)) {
                    iMore += 1
                }
            }
            var kLess = 0
            var kMore = 0
            for (j <- i + 1 until size) {
                if (rating(j) < rating(i)) {
                    kLess += 1
                } else if (rating(j) > rating(i)) {
                    kMore += 1
                }
            }
            ans += iLess * kMore + iMore * kLess
        }
        ans
    }

    def minimumTotal(triangle: List[List[Int]]): Int = { //120
        val n = triangle.length
        for (i <- n - 1 to 1 by -1) {
            val s = triangle(i - 1).size
            for (j <- 0 until s) {
                triangle(i - 1)(j) += Math.min(triangle(i)(j), triangle(i)(j + 1))
            }
        }
        triangle(0)(0)
    }

    def imageSmoother(M: Array[Array[Int]]): Array[Array[Int]] = { //661
        val m = M.size
        val n = M(0).size
        val matrix = Array.ofDim[Int](m, n)
        for (i <- 0 until m) {
            for (j <- 0 until n) {
                var sum = M(i)(j)
                var count = 1
                if (i > 0) {
                    sum += M(i - 1)(j)
                    count += 1
                    if (j > 0) {
                        sum += M(i - 1)(j - 1)
                        count += 1
                    }
                    if (j < n - 1) {
                        sum += M(i - 1)(j + 1)
                        count += 1
                    }
                }
                if (i < m - 1) {
                    sum += M(i + 1)(j)
                    count += 1
                    if (j > 0) {
                        sum += M(i + 1)(j - 1)
                        count += 1
                    }
                    if (j < n - 1) {
                        sum += M(i + 1)(j + 1)
                        count += 1
                    }
                }
                if (j > 0) {
                    sum += M(i)(j - 1)
                    count += 1
                }
                if (j < n - 1) {
                    sum += M(i)(j + 1)
                    count += 1
                }
                matrix(i)(j) = sum / count
            }
        }
        matrix
    }

    def wiggleSort(nums: Array[Int]): Unit = {//280
        nums.sortInPlace()
        var tmp = 0
        for (i <- 2 until nums.size by 2) {
            tmp = nums(i)
            nums(i) = nums(i - 1)
            nums(i - 1) = tmp
        }
    }

    def kthSmallest(matrix: Array[Array[Int]], k: Int): Int = { //378
        val n = matrix.size
        var left = matrix(0)(0)
        var right = matrix(n - 1)(n - 1)
        var mid = 0
        while (left < right) {
            mid = left + ((right - left) >> 1)
            if (count(matrix, mid, k, n)) right = mid
            else left = mid + 1
        }
        left
    }

    private def count(matrix: Array[Array[Int]], mid: Int, k: Int, n: Int): Boolean = {
        var i = n - 1
        var j = 0
        var count = 0
        while (i >= 0 && j < n) {
            if (matrix(i)(j) <= mid) {
                count += i + 1
                j += 1
            } else {
                i -= 1
            }
        }
        count >= k
    }

    def prisonAfterNDays(cells: Array[Int], N: Int): Array[Int] = { //957
        var pre = 0
        var next = 0
        var tmp = 0
        for (i <- 0 until N) {
            pre = -1
            for (j <- 1 until cells.size - 1) {
                if (pre == -1) {
                    pre = cells(j - 1)
                }
                next = cells(j + 1)
                tmp = cells(j)
                if (pre == next) {
                    cells(j) = 1
                } else {
                    cells(j) = 0
                }
                pre = tmp
            }
            if (cells(0) != 0) {
                cells(0) = 0
            } else if (cells.last != 0) {
                cells(cells.size - 1) = 0
            }
        }
        cells
    }

    def uniquePathsWithObstacles1(obstacleGrid: Array[Array[Int]]): Int = { //63
        val row = obstacleGrid.size
        val column = obstacleGrid(0).size
        if (obstacleGrid(0)(0) == 1) return 0
        for (i <- 0 until row) {
            for (j <- 0 until column) {
                if (obstacleGrid(i)(j) == 1) {
                    obstacleGrid(i)(j) = 0
                } else if (i == 0 && j == 0) {
                    obstacleGrid(0)(0) = 1
                } else if (i == 0) {
                    obstacleGrid(i)(j) = obstacleGrid(i)(j - 1)
                } else if (j == 0) {
                    obstacleGrid(i)(j) = obstacleGrid(i - 1)(j)
                } else {
                    obstacleGrid(i)(j) = obstacleGrid(i - 1)(j) + obstacleGrid(i)(j - 1)
                }
            }
        }
        obstacleGrid(row - 1)(column - 1)
    }

    def distanceBetweenBusStops(distance: Array[Int], start: Int, destination: Int): Int = {//1184
        if (start == destination) return 0
        val n = distance.size
        var s = start.min(destination)
        var d = destination.max(start)
        var forward = 0
        while (s != d) {//clockwise
            forward += distance(s)
            s = (s + 1) % n
        }
        s = start.min(destination)
        d = destination.max(start)
        var backward = 0
        while (s != d) {//counterclockwise
            s = ((s - 1) % n + n) % n
            backward += distance(s)
        }
        forward.min(backward)
    }

    def plusOne(digits: Array[Int]): Array[Int] = { //68
        val size = digits.size
        var reminder = 1
        for (i <- size - 1 to 0 by -1) {
            digits(i) += reminder
            reminder = digits(i) / 10
            digits(i) %= 10
        }
        if (reminder > 0) {
            val ans = Array.ofDim[Int](size + 1)
            ans(0) = reminder
            for (i <- 0 until size) {
                ans(i + 1) = digits(i)
            }
            return ans
        }
        digits
    }

    def binaryGap(N: Int): Int = { //868
        val list = scala.collection.mutable.ListBuffer[Int]()
        var n = N
        var i = 0
        while (n > 0) {
            if (n % 2 == 1) {
                list += i
            }
            i += 1
            n >>= 1
        }
        if (list.size <= 1) {
            return 0
        }
        var span = 0
        for (i <- 1 until list.size) {
            span = span.max(list(i) - list(i - 1))
        }
        span
    }

    def prefixesDivBy5(A: Array[Int]): Array[Boolean] = { //1018
        val size = A.size
        val ans = Array.ofDim[Boolean](size)
        var sum = 0
        for (i <- 0 until size) {
            sum = sum * 2 + A(i)
            sum %= 5//in case of StackOverFlow
            ans(i) = sum == 0
        }
        ans
    }

    def projectionArea(grid: Array[Array[Int]]): Int = { //883
        val x = grid.size
        val y = grid(0).size
        var xy = 0
        var yz = 0
        var xz = 0
        for (i <- 0 until x) {
            for (j <- 0 until y) {
                if (grid(i)(j) != 0) {
                    xy += 1
                }
            }
        }
        for (i <- 0 until x) {
            var tmpxz = 0
            for (j <- 0 until y) {
                if (grid(i)(j) > tmpxz) {
                    tmpxz = grid(i)(j)
                }
            }
            xz += tmpxz
        }
        for (j <- 0 until y) {
            var tmpyz = 0
            for (i <- 0 until x) {
                if (grid(i)(j) > tmpyz) {
                    tmpyz = grid(i)(j)
                }
            }
            yz += tmpyz
        }
        xy + yz + xz
    }

    def removePalindromeSub(s: String): Int = {//1332
        if (s.isEmpty) return 0
        var i = 0
        var j = s.size - 1
        while (i < j) {
            if (s(i) != s(j)) {
                return 2
            }
            i += 1
            j -= 1
        }
        1
    }

    def islandPerimeter(grid: Array[Array[Int]]): Int = {//463, BFS
        if (grid.isEmpty || grid(0).isEmpty) return 0
        val row = grid.size
        val column = grid(0).size
        var ans = 0
        val pairs = scala.collection.mutable.ListBuffer[List[Int]]()
        for (i <- 0 until row) {
            for (j <- 0 until column) {
                if (grid(i)(j) == 1) {
                    pairs += List(i, j)
                }
            }
        }
        var x = 0
        var y = 0
        pairs.foreach(pair => {
            x = pair(0)
            y = pair(1)
            if (x == 0) ans += 1
            if (y == 0) ans += 1
            if (x == row - 1) ans += 1
            if (y == column - 1) ans += 1
            if (x > 0 && grid(x - 1)(y) == 0) ans += 1
            if (y > 0 && grid(x)(y - 1) == 0) ans += 1
            if (x < row - 1 && grid(x + 1)(y) == 0) ans += 1
            if (y < column - 1 && grid(x)(y + 1) == 0) ans += 1
        }
        )
        ans
    }

    def relativeSortArray(arr1: Array[Int], arr2: Array[Int]): Array[Int] = { //1122
        arr1.sortInPlaceWith((a, b) => {
            val ia = arr2.indexOf(a)
            val ib = arr2.indexOf(b)
            if (ia == -1 && ib == -1) {
                a < b
            } else if (ia == -1) {
                false
            } else if (ia != -1 && ib != -1) {
                ia < ib
            } else {
                true
            }
        }
        )
        arr1
    }

    def divingBoard(shorter: Int, longer: Int, k: Int): Array[Int] = {//面试题 16.11
        if (k == 0) return Array()
        if (shorter == longer) return Array(shorter * k)
        val ans = scala.collection.mutable.Set[Int]()
        for (i <- 0 to k) {
            ans += shorter * i + longer * (k - i)
        }
        ans.toArray.sorted
    }

}
