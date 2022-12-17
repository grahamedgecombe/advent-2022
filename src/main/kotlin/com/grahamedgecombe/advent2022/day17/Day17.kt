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

    class Tower(private val pattern: String) {
        private val rows = mutableListOf<Int>()
        private var shapeIndex = 0
        private var patternIndex = 0

        val height
            get() = rows.size

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

    override fun solvePart1(input: String): Int {
        val tower = Tower(input)
        for (i in 0 until 2022) {
            tower.fall()
        }
        return tower.height
    }
}
