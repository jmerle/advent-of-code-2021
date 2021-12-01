package com.jaspervanmerle.aoc2021.day

abstract class Day(private val answerPartOne: String? = null, private val answerPartTwo: String? = null) {
    val number = javaClass.simpleName.replace("Day", "").toInt()

    protected abstract fun solvePartOne(): Any
    protected abstract fun solvePartTwo(): Any

    fun solve(part: Int): String = when (part) {
        1 -> solvePartOne().toString()
        2 -> solvePartTwo().toString()
        else -> throw Error("Part $part does not exist")
    }

    fun getAnswer(part: Int): String? = when (part) {
        1 -> answerPartOne
        2 -> answerPartTwo
        else -> throw Error("Part $part does not exist")
    }

    protected fun getInput(): String {
        return javaClass
            .getResource("/input-${number.toString().padStart(2, '0')}.txt")
            .readText()
            .trim()
    }
}
