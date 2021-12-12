package com.jaspervanmerle.aoc2021.day

class Day01 : Day("1752", "1781") {
    private val depths = input
        .lines()
        .map { it.toInt() }

    override fun solvePartOne(): Any {
        return depths
            .windowed(2)
            .count { it[1] > it[0] }
    }

    override fun solvePartTwo(): Any {
        return depths
            .windowed(3)
            .map { it.sum() }
            .windowed(2)
            .count { it[1] > it[0] }
    }
}
