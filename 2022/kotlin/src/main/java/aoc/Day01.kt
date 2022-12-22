package aoc

fun main() {
    println("Part one: ${Day01().partOne()}")
    println("Part two: ${Day01().partTwo()}")
}

class Day01 {
    fun partOne(): Any {
        return eachElvesCalories().maxOf { it }
    }
    
    fun partTwo(): Any {
        return eachElvesCalories().sortedDescending().slice(0..2).sum()
    }

    private fun eachElvesCalories(): List<Int> {
        val inputText = InputFiles().inputTextForDay(1)
        val elves: List<String> = inputText.split("\n\n")
        val eachElfsCalories = elves.map { elf ->
            val snacks = elf.split("\n")
            snacks.stream().mapToInt(Integer::parseInt).reduce(0, Integer::sum)
        }
        return eachElfsCalories
    }

}

