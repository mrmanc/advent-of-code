package aoc

import kotlin.collections.HashMap
import kotlin.collections.HashSet

fun main() {
    println("Part one: ${Day23().partOne()}")
    println("Part two: ${Day23().partTwo()}")
}

class Day23 {
    private val debug: Boolean = false
    private val directions =
        listOf(
            Day22.Direction.NORTH,
            Day22.Direction.SOUTH,
            Day22.Direction.WEST,
            Day22.Direction.EAST
        )
    private val positions = HashMap<Pair<Int, Int>, Elf>()

    fun partOne(): Any {
        val inputText = InputFiles().inputTextForDay(23)
//        val inputText = InputFiles().testInputTextForDay(23)
        val maxima = plotElves(inputText)
        val maxEasting = maxima.first
        val maxSouthing = maxima.second
        if (debug) println("Found ${this.positions.size} elves")
        if (debug) printElves(maxEasting, maxSouthing)
        var directionOffset = 0
        repeat(10) {
            val proposals = generateProposals(directionOffset)
            moveElves(proposals)
            if (debug) printElves(maxEasting, maxSouthing)
            directionOffset++ // rotate directions
        }
        val eastings = this.positions.keys.map { position -> position.first }.sorted()
        val southings = this.positions.keys.map { position -> position.second }.sorted()
        var emptyTiles = 0
        for (easting in eastings.first()..eastings.last()) {
            for (southing in southings.first()..southings.last()) {
                if (emptySpaceAt(Pair(easting, southing))) {
                    emptyTiles++
                }
            }
        }
        return emptyTiles
    }

    fun partTwo(): Int {
        val inputText = InputFiles().inputTextForDay(23)
//        val inputText = InputFiles().testInputTextForDay(23)
        val maxima = plotElves(inputText)
        val maxEasting = maxima.first
        val maxSouthing = maxima.second
        if (debug) printElves(maxEasting, maxSouthing)
        var directionOffset = 0
        var elvesMoved = true
        var roundIndex = 0
        while (elvesMoved) {
            roundIndex++
            val proposals = generateProposals(directionOffset++)
            val elvesWhoMoved = moveElves(proposals)
            elvesMoved = elvesWhoMoved > 0
            if (debug) println("$elvesWhoMoved elves moved")
            if (debug) printElves(maxEasting, maxSouthing)
            directionOffset++ // rotate directions
        }
        return roundIndex
    }

    private fun generateProposals(
        directionOffset: Int
    ): HashMap<Pair<Int, Int>, HashSet<Elf>> {
        if (debug) println("preference this round is ${directions[directionOffset % 4]}")
        val proposals = HashMap<Pair<Int,Int>, HashSet<Elf>>()
        this.positions.forEach { (position, elf) ->
            run {
                val surroundings = calculateSurroundings(elf)
                if (emptySpacesAt(surroundings)) {
                    if (debug) println("no need for $elf to move")
                    proposals.putIfAbsent(position, HashSet())
                    proposals[position]?.add(elf)
                } else {
                    for (directionIndex in 0 until 4) {
                        val direction = directions[(directionIndex + directionOffset) % 4]
                        val proposal = calculateSpace(direction, elf.position())
                        val spacesToCheck = HashSet<Pair<Int, Int>>()
                        spacesToCheck.add(proposal)
                        if (direction == Day22.Direction.NORTH || direction == Day22.Direction.SOUTH) {
                            spacesToCheck.add(calculateSpace(Day22.Direction.WEST, proposal))
                            spacesToCheck.add(calculateSpace(Day22.Direction.EAST, proposal))
                        } else {
                            spacesToCheck.add(calculateSpace(Day22.Direction.NORTH, proposal))
                            spacesToCheck.add(calculateSpace(Day22.Direction.SOUTH, proposal))
                        }
                        if (debug) println("Direction $direction ($elf), proposal $proposal, checking $spacesToCheck")
                        if (emptySpacesAt(spacesToCheck)) {
                            proposals.putIfAbsent(proposal, HashSet())
                            proposals[proposal]?.add(elf)
                            break
                        }
                    }
                }
            }
        }
        return proposals
    }

    private fun plotElves(
        inputText: String
    ): Pair<Int, Int> {
        var maxEasting = 0
        var maxSouthing = 0
        val lines = inputText.split("\n").toList()
        lines.forEachIndexed { southing, line ->
            line.forEachIndexed { easting, position ->
                if (position == '#') {
                    this.positions[Pair(easting, southing)] = Elf(easting, southing)
                }
                if (easting > maxEasting) maxEasting = easting
                if (southing > maxSouthing) maxSouthing = southing
            }
        }
        return Pair(maxEasting, maxSouthing)
    }

    private fun calculateSurroundings(elf: Elf): Set<Pair<Int, Int>> {
        val north = calculateSpace(Day22.Direction.NORTH, elf.position())
        val south = calculateSpace(Day22.Direction.SOUTH, elf.position())
        return setOf(
            calculateSpace(Day22.Direction.WEST, north), north, calculateSpace(Day22.Direction.EAST, north),
            calculateSpace(Day22.Direction.WEST, elf.position()), calculateSpace(Day22.Direction.EAST, elf.position()),
            calculateSpace(Day22.Direction.WEST, south), south, calculateSpace(Day22.Direction.EAST, south),
        )
    }

    private fun printElves(maxEasting: Int, maxSouthing: Int) {
        println("")
        for (southing in 0..maxSouthing) {
            for (easting in 0..maxEasting) {
                if (this.positions.containsKey(Pair(easting, southing))) {
                    print("#")
                } else {
                    print(".")
                }
            }
            print("\n")
        }
    }

    private fun emptySpaceAt(position: Pair<Int, Int>): Boolean {
        return !this.positions.containsKey(position)
    }

    fun emptySpacesAt(positionsToCheck: Set<Pair<Int, Int>>): Boolean {
        return positionsToCheck.map { position -> emptySpaceAt(position) }.all { c -> c }
    }

    private fun calculateSpace(direction: Day22.Direction, start: Pair<Int, Int>): Pair<Int, Int> {
        return when (direction) {
            Day22.Direction.NORTH -> Pair(start.first, start.second - 1)
            Day22.Direction.EAST -> Pair(start.first + 1, start.second)
            Day22.Direction.SOUTH -> Pair(start.first, start.second + 1)
            Day22.Direction.WEST -> Pair(start.first - 1, start.second)
        }
    }

    private fun moveElves(
        proposals: HashMap<Pair<Int, Int>, HashSet<Elf>>
    ): Int {
        var elvesWhoMoved = 0
        proposals.forEach { (proposal, elves) ->
            run {
                if (debug) println("pos $proposal elves $elves")
                if (elves.size == 1) {
                    val elf = elves.first()
                    if (elf.position() != proposal) {
    //                            println("pos $proposal elves $elves")
                        elvesWhoMoved++
                        this.positions.remove(elf.position())
                        elf.easting = proposal.first
                        elf.southing = proposal.second
                        this.positions[elf.position()] = elf
                    }
                } else {
                    if (debug) println("collision at $proposal")
                }
            }
        }
        return elvesWhoMoved
    }
}

data class Elf(var easting: Int, var southing: Int) {
    fun position(): Pair<Int, Int> {
        return Pair(this.easting, this.southing)
    }
}
