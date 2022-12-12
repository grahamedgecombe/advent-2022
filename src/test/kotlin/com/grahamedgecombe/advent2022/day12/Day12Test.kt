package com.grahamedgecombe.advent2022.day12

import kotlin.test.Test
import kotlin.test.assertEquals

class Day12Test {
    @Test
    fun testPart1() {
        assertEquals(31, Day12.solvePart1(TEST_INPUT))
        assertEquals(497, Day12.solvePart1(PROD_INPUT))
    }

    @Test
    fun testPart2() {
        assertEquals(29, Day12.solvePart2(TEST_INPUT))
        assertEquals(492, Day12.solvePart2(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day12.parse("""
            Sabqponm
            abcryxxl
            accszExk
            acctuvwj
            abdefghi
        """.trimIndent())
        private val PROD_INPUT = Day12.parse()
    }
}
