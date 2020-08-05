package me.bytebeats.algo.kt.design

class AutocompleteSystem(val sentences: Array<String>, val times: IntArray) {//642
    val map = mutableMapOf<String, Int>()
    var prefix = StringBuilder()

    init {
        sentences.forEachIndexed { index, s -> map[s] = times[index] }
    }

    fun input(c: Char): List<String> {
        if (c == '#') {
            map.compute(prefix.toString()) { k, v -> if (v == null) 1 else v + 1 }
            prefix = StringBuilder()
            return emptyList()
        }
        prefix.append(c)
        return map.entries.filter { it.key.startsWith(prefix) }
            .sortedBy { it.key }
            .sortedByDescending { it.value }
            .take(3)
            .map { it.key }.toList()
    }

}