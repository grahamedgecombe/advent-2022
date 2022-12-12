package com.grahamedgecombe.advent2022.day12

import com.grahamedgecombe.advent2022.Puzzle
import com.grahamedgecombe.advent2022.util.AStar
import com.grahamedgecombe.advent2022.util.Bfs
import com.grahamedgecombe.advent2022.util.Vector2
import kotlin.math.abs

object Day12 : Puzzle<Day12.Grid>(12) {
    class Grid private constructor(
        private val heights: CharArray,
        private val width: Int,
        private val height: Int,
        val start: Vector2,
        val end: Vector2
    ) {
        operator fun contains(v: Vector2): Boolean {
            return v.x in 0 until width && v.y in 0 until height
        }

        operator fun get(v: Vector2): Char {
            require(v in this)

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

    val Vector2.neighbours: Sequence<Vector2>
        get() = sequence {
            for (dy in -1..1) {
                for (dx in -1..1) {
                    if (dx == 0 && dy == 0) {
                        continue
                    } else if (dx != 0 && dy != 0) {
                        continue
                    }

                    yield(Vector2(x + dx, y + dy))
                }
            }
        }

    data class NodePart1(val grid: Grid, val position: Vector2) : AStar.Node<NodePart1> {
        override val isGoal: Boolean
            get() = position == grid.end

        override val neighbours: Sequence<AStar.Neighbour<NodePart1>>
            get() = sequence {
                val current = grid[position]
                for (nextPosition in position.neighbours) {
                    if (nextPosition !in grid) {
                        continue
                    }

                    val next = grid[nextPosition]

                    val diff = next.code - current.code
                    if (diff > 1) {
                        continue
                    }

                    yield(AStar.Neighbour(NodePart1(grid, nextPosition), 1))
                }
            }

        override val cost: Int
            get() = abs(position.x - grid.end.x) + abs(position.y - grid.end.y)
    }

    data class NodePart2(val grid: Grid, val position: Vector2) : Bfs.Node<NodePart2> {
        override val isGoal: Boolean
            get() = grid[position] == 'a'

        override val neighbours: Sequence<NodePart2>
            get() = sequence {
                val current = grid[position]

                for (prevPosition in position.neighbours) {
                    if (prevPosition !in grid) {
                        continue
                    }

                    val prev = grid[prevPosition]

                    val diff = current.code - prev.code
                    if (diff > 1) {
                        continue
                    }

                    yield(NodePart2(grid, prevPosition))
                }
            }
    }

    override fun parse(input: Sequence<String>): Grid {
        return Grid.parse(input.toList())
    }

    override fun solvePart1(input: Grid): Int {
        return AStar.search(NodePart1(input, input.start)).first().distance
    }

    override fun solvePart2(input: Grid): Int {
        return Bfs.search(NodePart2(input, input.end)).minOf { it.size - 1 }
    }
}
