package com.grahamedgecombe.advent2022.day14

import kotlin.test.Test
import kotlin.test.assertEquals

class Day14Test {
    @Test
    fun testPart1() {
        assertEquals(24, Day14.solvePart1(TEST_INPUT))
        assertEquals(1061, Day14.solvePart1(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day14.parse("""
            498,4 -> 498,6 -> 496,6
            503,4 -> 502,4 -> 502,9 -> 494,9
        """.trimIndent())
        private val PROD_INPUT = Day14.parse()
    }
}
