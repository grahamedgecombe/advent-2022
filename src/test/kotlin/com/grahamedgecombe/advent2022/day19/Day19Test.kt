package com.grahamedgecombe.advent2022.day19

import kotlin.test.Test
import kotlin.test.assertEquals

class Day19Test {
    @Test
    fun testPart1() {
        assertEquals(33, Day19.solvePart1(TEST_INPUT))
        assertEquals(1092, Day19.solvePart1(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day19.parse("""
            Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.
            Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.
        """.trimIndent())
        private val PROD_INPUT = Day19.parse()
    }
}
