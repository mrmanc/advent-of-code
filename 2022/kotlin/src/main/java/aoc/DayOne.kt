package aoc

import java.io.File
import java.net.URL

fun main(args: Array<String>) {
    println(DayOne().partOne())
}

class DayOne {
    fun partOne(): Any {
        val resource: URL = javaClass.classLoader.getResource("1.txt") ?: throw RuntimeException("Input not found,.")
        val elves: List<String> = File(resource.toURI()).readText().split("\n\n")
        val eachElfsCalories = elves.map { elf ->
            val snacks = elf.split("\n")
            snacks.stream().mapToInt(Integer::parseInt).reduce(0, Integer::sum);
        }
        return eachElfsCalories.maxOf { it }
    }
}
