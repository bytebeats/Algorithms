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
}
