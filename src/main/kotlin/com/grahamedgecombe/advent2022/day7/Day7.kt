package com.grahamedgecombe.advent2022.day7

import com.grahamedgecombe.advent2022.Puzzle

object Day7 : Puzzle<Day7.Node>(7) {
    sealed class Node private constructor(val parent: Directory?) {
        class File(parent: Directory, val size: Int) : Node(parent) {
            override fun walk(f: (Node, Int) -> Unit): Int {
                f(this, size)
                return size
            }
        }

        class Directory(parent: Directory?) : Node(parent) {
            val children = mutableMapOf<String, Node>()

            override fun walk(f: (Node, Int) -> Unit): Int {
                val size = children.values.sumOf { it.walk(f) }
                f(this, size)
                return size
            }
        }

        abstract fun walk(f: (Node, Int) -> Unit): Int
    }

    private const val CD = "$ cd "
    private const val LS = "$ ls"

    private const val MAX_TOTAL_SIZE = 100000

    private const val CAPACITY = 70000000
    private const val REQUIRED_SPACE = 30000000

    override fun parse(input: Sequence<String>): Node {
        val root = Node.Directory(parent = null)
        var current = root

        for (s in input) {
            if (s.startsWith(CD)) {
                current = when (val path = s.substring(CD.length)) {
                    "/" -> root
                    ".." -> current.parent ?: throw IllegalArgumentException()
                    else -> {
                        val node = current.children[path] ?: throw IllegalArgumentException()
                        if (node !is Node.Directory) {
                            throw IllegalArgumentException()
                        }
                        node
                    }
                }
            } else if (s == LS) {
                continue
            } else {
                val (k, v) = s.split(' ', limit = 2)

                if (k == "dir") {
                    current.children[v] = Node.Directory(parent = current)
                } else {
                    current.children[v] = Node.File(parent = current, k.toInt())
                }
            }
        }

        return root
    }

    override fun solvePart1(input: Node): Int {
        var sum = 0

        input.walk { node, size ->
            if (node is Node.Directory && size <= MAX_TOTAL_SIZE) {
                sum += size
            }
        }

        return sum
    }

    override fun solvePart2(input: Node): Int {
        val used = input.walk { _, _ ->  }

        val free = CAPACITY - used
        val remainingRequiredSpace = REQUIRED_SPACE - free

        var deleteSize = used // worst case we delete everything

        input.walk { node, size ->
            if (node is Node.Directory && size >= remainingRequiredSpace) {
                deleteSize = minOf(deleteSize, size)
            }
        }

        return deleteSize
    }
}
