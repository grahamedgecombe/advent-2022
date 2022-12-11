package com.grahamedgecombe.advent2022.day11

import com.grahamedgecombe.advent2022.Puzzle

object Day11 : Puzzle<List<Day11.Monkey>>(11) {
    sealed interface Operand {
        object Old : Operand
        data class Constant(val n: Int): Operand

        companion object {
            fun parse(s: String): Operand {
                return if (s == "old") {
                    Old
                } else {
                    Constant(s.toInt())
                }
            }
        }
    }

    enum class Operator {
        ADD,
        MULTIPLY;

        companion object {
            fun parse(s: String): Operator {
                return when (s) {
                    "+" -> ADD
                    "*" -> MULTIPLY
                    else -> throw IllegalArgumentException()
                }
            }
        }
    }

    data class Monkey(
        val items: List<Int>,
        val operator: Operator,
        val rightOperand: Operand,
        val divisor: Int,
        val trueMonkey: Int,
        val falseMonkey: Int
    ) {
        fun inspect(worry: Int): Int {
            val operand = when (rightOperand) {
                is Operand.Old -> worry
                is Operand.Constant -> rightOperand.n
            }

            return when (operator) {
                Operator.ADD -> worry + operand
                Operator.MULTIPLY -> worry * operand
            }
        }

        companion object {
            private const val ITEMS_PREFIX = "  Starting items: "
            private const val OPERATION_PREFIX = "  Operation: new = old "
            private const val TEST_PREFIX = "  Test: divisible by "
            private const val TRUE_MONKEY_PREFIX = "    If true: throw to monkey "
            private const val FALSE_MONKEY_PREFIX = "    If false: throw to monkey "

            fun parse(input: List<String>): Monkey {
                require(input.size == 6)

                val items = parseItems(input[1])
                val (operator, rightOperand) = parseOperation(input[2])
                val divisor = parseInt(TEST_PREFIX, input[3])
                val trueMonkey = parseInt(TRUE_MONKEY_PREFIX, input[4])
                val falseMonkey = parseInt(FALSE_MONKEY_PREFIX, input[5])

                return Monkey(items, operator, rightOperand, divisor, trueMonkey, falseMonkey)
            }

            private fun parseItems(s: String): List<Int> {
                if (!s.startsWith(ITEMS_PREFIX)) {
                    throw IllegalArgumentException()
                }

                return s.substring(ITEMS_PREFIX.length)
                    .split(", ")
                    .map(String::toInt)
                    .toList()
            }

            private fun parseOperation(s: String): Pair<Operator, Operand> {
                if (!s.startsWith(OPERATION_PREFIX)) {
                    throw IllegalArgumentException()
                }

                val (operator, operand) = s.substring(OPERATION_PREFIX.length)
                    .split(" ", limit = 2)

                return Pair(Operator.parse(operator), Operand.parse(operand))
            }

            private fun parseInt(prefix: String, s: String): Int {
                if (!s.startsWith(prefix)) {
                    throw IllegalArgumentException()
                }

                return s.substring(prefix.length).toInt()
            }
        }
    }

    class State(val monkey: Monkey) {
        val items = monkey.items.toMutableList()
        var inspections = 0
    }

    override fun parse(input: Sequence<String>): List<Monkey> {
        val monkeys = mutableListOf<Monkey>()

        val list = input.toList()
        var start = 0

        for ((i, s) in list.withIndex()) {
            if (s.isEmpty()) {
                monkeys += Monkey.parse(list.slice(start until i))
                start = i + 1
            }
        }

        monkeys += Monkey.parse(list.slice(start until list.size))

        return monkeys
    }

    override fun solvePart1(input: List<Monkey>): Int {
        val states = input.map(::State)

        for (round in 0 until 20) {
            for (state in states) {
                val monkey = state.monkey

                for (item in state.items) {
                    val worry = monkey.inspect(item) / 3

                    if ((worry % monkey.divisor) == 0) {
                        states[monkey.trueMonkey].items += worry
                    } else {
                        states[monkey.falseMonkey].items += worry
                    }
                }

                state.inspections += state.items.size
                state.items.clear()
            }
        }

        return states.map(State::inspections)
            .sortedDescending()
            .take(2)
            .reduce(Int::times)
    }
}
