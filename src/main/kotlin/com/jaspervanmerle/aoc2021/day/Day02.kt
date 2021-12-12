package com.jaspervanmerle.aoc2021.day

class Day02 : Day("1804520", "1971095320") {
    private val commands = input
        .lines()
        .map { it.split(" ") }
        .map { it[0] to it[1].toInt() }

    override fun solvePartOne(): Any {
        var position = 0
        var depth = 0

        for ((type, x) in commands) {
            when (type) {
                "forward" -> position += x
                "down" -> depth += x
                "up" -> depth -= x
            }
        }

        return position * depth
    }

    override fun solvePartTwo(): Any {
        var aim = 0
        var position = 0
        var depth = 0

        for ((type, x) in commands) {
            when (type) {
                "down" -> aim += x
                "up" -> aim -= x
                "forward" -> {
                    position += x
                    depth += aim * x
                }
            }
        }

        return position * depth
    }
}
