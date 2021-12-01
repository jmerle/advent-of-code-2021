package com.jaspervanmerle.aoc2021.day

import com.jaspervanmerle.aoc2021.Reflection
import com.jaspervanmerle.aoc2021.createDay
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class DayTests {
    @TestFactory
    fun generateDayTests(): Iterator<DynamicTest> = sequence {
        Reflection.getAllDayClasses().forEach {
            val day = it.createDay()

            for (part in 1..2) {
                val answer = day.getAnswer(part) ?: continue

                yield(DynamicTest.dynamicTest("Day ${day.number} Part $part") {
                    Assertions.assertEquals(answer, day.solve(part))
                })
            }
        }
    }.iterator()
}
