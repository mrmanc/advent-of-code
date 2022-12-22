package aoc

fun main() {
    println("Part one: ${Day02().partOne()}")
    println("Part two: ${Day02().partTwo()}")
}

class Day02 {
    fun partOne(): Any {
        val inputText = InputFiles().inputTextForDay(2).replace(Regex("[AX]"), "rock").replace(Regex("[BY]"), "paper")
            .replace(Regex("[CZ]"), "scissors")
        val moves = mapOf("rock" to 1, "paper" to 2, "scissors" to 3)
        var roundScores: ArrayList<Int> = ArrayList()
        for ((p1, p2) in inputText.split("\n").map { round -> round.split(" ") }) {
            var roundScore = moves.get(p2) ?: 0;
            if (p1 == p2) roundScore += 3
            if (p2.equals("rock") && p1.equals("scissors") || p2.equals("paper") && p1.equals("rock") || p2.equals("scissors") && p1.equals(
                    "paper"
                )
            ) roundScore += 6
            roundScores.add(roundScore)
        }
        return roundScores.sum()
    }

    fun partTwo(): Any {
        
        val p1Moves: Map<String,String> = mapOf("A" to "rock", "B" to "paper", "C" to "scissors")
        val objectives: Map<String,String> = mapOf("X" to "make lose", "Y" to "make draw", "Z" to "make win")
        val rules = mapOf(
            "rock"     to mapOf("make lose" to "scissors", "make win" to "paper"   , "make draw" to "rock"),
            "paper"    to mapOf("make lose" to "rock"    , "make win" to "scissors", "make draw" to "paper"),
            "scissors" to mapOf("make lose" to "paper"   , "make win" to "rock"    , "make draw" to "scissors"),
        )
        val roundScores: ArrayList<Int> = ArrayList()
        val joinToString = InputFiles().inputTextForDay(2)
        val rounds = joinToString.split("\n").map { round -> round.split(" ") }
        for ((c1, c2) in rounds) {
            val objective = objectives[c2] ?: throw RuntimeException("Objective not found $c2")
            val p1Move = p1Moves[c1] ?: throw RuntimeException("Move not found for $c1")
            val p1Objectives = rules[p1Move] ?: throw RuntimeException("Rule not found for $p1Move")
            val p2Move = p1Objectives.get(objective)
            roundScores.add(score(p2Move) + score(objective))
        }
        return roundScores.sum()
    }

    private fun score(p2Move: String?): Int {
        return mapOf(
            "rock" to 1, "paper" to 2, "scissors" to 3, "make lose" to 0, "make draw" to 3, "make win" to 6)[p2Move] ?: throw RuntimeException("No score for $p2Move")
    }

}
