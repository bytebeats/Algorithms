package me.bytebeats.algorithms.kt.design

class StringIterator(var compressedString: String) {

    fun next(): Char {
        if (compressedString.isEmpty()) {
            return ' '
        } else {
            val s = 0
            var e = -1
            for (i in 1 until compressedString.length) {
                if (compressedString[i].isLetter()) {
                    e = i
                    break
                }
            }
            if (e == -1) {
                e = compressedString.length
            }
            var pre = compressedString.substring(s, e)
            val res = pre.first()
            var count = pre.substring(1, pre.length).toInt()
            count--
            if (count == 0) {
                if (e == compressedString.length) {
                    compressedString = ""
                } else {
                    compressedString = compressedString.substring(e)
                }
            } else {
                if (e == compressedString.length) {
                    compressedString = "$res$count"
                } else {
                    compressedString = "$res$count${compressedString.substring(e)}"
                }
            }
            return res
        }
    }

    fun hasNext(): Boolean = compressedString.isNotEmpty()

}