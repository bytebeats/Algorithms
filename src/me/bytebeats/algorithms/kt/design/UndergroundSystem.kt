package me.bytebeats.algorithms.kt.design

class UndergroundSystem() {//1396
    private val trips = mutableListOf<Trip>()

    fun checkIn(id: Int, stationName: String, t: Int) {
        trips.add(Trip(id, stationName, t))
    }

    fun checkOut(id: Int, stationName: String, t: Int) {
        val trip = trips.first { it.id == id && it.outTime == 0 && it.inTime > 0 }
        trip?.outStation = stationName
        trip?.outTime = t
    }

    fun getAverageTime(startStation: String, endStation: String): Double {
        return trips.filter { it.inStation == startStation && it.outStation == endStation }
            .map { it.outTime - it.inTime }.average()
    }

    class Trip(val id: Int, val inStation: String, val inTime: Int) {
        var outStation: String = ""
        var outTime: Int = 0
    }

}