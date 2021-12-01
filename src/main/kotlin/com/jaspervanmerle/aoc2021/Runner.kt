package com.jaspervanmerle.aoc2021

import com.jaspervanmerle.aoc2021.day.Day

object Runner {
    fun runSingleDay(dayNumber: Int) {
        run(Reflection.getDayClass(dayNumber).createDay())
    }

    fun runLatestDay() {
        run(Reflection.getAllDayClasses().maxByOrNull { it.simpleName }!!.createDay())
    }

    private fun run(day: Day) {
        println("Running day ${day.number}")
        runPart(day, 1)
        runPart(day, 2)
    }

    private fun runPart(day: Day, part: Int) {
        val result = try {
            day.solve(part)
        } catch (err: NotImplementedError) {
            "TODO"
        }

        val answer = day.getAnswer(part)
        val status = if (answer != null) {
            if (result == answer) {
                "correct"
            } else {
                "incorrect, expected $answer"
            }
        } else {
            null
        }

        val resultWithStatus = if (status != null) "$result ($status)" else result
        println("Part $part: $resultWithStatus")
    }
}

fun main(args: Array<String>) {
    if (args.isNotEmpty()) {
        Runner.runSingleDay(args[0].toInt())
    } else {
        Runner.runLatestDay()
    }
}
