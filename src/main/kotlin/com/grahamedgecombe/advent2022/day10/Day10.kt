package com.grahamedgecombe.advent2022.day10

import com.grahamedgecombe.advent2022.Puzzle

object Day10 : Puzzle<List<Day10.Insn>>(10) {
    sealed interface Insn {
        data class AddX(val v: Int) : Insn
        object NoOp : Insn

        companion object {
            fun parse(s: String): Insn {
                if (s == "noop") {
                    return NoOp
                } else if (!s.startsWith("addx ")) {
                    throw IllegalArgumentException()
                }

                val x = s.substring("addx ".length).toInt()
                return AddX(x)
            }
        }
    }

    fun interface CycleListener {
        fun onIncrement(cycle: Int, x: Int)
    }

    class Vm(
        private val program: List<Insn>,
        private val listener: CycleListener,
    ) {
        private var x = 1
        private var cycle = 0

        fun run() {
            for (insn in program) {
                when (insn) {
                    is Insn.AddX -> {
                        incrementCycle(2)
                        x += insn.v
                    }
                    is Insn.NoOp -> incrementCycle(1)
                }
            }
        }

        private fun incrementCycle(n: Int) {
            for (i in 0 until n) {
                cycle++
                listener.onIncrement(cycle, x)
            }
        }
    }

    override fun parse(input: Sequence<String>): List<Insn> {
        return input.map(Insn::parse).toList()
    }

    override fun solvePart1(input: List<Insn>): Int {
        var sum = 0

        Vm(input) { cycle, x ->
            if ((cycle - 20) % 40 == 0 && cycle <= 220) {
                sum += cycle * x
            }
        }.run()

        return sum
    }

    override fun solvePart2(input: List<Insn>): String {
        val s = StringBuilder()

        Vm(input) { cycle, x ->
            val position = (cycle - 1) % 40
            if (position in (x - 1)..(x + 1)) {
                s.append('#')
            } else {
                s.append('.')
            }

            if (position == 39) {
                s.append('\n')
            }
        }.run()

        return s.trimEnd('\n').toString()
    }
}
