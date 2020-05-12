package me.bytebeats.algorithms.kt.design

class LogSystem() {//635

    val logs = mutableListOf<LongArray>()
    val map = mapOf("Year" to 0, "Month" to 1, "Day" to 2, "Hour" to 3, "Minute" to 4, "Second" to 5)

    fun put(id: Int, timestamp: String) {
        val st = timestamp.split(":").map { it.toLong() }.toLongArray()
        logs.add(longArrayOf(convert(st), id.toLong()))
    }

    private fun convert(st: LongArray): Long {
        st[1] -= if (st[1] == 0L) 0L else 1L
        st[2] -= if (st[2] == 0L) 0L else 1L
        return (st[0] - 1999L) * (31 * 12) * 24 * 60 * 60 + st[1] * 31 * 24 * 60 * 60 + st[2] * 24 * 60 * 60 + st[3] * 60 * 60 + st[4] * 60 + st[5]
    }

    fun retrieve(s: String, e: String, gra: String): List<Int> {
        val ans = mutableListOf<Int>()
        val start = granularity(s, gra, false)
        val end = granularity(e, gra, true)
        for (i in logs.indices) {
            if (logs[i][0] in start until end) {
                ans.add(logs[i][1].toInt())
            }
        }
        return ans
    }

    private fun granularity(s: String, gra: String, end: Boolean): Long {
        val res = arrayOf("1999", "00", "00", "00", "00", "00")
        val split = s.split(":")
        for (i in 0..map[gra]!!) {
            res[i] = split[i]
        }
        val t = res.map { it.toLong() }.toLongArray()
        if (end) {
            t[map[gra]!!]++
        }
        return convert(t)
    }

}