package com.grahamedgecombe.advent2022.day9

import kotlin.test.Test
import kotlin.test.assertEquals

class Day9Test {
    @Test
    fun testPart1() {
        assertEquals(13, Day9.solvePart1(TEST_INPUT))
        assertEquals(6464, Day9.solvePart1(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day9.parse("""
            R 4
            U 4
            L 3
            D 1
            R 4
            D 1
            L 5
            R 2
        """.trimIndent())
        private val PROD_INPUT = Day9.parse()
    }
}
