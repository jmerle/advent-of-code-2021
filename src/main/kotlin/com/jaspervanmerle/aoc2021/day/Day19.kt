package com.jaspervanmerle.aoc2021.day

import kotlin.math.abs

class Day19 : Day("462", "12158") {
    private data class RotationMatrix(
        val a: Int, val b: Int, val c: Int,
        val d: Int, val e: Int, val f: Int,
        val g: Int, val h: Int, val i: Int
    ) {
        companion object {
            // Adapted from https://www.euclideanspace.com/maths/algebra/matrix/transforms/examples/index.htm
            val ROTATIONS = listOf(
                RotationMatrix(
                    1, 0, 0,
                    0, 1, 0,
                    0, 0, 1
                ),
                RotationMatrix(
                    1, 0, 0,
                    0, 0, -1,
                    0, 1, 0
                ),
                RotationMatrix(
                    1, 0, 0,
                    0, -1, 0,
                    0, 0, -1
                ),
                RotationMatrix(
                    1, 0, 0,
                    0, 0, 1,
                    0, -1, 0
                ),
                RotationMatrix(
                    0, -1, 0,
                    1, 0, 0,
                    0, 0, 1
                ),
                RotationMatrix(
                    0, 0, 1,
                    1, 0, 0,
                    0, 1, 0
                ),
                RotationMatrix(
                    0, 1, 0,
                    1, 0, 0,
                    0, 0, -1
                ),
                RotationMatrix(
                    0, 0, -1,
                    1, 0, 0,
                    0, -1, 0
                ),
                RotationMatrix(
                    -1, 0, 0,
                    0, -1, 0,
                    0, 0, 1
                ),
                RotationMatrix(
                    -1, 0, 0,
                    0, 0, -1,
                    0, -1, 0
                ),
                RotationMatrix(
                    -1, 0, 0,
                    0, 1, 0,
                    0, 0, -1
                ),
                RotationMatrix(
                    -1, 0, 0,
                    0, 0, 1,
                    0, 1, 0
                ),
                RotationMatrix(
                    0, 1, 0,
                    -1, 0, 0,
                    0, 0, 1
                ),
                RotationMatrix(
                    0, 0, 1,
                    -1, 0, 0,
                    0, -1, 0
                ),
                RotationMatrix(
                    0, -1, 0,
                    -1, 0, 0,
                    0, 0, -1
                ),
                RotationMatrix(
                    0, 0, -1,
                    -1, 0, 0,
                    0, 1, 0
                ),
                RotationMatrix(
                    0, 0, -1,
                    0, 1, 0,
                    1, 0, 0
                ),
                RotationMatrix(
                    0, 1, 0,
                    0, 0, 1,
                    1, 0, 0
                ),
                RotationMatrix(
                    0, 0, 1,
                    0, -1, 0,
                    1, 0, 0
                ),
                RotationMatrix(
                    0, -1, 0,
                    0, 0, -1,
                    1, 0, 0
                ),
                RotationMatrix(
                    0, 0, -1,
                    0, -1, 0,
                    -1, 0, 0
                ),
                RotationMatrix(
                    0, -1, 0,
                    0, 0, 1,
                    -1, 0, 0
                ),
                RotationMatrix(
                    0, 0, 1,
                    0, 1, 0,
                    -1, 0, 0
                ),
                RotationMatrix(
                    0, 1, 0,
                    0, 0, -1,
                    -1, 0, 0
                )
            )
        }

        fun applyTo(point: Point): Point {
            return Point(
                a * point.x + b * point.y + c * point.z,
                d * point.x + e * point.y + f * point.z,
                g * point.x + h * point.y + i * point.z
            )
        }
    }

    private data class Point(val x: Int, val y: Int, val z: Int) {
        fun add(other: Point): Point {
            return Point(x + other.x, y + other.y, z + other.z)
        }

        fun subtract(other: Point): Point {
            return Point(x - other.x, y - other.y, z - other.z)
        }

        fun distanceTo(other: Point): Int {
            return abs(other.x - x) + abs(other.y - y) + abs(other.z - z)
        }
    }

    private data class Scanner(var beacons: List<Point>, var position: Point? = null) {
        fun tryMatch(other: Scanner) {
            for (rotation in RotationMatrix.ROTATIONS) {
                val positionCounter = mutableMapOf<Point, Int>()

                for (a in beacons) {
                    for (b in other.beacons) {
                        val newPosition = a.subtract(rotation.applyTo(b))
                        positionCounter.merge(newPosition, 1, Int::plus)
                    }
                }

                for ((point, counter) in positionCounter) {
                    if (counter >= 12) {
                        other.position = point
                        other.beacons = other.beacons.map { point.add(rotation.applyTo(it)) }
                        return
                    }
                }
            }
        }
    }

    override fun solvePartOne(): Any {
        return getScanners().flatMap { it.beacons }.distinct().size
    }

    override fun solvePartTwo(): Any {
        val scanners = getScanners()

        return scanners.maxOf { a ->
            scanners.maxOf { b ->
                a.position!!.distanceTo(b.position!!)
            }
        }
    }

    private fun getScanners(): List<Scanner> {
        val scanners = input
            .split("\n\n")
            .map { section ->
                section.lines().drop(1).map { line ->
                    val parts = line.split(",")
                    Point(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
                }
            }
            .map { Scanner(it) }

        scanners[0].position = Point(0, 0, 0)

        while (scanners.any { it.position == null }) {
            for (a in scanners) {
                if (a.position == null) {
                    continue
                }

                for (b in scanners) {
                    if (b.position != null) {
                        continue
                    }

                    a.tryMatch(b)
                }
            }
        }

        return scanners
    }
}
