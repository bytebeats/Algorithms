class CustomStack(val maxSize: Int) {
    private var size = 0
    private val table = IntArray(maxSize)

    fun push(x: Int) {
        if (size >= maxSize) {
            return
        }
        table[size] = x
        size++
    }

    fun pop(): Int {
        if (size < 1) {
            return -1
        } else {
            size--
            return table[size]
        }
    }

    fun increment(k: Int, `val`: Int) {
        val firstK = if (k > size) size else k
        for (i in 0 until firstK) {
            table[i] += `val`
        }
    }

}