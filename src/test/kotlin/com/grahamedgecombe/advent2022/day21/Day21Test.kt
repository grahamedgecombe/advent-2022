package com.grahamedgecombe.advent2022.day21

import kotlin.test.Test
import kotlin.test.assertEquals

class Day21Test {
    @Test
    fun testPart1() {
        assertEquals(152, Day21.solvePart1(TEST_INPUT))
        assertEquals(194501589693264, Day21.solvePart1(PROD_INPUT))
    }

    @Test
    fun testPart2() {
        assertEquals(301, Day21.solvePart2(TEST_INPUT))
        assertEquals(3887609741189, Day21.solvePart2(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day21.parse("""
            root: pppw + sjmn
            dbpl: 5
            cczh: sllz + lgvd
            zczc: 2
            ptdq: humn - dvpt
            dvpt: 3
            lfqf: 4
            humn: 5
            ljgn: 2
            sjmn: drzm * dbpl
            sllz: 4
            pppw: cczh / lfqf
            lgvd: ljgn * ptdq
            drzm: hmdt - zczc
            hmdt: 32
        """.trimIndent())
        private val PROD_INPUT = Day21.parse()
    }
}
