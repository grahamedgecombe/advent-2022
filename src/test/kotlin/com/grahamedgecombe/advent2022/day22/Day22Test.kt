package com.grahamedgecombe.advent2022.day22

import kotlin.test.Test
import kotlin.test.assertEquals

class Day22Test {
    @Test
    fun testPart1() {
        assertEquals(6032, Day22.solvePart1(TEST_INPUT))
        assertEquals(31568, Day22.solvePart1(PROD_INPUT))
    }

    @Test
    fun testPart2() {
        assertEquals(5031, Day22.solvePart2(TEST_INPUT))
        assertEquals(36540, Day22.solvePart2(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day22.parse("""
                    ...#
                    .#..
                    #...
                    ....
            ...#.......#
            ........#...
            ..#....#....
            ..........#.
                    ...#....
                    .....#..
                    .#......
                    ......#.
            
            10R5L5R10L4R5L5
        """.trimIndent())
        private val PROD_INPUT = Day22.parse()
    }
}
