package com.jaspervanmerle.aoc2021.day

class Day06 : Day("380758", "1710623015163") {
    private val timers = input
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

        for (timer in timers) {
            currentTimerCounts[timer]++
        }

        for (i in 0 until days) {
            currentTimerCounts = LongArray(9) {
                val newTimers = if (it == 6) currentTimerCounts[0] else 0
                currentTimerCounts[(it + 1) % 9] + newTimers
            }
        }

        return currentTimerCounts.sum()
    }
}
