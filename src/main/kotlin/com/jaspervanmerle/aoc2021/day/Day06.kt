package com.jaspervanmerle.aoc2021.day

class Day06 : Day("380758", "1710623015163") {
    private val timers = getInput()
        .split(",")
        .map { it.toInt() }

    override fun solvePartOne(): Any {
        return getFishAfterDays(80)
    }

    override fun solvePartTwo(): Any {
        return getFishAfterDays(256)
    }

    private fun getFishAfterDays(days: Int): Long {
        var currentTimerCounts = LongArray(9)
        val newTimerCounts = LongArray(9)

        for (timer in timers) {
            currentTimerCounts[timer]++
        }

        for (i in 0 until days) {
            for (j in currentTimerCounts.indices) {
                newTimerCounts[j] = currentTimerCounts[(j + 1) % 9]
            }

            newTimerCounts[6] += currentTimerCounts[0]
            currentTimerCounts = newTimerCounts.clone()
        }

        return currentTimerCounts.sum()
    }
}
