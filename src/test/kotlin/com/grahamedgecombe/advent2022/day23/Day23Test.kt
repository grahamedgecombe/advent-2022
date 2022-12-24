package com.grahamedgecombe.advent2022.day23

import kotlin.test.Test
import kotlin.test.assertEquals

class Day23Test {
    @Test
    fun testPart1() {
        assertEquals(110, Day23.solvePart1(TEST_INPUT))
        assertEquals(4034, Day23.solvePart1(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day23.parse("""
            ....#..
            ..###.#
            #...#.#
            .#...##
            #.###..
            ##.#.##
            .#..#..
        """.trimIndent())
        private val PROD_INPUT = Day23.parse()
    }
}
