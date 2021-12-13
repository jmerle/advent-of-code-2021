package com.jaspervanmerle.aoc2021.day

class Day12 : Day("5076", "145643") {
    private data class Node(
        val id: String,
        val isSmall: Boolean = id.lowercase() == id,
        val neighbors: MutableList<Node> = mutableListOf()
    )

    private val caves = getCaves()

    override fun solvePartOne(): Any {
        return countPathsPartOne(caves["start"]!!)
    }

    override fun solvePartTwo(): Any {
        return countPathsPartTwo(caves["start"]!!)
    }

    private fun getCaves(): Map<String, Node> {
        val caves = mutableMapOf<String, Node>()

        for (line in input.lines()) {
            val (from, to) = line.split("-")

            if (from !in caves) {
                caves[from] = Node(from)
            }

            if (to !in caves) {
                caves[to] = Node(to)
            }

            caves[from]!!.neighbors.add(caves[to]!!)
            caves[to]!!.neighbors.add(caves[from]!!)
        }

        return caves
    }

    private fun countPathsPartOne(node: Node, usedCaves: Set<String> = emptySet()): Int {
        if (node.id == "end") {
            return 1
        }

        return node
            .neighbors
            .filter { !it.isSmall || it.id !in usedCaves }
            .sumOf { countPathsPartOne(it, usedCaves + node.id) }
    }

    private fun countPathsPartTwo(
        node: Node,
        path: List<String> = listOf(node.id),
        usedPaths: MutableSet<List<String>> = mutableSetOf(),
        usedCaves: Set<String> = emptySet(),
        duplicateSmallCave: String? = null,
        duplicated: Boolean = false,
    ): Int {
        if (node.id == "end") {
            return if (usedPaths.add(path)) 1 else 0
        }

        return node
            .neighbors
            .filter { !it.isSmall || (it.id !in usedCaves || (it.id == duplicateSmallCave && !duplicated)) }
            .sumOf {
                val newPath = path + it.id
                val newUsedCaves = usedCaves + node.id
                val newDuplicated = if (it.id == duplicateSmallCave) true else duplicated
                var paths = countPathsPartTwo(it, newPath, usedPaths, newUsedCaves, duplicateSmallCave, newDuplicated)

                if (it.isSmall && duplicateSmallCave == null) {
                    paths += countPathsPartTwo(it, newPath, usedPaths, newUsedCaves, it.id, false)
                }

                paths
            }
    }
}
