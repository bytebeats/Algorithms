package me.bytebeats.algorithms.kt.design

class AnimalShelf() {

    private val animals = mutableListOf<IntArray>()

    fun enqueue(animal: IntArray) {
        animals.add(animal)
    }

    fun dequeueAny(): IntArray {
        if (animals.isNotEmpty()) {
            return animals.removeAt(0)
        }
        return intArrayOf(-1, -1)
    }

    fun dequeueDog(): IntArray {
        for (i in animals.indices) {
            if (animals[i][1] == 1) {
                return animals.removeAt(i)
            }
        }
        return intArrayOf(-1, -1)
    }

    fun dequeueCat(): IntArray {
        for (i in animals.indices) {
            if (animals[i][1] == 0) {
                return animals.removeAt(i)
            }
        }
        return intArrayOf(-1, -1)
    }

}