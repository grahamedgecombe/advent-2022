package com.grahamedgecombe.advent2022.day2

import kotlin.test.Test
import kotlin.test.assertEquals

class Day2Test {
    @Test
    fun testPart1() {
        assertEquals(15, Day2.solvePart1(TEST_INPUT))
        assertEquals(10718, Day2.solvePart1(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day2.parse("""
            A Y
            B X
            C Z
        """.trimIndent())
        private val PROD_INPUT = Day2.parse()
    }
}
