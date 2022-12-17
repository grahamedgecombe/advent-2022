package com.grahamedgecombe.advent2022.day17

import kotlin.test.Test
import kotlin.test.assertEquals

class Day17Test {
    @Test
    fun testPart1() {
        assertEquals(3068, Day17.solvePart1(TEST_INPUT))
        assertEquals(3153, Day17.solvePart1(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day17.parse(">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>")
        private val PROD_INPUT = Day17.parse()
    }
}
