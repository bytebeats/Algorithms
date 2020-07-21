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

}
