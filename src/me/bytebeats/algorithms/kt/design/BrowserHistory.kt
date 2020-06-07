package me.bytebeats.algorithms.kt.design

class BrowserHistory(homepage: String) {//5430
    val history = mutableListOf<String>()
    var curIndex = 0

    init {
        history.add(homepage)
        curIndex = 0
    }

    fun visit(url: String) {
        if (curIndex < history.lastIndex) {
            var delete = history.lastIndex - curIndex
            while (delete-- > 0) {
                history.removeAt(history.lastIndex)
            }
        }
        history.add(url)
        curIndex++
    }

    fun back(steps: Int): String {
        var back = if (steps > curIndex) curIndex else steps
        while (back-- > 0) {
            curIndex--
        }
        return history[curIndex]
    }

    fun forward(steps: Int): String {
        var forward = if (steps + curIndex > history.lastIndex) history.lastIndex - curIndex else steps
        while (forward-- > 0) {
            curIndex++
        }
        return history[curIndex]
    }

}