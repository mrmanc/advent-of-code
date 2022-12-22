package aoc

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day22Test() {
    @Test
    internal fun howSplitWorks() {
        assertThat(" , , ".split(",").get(0)).isEqualTo(" ")
        assertThat("   ".split("").drop(1).dropLast(1).get(0)).isEqualTo(" ")
    }

    @Test
    internal fun parseInstructions() {
        assertThat(Day22().parseInstructions("10R5L")).containsExactly("10","R","5","L")
    }

    @Test
    internal fun directions() {
        assertThat(Day22.Direction.EAST.turn("L")).isEqualTo(Day22.Direction.NORTH)
        assertThat(Day22.Direction.EAST.turn("R")).isEqualTo(Day22.Direction.SOUTH)
        assertThat(Day22.Direction.NORTH.turn("L")).isEqualTo(Day22.Direction.WEST)
        assertThat(Day22.Direction.NORTH.turn("R")).isEqualTo(Day22.Direction.EAST)
        assertThat(Day22.Direction.NORTH.opposite()).isEqualTo(Day22.Direction.SOUTH)
        assertThat(Day22.Direction.WEST.opposite()).isEqualTo(Day22.Direction.EAST)
    }

    @Test
    internal fun dealWithSpaces() {
        val board = Day22.BoardBuilder().buildBoard("  ..\n  ..")
        assertThat(board.tiles).hasSize(4)
    }

    @Test
    internal fun findStart() {
        val board = Day22.BoardBuilder().buildBoard("  ..\n  ..")
        assertThat(board.position).isEqualTo(Day22.Position(1, 3))
    }
    @Test
    internal fun moveOpen() {
        val board = Day22.BoardBuilder().buildBoard("..\n..")
        assertThat(board.position).isEqualTo(Day22.Position(1,1))
        assertThat(board.tiles.size).isEqualTo(4)
        board.move(1)
        assertThat(board.position).isEqualTo(Day22.Position(1,2))
        board.move(1)
        assertThat(board.position).isEqualTo(Day22.Position(1,1))
        board.move(1)
        assertThat(board.position).isEqualTo(Day22.Position(1,2))
        board.turn("R")
        board.move(1)
        assertThat(board.position).isEqualTo(Day22.Position(2,2))
        board.move(3)
        assertThat(board.position).isEqualTo(Day22.Position(1,2))
    }

    @Test
    internal fun positionMove() {
        assertThat(Day22.Position(0,0).move(Day22.Direction.EAST)).isEqualTo(Day22.Position(0,1))
        assertThat(Day22.Position(0,1).move(Day22.Direction.EAST)).isEqualTo(Day22.Position(0,2))
        assertThat(Day22.Position(0,0).move(Day22.Direction.SOUTH)).isEqualTo(Day22.Position(1,0))
    }

    @Test
    internal fun moveWall() {
        val board = Day22.BoardBuilder().buildBoard(".\n#")
        assertThat(board.position).isEqualTo(Day22.Position(1,1))
        board.move(1)
        assertThat(board.position).isEqualTo(Day22.Position(1,1))
        board.move(1)
        assertThat(board.position).isEqualTo(Day22.Position(1,1))
    }

    @Test
    internal fun testData() {
        val board = Day22().partOne(InputFiles().testInputTextForDay(22))
        assertThat(board.score()).isEqualTo(6032)
    }
}

