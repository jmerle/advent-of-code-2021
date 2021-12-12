package com.jaspervanmerle.aoc2021.day

class Day03 : Day("3959450", "7440311") {
    private val numbers = input.lines()

    override fun solvePartOne(): Any {
        val gammaRate = getRate { zeros, ones -> zeros > ones }
        val epsilonRate = getRate { zeros, ones -> zeros < ones }
        return gammaRate * epsilonRate
    }

    override fun solvePartTwo(): Any {
        val generatorRating = getRating { zeros, ones -> ones < zeros }
        val scrubberRating = getRating { zeros, ones -> zeros <= ones }
        return generatorRating * scrubberRating
    }

    private fun getRate(addZero: (zeros: Int, ones: Int) -> Boolean): Int {
        return (0 until numbers[0].length)
            .map { i ->
                val zeros = numbers.count { it[i] == '0' }
                val ones = numbers.count { it[i] == '1' }
                if (addZero(zeros, ones)) '0' else '1'
            }
            .joinToString("")
            .toInt(2)
    }

    private fun getRating(useZeros: (zeros: Int, ones: Int) -> Boolean): Int {
        return (0 until numbers[0].length)
            .fold(numbers) { acc, i ->
                if (acc.size == 1) {
                    acc
                } else {
                    val zeros = acc.filter { it[i] == '0' }
                    val ones = acc.filter { it[i] == '1' }
                    if (useZeros(zeros.size, ones.size)) zeros else ones
                }
            }
            .first()
            .toInt(2)
    }
}
