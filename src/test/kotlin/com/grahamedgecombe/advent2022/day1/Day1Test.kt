package com.grahamedgecombe.advent2022.day1

import kotlin.test.Test
import kotlin.test.assertEquals

class Day1Test {
    @Test
    fun testPart1() {
        assertEquals(24000, Day1.solvePart1(TEST_INPUT))
        assertEquals(69177, Day1.solvePart1(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day1.parse("""
            1000
            2000
            3000
            
            4000
            
            5000
            6000
            
            7000
            8000
            9000
            
            10000
        """.trimIndent())
        private val PROD_INPUT = Day1.parse()
    }
}
