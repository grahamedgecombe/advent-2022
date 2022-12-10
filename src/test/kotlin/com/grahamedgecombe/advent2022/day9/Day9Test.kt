package com.grahamedgecombe.advent2022.day9

import kotlin.test.Test
import kotlin.test.assertEquals

class Day9Test {
    @Test
    fun testPart1() {
        assertEquals(13, Day9.solvePart1(TEST_INPUT_1))
        assertEquals(6464, Day9.solvePart1(PROD_INPUT))
    }

    @Test
    fun testPart2() {
        assertEquals(1, Day9.solvePart2(TEST_INPUT_1))
        assertEquals(36, Day9.solvePart2(TEST_INPUT_2))
        assertEquals(2604, Day9.solvePart2(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT_1 = Day9.parse("""
            R 4
            U 4
            L 3
            D 1
            R 4
            D 1
            L 5
            R 2
        """.trimIndent())
        private val TEST_INPUT_2 = Day9.parse("""
            R 5
            U 8
            L 8
            D 3
            R 17
            D 10
            L 25
            U 20
        """.trimIndent())
        private val PROD_INPUT = Day9.parse()
    }
}
