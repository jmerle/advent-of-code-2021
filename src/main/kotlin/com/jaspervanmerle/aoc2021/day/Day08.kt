package com.jaspervanmerle.aoc2021.day

class Day08 : Day("321", "1028926") {
    private data class Entry(val signalPatterns: List<String>, val outputPatterns: List<String>)

    private val entries = getInput()
        .lines()
        .map { it.split(" | ") }
        .map { Entry(it[0].split(" "), it[1].split(" ")) }

    private val segmentIndicesByDigit = mapOf(
        0 to listOf(0, 1, 2, 4, 5, 6),
        1 to listOf(2, 5),
        2 to listOf(0, 2, 3, 4, 6),
        3 to listOf(0, 2, 3, 5, 6),
        4 to listOf(1, 2, 3, 5),
        5 to listOf(0, 1, 3, 5, 6),
        6 to listOf(0, 1, 3, 4, 5, 6),
        7 to listOf(0, 2, 5),
        8 to listOf(0, 1, 2, 3, 4, 5, 6),
        9 to listOf(0, 1, 2, 3, 5, 6)
    )

    override fun solvePartOne(): Any {
        return entries
            .flatMap { it.outputPatterns }
            .map { it.length }
            .count { it == 2 || it == 4 || it == 3 || it == 7 }
    }

    override fun solvePartTwo(): Any {
        val segmentsPermutations = generateSegmentsPermutations()

        return entries.sumOf { entry ->
            val segments = segmentsPermutations.find { segments ->
                entry.signalPatterns.all { patternToDigit(it, segments) != null }
            }!!

            (0 until 4)
                .map { patternToDigit(entry.outputPatterns[it], segments) }
                .joinToString("")
                .toInt()
        }
    }

    private fun generateSegmentsPermutations(
        prefix: String = "abcdefg",
        suffix: String = "",
        generatedPermutations: MutableList<String> = mutableListOf()
    ): List<String> {
        if (prefix.isEmpty()) {
            generatedPermutations.add(suffix)
        } else {
            for (i in prefix.indices) {
                generateSegmentsPermutations(
                    prefix.substring(0, i) + prefix.substring(i + 1),
                    suffix + prefix[i],
                    generatedPermutations
                )
            }
        }

        return generatedPermutations
    }

    private fun patternToDigit(pattern: String, segments: String): Int? {
        outer@ for ((digit, segmentIndices) in segmentIndicesByDigit) {
            if (pattern.length != segmentIndices.size) {
                continue
            }

            for (index in segmentIndices) {
                if (segments[index] !in pattern) {
                    continue@outer
                }
            }

            return digit
        }

        return null
    }
}
