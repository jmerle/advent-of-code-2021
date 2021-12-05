package com.jaspervanmerle.aoc2021.day

import kotlin.math.max
import kotlin.math.min

class Day05 : Day("4993", "21101") {
    private data class Point(val x: Int, val y: Int)

    private data class Line(val from: Point, val to: Point) {
        val coordinates = if (from.x == to.x) {
            val yFrom = min(from.y, to.y)
            val yTo = max(from.y, to.y)
            (yFrom..yTo).map { Point(from.x, it) }
        } else {
            val coords = mutableListOf<Point>()

            val slope = (to.y - from.y) / (to.x - from.x)
            val yIntercept = from.y - (slope * from.x)

            val xFrom = min(from.x, to.x)
            val xTo = max(from.x, to.x)

            (xFrom..xTo).map { Point(it, slope * it + yIntercept) }
        }
    }

    private val lines = getInput()
        .lines()
        .flatMap { it.split(" -> ") }
        .map {
            val parts = it.split(",")
            Point(parts[0].toInt(), parts[1].toInt())
        }
        .windowed(2, 2)
        .map { Line(it[0], it[1]) }

    override fun solvePartOne(): Any {
        return getNumberOfOverlappingCoordinates { it.from.x == it.to.x || it.from.y == it.to.y }
    }

    override fun solvePartTwo(): Any {
        return getNumberOfOverlappingCoordinates { true }
    }

    private fun getNumberOfOverlappingCoordinates(lineFilter: (line: Line) -> Boolean): Int {
        return lines
            .filter(lineFilter)
            .flatMap { it.coordinates }
            .groupBy { it }
            .count { it.value.size > 1 }
    }
}
