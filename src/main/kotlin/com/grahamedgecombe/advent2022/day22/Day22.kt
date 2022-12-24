package com.grahamedgecombe.advent2022.day22

import com.grahamedgecombe.advent2022.Puzzle
import com.grahamedgecombe.advent2022.UnsolvableException
import com.grahamedgecombe.advent2022.util.Vector2
import kotlin.math.roundToInt
import kotlin.math.sqrt

object Day22 : Puzzle<Day22.Input>(22) {
    data class Input(val grid: Grid, val instructions: List<Insn>)

    class Grid private constructor(val width: Int, val height: Int, private val tiles: CharArray) {
        val length: Int = sqrt(tiles.count { it != ' ' } / 6.0).roundToInt()
        val graph: Map<Pair<Vector2, Direction>, Pair<Vector2, Direction>> = createCubeGraph()

        private fun createCubeGraph(): Map<Pair<Vector2, Direction>, Pair<Vector2, Direction>> {
            val graph = mutableMapOf<Pair<Vector2, Direction>, Pair<Vector2, Direction>>()

            // adjacent sides
            val start = start()
            val queue = ArrayDeque<Vector2>()
            queue += Vector2(start.x / length, start.y / length)

            val seen = mutableSetOf<Vector2>()

            while (true) {
                val side = queue.removeFirstOrNull() ?: break
                if (!seen.add(side)) {
                    continue
                }

                for (edge in Direction.values()) {
                    val x = (side.x + edge.vector.x) * length
                    val y = (side.y + edge.vector.y) * length

                    if (x !in 0 until width || y !in 0 until height || this[x, y] == ' ') {
                        continue
                    }

                    val neighbour = Vector2(x / length, y / length)
                    graph[Pair(side, edge)] = Pair(neighbour, edge.opposite())

                    if (neighbour !in seen) {
                        queue += neighbour
                    }
                }
            }

            // fold corners together
            val sides = graph.values.map { it.first }.distinct()
            while (graph.size < 24) {
                for (side in sides) {
                    for (edge in Direction.values()) {
                        /*
                         *
                         * edge ---->
                         *
                         * +---+---+
                         * | s | 1 |
                         * +---+---+
                         * | 2 |
                         * +---+
                         *
                         */
                        val side1 = graph[Pair(side, edge)]
                        val side2 = graph[Pair(side, edge.right())]
                        if (side1 == null || side2 == null) {
                            continue
                        }

                        val pair1 = Pair(side1.first, side1.second.left())
                        val pair2 = Pair(side2.first, side2.second.right())
                        graph[pair1] = pair2
                        graph[pair2] = pair1
                    }
                }
            }

            return graph
        }

        fun start(): Vector2 {
            for (x in 0 until width) {
                val c = get(x, 0)
                if (c == '.') {
                    return Vector2(x, 0)
                }
            }

            throw UnsolvableException()
        }

        operator fun contains(v: Vector2): Boolean {
            return v.x in 0 until width && v.y in 0 until height
        }

        operator fun get(v: Vector2): Char {
            return get(v.x, v.y)
        }

        operator fun get(x: Int, y: Int): Char {
            require(x in 0 until width && y in 0 until height)
            return tiles[y * width + x]
        }

        companion object {
            fun parse(input: Iterator<String>): Grid {
                val lines = mutableListOf<String>()

                for (s in input) {
                    if (s.isEmpty()) {
                        break
                    }

                    lines += s
                }

                val height = lines.size
                val width = lines.maxOf(String::length)

                val tiles = CharArray(width * height)

                var index = 0
                for (line in lines) {
                    for (x in 0 until width) {
                        tiles[index++] = if (x in line.indices) {
                            line[x]
                        } else {
                            ' '
                        }
                    }
                }

                return Grid(width, height, tiles)
            }
        }
    }

    sealed interface Insn {
        data class Move(val n: Int) : Insn
        object TurnLeft : Insn
        object TurnRight : Insn

        companion object {
            fun parse(s: String): List<Insn> {
                val insns = mutableListOf<Insn>()
                var n = 0

                for (c in s) {
                    if (c in '0'..'9') {
                        n = (n * 10) + (c.code - '0'.code)
                    } else if (c == 'L' || c == 'R') {
                        if (n != 0) {
                            insns += Move(n)
                            n = 0
                        }

                        if (c == 'L') {
                            insns += TurnLeft
                        } else {
                            insns += TurnRight
                        }
                    } else {
                        throw IllegalArgumentException()
                    }
                }

                if (n != 0) {
                    insns += Move(n)
                }

                return insns
            }
        }
    }

    enum class Direction(val vector: Vector2) {
        RIGHT(Vector2(1, 0)),
        DOWN(Vector2(0, 1)),
        LEFT(Vector2(-1, 0)),
        UP(Vector2(0, -1));

        fun left(): Direction {
            val values = Direction.values()
            return values[(ordinal + values.size - 1) % values.size]
        }

        fun right(): Direction {
            val values = Direction.values()
            return values[(ordinal + 1) % values.size]
        }

        fun opposite(): Direction {
             val values = Direction.values()
            return values[(ordinal + 2) % values.size]
        }
    }

    override fun parse(input: Sequence<String>): Input {
        val it = input.iterator()

        val grid = Grid.parse(it)

        require(it.hasNext())
        val instructions = Insn.parse(it.next())

        require(!it.hasNext())

        return Input(grid, instructions)
    }

    private fun solve(input: Input, move: (Grid, Vector2, Direction) -> Pair<Vector2, Direction>): Int {
        var position = input.grid.start()
        var direction = Direction.RIGHT

        for (insn in input.instructions) {
            when (insn) {
                is Insn.Move -> {
                    for (step in 0 until insn.n) {
                        val (nextPosition, nextDirection) = move(input.grid, position, direction)
                        if (input.grid[nextPosition] == '.') {
                            position = nextPosition
                            direction = nextDirection
                        }
                    }
                }

                is Insn.TurnLeft -> direction = direction.left()
                is Insn.TurnRight -> direction = direction.right()
            }
        }

        return 1000 * (position.y + 1) + 4 * (position.x + 1) + direction.ordinal
    }

    private fun movePart1(grid: Grid, position: Vector2, direction: Direction): Pair<Vector2, Direction> {
        var x = position.x
        var y = position.y

        do {
            x = (x + direction.vector.x + grid.width) % grid.width
            y = (y + direction.vector.y + grid.height) % grid.height
        } while (grid[x, y] == ' ')

        return Pair(Vector2(x, y), direction)
    }

    private fun movePart2(grid: Grid, position: Vector2, direction: Direction): Pair<Vector2, Direction> {
        var nextPosition = position + direction.vector
        if (nextPosition in grid && grid[nextPosition] != ' ') {
            return Pair(nextPosition, direction)
        }

        val sideX = position.x / grid.length
        val sideY = position.y / grid.length

        val interiorX = position.x % grid.length
        val interiorY = position.y % grid.length

        val (neighbour, edge) = grid.graph[Pair(Vector2(sideX, sideY), direction)]!!

        nextPosition = Vector2(
            neighbour.x * grid.length,
            neighbour.y * grid.length,
        ) + when (direction) {
            Direction.RIGHT -> when (edge) {
                Direction.RIGHT -> Vector2(grid.length - 1, grid.length - interiorY - 1)
                Direction.DOWN -> Vector2(interiorY, grid.length - 1)
                Direction.LEFT -> Vector2(0, interiorY)
                Direction.UP -> Vector2(grid.length - interiorY - 1, 0)
            }
            Direction.DOWN -> when (edge) {
                Direction.RIGHT -> Vector2(grid.length - 1, interiorX)
                Direction.DOWN -> Vector2(grid.length - interiorX - 1, grid.length - 1)
                Direction.LEFT -> Vector2(0, grid.length - interiorX - 1)
                Direction.UP -> Vector2(interiorX, 0)
            }
            Direction.LEFT -> when (edge) {
                Direction.RIGHT -> Vector2(grid.length - 1, interiorY)
                Direction.DOWN -> Vector2(grid.length - interiorY - 1, grid.length - 1)
                Direction.LEFT -> Vector2(0, grid.length - interiorY - 1)
                Direction.UP -> Vector2(interiorY, 0)
            }
            Direction.UP -> when (edge) {
                Direction.RIGHT -> Vector2(grid.length - 1, grid.length - interiorX - 1)
                Direction.DOWN -> Vector2(interiorX, grid.length - 1)
                Direction.LEFT -> Vector2(0, interiorX)
                Direction.UP -> Vector2(grid.length - interiorX - 1, 0)
            }
        }

        return Pair(nextPosition, edge.opposite())
    }

    override fun solvePart1(input: Input): Int {
        return solve(input, ::movePart1)
    }

    override fun solvePart2(input: Input): Int {
        return solve(input, ::movePart2)
    }
}
