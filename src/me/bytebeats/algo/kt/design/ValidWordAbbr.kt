package me.bytebeats.algo.kt.design

class ValidWordAbbr(dictionary: Array<String>) { //288
    private val abbrMap = mutableMapOf<String, MutableSet<String>>()

    init {
        dictionary.forEach {
            val abbr = abbr(it)
            if (abbrMap.containsKey(abbr)) {
                abbrMap[abbr]?.add(it)
            } else {
                val set = mutableSetOf<String>()
                set.add(it)
                abbrMap[abbr] = set
            }
        }
    }

    fun isUnique(word: String): Boolean {
        val abbr = abbr(word)
        return !abbrMap.containsKey(abbr) || abbrMap[abbr]?.contains(word) == true && abbrMap[abbr]?.size ?: 0 == 1
    }

    private fun abbr(word: String): String {
        val abbr = StringBuilder()
        if (word.length > 2) {
            val length = word.length
            abbr.append(word[0])
            abbr.append(length - 2)
            abbr.append(word.last())
        } else {
            abbr.append(word)
        }
        return abbr.toString()
    }

}