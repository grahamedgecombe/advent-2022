package com.grahamedgecombe.advent2022.day18

import kotlin.test.Test
import kotlin.test.assertEquals

class Day18Test {
    @Test
    fun testPart1() {
        assertEquals(10, Day18.solvePart1(TEST_INPUT_1))
        assertEquals(64, Day18.solvePart1(TEST_INPUT_2))
        assertEquals(4370, Day18.solvePart1(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT_1 = Day18.parse("""
            1,1,1
            2,1,1
        """.trimIndent())
        private val TEST_INPUT_2 = Day18.parse("""
            2,2,2
            1,2,2
            3,2,2
            2,1,2
            2,3,2
            2,2,1
            2,2,3
            2,2,4
            2,2,6
            1,2,5
            3,2,5
            2,1,5
            2,3,5
        """.trimIndent())
        private val PROD_INPUT = Day18.parse()
    }
}
