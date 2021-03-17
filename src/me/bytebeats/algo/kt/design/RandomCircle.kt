package me.bytebeats.algo.kt.design

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/3/17 16:42
 * @Version 1.0
 * @Description TO-DO
 */

class RandomCircle(val radius: Double, val x_center: Double, val y_center: Double) {//478

    fun randPoint(): DoubleArray {
        val angle = 2 * PI * Random.nextDouble()//[0,1), random angle
        val newRadius = radius * sqrt(Random.nextDouble())//[0, 1), random radius
        val x = newRadius * cos(angle)
        val y = newRadius * sin(angle)
        return doubleArrayOf(x_center + x, y_center + y)
    }

}