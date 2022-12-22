package aoc

fun main() {
    println("Part one: ${Day22().partOne(InputFiles().inputTextForDay(22)).score()}")
    println("Part two: ${Day22().partTwo()}")
}

class Day22 {
    fun partOne(inputText: String): Board {
        val boardText = inputText.split("\n\n")[0]
        val instructions = parseInstructions(inputText.split("\n\n")[1])
        val board = BoardBuilder().buildBoard(boardText)
        board.followInstructions(instructions)
        println(board)
        return board
    }

    fun parseInstructions(instructionText: String): List<String> {
        return Regex("[LR]|[0-9]+").findAll(instructionText).map { matchResult -> matchResult.value }.toList()
    }
    fun partTwo(): Any {
        return ""
    }
    class BoardBuilder() {
        fun buildBoard(boardText: String): Board {
            val tiles = HashMap<Position, Tile>()
            boardText.split("\n").forEachIndexed { rowIndex, rowText ->
                run {
                    rowText.split("").drop(1).dropLast(1).forEachIndexed { colIndex, tile ->
                        run {
                            if (tile != " ") {
                                tiles[Position(rowIndex+1, colIndex+1)] = when(tile) {
                                    "#" -> Tile(false)
                                    else -> Tile(true)
                                }
                            }
                        }
                    }
                }
            }
            return Board(tiles)
        }

    }
    class Board(var tiles: Map<Position, Tile>) {
        private val moves: HashMap<Position, Direction> = HashMap()
        var position: Position = Position(1, 1)
        var direction: Direction = Direction.EAST

        init {
            while (!tileExistsAt(position)) {
                position.col++
                moves.put(position, Direction.EAST)
            }
            println("start position is ${position}")
        }

        fun move(amount: Int): Position {
            println("attempting to move $direction")
            var moved = 0
            while (moved++ < amount) {
                moveTo(position, direction)
                var newPosition = position.move(direction)
                if (!tileExistsAt(newPosition)) { // wrap by going backwards as much as possible
                    println("wrapping at $newPosition")
                    while (tileExistsAt(newPosition.move(direction.opposite()))) {
                        newPosition = newPosition.move(direction.opposite())
                    }
                    println("wrapped to $newPosition")
                }
                if (tileAt(newPosition).isPassable) {
                    println("moving forward")
                    moveTo(newPosition, direction)
                } else {
                    println("hit a wall")
                    break
                }
            }
            return position
        }

        private fun moveTo(newPosition: Position, direction: Direction) {
            moves.put(newPosition, direction)
            position = newPosition
        }

        private fun tileAt(position: Position): Tile {
            return this.tiles[position] ?: throw RuntimeException("Nothing at $position")
        }

        private fun tileExistsAt(position: Position): Boolean {
            return this.tiles.containsKey(position)
        }

        override fun toString(): String {
            val rows = tiles.keys.map { position -> position.row }.maxOrNull()!!
            val cols = tiles.keys.map { position -> position.col }.maxOrNull()!!
            for (row in 1..rows) {
                for (col in 1..cols) {
                    val currentPosition = Position(row, col)
                    if (tileExistsAt(currentPosition)) {
                        if (tileAt(currentPosition).isPassable) {
                            if (this.moves.contains(currentPosition)) {
                                print(this.moves.get(currentPosition)!!.arrow)
                            } else {
                                print(".")
                            }
                        } else {
                            print("#")
                        }
                    }
                    else {
                        print(" ")
                    }
                }
                println()
            }
            return "rows $rows cols $cols"
        }

        fun turn(instruction: String) {
            this.direction = this.direction.turn(instruction)
        }

        fun score() =
            this.position.row * 1000 + this.position.col * 4 + this.direction.ordinal

        internal fun followInstructions(instructions: List<String>) {
            instructions.forEach { instruction ->
                run {
                    if (instruction.matches(Regex("\\d+"))) {
                        println("attempting to travel $instruction ${this.direction}")
                        val amount = Integer.parseInt(instruction)
                        this.move(amount)
                    } else {
                        this.turn(instruction)
                    }
                }
            }
        }
    }
    data class Tile(val isPassable: Boolean) {
    }
    enum class Direction(val arrow: String) {
        EAST(">"), SOUTH("v"), WEST("<"), NORTH("^");
        fun turn(direction: String): Direction {
            var amount = 1
            if (direction.equals("L")) amount = -amount
            return shift(amount)
        }

        fun opposite(): Direction {
            return shift(2)
        }

        private fun shift(amount: Int): Direction {
            val ordinal = values().indexOf(this)
            return values()[(ordinal + amount + 4) % 4]
        }
    }
    data class Position(var row: Int, var col: Int) {
        override fun toString(): String {
            return "row $row col $col"
        }

        fun move(direction: Direction): Position {
            return when (direction) {
                Direction.EAST -> Position(row, col+1)
                Direction.SOUTH -> Position(row+1, col)
                Direction.WEST -> Position(row, col-1)
                Direction.NORTH -> Position(row-1, col)
            }
        }
    }
}
