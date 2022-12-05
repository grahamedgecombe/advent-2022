package com.grahamedgecombe.advent2022.day5

import kotlin.test.Test
import kotlin.test.assertEquals

class Day5Test {
    @Test
    fun testPart1() {
        assertEquals("CMZ", Day5.solvePart1(TEST_INPUT))
        assertEquals("SPFMVDTZT", Day5.solvePart1(PROD_INPUT))
    }

    @Test
    fun testPart2() {
        assertEquals("MCD", Day5.solvePart2(TEST_INPUT))
        assertEquals("ZFSJBPRFP", Day5.solvePart2(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day5.parse("""
                [D]    
            [N] [C]    
            [Z] [M] [P]
             1   2   3 
            
            move 1 from 2 to 1
            move 3 from 1 to 3
            move 2 from 2 to 1
            move 1 from 1 to 2
        """.trimIndent())
        private val PROD_INPUT = Day5.parse()
    }
}
