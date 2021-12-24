package com.jaspervanmerle.aoc2021.day

class Day23 : Day("15385", "49803") {
    override fun solvePartOne(): Any {
        /*
        Initial diagram:
        #############
        #...........#
        ###A#D#A#C###
          #C#D#B#B#
          #########

        Step 1 (used energy = 7):
        #############
        #A..........#
        ###A#D#.#C###
          #C#D#B#B#
          #########

        Step 2 (used energy = 50):
        #############
        #A..B.......#
        ###A#D#.#C###
          #C#D#.#B#
          #########

        Step 3 (used energy = 500):
        #############
        #A..B.......#
        ###A#D#.#.###
          #C#D#C#B#
          #########

        Step 4 (used energy = 30):
        #############
        #A..B.....B.#
        ###A#D#.#.###
          #C#D#C#.#
          #########

        Step 5 (used energy = 7000):
        #############
        #A..B.....B.#
        ###A#.#.#.###
          #C#D#C#D#
          #########

        Step 6 (used energy = 7000):
        #############
        #A..B.....B.#
        ###A#.#.#D###
          #C#.#C#D#
          #########

        Step 7 (used energy = 30):
        #############
        #A........B.#
        ###A#.#.#D###
          #C#B#C#D#
          #########

        Step 8 (used energy = 60):
        #############
        #A..........#
        ###A#B#.#D###
          #C#B#C#D#
          #########

        Step 9 (used energy = 2):
        #############
        #AA.........#
        ###.#B#.#D###
          #C#B#C#D#
          #########

        Step 9 (used energy = 700):
        #############
        #AA.........#
        ###.#B#C#D###
          #.#B#C#D#
          #########

        Step 10 (used energy = 3):
        #############
        #A..........#
        ###.#B#C#D###
          #A#B#C#D#
          #########

        Step 11 (used energy = 3):
        #############
        #...........#
        ###A#B#C#D###
          #A#B#C#D#
          #########
         */
        return 7 + 50 + 500 + 30 + 7000 + 7000 + 30 + 60 + 2 + 700 + 3 + 3
    }

    override fun solvePartTwo(): Any {
        // Solved manually using https://aochelper2021.blob.core.windows.net/day23/index.html
        return 49803
    }
}
