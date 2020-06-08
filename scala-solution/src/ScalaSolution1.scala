

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
}
