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
        var head = Vector2.ZERO
        var tail = Vector2.ZERO

        val visited = mutableSetOf(tail)

        for (motion in input) {
            head = motion.translate(head)

            while (true) {
                val dx = head.x - tail.x
                val dy = head.y - tail.y

                if (dx == 0) {
                    if (dy < -1 || dy > 1) {
                        tail = tail.add(0, dy.sign)
                    } else {
                        break
                    }
                } else if (dy == 0) {
                    if (dx < -1 || dx > 1) {
                        tail = tail.add(dx.sign, 0)
                    } else {
                        break
                    }
                } else if (abs(dx) <= 1 && abs(dy) <= 1) {
                    break
                } else {
                    tail = tail.add(dx.sign, dy.sign)
                }

                visited += tail
            }
        }

        return visited.size
    }
}
