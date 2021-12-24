package com.jaspervanmerle.aoc2021.day

import kotlin.math.max
import kotlin.math.min

class Day22 : Day("655005", "1125649856443608") {
    private data class Cuboid(val xRange: IntRange, val yRange: IntRange, val zRange: IntRange) {
        val volume = xRange.size() * yRange.size() * zRange.size()

        fun overlapsWith(other: Cuboid): Boolean {
            return xRange.first <= other.xRange.last && xRange.last >= other.xRange.first
                    && yRange.first <= other.yRange.last && yRange.last >= other.yRange.first
                    && zRange.first <= other.zRange.last && zRange.last >= other.zRange.first
        }

        fun splitAround(other: Cuboid): List<Cuboid> {
            val belowX = xRange.first until other.xRange.first
            val middleX = max(xRange.first, other.xRange.first)..min(xRange.last, other.xRange.last)
            val aboveX = other.xRange.last + 1..xRange.last

            val belowY = yRange.first until other.yRange.first
            val middleY = max(yRange.first, other.yRange.first)..min(yRange.last, other.yRange.last)
            val aboveY = other.yRange.last + 1..yRange.last

            val belowZ = zRange.first until other.zRange.first
            val middleZ = max(zRange.first, other.zRange.first)..min(zRange.last, other.zRange.last)
            val aboveZ = other.zRange.last + 1..zRange.last

            val cuboids = mutableListOf<Cuboid>()

            for (x in listOf(belowX, middleX, aboveX)) {
                for (y in listOf(belowY, middleY, aboveY)) {
                    for (z in listOf(belowZ, middleZ, aboveZ)) {
                        val cuboid = Cuboid(x, y, z)
                        if (cuboid.volume > 0 && !cuboid.overlapsWith(other)) {
                            cuboids.add(cuboid)
                        }
                    }
                }
            }

            return cuboids
        }

        private fun IntRange.size(): Long {
            return max(0L, last.toLong() - first.toLong() + 1L)
        }
    }

    private data class RebootStep(val on: Boolean, val xRange: IntRange, val yRange: IntRange, val zRange: IntRange)

    private val rebootSteps = input.lines().map {
        val on = it.startsWith("on")

        val rangeMatches = "(-?\\d+)\\.\\.(-?\\d+)".toRegex().findAll(it).toList()
        val xRange = rangeMatches[0].groups[1]!!.value.toInt()..rangeMatches[0].groups[2]!!.value.toInt()
        val yRange = rangeMatches[1].groups[1]!!.value.toInt()..rangeMatches[1].groups[2]!!.value.toInt()
        val zRange = rangeMatches[2].groups[1]!!.value.toInt()..rangeMatches[2].groups[2]!!.value.toInt()

        RebootStep(on, xRange, yRange, zRange)
    }

    override fun solvePartOne(): Any {
        return countOnCubes { max(it.first, -50)..min(it.last, 50) }
    }

    override fun solvePartTwo(): Any {
        return countOnCubes { it }
    }

    private fun countOnCubes(rangeLimiter: (IntRange) -> IntRange): Long {
        var onCuboids = emptyList<Cuboid>()

        for (step in rebootSteps) {
            val newOnCuboids = mutableListOf<Cuboid>()
            val stepCuboid = Cuboid(rangeLimiter(step.xRange), rangeLimiter(step.yRange), rangeLimiter(step.zRange))

            for (cuboid in onCuboids) {
                if (cuboid.overlapsWith(stepCuboid)) {
                    newOnCuboids.addAll(cuboid.splitAround(stepCuboid))
                } else {
                    newOnCuboids.add(cuboid)
                }
            }

            if (step.on) {
                newOnCuboids.add(stepCuboid)
            }

            onCuboids = newOnCuboids
        }

        return onCuboids.sumOf { it.volume }
    }
}
