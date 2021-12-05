package com.jaspervanmerle.aoc2021.day

class Day04 : Day("25410", "2730") {
    private val markedNumbers = getInput()
        .lines()[0]
        .split(",")
        .map { it.toInt() }

    private val boards = getInput().split("\n\n").drop(1).map { board ->
        board.split("\n").map { row ->
            row.split(" ").filter { it.isNotBlank() }.map { it.toInt() }
        }
    }

    override fun solvePartOne(): Any {
        return getWinningScore(true)
    }

    override fun solvePartTwo(): Any {
        return getWinningScore(false)
    }

    private fun getWinningScore(firstWin: Boolean): Int {
        val boardsToMarkedIndices = boards
            .map { board ->
                val columns = board[0].indices.map { column -> board.indices.map { board[it][column] } }
                val bestIndex = (board + columns)
                    .minOf { numbers ->
                        if (numbers.intersect(markedNumbers).size != numbers.size) {
                            Int.MAX_VALUE
                        } else {
                            numbers.maxOf { markedNumbers.indexOf(it) }
                        }
                    }

                board to bestIndex
            }

        val (winningBoard, markedIndex) = if (firstWin) {
            boardsToMarkedIndices.minByOrNull { it.second }!!
        } else {
            boardsToMarkedIndices.maxByOrNull { it.second }!!
        }

        val unmarkedSum = winningBoard.sumOf { row ->
            row.filter {
                !markedNumbers.contains(it) || markedNumbers.indexOf(it) > markedIndex
            }.sum()
        }

        return unmarkedSum * markedNumbers[markedIndex]
    }
}
