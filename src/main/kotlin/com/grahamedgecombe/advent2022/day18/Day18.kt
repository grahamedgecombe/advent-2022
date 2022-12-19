package com.grahamedgecombe.advent2022.day18

import com.grahamedgecombe.advent2022.Puzzle
import com.grahamedgecombe.advent2022.util.Vector3

object Day18 : Puzzle<Set<Vector3>>(18) {
    override fun parse(input: Sequence<String>): Set<Vector3> {
        return input.map { s ->
            val (x, y, z) = s.split(',', limit = 3)
            Vector3(x.toInt(), y.toInt(), z.toInt())
        }.toSet()
    }

    private val Vector3.neighbours
        get() = sequence {
            yield(add(-1, 0, 0))
            yield(add(1, 0, 0))
            yield(add(0, -1, 0))
            yield(add(0, 1, 0))
            yield(add(0, 0, -1))
            yield(add(0, 0, 1))
        }

    override fun solvePart1(input: Set<Vector3>): Int {
        var area = 0

        for (v in input) {
            for (n in v.neighbours) {
                if (n !in input) {
                    area++
                }
            }
        }

        return area
    }
}
