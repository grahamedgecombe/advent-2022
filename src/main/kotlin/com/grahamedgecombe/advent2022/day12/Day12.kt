package com.grahamedgecombe.advent2022.day12

import com.grahamedgecombe.advent2022.Puzzle
import com.grahamedgecombe.advent2022.util.AStar
import com.grahamedgecombe.advent2022.util.Vector2
import kotlin.math.abs

object Day12 : Puzzle<Day12.Grid>(12) {
    class Grid private constructor(
        private val heights: CharArray,
        val width: Int,
        val height: Int,
        val start: Vector2,
        val end: Vector2
    ) {
        operator fun get(v: Vector2): Char {
            require(v.x in 0 until width)
            require(v.y in 0 until height)

            return heights[v.y * width + v.x]
        }

        companion object {
            fun parse(input: List<String>): Grid {
                require(input.isNotEmpty())

                val height = input.size
                val width = input.first().length

                val heights = CharArray(width * height)

                var index = 0
                var start: Vector2? = null
                var end: Vector2? = null

                for ((y, row) in input.withIndex()) {
                    for ((x, c) in row.withIndex()) {
                        when (c) {
                            'S' -> {
                                start = Vector2(x, y)
                                heights[index++] = 'a'
                            }
                            'E' -> {
                                end = Vector2(x, y)
                                heights[index++] = 'z'
                            }
                            else -> heights[index++] = c
                        }
                    }
                }

                if (start == null || end == null) {
                    throw IllegalArgumentException()
                }

                return Grid(heights, width, height, start, end)
            }
        }
    }

    data class Node(val grid: Grid, val position: Vector2) : AStar.Node<Node> {
        override val isGoal: Boolean
            get() = position == grid.end

        override val neighbours: Sequence<AStar.Neighbour<Node>>
            get() = sequence {
                val current = grid[position]

                for (dy in -1..1) {
                    for (dx in -1..1) {
                        if (dx == 0 && dy == 0) {
                            continue
                        } else if (dx != 0 && dy != 0) {
                            continue
                        }

                        val nextPosition = position.add(dx, dy)
                        if (nextPosition.x !in 0 until grid.width) {
                            continue
                        } else if (nextPosition.y !in 0 until grid.height) {
                            continue
                        }

                        val next = grid[nextPosition]

                        val diff = next.code - current.code
                        if (diff > 1) {
                            continue
                        }

                        yield(AStar.Neighbour(Node(grid, nextPosition), 1))
                    }
                }
            }

        override val cost: Int
            get() = abs(position.x - grid.end.x) + abs(position.y - grid.end.y)
    }

    override fun parse(input: Sequence<String>): Grid {
        return Grid.parse(input.toList())
    }

    override fun solvePart1(input: Grid): Int {
        return AStar.search(Node(input, input.start)).first().distance
    }
}
