

case class ScalaSolution1() {
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
        })
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
}
