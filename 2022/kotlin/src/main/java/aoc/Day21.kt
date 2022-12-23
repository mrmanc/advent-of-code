package aoc

import kotlin.collections.HashMap

fun main() {
    println("\nPart one: ${Day21().partOne()}") // not 1425736276
    println("Part two: ${Day21().partTwo()}")
}

class Day21 {
    fun partOne(): Any {
        val inputText = InputFiles().inputTextForDay(21)
//        val inputText = InputFiles().testInputTextForDay(21)
        val tokens = HashMap<String, String>()
        inputText.split("\n").forEach { line ->
            run {
                tokens.put(line.split(":")[0], line.split(":")[1].trim())
            }
        }
        val result = recursiveReplace(tokens, "root")
        return result
    }


    private fun recursiveReplace(tokens: HashMap<String, String>, current: String): Long {
        val value = tokens[current]!!
        if (value.matches(Regex("[0-9]+"))) {
            print(value)
            return value.toLong()
        } else {
            val left = value.split(" ")[0]
            val operator = value.split(" ")[1]
            val right = value.split(" ")[2]
            print("(")
            val leftVal = recursiveReplace(tokens, left)
            print(operator)
            val rightVal = recursiveReplace(tokens, right)
            print(")")
            return when (operator) {
                "+" -> leftVal + rightVal
                "/" -> leftVal / rightVal
                "-" -> leftVal - rightVal
                "*" -> leftVal * rightVal
                else -> throw RuntimeException()
            }
        }
    }


    fun partTwo(): Any {
        return ""
    }
}
