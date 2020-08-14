package me.bytebeats.algo.designs

class CombinationIterator(val characters: String, val combinationLength: Int) {
    //1286
    private val idx = IntArray(combinationLength) { it }

    fun next(): String {
        val sb = StringBuilder()
        for (i in idx) {
            sb.append(characters[i])
        }

        return sb.toString()
    }

    fun hasNext(): Boolean {
        return false
    }

}