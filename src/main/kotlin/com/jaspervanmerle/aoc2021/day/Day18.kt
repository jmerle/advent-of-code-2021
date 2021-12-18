package com.jaspervanmerle.aoc2021.day

class Day18 : Day("4124", "4673") {
    private data class Element(var value: Int, var depth: Int)

    override fun solvePartOne(): Any {
        val numbers = input.lines().map { parseLine(it) }

        var result = numbers[0]
        for (number in numbers.drop(1)) {
            result = add(result, number)
        }

        return magnitude(result)
    }

    override fun solvePartTwo(): Any {
        val numbers = input.lines().map { parseLine(it) }

        return numbers.indices
            .flatMap { i -> numbers.indices.filter { it != i }.map { i to it } }
            .maxOf { magnitude(add(numbers[it.first], numbers[it.second])) }
    }

    private fun parseLine(line: String): MutableList<Element> {
        val elements = mutableListOf<Element>()
        var currentDepth = 0

        for (ch in line) {
            when (ch) {
                '[' -> currentDepth++
                ']' -> currentDepth--
                ',' -> continue
                else -> elements.add(Element(ch.digitToInt(), currentDepth))
            }
        }

        return elements
    }

    private fun add(lhs: List<Element>, rhs: List<Element>): MutableList<Element> {
        val newElements = (lhs + rhs)
            .map { it.copy(depth = it.depth + 1) }
            .toMutableList()

        while (true) {
            if (explode(newElements)) {
                continue
            }

            if (split(newElements)) {
                continue
            }

            break
        }

        return newElements
    }

    private fun explode(elements: MutableList<Element>): Boolean {
        for (i in 0 until elements.size - 1) {
            val element = elements[i]
            val nextElement = elements[i + 1]

            if (element.depth <= 4 || element.depth != nextElement.depth) {
                continue
            }

            if (i != 0) {
                elements[i - 1].value += element.value
            }

            if (i + 2 < elements.size) {
                elements[i + 2].value += nextElement.value
            }

            nextElement.value = 0
            nextElement.depth--
            elements.removeAt(i)

            return true
        }

        return false
    }

    private fun split(elements: MutableList<Element>): Boolean {
        for (i in elements.indices) {
            val element = elements[i]

            if (element.value <= 9) {
                continue
            }

            val leftValue = element.value / 2
            val rightValue = if (element.value % 2 == 0) leftValue else leftValue + 1

            element.value = leftValue
            element.depth++
            elements.add(i + 1, Element(rightValue, element.depth))

            return true
        }

        return false
    }

    private fun magnitude(elements: MutableList<Element>): Int {
        while (elements.size > 1) {
            for (i in 0 until elements.size - 1) {
                val element = elements[i]
                val nextElement = elements[i + 1]

                if (element.depth != nextElement.depth) {
                    continue
                }

                element.value = 3 * element.value + 2 * nextElement.value
                element.depth--
                elements.removeAt(i + 1)
                break
            }
        }

        return elements[0].value
    }
}
