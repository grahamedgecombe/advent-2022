package com.grahamedgecombe.advent2022.day3

import com.grahamedgecombe.advent2022.Puzzle

object Day3 : Puzzle<List<Day3.Rucksack>>(3) {
    data class Rucksack(val left: Set<Char>, val right: Set<Char>) {
        val intersection = left intersect right

        companion object {
            fun parse(line: String): Rucksack {
                val n = line.length / 2

                val left = line.substring(0 until n).toSet()
                val right = line.substring(n).toSet()

                return Rucksack(left, right)
            }
        }
    }

    private val Char.priority
        get() = when (this) {
            in 'a'..'z' -> (this.code - 'a'.code) + 1
            in 'A'..'Z' -> (this.code - 'A'.code) + 27
            else -> throw IllegalArgumentException()
        }

    override fun parse(input: Sequence<String>): List<Rucksack> {
        return input.map(Rucksack::parse).toList()
    }

    override fun solvePart1(input: List<Rucksack>): Int {
        return input.flatMap(Rucksack::intersection)
            .sumOf { it.priority }
    }
}
