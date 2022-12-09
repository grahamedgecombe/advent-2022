package com.grahamedgecombe.advent2022.day8

import com.grahamedgecombe.advent2022.Puzzle
import java.util.*
import kotlin.math.abs

object Day8 : Puzzle<Day8.Grid>(8) {
    class Grid private constructor(
        private val width: Int,
        private val height: Int,
        private val heights: IntArray
    ) {
        private fun get(x: Int, y: Int): Int {
            require(x in 0 until width)
            require(y in 0 until height)

            return heights[y * width + x]
        }

        fun countVisible(): Int {
            val visible = BitSet()

            for (x in 0 until width) {
                countVisibleVertical(visible, x, 0 until height)
                countVisibleVertical(visible, x, (height - 1) downTo 0)
            }

            for (y in 0 until height) {
                countVisibleHorizontal(visible, 0 until width, y)
                countVisibleHorizontal(visible, (width - 1) downTo 0, y)
            }

            return visible.cardinality()
        }

        private fun countVisibleHorizontal(visible: BitSet, xs: IntProgression, y: Int) {
            var max = Int.MIN_VALUE

            for (x in xs) {
                val tree = get(x, y)
                if (tree > max) {
                    visible.set(y * width + x)
                }

                max = maxOf(max ,tree)
            }
        }

        private fun countVisibleVertical(visible: BitSet, x: Int, ys: IntProgression) {
            var max = Int.MIN_VALUE

            for (y in ys) {
                val tree = get(x, y)
                if (tree > max) {
                    visible.set(y * width + x)
                }

                max = maxOf(max ,tree)
            }
        }

        fun maxScore(): Int {
            var max = 0

            for (x in 0 until width) {
                for (y in 0 until height) {
                    max = maxOf(max, score(x, y))
                }
            }

            return max
        }

        private fun score(x: Int, y: Int): Int {
            val treehouse = get(x, y)

            val up = verticalViewingDistance(treehouse, x, y - 1 downTo 0)
            val down = verticalViewingDistance(treehouse, x, y + 1 until height)

            val left = horizontalViewingDistance(treehouse, x - 1 downTo 0, y)
            val right = horizontalViewingDistance(treehouse, x + 1 until width, y)

            return up * down * left * right
        }

        private fun horizontalViewingDistance(treehouse: Int, xs: IntProgression, y: Int): Int {
            if (xs.isEmpty()) {
                return 0
            }

            for (x in xs) {
                val tree = get(x, y)
                if (tree >= treehouse) {
                    return abs(x - xs.first) + 1
                }
            }

            return abs(xs.last - xs.first) + 1
        }

        private fun verticalViewingDistance(treehouse: Int, x: Int, ys: IntProgression): Int {
            if (ys.isEmpty()) {
                return 0
            }

            for (y in ys) {
                val tree = get(x, y)
                if (tree >= treehouse) {
                    return abs(y - ys.first) + 1
                }
            }

            return abs(ys.last - ys.first) + 1
        }

        companion object {
            fun parse(input: List<String>): Grid {
                require(input.isNotEmpty())

                val height = input.size
                val width = input.first().length

                val heights = IntArray(width * height)

                var index = 0

                for (row in input) {
                    for (v in row) {
                        heights[index++] = v.digitToInt()
                    }
                }

                check(index == width * height)

                return Grid(width, height, heights)
            }
        }
    }

    override fun parse(input: Sequence<String>): Grid {
        return Grid.parse(input.toList())
    }

    override fun solvePart1(input: Grid): Int {
        return input.countVisible()
    }

    override fun solvePart2(input: Grid): Int {
        return input.maxScore()
    }
}
