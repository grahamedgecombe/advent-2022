package com.grahamedgecombe.advent2022.day20

import com.grahamedgecombe.advent2022.Puzzle

object Day20 : Puzzle<List<Int>>(20) {
    class Node(val n: Int) {
        lateinit var prev: Node
        lateinit var next: Node

        fun mix() {
            var position = prev

            // unlink
            prev.next = next
            next.prev = prev

            // find position to insert at
            if (n > 0) {
                for (i in 0 until n) {
                    position = position.next
                }
            } else if (n < 0) {
                for (i in 0 until -n) {
                    position = position.prev
                }
            }

            // link
            prev = position
            next = position.next

            prev.next = this
            next.prev = this
        }
    }

    class Ring private constructor(private val nodes: List<Node>) {
        fun mix(): Int {
            for (node in nodes) {
                node.mix()
            }

            var current = nodes.first()
            while (current.n != 0) {
                current = current.next
            }

            var sum = 0

            for (i in 0 until 3) {
                for (j in 0 until 1000) {
                    current = current.next
                }

                sum += current.n
            }

            return sum
        }

        companion object {
            fun create(input: List<Int>): Ring {
                require(input.isNotEmpty())

                val nodes = mutableListOf<Node>()

                for (n in input) {
                    nodes += Node(n)
                }

                for ((i, node) in nodes.withIndex()) {
                    node.prev = nodes[(i + nodes.size - 1) % nodes.size]
                    node.next = nodes[(i + 1) % nodes.size]
                }

                return Ring(nodes)
            }
        }
    }

    override fun parse(input: Sequence<String>): List<Int> {
        return input.map(String::toInt).toList()
    }

    override fun solvePart1(input: List<Int>): Int {
        return Ring.create(input).mix()
    }
}
