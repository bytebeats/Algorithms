package me.bytebeats.algorithms.kt.design

class NumMatrix(val matrix: Array<IntArray>) {//308

    fun update(row: Int, col: Int, `val`: Int) {
        matrix[row][col] = `val`
    }

    fun sumRegion(row1: Int, col1: Int, row2: Int, col2: Int): Int {
        var sum = 0
        for (i in row1..row2) {
            for (j in col1..col2) {
                sum += matrix[i][j]
            }
        }
        return sum
    }

}

class NumMatrix2(val matrix: Array<IntArray>) {//308

    private val sumMatrix: Array<IntArray> =
        Array(matrix.size + 1) { IntArray(if (matrix.isNotEmpty()) matrix[0].size + 1 else 1) }

    init {
        for (i in matrix.indices) {
            for (j in matrix[0].indices) {
                sumMatrix[i + 1][j + 1] = matrix[i][j] + sumMatrix[i + 1][j] + sumMatrix[i][j + 1] - sumMatrix[i][j]
            }
        }

    }

    fun update(row: Int, col: Int, `val`: Int) {
        val diff = `val` - matrix[row][col]
        matrix[row][col] = `val`
        for (i in row until matrix.size) {
            for (j in col until matrix[0].size) {
                sumMatrix[i + 1][j + 1] += diff
            }
        }
    }

    fun sumRegion(row1: Int, col1: Int, row2: Int, col2: Int): Int {
        return sumMatrix[row1][col1] - sumMatrix[row1][col2 + 1] - sumMatrix[row2 + 1][col1] + sumMatrix[row2 + 1][col2 + 1]
    }

}

class NumMatrix3(matrix: Array<IntArray>) {//304

    private val sumMatrix = Array(matrix.size + 1) { IntArray(if (matrix.isNotEmpty()) matrix[0].size + 1 else 1) }

    init {
        for (i in matrix.indices) {
            for (j in matrix[0].indices) {
                sumMatrix[i + 1][j + 1] = sumMatrix[i + 1][j] + sumMatrix[i][j + 1] + matrix[i][j] - sumMatrix[i][j]
            }
        }
    }

    fun sumRegion(row1: Int, col1: Int, row2: Int, col2: Int): Int {
        return sumMatrix[row2 + 1][col2 + 1] - sumMatrix[row1][col2 + 1] - sumMatrix[row2 + 1][col1] + sumMatrix[row1][col1]
    }

}