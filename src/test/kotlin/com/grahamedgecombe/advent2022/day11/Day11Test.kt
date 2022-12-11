package com.grahamedgecombe.advent2022.day11

import kotlin.test.Test
import kotlin.test.assertEquals

class Day11Test {
    @Test
    fun testPart1() {
        assertEquals(10605, Day11.solvePart1(TEST_INPUT))
        assertEquals(57348, Day11.solvePart1(PROD_INPUT))
    }

    @Test
    fun testPart2() {
        assertEquals(2713310158, Day11.solvePart2(TEST_INPUT))
        assertEquals(14106266886, Day11.solvePart2(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day11.parse("""
            Monkey 0:
              Starting items: 79, 98
              Operation: new = old * 19
              Test: divisible by 23
                If true: throw to monkey 2
                If false: throw to monkey 3
            
            Monkey 1:
              Starting items: 54, 65, 75, 74
              Operation: new = old + 6
              Test: divisible by 19
                If true: throw to monkey 2
                If false: throw to monkey 0
            
            Monkey 2:
              Starting items: 79, 60, 97
              Operation: new = old * old
              Test: divisible by 13
                If true: throw to monkey 1
                If false: throw to monkey 3
            
            Monkey 3:
              Starting items: 74
              Operation: new = old + 3
              Test: divisible by 17
                If true: throw to monkey 0
                If false: throw to monkey 1
        """.trimIndent())
        private val PROD_INPUT = Day11.parse()
    }
}
