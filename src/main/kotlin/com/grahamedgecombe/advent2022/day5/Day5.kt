package com.grahamedgecombe.advent2022.day5

import com.grahamedgecombe.advent2022.Puzzle

object Day5 : Puzzle<Day5.Input>(5) {
    data class Input(val stacks: List<List<Char>>, val moves: List<Move>)

    data class Move(val n: Int, val from: Int, val to: Int) {
        companion object {
            private val REGEX = Regex("move (\\d+) from (\\d) to (\\d)")

            fun parse(line: String): Move {
                val m = REGEX.matchEntire(line) ?: throw IllegalArgumentException()
                val (n, from, to) = m.destructured
                return Move(n.toInt(), from.toInt(), to.toInt())
            }
        }
    }

    override fun parse(input: Sequence<String>): Input {
        val stacks = sortedMapOf<Int, ArrayDeque<Char>>()

        val it = input.iterator()
        outer@while (true) {
            if (!it.hasNext()) {
                throw IllegalArgumentException()
            }

            val line = it.next()

            for (i in 0..line.length / 4) {
                val item = line[i * 4 + 1]
                if (item == ' ') {
                    continue
                } else if (item in '1'..'9') {
                    break@outer
                } else if (item !in 'A'..'Z') {
                    throw IllegalArgumentException()
                }

                val stack = stacks.computeIfAbsent(i, ::ArrayDeque)
                stack.addFirst(item)
            }
        }

        if (!it.hasNext() || it.next().isNotEmpty()) {
            throw IllegalArgumentException()
        }

        val moves = mutableListOf<Move>()
        while (it.hasNext()) {
            moves += Move.parse(it.next())
        }

        return Input(stacks.values.toList(), moves)
    }

    override fun solvePart1(input: Input): String {
        val stacks = input.stacks.map(::ArrayDeque)

        for (move in input.moves) {
            val from = stacks[move.from - 1]
            val to = stacks[move.to - 1]

            for (i in 0 until move.n) {
                to.addLast(from.removeLast())
            }
        }

        return stacks.map(ArrayDeque<Char>::last).joinToString(separator = "")
    }

    override fun solvePart2(input: Input): Any {
        val stacks = input.stacks.map(::ArrayDeque)
        val mover = ArrayDeque<Char>()

        for (move in input.moves) {
            val from = stacks[move.from - 1]
            val to = stacks[move.to - 1]

            for (i in 0 until move.n) {
                mover.addLast(from.removeLast())
            }

            while (mover.isNotEmpty()) {
                to.addLast(mover.removeLast())
            }
        }

        return stacks.map(ArrayDeque<Char>::last).joinToString(separator = "")
    }
}
