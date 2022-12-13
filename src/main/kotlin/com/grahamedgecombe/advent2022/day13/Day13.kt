package com.grahamedgecombe.advent2022.day13

import com.grahamedgecombe.advent2022.Puzzle
import java.io.BufferedReader
import java.io.StringReader

object Day13 : Puzzle<List<Pair<Day13.Packet, Day13.Packet>>>(13) {
    sealed interface Packet : Comparable<Packet> {
        data class PList(val items: List<Packet>) : Packet {
            override fun toList(): PList {
                return this
            }

            override fun compareTo(other: Packet): Int {
                if (other is PList) {
                    val size = minOf(items.size, other.items.size)
                    for (i in 0 until size) {
                        val cmp = items[i].compareTo(other.items[i])
                        if (cmp != 0) {
                            return cmp
                        }
                    }

                    return items.size - other.items.size
                } else {
                    return compareTo(other.toList())
                }
            }
        }

        data class Value(val n: Int) : Packet {
            override fun toList(): PList {
                return PList(listOf(this))
            }

            override fun compareTo(other: Packet): Int {
                return if (other is Value) {
                    n - other.n
                } else {
                    toList().compareTo(other)
                }
            }
        }

        fun toList(): PList

        companion object {
            private fun BufferedReader.peek(): Int {
                mark(1)
                val c = read()
                reset()
                return c
            }

            fun parse(s: String): Packet {
                return BufferedReader(StringReader(s)).use(::parse)
            }

            fun parse(r: BufferedReader): Packet {
                when (val char = r.read()) {
                    '['.code -> {
                        val c = r.peek()
                        if (c == ']'.code) {
                            return PList(emptyList())
                        }

                        val items = mutableListOf<Packet>()

                        while (true) {
                            items += parse(r)

                            when (r.read()) {
                                ']'.code -> break
                                ','.code -> continue
                                else -> throw IllegalArgumentException()
                            }
                        }

                        return PList(items)
                    }
                    in '0'.code..'9'.code -> {
                        val builder = StringBuilder()
                        builder.append(char.toChar())

                        while (true) {
                            val c = r.peek()
                            if (c !in '0'.code..'9'.code) {
                                break
                            }

                            r.skip(1)
                            builder.append(c.toChar())
                        }

                        return Value(builder.toString().toInt())
                    }
                    else -> throw IllegalArgumentException()
                }
            }
        }
    }

    override fun parse(input: Sequence<String>): List<Pair<Packet, Packet>> {
        val pairs = mutableListOf<Pair<Packet, Packet>>()

        for (pair in input.windowed(3, step = 3, partialWindows = true)) {
            if (pair.size < 2) {
                throw IllegalArgumentException()
            } else if (pair.size == 3) {
                require(pair[2].isEmpty())
            }

            pairs += Pair(Packet.parse(pair[0]), Packet.parse(pair[1]))
        }

        return pairs
    }

    override fun solvePart1(input: List<Pair<Packet, Packet>>): Int {
        var sum = 0

        for ((i, pair) in input.withIndex()) {
            if (pair.first <= pair.second) {
                sum += i + 1
            }
        }

        return sum
    }

    override fun solvePart2(input: List<Pair<Packet, Packet>>): Int {
        val divider1 = Packet.parse("[[2]]")
        val divider2 = Packet.parse("[[6]]")

        val sorted = input.flatMap { (left, right) -> sequenceOf(left, right) }
            .plus(divider1)
            .plus(divider2)
            .sorted()

        return (sorted.indexOf(divider1) + 1) * (sorted.indexOf(divider2) + 1)
    }
}
