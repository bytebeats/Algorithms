package me.bytebeats.algorithms.kt.design

class MagicDictionary() {//676

    /** Initialize your data structure here. */
    val mDict = mutableSetOf<String>()

    /** Build a dictionary through a list of words */
    fun buildDict(dict: Array<String>) {
        mDict.addAll(dict)
    }

    /** Returns if there is any word in the trie that equals to the given word after modifying exactly one character */
    fun search(word: String): Boolean {
        for (w in mDict) {
            if (word.length == w.length) {
                var diff = 0
                for (i in word.indices) {
                    if (w[i] != word[i]) {
                        diff++
                    }
                }
                if (diff == 1) {
                    return true
                }
            }
        }
        return false
    }

}