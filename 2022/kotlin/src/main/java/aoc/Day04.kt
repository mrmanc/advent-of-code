package aoc

fun main() {
    println("Part one: ${Day04().partOne()}")
//    println("Part two: ${Day04().partTwo()}")
}

class Day04 {
    fun partOne(): Any {
        val inputText = InputFiles().inputTextForDay(4)
        var enclosedPairs = 0
        inputText.split("\n").forEach { line -> run {
            val firstInts = findInts(line.split(",")[0])
            val secondInts = findInts(line.split(",")[1])
            if (firstInts.containsAll(secondInts) || secondInts.containsAll(firstInts)) {
                enclosedPairs++
            }
        } }
        return enclosedPairs
    }

    private fun findInts(rangeText: String): List<Int> {
        val first =
            Integer.parseInt(rangeText.split("-")[0])..Integer.parseInt(rangeText.split("-")[1])
        val firstInts = first.toList()
        return firstInts
    }
}
