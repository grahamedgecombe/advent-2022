package com.grahamedgecombe.advent2022.day25

import com.grahamedgecombe.advent2022.Puzzle

object Day25 : Puzzle<List<Long>>(25) {
    private fun String.snafuToLong(): Long {
        var value = 0L
        var place = 1L

        for (c in reversed()) {
            val digit = when (c) {
                '2' -> 2
                '1' -> 1
                '0' -> 0
                '-' -> -1
                '=' -> -2
                else -> throw IllegalArgumentException()
            }

            value += digit * place
            place *= 5
        }

        return value
    }

    private fun Long.snafuToString(): String {
        val b = StringBuilder()
        var value = this

        while (value != 0L) {
            val c = when ((value + 2) % 5) {
                4L -> '2'
                3L -> '1'
                2L -> '0'
                1L -> '-'
                0L -> '='
                else -> throw IllegalStateException()
            }
            b.append(c)

            value = (value + 2) / 5
        }

        b.reverse()
        return b.toString()
    }

    override fun parse(input: Sequence<String>): List<Long> {
        return input.map { it.snafuToLong() }.toList()
    }

    override fun solvePart1(input: List<Long>): String {
        return input.sum().snafuToString()
    }
}
