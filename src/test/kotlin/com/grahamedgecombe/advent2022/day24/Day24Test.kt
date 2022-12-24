package com.grahamedgecombe.advent2022.day24

import kotlin.test.Test
import kotlin.test.assertEquals

class Day24Test {
    @Test
    fun testPart1() {
        assertEquals(18, Day24.solvePart1(TEST_INPUT))
        assertEquals(311, Day24.solvePart1(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day24.parse("""
            #.######
            #>>.<^<#
            #.<..<<#
            #>v.><>#
            #<^v^^>#
            ######.#
        """.trimIndent())
        private val PROD_INPUT = Day24.parse()
    }
}
