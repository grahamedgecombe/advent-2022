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

    override fun solvePart2(input: Set<Vector3>): Int {
        val x0 = input.minOf(Vector3::x) - 1
        val x1 = input.maxOf(Vector3::x) + 1

        val y0 = input.minOf(Vector3::y) - 1
        val y1 = input.maxOf(Vector3::y) + 1

        val z0 = input.minOf(Vector3::z) - 1
        val z1 = input.maxOf(Vector3::z) + 1

        val start = Vector3(x0, y0, z0)

        val exterior = mutableSetOf<Vector3>()
        exterior += start

        val queue = ArrayDeque<Vector3>()
        queue += start

        while (true) {
            val v = queue.removeFirstOrNull() ?: break

            for (n in v.neighbours) {
                if (n.x !in x0..x1 || n.y !in y0..y1 || n.z !in z0..z1) {
                    continue
                } else if (n in input || n in exterior) {
                    continue
                }

                queue += n
                exterior += n
            }
        }

        var area = 0

        for (v in input) {
            for (n in v.neighbours) {
                if (n !in input && n in exterior) {
                    area++
                }
            }
        }

        return area
    }
}
