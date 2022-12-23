package aoc

fun main() {
    println("Part one: ${Day03().partOne()}")
    println("Part two: ${Day03().partTwo()}")
}

class Day03 {
    fun partOne(): Any {
        val inputText = InputFiles().inputTextForDay(3)
        val rucksacks = inputText.split("\n").map { line -> Pair(firstHalf(line), secondHalf(line)) }
        val misplacedItems = rucksacks.map { rucksack -> rucksack.first.intersect(rucksack.second).first()[0] }
        return misplacedItems.map { item -> toPriority(item) }.sum()
    }

    private fun toPriority(item: Char): Int {
        var priority = item.lowercaseChar().code - 'a'.code + 1
        if (item.isUpperCase()) priority += 26
        return priority
    }
    
    private fun firstHalf(line: String) = items(line, 0,line.length / 2)

    private fun secondHalf(line: String) = items(line, line.length / 2, line.length)

    private fun items(line: String, start: Int, end: Int): Set<String> {
        return line
            .substring(start until end)
            .chunked(1)
            .toSet()
    }

    fun partTwo(): Any {
        val inputText = InputFiles().inputTextForDay(3)
        val rucksacks = inputText.split("\n")
        if (rucksacks.size % 3 != 0) throw RuntimeException("Rucksacks are in groups of three")
        var totalPriority = 0
        for (group in 0 until rucksacks.size / 3) {
            totalPriority +=  toPriority(
                items(rucksacks, group, 0)
                    .intersect(items(rucksacks, group, 1))
                    .intersect(items(rucksacks, group, 2))
                    .first())
        }
        return totalPriority
    }

    private fun items(rucksacks: List<String>, group: Int, rucksack: Int): Set<Char> {
        return rucksacks[group * 3 + rucksack].chunked(1).map { item -> item[0] }.toSet()
    }
}
