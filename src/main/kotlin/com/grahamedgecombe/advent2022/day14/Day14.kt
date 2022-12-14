package com.grahamedgecombe.advent2022.day14

import com.grahamedgecombe.advent2022.Puzzle
import com.grahamedgecombe.advent2022.util.Vector2

object Day14 : Puzzle<List<Day14.Path>>(14) {
    data class Path(val points: List<Vector2>) {
        companion object {
            fun parse(s: String): Path {
                val points = mutableListOf<Vector2>()

                for (p in s.split(" -> ")) {
                    val (x, y) = p.split(',', limit = 2)
                    points += Vector2(x.toInt(), y.toInt())
                }

                return Path(points)
            }
        }
    }

    enum class Tile {
        AIR,
        ROCK,
        SAND
    }

    class Grid private constructor(
        val maxY: Int,
        private val floor: Boolean
    ) {
        private val tiles = mutableMapOf<Vector2, Tile>()

        operator fun get(x: Int, y: Int): Tile {
            if (floor && y == (maxY + 2)) {
                return Tile.ROCK
            }

            return tiles[Vector2(x, y)] ?: Tile.AIR
        }

        operator fun set(x: Int, y: Int, tile: Tile) {
            tiles[Vector2(x, y)] = tile
        }

        private fun setLine(src: Vector2, dest: Vector2, tile: Tile) {
            when {
                src.x == dest.x -> {
                    val y0 = minOf(src.y, dest.y)
                    val y1 = maxOf(src.y, dest.y)

                    for (y in y0..y1) {
                        set(src.x, y, tile)
                    }
                }

                src.y == dest.y -> {
                    val x0 = minOf(src.x, dest.x)
                    val x1 = maxOf(src.x, dest.x)

                    for (x in x0..x1) {
                        set(x, src.y, tile)
                    }
                }

                else -> throw IllegalArgumentException()
            }
        }

        companion object {
            fun create(paths: List<Path>, floor: Boolean): Grid {
                val maxY = paths.maxOf { path -> path.points.maxOf(Vector2::y) }
                val grid = Grid(maxY, floor)

                for (path in paths) {
                    for (pair in path.points.zipWithNext()) {
                        grid.setLine(pair.first, pair.second, Tile.ROCK)
                    }
                }

                return grid
            }
        }
    }

    override fun parse(input: Sequence<String>): List<Path> {
        return input.map(Path::parse).toList()
    }

    private fun solve(input: List<Path>, floor: Boolean): Int {
        val grid = Grid.create(input, floor)
        var units = 0

        outer@while (true) {
            var x = 500
            var y = 0

            if (floor && grid[x, y] == Tile.SAND) {
                break
            }

            while (true) {
                if (!floor && y > grid.maxY) {
                    break@outer
                } else if (grid[x, y + 1] == Tile.AIR) {
                    y++
                } else if (grid[x - 1, y + 1] == Tile.AIR) {
                    x--
                    y++
                } else if (grid[x + 1, y + 1] == Tile.AIR) {
                    x++
                    y++
                } else {
                    grid[x, y] = Tile.SAND
                    units++
                    break
                }
            }
        }

        return units
    }

    override fun solvePart1(input: List<Path>): Int {
        return solve(input, floor = false)
    }

    override fun solvePart2(input: List<Path>): Int {
        return solve(input, floor = true)
    }
}
