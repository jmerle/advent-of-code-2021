package com.jaspervanmerle.aoc2021.day

class Day11 : Day("1719", "232") {
    private data class Point(val x: Int, val y: Int)

    private val width = input.lines()[0].length
    private val height = input.lines().size

    private val directions = listOf(
        -1 to 0,
        1 to 0,
        0 to -1,
        0 to 1,
        -1 to -1,
        -1 to 1,
        1 to -1,
        1 to 1
    )

    override fun solvePartOne(): Any {
        return simulateFlashes().take(100).sum()
    }

    override fun solvePartTwo(): Any {
        return simulateFlashes().withIndex().first { it.value == width * height }.index + 1
    }

    private fun simulateFlashes(): Sequence<Int> {
        return sequence {
            val energyLevels = input
                .lines()
                .map { line -> line.toCharArray().map { it.digitToInt() }.toMutableList() }

            while (true) {
                var flashes = 0
                val queue = ArrayDeque<Point>()

                for (y in 0 until height) {
                    for (x in 0 until width) {
                        energyLevels[y][x]++

                        if (energyLevels[y][x] > 9) {
                            energyLevels[y][x] = 0
                            flashes++
                            queue.add(Point(x, y))
                        }
                    }
                }

                while (!queue.isEmpty()) {
                    val (currentX, currentY) = queue.removeFirst()

                    for ((dx, dy) in directions) {
                        val newX = currentX + dx
                        val newY = currentY + dy

                        if (newX !in 0 until width || newY !in 0 until height) {
                            continue
                        }

                        if (energyLevels[newY][newX] == 0) {
                            continue
                        }

                        energyLevels[newY][newX]++

                        if (energyLevels[newY][newX] > 9) {
                            energyLevels[newY][newX] = 0
                            flashes++
                            queue.add(Point(newX, newY))
                        }
                    }
                }

                yield(flashes)
            }
        }
    }
}
