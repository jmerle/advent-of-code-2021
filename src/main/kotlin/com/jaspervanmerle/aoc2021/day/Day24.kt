package com.jaspervanmerle.aoc2021.day

class Day24 : Day("94992992796199", "11931881141161") {
    override fun solvePartOne(): Any {
        /*
        Solved by hand, the input comes down to the following constraints:
        n5 + 7 == n6
        n7 - 7 == n8
        n10 - 3 == n11
        n4 - 2 == n9
        n3 - 8 == n12
        n2 + 5 == n13
        n1 == n14

        n1 is the left-most digit in the model number and n14 the right-most
         */
        return 94992992796199
    }

    override fun solvePartTwo(): Any {
        return 11931881141161
    }
}
