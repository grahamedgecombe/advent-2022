package com.grahamedgecombe.advent2022.day2

import com.grahamedgecombe.advent2022.Puzzle

object Day2 : Puzzle<List<Pair<Char, Char>>>(2) {
    override fun parse(input: Sequence<String>): List<Pair<Char, Char>> {
        return input.map { line ->
            val (opponent, player) = line.split(' ', limit = 2)
            Pair(opponent.first(), player.first())
        }.toList()
    }

    override fun solvePart1(input: List<Pair<Char, Char>>): Int {
        return input.sumOf(Day2::playPart1)
    }

    override fun solvePart2(input: List<Pair<Char, Char>>): Int {
        return input.sumOf(Day2::playPart2)
    }

    private fun playPart1(round: Pair<Char, Char>): Int {
        val (opponentChar, playerChar) = round

        val opponent = opponentChar.code - 'A'.code
        val player = playerChar.code - 'X'.code

        val score = player + 1

        return when (player) {
            // draw
            opponent -> score + 3
            // player wins
            (opponent + 1) % 3 -> score + 6
            // opponent wins
            else -> score
        }
    }

    private fun playPart2(round: Pair<Char, Char>): Int {
        val (opponentChar, playerChar) = round

        val opponent = opponentChar.code - 'A'.code

        return when (playerChar) {
            // opponent wins
            'X' -> ((opponent + 2) % 3) + 1
            // draw
            'Y' -> opponent + 4
            // player wins
            else -> ((opponent + 1) % 3) + 7
        }
    }
}
