package me.bytebeats.algorithms.kt.design

class WordDistance(val words: Array<String>) {

    fun shortest(word1: String, word2: String): Int {
        val indices1 = findIndex(words, word1)
        val indices2 = findIndex(words, word2)
        return minDistance(indices1, indices2)
    }

    private fun findIndex(words: Array<String>, word: String): List<Int> {
        val list = ArrayList<Int>()
        words.forEachIndexed { index, s ->
            if (word == s) {
                list.add(index)
            }
        }
        return list
    }

    private fun minDistance(indices1: List<Int>, indices2: List<Int>): Int {
        var minDistance = Int.MAX_VALUE
        for (i in indices1.indices) {
            for (j in indices2.indices) {
                if (Math.abs(indices1[i] - indices2[j]) < minDistance) {
                    minDistance = Math.abs(indices1[i] - indices2[j])
                    if (minDistance == 1) {
                        return minDistance
                    }
                }
            }
        }
        return minDistance
    }
}