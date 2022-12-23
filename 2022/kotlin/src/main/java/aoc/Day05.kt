package aoc

import java.util.*

fun main() {
    println("Part one: ${Day05().partOne()}")
    println("Part two: ${Day05().partTwo()}")
}

class Day05 {
    fun partOne(): Any {
        val inputText = InputFiles().inputTextForDay(5)
        val stackText = inputText.split("\n\n")[0]
        val numberOfStacks = stackText.split("\n").last().split(Regex(" +")).drop(1).dropLast(1).size
        val stacks = (0 until numberOfStacks).map { index -> readStack(stackText, index) }.toList()
        val procedure = inputText.split("\n\n")[1].split("\n")
        procedure.forEach { instruction ->
            run {
                println(instruction)
                val parts = instruction.split(" ")
                val toStack = stacks[parts[5].toInt() - 1]
                val fromStack = stacks[parts[3].toInt() - 1]
                repeat(parts[1].toInt()) {
                    toStack.push(fromStack.pop())
//                stacks.forEachIndexed { index, stack -> println("${index+1} $stack") }
                }
            }
        }
        return stacks.map { stack -> run { stack.peek() } }.toList().joinToString("")
    }

    private fun readStack(stackText: String, stackIndex: Int): Stack<String> {
        val stack = Stack<String>()
        stackText.split("\n").dropLast(1).map { line ->
            run {
                line.substring(1 + (4 * stackIndex) until 2 + (4 * stackIndex))
            }
        }.filterNot { entry -> entry.equals(" ") }.reversed().forEach { crate -> stack.push(crate) }
        return stack
    }

    fun partTwo(): Any {
        val inputText = InputFiles().inputTextForDay(5)
        val stackText = inputText.split("\n\n")[0]
        val numberOfStacks = stackText.split("\n").last().split(Regex(" +")).drop(1).dropLast(1).size
        val stacks = (0 until numberOfStacks).map { index -> readStack(stackText, index) }.toList()
        val procedure = inputText.split("\n\n")[1].split("\n")
        procedure.forEach { instruction ->
            run {
                println(instruction)
                val parts = instruction.split(" ")
                val toStack = stacks[parts[5].toInt() - 1]
                val fromStack = stacks[parts[3].toInt() - 1]
                val tmpStack = Stack<String>()
                repeat(parts[1].toInt()) {
                    tmpStack.push(fromStack.pop())
                }
                repeat(parts[1].toInt()) {
                    toStack.push(tmpStack.pop())
                    stacks.forEachIndexed { index, stack -> println("${index+1} $stack") }
                }
            }
        }
        return stacks.map { stack -> run { stack.peek() } }.toList().joinToString("")
    }
}
