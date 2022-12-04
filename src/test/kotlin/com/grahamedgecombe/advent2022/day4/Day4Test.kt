package com.grahamedgecombe.advent2022.day4

import kotlin.test.Test
import kotlin.test.assertEquals

class Day4Test {
    @Test
    fun testPart1() {
        assertEquals(2, Day4.solvePart1(TEST_INPUT))
        assertEquals(448, Day4.solvePart1(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day4.parse("""
            2-4,6-8
            2-3,4-5
            5-7,7-9
            2-8,3-7
            6-6,4-6
            2-6,4-8
        """.trimIndent())
        private val PROD_INPUT = Day4.parse()
    }
}
