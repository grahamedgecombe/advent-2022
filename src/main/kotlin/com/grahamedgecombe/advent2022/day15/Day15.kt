package com.grahamedgecombe.advent2022.day15

import com.google.common.collect.ContiguousSet
import com.google.common.collect.DiscreteDomain
import com.google.common.collect.Range
import com.google.common.collect.TreeRangeSet
import com.grahamedgecombe.advent2022.Puzzle
import com.grahamedgecombe.advent2022.util.Vector2
import kotlin.math.abs

object Day15 : Puzzle<List<Day15.Sensor>>(15) {
    data class Sensor(val position: Vector2, val beacon: Vector2) {
        companion object {
            private val REGEX = Regex("Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)")

            fun parse(s: String): Sensor {
                val m = REGEX.matchEntire(s) ?: throw IllegalArgumentException()
                val (sensorX, sensorY, beaconX, beaconY) = m.destructured
                return Sensor(Vector2(sensorX.toInt(), sensorY.toInt()), Vector2(beaconX.toInt(), beaconY.toInt()))
            }
        }
    }

    override fun parse(input: Sequence<String>): List<Sensor> {
        return input.map(Sensor::parse).toList()
    }

    fun solveRow(sensors: List<Sensor>, y: Int): Int {
        val xs = TreeRangeSet.create<Int>()

        for (sensor in sensors) {
            val radius = (sensor.position - sensor.beacon).magnitudeSquared

            val dx = radius - abs(sensor.position.y - y)
            if (dx <= 0) {
                continue
            }

            xs.add(Range.closed(sensor.position.x - dx, sensor.position.x + dx))
        }

        for (sensor in sensors) {
            if (y == sensor.beacon.y) {
                xs.remove(Range.singleton(sensor.beacon.x))
            }
        }

        return xs.asRanges().sumOf { range ->
            val set = ContiguousSet.create(range, DiscreteDomain.integers())
            set.last() - set.first() + 1
        }
    }

    override fun solvePart1(input: List<Sensor>): Int {
        return solveRow(input, 2000000)
    }
}
