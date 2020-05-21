package me.bytebeats.algorithms.kt.design

class FileSystem() {
    //588
    private val root = File()

    fun ls(path: String): List<String> {
        val fileNames = mutableListOf<String>()
        var file = root
        if (path == "/") {
            fileNames.addAll(file.files.map { it.name })
        } else {
            val dirs = path.split("/")
            dirs.forEach { dir ->
                if (dir.isEmpty()) {
                    file = root
                } else {
                    file = file.files.first { it.name == dir }
                }
            }
            if (file.content.isNotEmpty()) {
                fileNames.add(file.name)
            }
            fileNames.addAll(file.files.map { it.name })
        }
        return fileNames.sorted()
    }

    fun mkdir(path: String) {
        if (path == "/") {
            return
        }
        val dirs = path.split("/")
        var file = root
        dirs.forEach { dir ->
            if (dir.isEmpty()) {
                file = root
            } else {
                if (file.files.map { it.name }.contains(dir)) {
                    file = file.files.first { it.name == dir }
                } else {
                    val newDir = File()
                    newDir.name = dir
                    file.files.add(newDir)
                    file = newDir
                }
            }
        }
    }

    fun addContentToFile(filePath: String, content: String) {
        findFile(filePath)?.apply {
            this.content = "${this.content}$content"
        }
    }

    fun readContentFromFile(filePath: String): String {
        return findFile(filePath)?.content ?: ""
    }

    private fun findFile(filePath: String): File? {
        if (filePath == "/") {
            return root
        }
        val dirs = filePath.split("/")
        var file = root
        dirs.forEach { dir ->
            if (dir.isEmpty()) {
                file = root
            } else {
                if (file.files.map { it.name }.contains(dir)) {
                    file = file.files.first { it.name == dir }
                } else {
                    val newDir = File()
                    newDir.name = dir
                    file.files.add(newDir)
                    file = newDir
                }
            }
        }
        return file
    }

    class File {
        var name = ""
        var content = ""
        var files = mutableListOf<File>()
    }

}

class FileSystem1() { //1166
    val root = Trie("", -1)

    fun createPath(path: String, value: Int): Boolean {
        val paths = path.substring(1).split("/")
        var trie = root
        if (paths.size == 1) {
            if (trie.map.containsKey(paths[0])) {
                return false
            } else {
                trie.map[paths[0]] = Trie(paths[0], value)
                return true
            }
        } else {
            for (i in 0 until paths.lastIndex) {
                if (trie.map.containsKey(paths[i])) {
                    trie = trie.map[paths[i]]!!
                } else {
                    return false
                }
            }
            if (trie.map.containsKey(paths.last())) {
                return false
            } else {
                trie.map[paths.last()] = Trie(paths.last(), value)
                return true
            }
        }
    }

    fun get(path: String): Int {
        var trie = root
        val paths = path.substring(1).split("/")
        for (p in paths) {
            if (trie.map.containsKey(p)) {
                trie = trie.map[p]!!
            } else {
                return -1
            }
        }
        return trie.value
    }

    class Trie(val path: String, var value: Int) {
        val map = mutableMapOf<String, Trie>()
    }

}