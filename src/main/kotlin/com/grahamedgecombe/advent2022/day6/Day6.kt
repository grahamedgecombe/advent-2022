package com.grahamedgecombe.advent2022.day6

import com.grahamedgecombe.advent2022.Puzzle
import com.grahamedgecombe.advent2022.UnsolvableException

object Day6 : Puzzle<String>(6) {
    override fun parse(input: Sequence<String>): String {
        return input.single()
    }

    override fun solvePart1(input: String): Int {
        for (i in 4..input.length) {
            val history = input.substring(i - 4, i)

            if (history.toSet().size == 4) {
                return i
            }
        }

        throw UnsolvableException()
    }
}
