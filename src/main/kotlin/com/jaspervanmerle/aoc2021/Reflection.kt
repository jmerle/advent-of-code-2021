package com.jaspervanmerle.aoc2021

import com.jaspervanmerle.aoc2021.day.Day
import io.github.classgraph.ClassGraph

object Reflection {
    fun getAllDayClasses(): List<Class<Day>> {
        return ClassGraph()
            .enableClassInfo()
            .acceptPackages(Day::class.java.`package`.name)
            .scan()
            .use {
                it.allClasses
                    .filter { cls -> cls.extendsSuperclass(Day::class.java.name) }
                    .map { cls ->
                        @Suppress("UNCHECKED_CAST")
                        cls.loadClass() as Class<Day>
                    }
            }
    }

    fun getDayClass(dayNumber: Int): Class<Day> {
        val className = "Day${dayNumber.toString().padStart(2, '0')}"
        return getAllDayClasses().first { it.simpleName == className }
    }
}

fun Class<Day>.createDay(): Day {
    return getDeclaredConstructor().newInstance()
}
