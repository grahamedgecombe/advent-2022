package com.grahamedgecombe.advent2022.day6

import com.grahamedgecombe.advent2022.Puzzle
import com.grahamedgecombe.advent2022.UnsolvableException

object Day6 : Puzzle<String>(6) {
    override fun parse(input: Sequence<String>): String {
        return input.single()
    }

    override fun solvePart1(input: String): Int {
        return solve(input, 4)
    }

    override fun solvePart2(input: String): Int {
        return solve(input, 14)
    }

    private fun solve(input: String, n: Int): Int {
        for (i in n..input.length) {
            val history = input.substring(i - n, i)

            if (history.toSet().size == n) {
                return i
            }
        }

        throw UnsolvableException()
    }
}
