package com.grahamedgecombe.advent2022.day23

import com.grahamedgecombe.advent2022.Puzzle
import com.grahamedgecombe.advent2022.util.Vector2

object Day23 : Puzzle<Set<Vector2>>(23) {
    override fun parse(input: Sequence<String>): Set<Vector2> {
        val elves = mutableSetOf<Vector2>()

        for ((y, row) in input.withIndex()) {
            for ((x, c) in row.withIndex()) {
                if (c == '#') {
                    elves += Vector2(x, y)
                }
            }
        }

        return elves
    }

    enum class Direction(val vector: Vector2) {
        NORTH(Vector2(0, -1)),
        SOUTH(Vector2(0, 1)),
        WEST(Vector2(-1, 0)),
        EAST(Vector2(1, 0));

        fun next(): Direction {
            val values = Direction.values()
            return values[(ordinal + 1) % values.size]
        }
    }

    private fun noneAdjacent(elves: Set<Vector2>, v: Vector2): Boolean {
        for (dy in -1..1) {
            for (dx in -1..1) {
                if (dx == 0 && dy == 0) {
                    continue
                } else if (v.add(dx, dy) in elves) {
                    return false
                }
            }
        }

        return true
    }

    private fun validProposal(elves: Set<Vector2>, elf: Vector2, direction: Direction): Boolean {
        if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            for (x in -1..1) {
                if (elf + direction.vector + Vector2(x, 0) in elves) {
                    return false
                }
            }
        } else {
            for (y in -1..1) {
                if (elf + direction.vector + Vector2(0, y) in elves) {
                    return false
                }
            }
        }

        return true
    }

    override fun solvePart1(input: Set<Vector2>): Int {
        var firstDirection = Direction.NORTH
        var elves = input

        for (round in 0 until 10) {
            // part 1
            val nextElves = mutableSetOf<Vector2>()
            val elfToProposal = mutableMapOf<Vector2, Vector2>()
            val proposalToElves = mutableMapOf<Vector2, Set<Vector2>>()

            for (elf in elves) {
                if (noneAdjacent(elves, elf)) {
                    nextElves += elf
                    continue
                }

                var proposed = false
                var direction = firstDirection
                do {
                    val proposal = elf + direction.vector
                    if (validProposal(elves, elf, direction)) {
                        elfToProposal[elf] = proposal
                        proposalToElves.merge(proposal, setOf(elf), Set<Vector2>::union)
                        proposed = true
                        break
                    }

                    direction = direction.next()
                } while (direction != firstDirection)

                if (!proposed) {
                    nextElves += elf
                }
            }

            // part 2
            for ((elf, proposal) in elfToProposal) {
                nextElves += if (proposalToElves[proposal]!!.size == 1) {
                    proposal
                } else {
                    elf
                }
            }

            // prepare for next round
            firstDirection = firstDirection.next()
            elves = nextElves
        }

        val x0 = elves.minOf(Vector2::x)
        val x1 = elves.maxOf(Vector2::x)
        val y0 = elves.minOf(Vector2::y)
        val y1 = elves.maxOf(Vector2::y)

        var empty = 0

        for (x in x0..x1) {
            for (y in y0..y1) {
                if (Vector2(x, y) !in elves) {
                    empty++
                }
            }
        }

        return empty
    }
}
