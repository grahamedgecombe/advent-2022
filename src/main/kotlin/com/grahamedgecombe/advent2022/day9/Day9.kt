package com.grahamedgecombe.advent2022.day9

import com.grahamedgecombe.advent2022.Puzzle
import com.grahamedgecombe.advent2022.util.Vector2
import kotlin.math.abs
import kotlin.math.sign

object Day9 : Puzzle<List<Day9.Motion>>(9) {
    enum class Direction {
        UP, LEFT, RIGHT, DOWN;

        companion object {
            fun parse(s: String): Direction {
                return when (s) {
                    "U" -> UP
                    "L" -> LEFT
                    "R" -> RIGHT
                    "D" -> DOWN
                    else -> throw IllegalArgumentException()
                }
            }
        }
    }

    data class Motion(val direction: Direction, val steps: Int) {
        fun translate(v: Vector2): Vector2 {
            return when (direction) {
                Direction.UP -> v.add(0, steps)
                Direction.LEFT -> v.add(-steps, 0)
                Direction.RIGHT -> v.add(steps, 0)
                Direction.DOWN -> v.add(0, -steps)
            }
        }

        companion object {
            fun parse(s: String): Motion {
                val (direction, steps) = s.split(' ', limit = 2)
                return Motion(Direction.parse(direction), steps.toInt())
            }
        }
    }

    override fun parse(input: Sequence<String>): List<Motion> {
        return input.map(Motion::parse).toList()
    }

    override fun solvePart1(input: List<Motion>): Int {
        return solve(input, 2)
    }

    override fun solvePart2(input: List<Motion>): Int {
        return solve(input, 10)
    }

    private fun solve(motions: List<Motion>, len: Int): Int {
        require(len >= 1)

        val rope = Array(len) { Vector2.ZERO }
        val visited = mutableSetOf(rope.last())

        for (motion in motions) {
            rope[0] = motion.translate(rope[0])

            do {
                var changed = false

                for (i in 0 until rope.size - 1) {
                    val a = rope[i]
                    val b = rope[i + 1]

                    val next = follow(a, b)

                    if (b != next) {
                        rope[i + 1] = next
                        changed = true

                        if (i + 1 == rope.size - 1) {
                            visited += next
                        }
                    } else {
                        break
                    }
                }
            } while (changed)
        }

        return visited.size
    }

    private fun follow(a: Vector2, b: Vector2): Vector2 {
        val dx = a.x - b.x
        val dy = a.y - b.y

        if (dx == 0) {
            if (dy < -1 || dy > 1) {
                return b.add(0, dy.sign)
            }
        } else if (dy == 0) {
            if (dx < -1 || dx > 1) {
                return b.add(dx.sign, 0)
            }
        } else if (abs(dx) > 1 || abs(dy) > 1) {
            return b.add(dx.sign, dy.sign)
        }

        return b
    }
}
