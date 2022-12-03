package com.grahamedgecombe.advent2022.day3

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day3Test {
    @Test
    fun testPart1() {
        assertEquals(157, Day3.solvePart1(TEST_INPUT))
        assertEquals(8105, Day3.solvePart1(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day3.parse("""
            vJrwpWtwJgWrhcsFMMfFFhFp
            jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
            PmmdzqPrVvPwwTWBwg
            wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
            ttgJtRGJQctTZtZT
            CrZsJsPPZsGzwwsLwLmpwMDw
        """.trimIndent())
        private val PROD_INPUT = Day3.parse()
    }
}
