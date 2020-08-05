package me.bytebeats.algo.kt.design

class PeekingIterator(iterator: Iterator<Int>) : Iterator<Int> { //284
    val list = mutableListOf<Int>()

    init {
        while (iterator.hasNext()) {
            list.add(iterator.next())
        }
    }

    fun peek(): Int = list.first()

    override fun next(): Int = list.removeAt(0)

    override fun hasNext(): Boolean = list.isNotEmpty()
}