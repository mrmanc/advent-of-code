package aoc

import java.io.File
import java.net.URL

fun main() {
    println("Part one: ${DayOne().partOne()}")
    println("Part two: ${DayOne().partTwo()}")
}

class DayOne {
    fun partOne(): Any {
        return eachElvesCalories().maxOf { it }
    }
    
    fun partTwo(): Any {
        return eachElvesCalories().sortedDescending().slice(0..2).sum()
    }

    private fun eachElvesCalories(): List<Int> {
        val resource: URL = javaClass.classLoader.getResource("1.txt") ?: throw RuntimeException("Input not found,.")
        val elves: List<String> = File(resource.toURI()).readText().split("\n\n")
        val eachElfsCalories = elves.map { elf ->
            val snacks = elf.split("\n")
            snacks.stream().mapToInt(Integer::parseInt).reduce(0, Integer::sum)
        }
        return eachElfsCalories
    }
}
