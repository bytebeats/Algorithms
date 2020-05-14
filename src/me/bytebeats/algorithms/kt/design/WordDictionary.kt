package me.bytebeats.algorithms.kt.design

class WordDictionary() {//211

    /** Initialize your data structure here. */
    val dict = mutableSetOf<String>()

    /** Adds a word into the data structure. */
    fun addWord(word: String) {
        if (!dict.contains(word)) {
            dict.add(word)
        }
    }

    /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
    fun search(word: String): Boolean {
        if (dict.contains(word)) {
            return true
        }
        outer@ for (w in dict) {
            if (w.length == word.length) {
                for (i in w.indices) {
                    if (w[i] != '.' && word[i] != '.' && w[i] != word[i]) {
                        continue@outer
                    }
                }
                return true
            }
        }
        return false
    }

}