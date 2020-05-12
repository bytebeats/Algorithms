package me.bytebeats.algorithms.kt.design

class WordsFrequency(book: Array<String>) {//面试题 16.02

    val wordsFrequency = mutableMapOf<String, Int>()

    init {
        book.forEach {
            wordsFrequency.compute(it) { _, v -> if (v == null) 1 else v + 1 }
        }
    }

    fun get(word: String): Int = wordsFrequency[word] ?: 0

}