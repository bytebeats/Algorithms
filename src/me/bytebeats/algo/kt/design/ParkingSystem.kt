package me.bytebeats.algo.kt.design

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/3/19 10:30
 * @Version 1.0
 * @Description TO-DO
 */

class ParkingSystem(big: Int, medium: Int, small: Int) {
    //1603
    private val parked = mutableMapOf<Int, Int>()
    private val limits = mutableMapOf<Int, Int>()

    init {
        parked[1] = 0
        parked[2] = 0
        parked[3] = 0
        limits[1] = big
        limits[2] = medium
        limits[3] = small
    }

    fun addCar(carType: Int): Boolean {
        return if (parked[carType]!! >= limits[carType]!!) {
            false
        } else {
            parked.compute(carType) { _, v -> v!! + 1 }
            true
        }
    }

}