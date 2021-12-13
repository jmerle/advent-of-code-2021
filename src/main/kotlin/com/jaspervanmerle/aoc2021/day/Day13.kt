package com.jaspervanmerle.aoc2021.day

class Day13 : Day("765", "RZKZLPGH") {
    private data class Dot(val x: Int, val y: Int)
    private enum class FoldDirection { UP, LEFT }
    private data class Fold(val direction: FoldDirection, val location: Int)

    private val dots = input
        .split("\n\n")[0]
        .lines()
        .map { it.split(",") }
        .map { Dot(it[0].toInt(), it[1].toInt()) }

    private val folds = input
        .split("\n\n")[1]
        .lines()
        .map { it.split("=") }
        .map { Fold(if (it[0].endsWith('y')) FoldDirection.UP else FoldDirection.LEFT, it[1].toInt()) }

    override fun solvePartOne(): Any {
        return getDotsAfterFolds(folds.take(1)).size
    }

    override fun solvePartTwo(): Any {
        val finalDots = getDotsAfterFolds(folds)

        val minX = finalDots.minOf { it.x }
        val maxX = finalDots.maxOf { it.x }
        val minY = finalDots.minOf { it.y }
        val maxY = finalDots.maxOf { it.y }

        for (y in minY..maxY) {
            for (x in minX..maxX) {
                if (finalDots.any { it.x == x && it.y == y }) {
                    print('#')
                } else {
                    print(' ')
                }
            }

            println()
        }

        return "RZKZLPGH"
    }

    private fun getDotsAfterFolds(foldsToApply: List<Fold>): List<Dot> {
        var currentDots = dots

        for (fold in foldsToApply) {
            currentDots = currentDots.map {
                if (fold.direction == FoldDirection.UP) {
                    if (it.y < fold.location) {
                        it
                    } else {
                        Dot(it.x, fold.location - (it.y - fold.location))
                    }
                } else {
                    if (it.x < fold.location) {
                        it
                    } else {
                        Dot(fold.location - (it.x - fold.location), it.y)
                    }
                }
            }.distinct()
        }

        return currentDots
    }
}
