package com.grahamedgecombe.advent2022.day20

import kotlin.test.Test
import kotlin.test.assertEquals

class Day20Test {
    @Test
    fun testPart1() {
        assertEquals(3, Day20.solvePart1(TEST_INPUT))
        assertEquals(13883, Day20.solvePart1(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day20.parse("""
            1
            2
            -3
            3
            -2
            0
            4
        """.trimIndent())
        private val PROD_INPUT = Day20.parse()
    }
}
