package com.grahamedgecombe.advent2022.day1

import com.grahamedgecombe.advent2022.Puzzle

object Day1 : Puzzle<List<Day1.Elf>>(1) {
    data class Elf(val items: List<Int>) {
        val total = items.sum()
    }

    override fun parse(input: Sequence<String>): List<Elf> {
        val elves = mutableListOf<Elf>()
        var items = mutableListOf<Int>()

        for (s in input) {
            if (s.isEmpty()) {
                elves += Elf(items)
                items = mutableListOf()
            } else {
                items += s.toInt()
            }
        }

        if (items.isNotEmpty()) {
            elves += Elf(items)
        }

        return elves
    }

    override fun solvePart1(input: List<Elf>): Int {
        return input.maxOf(Elf::total)
    }
}
