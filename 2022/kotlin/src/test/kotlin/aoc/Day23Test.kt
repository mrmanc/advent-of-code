package aoc

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Day23Test() {

    @Test
    internal fun emptySpace() {
        assertThat(Day23().emptySpacesAt(setOf(Pair(1, 1))))
            .isFalse
    }
}