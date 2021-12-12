package com.jaspervanmerle.aoc2021.day

import kotlin.math.abs

class Day07 : Day("356179", "99788435") {
    private val positions = input
        .split(",")
        .map { it.toInt() }

    override fun solvePartOne(): Any {
        return getMinimalFuelToAlign { it }
    }

    override fun solvePartTwo(): Any {
        return getMinimalFuelToAlign { (it * (it + 1)) / 2 }
    }

    private fun getMinimalFuelToAlign(distanceToFuel: (distance: Int) -> Int): Int {
        return (positions.minOf { it }..positions.maxOf { it }).minOf { neutral ->
            positions.sumOf { distanceToFuel(abs(neutral - it)) }
        }
    }
}
