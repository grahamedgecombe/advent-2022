package com.grahamedgecombe.advent2022.day25

import kotlin.test.Test
import kotlin.test.assertEquals

class Day25Test {
    @Test
    fun testPart1() {
        assertEquals("2=-1=0", Day25.solvePart1(TEST_INPUT))
        assertEquals("2=-0=1-0012-=-2=0=01", Day25.solvePart1(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day25.parse("""
            1=-0-2
            12111
            2=0=
            21
            2=01
            111
            20012
            112
            1=-1=
            1-12
            12
            1=
            122
        """.trimIndent())
        private val PROD_INPUT = Day25.parse()
    }
}
