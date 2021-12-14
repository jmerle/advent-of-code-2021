package com.jaspervanmerle.aoc2021.day

class Day14 : Day("2851", "10002813279337") {
    private val template = input.split("\n\n")[0]

    private val rules = input
        .split("\n\n")[1]
        .lines()
        .map { it.replace(" -> ", "") }
        .associate { (it[0] to it[1]) to it[2] }

    override fun solvePartOne(): Any {
        return getAnswerAfterSteps(10)
    }

    override fun solvePartTwo(): Any {
        return getAnswerAfterSteps(40)
    }

    private fun getAnswerAfterSteps(steps: Int): Long {
        var counts = Array(26) { LongArray(26) }

        for (i in 0 until template.length - 1) {
            counts[template[i] - 'A'][template[i + 1] - 'A']++
        }

        for (i in 0 until steps) {
            val newCounts = Array(26) { LongArray(26) }

            for ((pair, insertion) in rules) {
                val pairLeftIndex = pair.first - 'A'
                val pairRightIndex = pair.second - 'A'
                val insertionIndex = insertion - 'A'

                newCounts[pairLeftIndex][insertionIndex] += counts[pairLeftIndex][pairRightIndex]
                newCounts[insertionIndex][pairRightIndex] += counts[pairLeftIndex][pairRightIndex]
            }

            counts = newCounts
        }

        val characters = 'A'..'Z'
        val occurrences = characters
            .map { ch1 ->
                var count = characters.sumOf { ch2 -> counts[ch1 - 'A'][ch2 - 'A'] + counts[ch2 - 'A'][ch1 - 'A'] }

                if (template[0] == ch1) {
                    count++
                }

                if (template.last() == ch1) {
                    count++
                }

                count / 2
            }
            .filter { it > 0 }

        return occurrences.maxOf { it } - occurrences.minOf { it }
    }
}
