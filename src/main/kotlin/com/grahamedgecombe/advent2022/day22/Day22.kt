package com.grahamedgecombe.advent2022.day22

import com.grahamedgecombe.advent2022.Puzzle
import com.grahamedgecombe.advent2022.UnsolvableException
import com.grahamedgecombe.advent2022.util.Vector2

object Day22 : Puzzle<Day22.Input>(22) {
    data class Input(val grid: Grid, val instructions: List<Insn>)

    class Grid private constructor(val width: Int, val height: Int, private val tiles: CharArray) {
        fun start(): Vector2 {
            for (x in 0 until width) {
                val c = get(x, 0)
                if (c == '.') {
                    return Vector2(x, 0)
                }
            }

            throw UnsolvableException()
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
    }

    override fun parse(input: Sequence<String>): Input {
        val it = input.iterator()

        val grid = Grid.parse(it)

        require(it.hasNext())
        val instructions = Insn.parse(it.next())

        require(!it.hasNext())

        return Input(grid, instructions)
    }

    private fun move(grid: Grid, position: Vector2, direction: Direction): Vector2 {
        var x = position.x
        var y = position.y

        while (true) {
            x = (x + direction.vector.x + grid.width) % grid.width
            y = (y + direction.vector.y + grid.height) % grid.height

            val tile = grid[x, y]
            if (tile == '.') {
                return Vector2(x, y)
            } else if (tile == '#') {
                return position
            } else if (tile != ' ') {
                throw IllegalArgumentException()
            }
        }
    }

    override fun solvePart1(input: Input): Int {
        var position = input.grid.start()
        var direction = Direction.RIGHT

        for (insn in input.instructions) {
            when (insn) {
                is Insn.Move -> {
                    for (step in 0 until insn.n) {
                        position = move(input.grid, position, direction)
                    }
                }

                is Insn.TurnLeft -> direction = direction.left()
                is Insn.TurnRight -> direction = direction.right()
            }
        }

        return 1000 * (position.y + 1) + 4 * (position.x + 1) + direction.ordinal
    }
}
