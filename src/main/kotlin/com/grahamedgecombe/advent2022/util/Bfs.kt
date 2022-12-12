package com.grahamedgecombe.advent2022.util

object Bfs {
    interface Node<T : Node<T>> {
        val isGoal: Boolean
        val neighbours: Sequence<T>
    }

    fun <T : Node<T>> search(root: T): Sequence<List<T>> {
        val queue = ArrayDeque<T>()
        val parents = mutableMapOf<T, T>()

        queue += root

        return sequence {
            while (true) {
                val current = queue.removeFirstOrNull() ?: break
                if (current.isGoal) {
                    val path = mutableListOf<T>()

                    var node: T? = current
                    while (node != null) {
                        path += node
                        node = parents[node]
                    }

                    path.reverse()
                    yield(path)
                }

                for (neighbour in current.neighbours) {
                    if (parents.containsKey(neighbour) || neighbour == root) {
                        continue
                    }

                    queue.addLast(neighbour)
                    parents[neighbour] = current
                }
            }
        }
    }
}

