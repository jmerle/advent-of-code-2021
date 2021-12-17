package com.jaspervanmerle.aoc2021.day

import kotlin.math.max

class Day17 : Day("23005", "2040") {
    private data class TargetArea(val horizontalRange: IntRange, val verticalRange: IntRange) {
        fun contains(x: Int, y: Int): Boolean {
            return x in horizontalRange && y in verticalRange
        }

        fun isOutOfReach(x: Int, y: Int): Boolean {
            return x > horizontalRange.last || y < verticalRange.first
        }
    }

    private val targetArea = parseTargetArea()

    override fun solvePartOne(): Any {
        return (0..1000)
            .flatMap { velX ->
                (0..1000).map { velY ->
                    simulate(velX, velY)
                }
            }
            .filterNotNull()
            .maxOf { it }
    }

    override fun solvePartTwo(): Any {
        return (0..1000)
            .flatMap { velX ->
                (-1000..1000).map { velY ->
                    simulate(velX, velY)
                }
            }
            .count { it != null }
    }

    private fun parseTargetArea(): TargetArea {
        val inputNumbers = Regex("(-?\\d+)")
            .findAll(input)
            .map { it.groupValues[0].toInt() }
            .toList()

        return TargetArea(inputNumbers[0]..inputNumbers[1], inputNumbers[2]..inputNumbers[3])
    }

    private fun simulate(initialVelocityX: Int, initialVelocityY: Int): Int? {
        var x = 0
        var y = 0
        var velX = initialVelocityX
        var velY = initialVelocityY

        var maxY = 0

        while (!targetArea.isOutOfReach(x + velX, y + velY)) {
            x += velX
            y += velY

            maxY = max(maxY, y)

            if (velX < 0) {
                velX++
            } else if (velX > 0) {
                velX--
            }

            velY--
        }

        return if (targetArea.contains(x, y)) maxY else null
    }
}
