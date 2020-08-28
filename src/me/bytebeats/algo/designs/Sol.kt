package me.bytebeats.algo.designs

/**
 * @author bytebeats
 * @email <happychinapc@gmail.com>
 * @github https://github.com/bytebeats
 * @created on 2020/8/28 17:00
 * @version 1.0
 * @description TO-DO
 */
interface SolBase {
    fun rand7(): Int
}

class Sol : SolBase {
    //470
    override fun rand7(): Int {
        return 0
    }

    fun rand10(): Int { //470, Reject Sampling Algorithm
        var r = 0
        var c = 0
        var idx = 0
        do {
            r = rand7()
            c = rand7()
            idx = c + (r - 1) * 7
        } while (idx > 40)
        return 1 + ((idx - 1) % 10 + 10) % 10
    }
}