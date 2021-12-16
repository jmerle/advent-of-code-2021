package com.jaspervanmerle.aoc2021.day

class Day16 : Day("1007", "834151779165") {
    private class Bits(input: String) {
        private var bits = input
            .toCharArray()
            .joinToString("") { it.digitToInt(16).toString(2).padStart(4, '0') }

        fun popBits(bitCount: Int): String {
            val removedBits = bits.substring(0, bitCount)
            bits = bits.substring(bitCount)
            return removedBits
        }

        fun popInt(bitCount: Int): Int {
            return popBits(bitCount).toInt(2)
        }

        fun getSize(): Int {
            return bits.length
        }

        fun isEmpty(): Boolean {
            return !bits.contains('1')
        }
    }

    private interface Packet {
        companion object {
            fun parse(bits: Bits): Packet {
                bits.popBits(3)
                return when (val typeId = bits.popInt(3)) {
                    4 -> LiteralPacket(bits)
                    0 -> SumPacket(bits)
                    1 -> ProductPacket(bits)
                    2 -> MinimumPacket(bits)
                    3 -> MaximumPacket(bits)
                    5 -> GreaterThanPacket(bits)
                    6 -> LessThanPacket(bits)
                    7 -> EqualToPacket(bits)
                    else -> throw RuntimeException("Unknown type id $typeId")
                }
            }
        }

        fun evaluate(): Long
    }

    private class LiteralPacket(bits: Bits) : Packet {
        var value: Long

        init {
            val valueBits = StringBuilder()

            while (true) {
                val part = bits.popBits(5)
                valueBits.append(part.substring(1))
                if (part[0] == '0') {
                    break
                }
            }

            value = valueBits.toString().toLong(2)
        }

        override fun evaluate(): Long {
            return value
        }
    }

    private abstract class OperatorPacket(bits: Bits) : Packet {
        val subPackets = mutableListOf<Packet>()

        init {
            val lengthTypeId = bits.popInt(1)
            if (lengthTypeId == 0) {
                val bitLength = bits.popInt(15)
                val bitSizeEnd = bits.getSize() - bitLength

                while (bits.getSize() > bitSizeEnd) {
                    subPackets.add(Packet.parse(bits))
                }
            } else {
                val subPacketCount = bits.popInt(11)
                for (i in 0 until subPacketCount) {
                    subPackets.add(Packet.parse(bits))
                }
            }
        }
    }

    private class SumPacket(bits: Bits) : OperatorPacket(bits) {
        override fun evaluate(): Long {
            return subPackets.sumOf { it.evaluate() }
        }
    }

    private class ProductPacket(bits: Bits) : OperatorPacket(bits) {
        override fun evaluate(): Long {
            return subPackets.fold(1L) { acc, packet -> acc * packet.evaluate() }
        }
    }

    private class MinimumPacket(bits: Bits) : OperatorPacket(bits) {
        override fun evaluate(): Long {
            return subPackets.minOf { it.evaluate() }
        }
    }

    private class MaximumPacket(bits: Bits) : OperatorPacket(bits) {
        override fun evaluate(): Long {
            return subPackets.maxOf { it.evaluate() }
        }
    }

    private class GreaterThanPacket(bits: Bits) : OperatorPacket(bits) {
        override fun evaluate(): Long {
            return if (subPackets[0].evaluate() > subPackets[1].evaluate()) 1L else 0L
        }
    }

    private class LessThanPacket(bits: Bits) : OperatorPacket(bits) {
        override fun evaluate(): Long {
            return if (subPackets[0].evaluate() < subPackets[1].evaluate()) 1L else 0L
        }
    }

    private class EqualToPacket(bits: Bits) : OperatorPacket(bits) {
        override fun evaluate(): Long {
            return if (subPackets[0].evaluate() == subPackets[1].evaluate()) 1L else 0L
        }
    }

    override fun solvePartOne(): Any {
        val bits = Bits(input)
        var versionSum = 0

        while (!bits.isEmpty()) {
            versionSum += bits.popInt(3)

            val typeId = bits.popInt(3)
            if (typeId == 4) {
                while (true) {
                    val part = bits.popBits(5)
                    if (part[0] == '0') {
                        break
                    }
                }
            } else {
                val lengthTypeId = bits.popInt(1)
                if (lengthTypeId == 0) {
                    bits.popBits(15)
                } else {
                    bits.popBits(11)
                }
            }
        }

        return versionSum
    }

    override fun solvePartTwo(): Any {
        return Packet.parse(Bits(input)).evaluate()
    }
}
