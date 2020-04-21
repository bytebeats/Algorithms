package me.bytebeats.algorithms.kt.design

interface BinaryMatrix {
    fun get(x: Int, y: Int): Int
    fun dimensions(): List<Int>
}