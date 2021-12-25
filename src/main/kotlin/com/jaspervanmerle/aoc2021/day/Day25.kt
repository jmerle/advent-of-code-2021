package com.jaspervanmerle.aoc2021.day

class Day25 : Day("492", "Remote start") {
    override fun solvePartOne(): Any {
        val state = input
            .lines()
            .map { it.toCharArray() }
            .toTypedArray()

        for (i in 1..Int.MAX_VALUE) {
            val movedHorizontally = updateState(state, '>') { x, y, width, _ -> ((x + 1) % width) to y }
            val movedVertically = updateState(state, 'v') { x, y, _, height -> x to ((y + 1) % height) }

            if (!movedHorizontally && !movedVertically) {
                return i
            }
        }

        return -1
    }

    override fun solvePartTwo(): Any {
        return "Remote start"
    }

    private fun updateState(
        state: Array<CharArray>,
        type: Char,
        getNextLocation: (x: Int, y: Int, width: Int, height: Int) -> Pair<Int, Int>
    ): Boolean {
        val width = state[0].size
        val height = state.size

        val moves = mutableListOf<Pair<Int, Int>>()
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (state[y][x] != type) {
                    continue
                }

                val (nextX, nextY) = getNextLocation(x, y, width, height)
                if (state[nextY][nextX] == '.') {
                    moves.add(x to y)
                }
            }
        }

        for ((x, y) in moves) {
            val (nextX, nextY) = getNextLocation(x, y, width, height)

            state[y][x] = '.'
            state[nextY][nextX] = type
        }

        return moves.isNotEmpty()
    }
}
