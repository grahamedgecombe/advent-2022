package com.grahamedgecombe.advent2022

import com.grahamedgecombe.advent2022.day1.Day1
import com.grahamedgecombe.advent2022.day2.Day2
import com.grahamedgecombe.advent2022.day3.Day3
import com.grahamedgecombe.advent2022.day4.Day4
import com.grahamedgecombe.advent2022.day5.Day5
import com.grahamedgecombe.advent2022.day6.Day6
import com.grahamedgecombe.advent2022.day7.Day7
import com.grahamedgecombe.advent2022.day8.Day8
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@ExperimentalTime
fun main() {
    val puzzles = listOf<Puzzle<*>>(
        Day1,
        Day2,
        Day3,
        Day4,
        Day5,
        Day6,
        Day7,
        Day8,
    )

    for (puzzle in puzzles) {
        solve(puzzle)
    }
}

@ExperimentalTime
private fun <T> solve(puzzle: Puzzle<T>) {
    val input = puzzle.parse()

    val solutionPart1 = measureTimedValue {
        puzzle.solvePart1(input)
    }
    printSolution(puzzle.number, 1, solutionPart1.value, solutionPart1.duration)

    val solutionPart2 = measureTimedValue {
        puzzle.solvePart2(input)
    }
    if (solutionPart2.value != null) {
        printSolution(puzzle.number, 2, solutionPart2.value!!, solutionPart2.duration)
    }
}

private fun printSolution(day: Int, part: Int, solution: Any, duration: Duration) {
    val value = solution.toString()
    if (value.contains('\n')) {
        println("Day $day Part $part: ($duration)")
        println(value.trim())
    } else {
        println("Day $day Part $part: $value ($duration)")
    }
}
