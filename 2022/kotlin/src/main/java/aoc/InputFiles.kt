package aoc

import java.io.File
import java.net.URL

class InputFiles {
    internal fun inputTextForDay(day: Int): String {
        val resource: URL = javaClass.classLoader.getResource("$day.txt") ?: throw RuntimeException("Input not found,.")
        val inputText = File(resource.toURI()).readText()
        return inputText
    }
    internal fun testInputTextForDay(day: Int): String {
        val resource: URL = javaClass.classLoader.getResource("$day-testdata.txt") ?: throw RuntimeException("Input not found,.")
        val inputText = File(resource.toURI()).readText()
        return inputText
    }
    
}