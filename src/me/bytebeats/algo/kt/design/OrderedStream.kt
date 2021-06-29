package me.bytebeats.algo.kt.design

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/6/29 16:23
 * @Version 1.0
 * @Description TO-DO
 */

class OrderedStream(val n: Int) {
    //1656
    private val data = sortedMapOf<Int, String>()
    private var ptr = 1

    fun insert(idKey: Int, value: String): List<String> {
        val ans = mutableListOf<String>()
        data[idKey] = value
        if (ptr == idKey) {
            while (data.containsKey(ptr)) {
                ans.add(data[ptr]!!)
                ptr += 1
            }
        } else {
        }
        return ans
    }

}

/**
 * Your OrderedStream object will be instantiated and called as such:
 * var obj = OrderedStream(n)
 * var param_1 = obj.insert(idKey,value)
 */