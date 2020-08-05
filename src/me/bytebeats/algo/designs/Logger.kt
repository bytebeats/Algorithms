package me.bytebeats.algo.designs

class Logger() {

    /** Initialize your data structure here. */
    private val map = HashMap<String, Int>()

    /** Returns true if the message should be printed in the given timestamp, otherwise returns false.
    If this method returns false, the message will not be printed.
    The timestamp is in seconds granularity. */
    fun shouldPrintMessage(timestamp: Int, message: String) =
        if (message.isNotEmpty()) {
            if (map.containsKey(message)) {
                val diff = map[message]?.minus(timestamp) ?: 0
                if (diff <= -10) {
                    map[message] = timestamp
                    true
                } else {
                    false
                }
            } else {
                map[message] = timestamp
                true
            }
        } else {
            false
        }
}