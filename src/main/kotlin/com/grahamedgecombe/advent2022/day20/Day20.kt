package com.grahamedgecombe.advent2022.day20

import com.grahamedgecombe.advent2022.Puzzle

object Day20 : Puzzle<List<Int>>(20) {
    class Node(val n: Long) {
        lateinit var prev: Node
        lateinit var next: Node

        fun mix(size: Int) {
            var position = prev

            // unlink
            prev.next = next
            next.prev = prev

            // find position to insert at
            val decrypted = n % (size - 1)
            if (decrypted > 0) {
                for (i in 0 until decrypted) {
                    position = position.next
                }
            } else if (decrypted < 0) {
                for (i in 0 until -decrypted) {
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
        fun mix(n: Int): Long {
            for (i in 0 until n) {
                for (node in nodes) {
                    node.mix(nodes.size)
                }
            }

            var current = nodes.first()
            while (current.n != 0L) {
                current = current.next
            }

            var sum = 0L

            for (i in 0 until 3) {
                for (j in 0 until 1000) {
                    current = current.next
                }

                sum += current.n
            }

            return sum
        }

        companion object {
            fun create(input: List<Int>, key: Int): Ring {
                require(input.isNotEmpty())

                val nodes = mutableListOf<Node>()

                for (n in input) {
                    nodes += Node(n.toLong() * key)
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

    override fun solvePart1(input: List<Int>): Long {
        return Ring.create(input, 1).mix(1)
    }

    override fun solvePart2(input: List<Int>): Long {
        return Ring.create(input, 811589153).mix(10)
    }
}
