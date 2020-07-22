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
}
