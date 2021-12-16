package com.jaspervanmerle.aoc2021.day

import java.util.*

class Day15 : Day("748", "3045") {
    private data class Node(
        val x: Int,
        val y: Int,
        val riskLevel: Int,
        val neighbors: MutableList<Node> = mutableListOf()
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Node

            if (x != other.x) return false
            if (y != other.y) return false
            if (riskLevel != other.riskLevel) return false

            return true
        }

        override fun hashCode(): Int {
            var result = x
            result = 31 * result + y
            result = 31 * result + riskLevel
            return result
        }
    }

    private val partialRiskLevels = input
        .lines()
        .map { line -> line.toCharArray().map { it.digitToInt() }.toIntArray() }
        .toTypedArray()

    override fun solvePartOne(): Any {
        return getLowestTotalRisk(partialRiskLevels)
    }

    override fun solvePartTwo(): Any {
        val partialWidth = partialRiskLevels[0].size
        val partialHeight = partialRiskLevels.size

        val allRiskLevels = Array(partialHeight * 5) { y ->
            IntArray(partialWidth * 5) { x ->
                val distance = x / partialWidth + y / partialHeight

                val originalRiskLevel = partialRiskLevels[y % partialHeight][x % partialWidth]
                val newRiskLevel = originalRiskLevel + distance

                if (newRiskLevel > 9) newRiskLevel - 9 else newRiskLevel
            }
        }

        return getLowestTotalRisk(allRiskLevels)
    }

    private fun createNodes(riskLevels: Array<IntArray>): Array<Array<Node>> {
        val width = riskLevels[0].size
        val height = riskLevels.size

        val nodes = Array(height) { y ->
            Array(width) { x ->
                Node(x, y, riskLevels[y][x])
            }
        }

        for (y in 0 until height) {
            for (x in 0 until width) {
                val node = nodes[y][x]

                if (x > 0) {
                    node.neighbors.add(nodes[y][x - 1])
                }

                if (x < width - 1) {
                    node.neighbors.add(nodes[y][x + 1])
                }

                if (y > 0) {
                    node.neighbors.add(nodes[y - 1][x])
                }

                if (y < height - 1) {
                    node.neighbors.add(nodes[y + 1][x])
                }
            }
        }

        return nodes
    }

    private fun getLowestTotalRisk(riskLevels: Array<IntArray>): Int {
        val nodes = createNodes(riskLevels).flatten()

        val source = nodes[0]
        val target = nodes.last()

        val distances = nodes.associateWith { Int.MAX_VALUE }.toMutableMap()
        distances[source] = 0

        val nodesToProcess = PriorityQueue<Node>(Comparator.comparingInt { distances[it]!! })
        nodesToProcess.add(source)

        while (nodesToProcess.isNotEmpty()) {
            val node = nodesToProcess.remove()

            if (node == target) {
                return distances[node]!!
            }

            for (neighbor in node.neighbors) {
                val newDistance = distances[node]!! + neighbor.riskLevel
                if (newDistance < distances[neighbor]!!) {
                    distances[neighbor] = newDistance
                    nodesToProcess.add(neighbor)
                }
            }
        }

        throw IllegalArgumentException("There is no path from source to target")
    }
}
