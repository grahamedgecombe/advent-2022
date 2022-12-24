package com.grahamedgecombe.advent2022.day24

import com.grahamedgecombe.advent2022.Puzzle
import com.grahamedgecombe.advent2022.UnsolvableException
import com.grahamedgecombe.advent2022.util.AStar
import com.grahamedgecombe.advent2022.util.Vector2
import kotlin.math.abs

object Day24 : Puzzle<Day24.Input>(24) {
    enum class Direction constructor(val vector: Vector2) {
        UP(Vector2(0, -1)),
        DOWN(Vector2(0, 1)),
        LEFT(Vector2(-1, 0)),
        RIGHT(Vector2(1, 0));

        companion object {
            fun parse(c: Char): Direction? {
                return when (c) {
                    '^' -> UP
                    'v' -> DOWN
                    '<' -> LEFT
                    '>' -> RIGHT
                    else -> null
                }
            }
        }
    }

    data class Blizzard(val position: Vector2, val direction: Direction)
    class Input(val blizzards: List<Blizzard>, val width: Int, val height: Int) {
        val start = Vector2(1, 0)
        val end = Vector2(width - 2, height - 1)
    }

    data class Node(
        val position: Vector2,
        val goal: Vector2,
        val width: Int,
        val height: Int,
        val blizzards: List<Blizzard>,
    ) : AStar.Node<Node> {
        override val isGoal: Boolean
            get() = position == goal

        override val neighbours: Sequence<AStar.Neighbour<Node>>
            get() = sequence {
                val blizzards = nextBlizzards(blizzards, width, height)

                // move
                for (move in Direction.values()) {
                    val nextPosition = position + move.vector
                    if (nextPosition == goal) {
                        // goal
                    } else if (nextPosition.x !in 1..width - 2 || nextPosition.y !in 1..height - 2) {
                        continue
                    } else if (blizzards.any { it.position == nextPosition }) {
                        continue
                    }

                    yield(AStar.Neighbour(Node(nextPosition, goal, width, height, blizzards), 1))
                }

                // wait
                if (blizzards.none { it.position == position }) {
                    yield(AStar.Neighbour(Node(position, goal, width, height, blizzards), 1))
                }
            }

        override val cost: Int
            get() = abs(position.x - goal.x) + abs(position.y - goal.y)
    }

    private fun nextBlizzards(blizzards: List<Blizzard>, width: Int, height: Int): List<Blizzard> {
        return blizzards.map { (position, direction) ->
            var x = position.x
            var y = position.y

            if (direction == Direction.LEFT && x == 1) {
                x = width - 2
            } else if (direction == Direction.RIGHT && x == width - 2) {
                x = 1
            } else if (direction == Direction.UP && y == 1) {
                y = height - 2
            } else if (direction == Direction.DOWN && y == height - 2) {
                y = 1
            } else {
                x += direction.vector.x
                y += direction.vector.y
            }

            Blizzard(Vector2(x, y), direction)
        }
    }

    override fun parse(input: Sequence<String>): Input {
        val grid = input.toList()
        require(grid.isNotEmpty())

        val blizzards = mutableListOf<Blizzard>()

        for ((y, row) in grid.withIndex()) {
            if (y == 0 || y == grid.size - 1) {
                continue
            }

            for ((x, c) in row.withIndex()) {
                if (x == 0 || x == row.length - 1) {
                    continue
                }

                val direction = Direction.parse(c)
                if (direction != null) {
                    blizzards += Blizzard(Vector2(x, y), direction)
                }
            }
        }

        val width = grid.first().length
        val height = grid.size

        return Input(blizzards, width, height)
    }

    override fun solvePart1(input: Input): Int {
        val path = AStar.search(Node(input.start, input.end, input.width, input.height, input.blizzards))
            .firstOrNull() ?: throw UnsolvableException()
        return path.distance
    }

    override fun solvePart2(input: Input): Int {
        val path1 = AStar.search(Node(input.start, input.end, input.width, input.height, input.blizzards))
            .firstOrNull() ?: throw UnsolvableException()

        val path2 = AStar.search(Node(input.end, input.start, input.width, input.height, path1.nodes.last().blizzards))
            .firstOrNull() ?: throw UnsolvableException()

        val path3 = AStar.search(Node(input.start, input.end, input.width, input.height, path2.nodes.last().blizzards))
            .firstOrNull() ?: throw UnsolvableException()

        return path1.distance + path2.distance + path3.distance
    }
}
