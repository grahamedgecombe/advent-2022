package com.grahamedgecombe.advent2022.day16

import com.google.common.math.IntMath
import com.grahamedgecombe.advent2022.Puzzle

object Day16 : Puzzle<Day16.Graph>(16) {
    class Graph private constructor(val valves: Map<String, Valve>) {
        val usefulValves = valves.filter { it.value.rate != 0 }
        val distances = findShortestPaths(valves)

        companion object {
            private fun findShortestPaths(valves: Map<String, Valve>): Map<Pair<String, String>, Int> {
                val distances = mutableMapOf<Pair<String, String>, Int>()

                for ((src, valve) in valves) {
                    for (dest in valve.tunnels) {
                        distances[Pair(src, dest)] = 1
                    }

                    distances[Pair(src, src)] = 0
                }

                for (k in valves.keys) {
                    for (i in valves.keys) {
                        for (j in valves.keys) {
                            val current = distances[Pair(i, j)] ?: Int.MAX_VALUE
                            val next = IntMath.saturatedAdd(distances[Pair(i, k)] ?: Int.MAX_VALUE, distances[Pair(k, j)] ?: Int.MAX_VALUE)
                            if (next < current) {
                                distances[Pair(i, j)] = next
                            }
                        }
                    }
                }

                return distances
            }

            fun parse(input: Sequence<String>): Graph {
                val valves = mutableMapOf<String, Valve>()

                for (s in input) {
                    val m = REGEX.matchEntire(s) ?: throw IllegalArgumentException()
                    val (valve, rate, tunnels) = m.destructured
                    valves[valve] = Valve(rate.toInt(), tunnels.split(", ").toSet())
                }

                return Graph(valves)
            }
        }
    }
    data class Valve(val rate: Int, val tunnels: Set<String>)

    private val REGEX = Regex("Valve (.*) has flow rate=(\\d+); tunnels? leads? to valves? (.*)")

    override fun parse(input: Sequence<String>): Graph {
        return Graph.parse(input)
    }

    private fun maxPressure(graph: Graph, position: String, time: Int, open: Set<String>, pressure: Int): Int {
        var max = pressure

        for ((k, v) in graph.usefulValves) {
            if (k == position || k in open) {
                continue
            }

            val t = time - (graph.distances[Pair(position, k)]!! + 1)
            if (t <= 0) {
                continue
            }

            max = maxOf(max, maxPressure(graph, k, t, open + k, pressure + t * v.rate))
        }

        return max
    }

    override fun solvePart1(input: Graph): Int {
        return maxPressure(input, "AA", 30, emptySet(), 0)
    }
}
