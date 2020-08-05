package me.bytebeats.algo.kt.design

class StockSpanner() {//901
    val prices = mutableListOf<Int>()
    val weights = mutableListOf<Int>()

    fun next(price: Int): Int {
        var weight = 1
        while (prices.isNotEmpty() && prices.last() <= price) {
            prices.removeAt(prices.lastIndex)
            weight += weights.removeAt(weights.lastIndex)
        }
        prices.add(price)
        weights.add(weight)
        return weight
    }

}