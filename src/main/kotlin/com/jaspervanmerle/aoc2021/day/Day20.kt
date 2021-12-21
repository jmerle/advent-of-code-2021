package com.jaspervanmerle.aoc2021.day

class Day20 : Day("4928", "16605") {
    private data class Pixel(val x: Int, val y: Int)

    private val enhancementAlgorithm = input
        .split("\n\n")[0]
        .toCharArray()
        .map { it == '#' }

    private val inputLitPixels = input
        .split("\n\n")[1]
        .lines()
        .flatMapIndexed { y, line ->
            line.toCharArray().mapIndexed { x, ch ->
                if (ch == '#') {
                    Pixel(x, y)
                } else {
                    null
                }
            }
        }
        .filterNotNull()
        .toSet()

    override fun solvePartOne(): Any {
        return getLitPixelsAfterEnhancements(2)
    }

    override fun solvePartTwo(): Any {
        return getLitPixelsAfterEnhancements(50)
    }

    private fun getLitPixelsAfterEnhancements(enhancements: Int): Int {
        var width = inputLitPixels.maxOf { it.x } + enhancements * 4 + 1
        var height = inputLitPixels.maxOf { it.y } + enhancements * 4 + 1

        var litPixels = Array(height) { y ->
            BooleanArray(width) { x ->
                inputLitPixels.contains(Pixel(x - enhancements * 2, y - enhancements * 2))
            }
        }

        for (i in 0 until enhancements) {
            width -= 2
            height -= 2

            litPixels = Array(height) { y ->
                BooleanArray(width) { x ->
                    val bits = StringBuilder()

                    bits.append(if (litPixels[y][x]) 1 else 0)
                    bits.append(if (litPixels[y][x + 1]) 1 else 0)
                    bits.append(if (litPixels[y][x + 2]) 1 else 0)
                    bits.append(if (litPixels[y + 1][x]) 1 else 0)
                    bits.append(if (litPixels[y + 1][x + 1]) 1 else 0)
                    bits.append(if (litPixels[y + 1][x + 2]) 1 else 0)
                    bits.append(if (litPixels[y + 2][x]) 1 else 0)
                    bits.append(if (litPixels[y + 2][x + 1]) 1 else 0)
                    bits.append(if (litPixels[y + 2][x + 2]) 1 else 0)

                    enhancementAlgorithm[bits.toString().toInt(2)]
                }
            }
        }

        return litPixels.sumOf { row -> row.count { it } }
    }
}
