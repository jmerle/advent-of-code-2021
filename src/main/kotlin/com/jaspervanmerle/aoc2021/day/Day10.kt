package com.jaspervanmerle.aoc2021.day

class Day10 : Day("390993", "2391385187") {
    private val lines = getInput().lines()

    private val openCharacterByClosing = mapOf(
        ')' to '(',
        ']' to '[',
        '}' to '{',
        '>' to '<'
    )

    private val errorScoreByCharacter = mapOf(
        ')' to 3L,
        ']' to 57L,
        '}' to 1197L,
        '>' to 25137L
    )

    private val incompleteScoreByCharacter = mapOf(
        '(' to 1L,
        '[' to 2L,
        '{' to 3L,
        '<' to 4L
    )

    override fun solvePartOne(): Any {
        return lines
            .map { getScore(it) }
            .filter { !it.first }
            .sumOf { it.second }
    }

    override fun solvePartTwo(): Any {
        val incompleteScores = lines
            .map { getScore(it) }
            .filter { it.first }
            .map { it.second }
            .sorted()

        return incompleteScores[incompleteScores.size / 2]
    }

    private fun getScore(line: String): Pair<Boolean, Long> {
        val stack = ArrayDeque<Char>()

        for (char in line) {
            if (char in openCharacterByClosing.keys) {
                val last = stack.removeLast()

                if (last != openCharacterByClosing[char]) {
                    return false to errorScoreByCharacter[char]!!
                }
            } else {
                stack.add(char)
            }
        }

        var incompleteScore = 0L
        while (!stack.isEmpty()) {
            incompleteScore *= 5L
            incompleteScore += incompleteScoreByCharacter[stack.removeLast()]!!
        }

        return true to incompleteScore
    }
}
