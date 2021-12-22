package com.jaspervanmerle.aoc2021.day

class Day21 : Day("888735", "647608359455719") {
    private val startingPositionOne = input.lines()[0].split(" ").last().toInt()
    private val startingPositionTwo = input.lines()[1].split(" ").last().toInt()

    override fun solvePartOne(): Any {
        var positionOne = startingPositionOne
        var positionTwo = startingPositionTwo

        var scoreOne = 0
        var scoreTwo = 0

        var currentPlayerOne = true

        var rolls = 0

        while (scoreOne < 1000 && scoreTwo < 1000) {
            val roll = rolls * 3 + 6
            rolls += 3

            if (currentPlayerOne) {
                positionOne = (positionOne + roll - 1) % 10 + 1
                scoreOne += positionOne
            } else {
                positionTwo = (positionTwo + roll - 1) % 10 + 1
                scoreTwo += positionTwo
            }

            currentPlayerOne = !currentPlayerOne
        }

        return if (scoreOne < scoreTwo) scoreOne * rolls else scoreTwo * rolls
    }

    override fun solvePartTwo(): Any {
        return countWins(startingPositionOne, startingPositionTwo).maxOf { it }
    }

    private fun countWins(
        positionOne: Int, positionTwo: Int,
        scoreOne: Int = 0, scoreTwo: Int = 0,
        knownStates: MutableMap<Pair<Pair<Int, Int>, Pair<Int, Int>>, LongArray> = mutableMapOf()
    ): LongArray {
        if (scoreOne >= 21) {
            return longArrayOf(1, 0)
        }

        if (scoreTwo >= 21) {
            return longArrayOf(0, 1)
        }

        val state = (positionOne to scoreOne) to (positionTwo to scoreTwo)
        if (knownStates.containsKey(state)) {
            return knownStates[state]!!
        }

        val wins = longArrayOf(0, 0)

        for (roll1 in 1..3) {
            for (roll2 in 1..3) {
                for (roll3 in 1..3) {
                    val newPositionOne = (positionOne + roll1 + roll2 + roll3 - 1) % 10 + 1
                    val newScoreOne = scoreOne + newPositionOne

                    val deltaWins = countWins(positionTwo, newPositionOne, scoreTwo, newScoreOne, knownStates)
                    wins[0] += deltaWins[1]
                    wins[1] += deltaWins[0]
                }
            }
        }

        knownStates[state] = wins
        return wins
    }
}
