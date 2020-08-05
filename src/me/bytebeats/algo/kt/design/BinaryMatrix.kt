package me.bytebeats.algo.kt.design

interface BinaryMatrix {
    fun get(x: Int, y: Int): Int
    fun dimensions(): List<Int>
}