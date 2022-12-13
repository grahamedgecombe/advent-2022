package com.grahamedgecombe.advent2022.day13

import kotlin.test.Test
import kotlin.test.assertEquals

class Day13Test {
    @Test
    fun testPart1() {
        assertEquals(13, Day13.solvePart1(TEST_INPUT))
        assertEquals(6076, Day13.solvePart1(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day13.parse("""
            [1,1,3,1,1]
            [1,1,5,1,1]

            [[1],[2,3,4]]
            [[1],4]

            [9]
            [[8,7,6]]

            [[4,4],4,4]
            [[4,4],4,4,4]

            [7,7,7,7]
            [7,7,7]

            []
            [3]

            [[[]]]
            [[]]

            [1,[2,[3,[4,[5,6,7]]]],8,9]
            [1,[2,[3,[4,[5,6,0]]]],8,9]
        """.trimIndent())
        private val PROD_INPUT = Day13.parse()
    }
}
