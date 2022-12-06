package com.grahamedgecombe.advent2022.day6

import kotlin.test.Test
import kotlin.test.assertEquals

class Day6Test {
    @Test
    fun testPart1() {
        assertEquals(7, Day6.solvePart1(TEST_INPUT_1))
        assertEquals(5, Day6.solvePart1(TEST_INPUT_2))
        assertEquals(6, Day6.solvePart1(TEST_INPUT_3))
        assertEquals(10, Day6.solvePart1(TEST_INPUT_4))
        assertEquals(11, Day6.solvePart1(TEST_INPUT_5))
        assertEquals(1816, Day6.solvePart1(PROD_INPUT))
    }

    private companion object {
        private const val TEST_INPUT_1 = "mjqjpqmgbljsphdztnvjfqwrcgsmlb"
        private const val TEST_INPUT_2 = "bvwbjplbgvbhsrlpgdmjqwftvncz"
        private const val TEST_INPUT_3 = "nppdvjthqldpwncqszvftbrmjlhg"
        private const val TEST_INPUT_4 = "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"
        private const val TEST_INPUT_5 = "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"
        private val PROD_INPUT = Day6.parse()
    }
}
