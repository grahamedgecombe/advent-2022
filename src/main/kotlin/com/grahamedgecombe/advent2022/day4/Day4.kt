package com.grahamedgecombe.advent2022.day4

import com.grahamedgecombe.advent2022.Puzzle

object Day4 : Puzzle<List<Pair<IntRange, IntRange>>>(4) {
    private val REGEX = Regex("(\\d+)-(\\d+),(\\d+)-(\\d+)")

    override fun parse(input: Sequence<String>): List<Pair<IntRange, IntRange>> {
        return input.map { line ->
            val match = REGEX.matchEntire(line) ?: throw IllegalArgumentException()

            val (a, b, c, d) = match.destructured

            val first = IntRange(a.toInt(), b.toInt())
            val second = IntRange(c.toInt(), d.toInt())

            Pair(first, second)
        }.toList()
    }

    private fun IntRange.containsAll(o: IntRange): Boolean {
        return o.first >= first && o.last <= last
    }

    override fun solvePart1(input: List<Pair<IntRange, IntRange>>): Int {
        return input.count { (first, second) ->
            first.containsAll(second) || second.containsAll(first)
        }
    }
}
