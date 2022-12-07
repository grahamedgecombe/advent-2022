package com.grahamedgecombe.advent2022.day7

import kotlin.test.Test
import kotlin.test.assertEquals

class Day7Test {
    @Test
    fun testPart1() {
        assertEquals(95437, Day7.solvePart1(TEST_INPUT))
        assertEquals(1297159, Day7.solvePart1(PROD_INPUT))
    }

    private companion object {
        private val TEST_INPUT = Day7.parse("""
            ${'$'} cd /
            ${'$'} ls
            dir a
            14848514 b.txt
            8504156 c.dat
            dir d
            ${'$'} cd a
            ${'$'} ls
            dir e
            29116 f
            2557 g
            62596 h.lst
            ${'$'} cd e
            ${'$'} ls
            584 i
            ${'$'} cd ..
            ${'$'} cd ..
            ${'$'} cd d
            ${'$'} ls
            4060174 j
            8033020 d.log
            5626152 d.ext
            7214296 k
        """.trimIndent())
        private val PROD_INPUT = Day7.parse()
    }
}
