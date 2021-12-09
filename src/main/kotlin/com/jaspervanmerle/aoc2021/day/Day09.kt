package com.jaspervanmerle.aoc2021.day

class Day09 : Day("572", "847044") {
    private val map = getInput()
        .lines()
        .map { line -> line.toCharArray().map { it.digitToInt() } }

    private val mapWidth = map[0].size
    private val mapHeight = map.size

    override fun solvePartOne(): Any {
        var riskSum = 0

        for (y in 0 until mapHeight) {
            for (x in 0 until mapWidth) {
                if (x != 0 && map[y][x] >= map[y][x - 1]) {
                    continue
                }

                if (x != mapWidth - 1 && map[y][x] >= map[y][x + 1]) {
                    continue
                }

                if (y != 0 && map[y][x] >= map[y - 1][x]) {
                    continue
                }

                if (y != mapHeight - 1 && map[y][x] >= map[y + 1][x]) {
                    continue
                }

                riskSum += 1 + map[y][x]
            }
        }

        return riskSum
    }

    override fun solvePartTwo(): Any {
        val basinSizes = mutableListOf<Int>()
        val filled = Array(mapHeight) { BooleanArray(mapWidth) }

        for (y in 0 until mapHeight) {
            for (x in 0 until mapWidth) {
                if (map[y][x] == 9 || filled[y][x]) {
                    continue
                }

                var basinSize = 0

                val locationsToFill = ArrayDeque<Pair<Int, Int>>()
                locationsToFill.add(x to y)

                while (!locationsToFill.isEmpty()) {
                    val (currentX, currentY) = locationsToFill.removeFirst()

                    if (currentX < 0 || currentX >= mapWidth || currentY < 0 || currentY >= mapHeight) {
                        continue
                    }

                    if (map[currentY][currentX] == 9 || filled[currentY][currentX]) {
                        continue
                    }

                    filled[currentY][currentX] = true
                    basinSize++

                    locationsToFill.add(currentX + 1 to currentY)
                    locationsToFill.add(currentX - 1 to currentY)
                    locationsToFill.add(currentX to currentY + 1)
                    locationsToFill.add(currentX to currentY - 1)
                }

                basinSizes.add(basinSize)
            }
        }

        basinSizes.sortDescending()
        return basinSizes[0] * basinSizes[1] * basinSizes[2]
    }
}
