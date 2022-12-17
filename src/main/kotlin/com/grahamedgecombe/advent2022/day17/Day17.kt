package com.grahamedgecombe.advent2022.day17

import com.grahamedgecombe.advent2022.Puzzle

object Day17 : Puzzle<String>(17) {
    class Shape private constructor(val width: Int, val height: Int, private val rows: IntArray) {
        fun intersects(tower: List<Int>, bottomLeftX: Int, bottomLeftY: Int): Boolean {
            for (y in 0 until height) {
                val towerY = bottomLeftY + y
                if (towerY >= tower.size) {
                    continue
                }

                if ((rows[y] shl bottomLeftX) and tower[towerY] != 0) {
                    return true
                }
            }

            return false
        }

        fun stamp(tower: MutableList<Int>, bottomLeftX: Int, bottomLeftY: Int) {
            for (y in 0 until height) {
                val towerY = bottomLeftY + y
                while (tower.size <= towerY) {
                    tower += 0
                }

                tower[towerY] = tower[towerY] or (rows[y] shl bottomLeftX)
            }
        }

        companion object {
            fun parse(s: String): Shape {
                val lines = s.trim().split("\n")
                require(lines.isNotEmpty())

                val height = lines.size
                val width = lines.first().length

                val rows = IntArray(height)

                for ((y, line) in lines.withIndex()) {
                    var row = 0

                    for ((x, c) in line.withIndex()) {
                        if (c == '#') {
                            row = row or (1 shl x)
                        }
                    }

                    rows[height - y - 1] = row
                }

                return Shape(width, height, rows)
            }
        }
    }

    data class Key(val shapeIndex: Int, val patternIndex: Int, val rows: List<Int>)

    class Tower(private val pattern: String) {
        private val rows = mutableListOf<Int>()
        private var shapeIndex = 0
        private var patternIndex = 0

        val height
            get() = rows.size

        fun key(): Key {
            val unblockedRows = mutableListOf<Int>()

            var y = rows.size - 1
            var blocked = 0
            while (blocked != (1 shl WIDTH) - 1) {
                if (y < 0) {
                    unblockedRows += (1 shl WIDTH) - 1
                    break
                }

                blocked = blocked or rows[y]
                unblockedRows += rows[y]
                y--
            }

            return Key(shapeIndex % SHAPES.size, patternIndex % pattern.length, unblockedRows)
        }

        fun fall() {
            val shape = SHAPES[shapeIndex++ % SHAPES.size]

            var x = 2
            var y = height + 3

            while (true) {
                val direction = pattern[patternIndex++ % pattern.length]

                if (direction == '<' && x > 0 && !shape.intersects(rows, x - 1, y)) {
                    x--
                }

                if (direction == '>' && (x + shape.width) < WIDTH && !shape.intersects(rows, x + 1, y)) {
                    x++
                }

                if (y > 0 && !shape.intersects(rows, x, y - 1)) {
                    y--
                } else {
                    shape.stamp(rows, x, y)
                    break
                }
            }
        }
    }

    private const val WIDTH = 7
    private val SHAPES = listOf(
        """
            ####
        """.trimIndent(),
        """
            .#.
            ###
            .#.
        """.trimIndent(),
        """
            ..#
            ..#
            ###
        """.trimIndent(),
        """
            #
            #
            #
            #
        """.trimIndent(),
        """
            ##
            ##
        """.trimIndent(),
    ).map(Shape::parse)

    override fun parse(input: Sequence<String>): String {
        return input.single()
    }

    private fun solve(input: String, rocks: Long): Long {
        val tower = Tower(input)
        val history = mutableMapOf<Key, Pair<Long, Long>>()

        var i = 0L
        while (i < rocks) {
            val k = tower.key()

            val v = history[k]
            if (v != null) {
                val (j, h) = v

                val loops = (rocks - i) / (i - j)
                val extraHeight = loops * (tower.height - h)

                i += loops * (i - j)

                while (i < rocks) {
                    tower.fall()
                    i++
                }

                return tower.height + extraHeight
            }

            history[k] = Pair(i, tower.height.toLong())

            tower.fall()
            i++
        }

        return tower.height.toLong()
    }

    override fun solvePart1(input: String): Long {
        return solve(input, 2022)
    }

    override fun solvePart2(input: String): Long {
        return solve(input, 1000000000000)
    }
}
