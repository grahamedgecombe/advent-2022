package com.grahamedgecombe.advent2022.day8

import kotlin.test.Test
import kotlin.test.assertEquals

class Day8Test {
    @Test
    fun testPart1() {
        assertEquals(21, Day8.solvePart1(TEST_INPUT))
        assertEquals(1870, Day8.solvePart1(PROD_INPUT))
    }

    @Test
    fun testPart2() {
        assertEquals(8, Day8.solvePart2(TEST_INPUT))
        assertEquals(517440, Day8.solvePart2(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day8.parse("""
            30373
            25512
            65332
            33549
            35390
        """.trimIndent())
        private val PROD_INPUT = Day8.parse()
    }
}
