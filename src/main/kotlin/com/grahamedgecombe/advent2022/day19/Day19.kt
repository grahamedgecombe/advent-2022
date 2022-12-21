package com.grahamedgecombe.advent2022.day19

import com.grahamedgecombe.advent2022.Puzzle

object Day19 : Puzzle<List<Day19.Blueprint>>(19) {
    private const val GEODES = 3

    data class Blueprint(
        val id: Int,
        val costs: List<List<Int>>,
    ) {
        val maxRobots: List<Int>

        init {
            maxRobots = mutableListOf()

            for (i in 0..GEODES) {
                maxRobots += 0
            }

            for (robot in 0..GEODES) {
                for (resource in 0..GEODES) {
                    maxRobots[resource] = maxOf(maxRobots[resource], costs[robot][resource])
                }
            }
        }

        companion object {
            private val REGEX = Regex("Blueprint (\\d+): Each ore robot costs (\\d+) ore[.] Each clay robot costs (\\d+) ore[.] Each obsidian robot costs (\\d+) ore and (\\d+) clay[.] Each geode robot costs (\\d+) ore and (\\d+) obsidian[.]")

            fun parse(s: String): Blueprint {
                val m = REGEX.matchEntire(s) ?: throw IllegalArgumentException()
                val (id, oreRobotOreCost, clayRobotOreCost, obsidianRobotOreCost, obsidianRobotClayCost, geodeRobotOreCost, geodeRobotObsidianCost) = m.destructured
                return Blueprint(
                    id.toInt(),
                    listOf(
                        listOf(oreRobotOreCost.toInt(), 0, 0, 0),
                        listOf(clayRobotOreCost.toInt(), 0, 0, 0),
                        listOf(obsidianRobotOreCost.toInt(), obsidianRobotClayCost.toInt(), 0, 0),
                        listOf(geodeRobotOreCost.toInt(), 0, geodeRobotObsidianCost.toInt(), 0),
                    ),
                )
            }
        }
    }

    override fun parse(input: Sequence<String>): List<Blueprint> {
        return input.map(Blueprint::parse).toList()
    }

    private fun maxGeodes(
        blueprint: Blueprint,
        time: Int,
        resources: List<Int>,
        robots: List<Int>,
    ): Int {
        if (time == 0) {
            return resources[GEODES]
        } else if (time < 0) {
            throw IllegalArgumentException()
        }

        var max = resources[GEODES] + robots[GEODES] * time

        outer@for (robot in 0..GEODES) {
            if (robot != GEODES && robots[robot] >= blueprint.maxRobots[robot]) {
                continue
            }

            var dt = 1
            for ((resource, cost) in blueprint.costs[robot].withIndex()) {
                val required = cost - resources[resource]
                if (required <= 0) {
                    continue
                }

                if (robots[resource] == 0) {
                    continue@outer
                }

                dt = maxOf(dt, 1 + (required + robots[resource] - 1) / robots[resource])
            }

            val nextTime = time - dt
            if (nextTime < 0) {
                continue
            }

            val nextResources = resources.toMutableList()
            for (resource in 0..GEODES) {
                nextResources[resource] += robots[resource] * dt - blueprint.costs[robot][resource]
            }

            val nextRobots = robots.toMutableList()
            nextRobots[robot]++

            max = maxOf(max, maxGeodes(blueprint, nextTime, nextResources, nextRobots))
        }

        return max
    }

    private fun maxGeodes(blueprint: Blueprint, time: Int): Int {
        return maxGeodes(blueprint, time, listOf(0, 0, 0, 0), listOf(1, 0, 0, 0))
    }

    override fun solvePart1(input: List<Blueprint>): Int {
        return input.sumOf { blueprint -> blueprint.id * maxGeodes(blueprint, 24) }
    }
}
