package com.grahamedgecombe.advent2022.day15

import com.google.common.collect.ContiguousSet
import com.google.common.collect.DiscreteDomain
import com.google.common.collect.Range
import com.google.common.collect.RangeSet
import com.google.common.collect.TreeRangeSet
import com.grahamedgecombe.advent2022.Puzzle
import com.grahamedgecombe.advent2022.UnsolvableException
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

    private fun getInvalidXs(sensors: List<Sensor>, y: Int): RangeSet<Int> {
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

        return xs
    }

    fun solveRow(sensors: List<Sensor>, y: Int): Int {
        return getInvalidXs(sensors, y).asRanges().sumOf { range ->
            val set = ContiguousSet.create(range, DiscreteDomain.integers())
            set.last() - set.first() + 1
        }
    }

    fun solve(sensors: List<Sensor>, max: Int): Long {
        val knownBeacons = sensors.map(Sensor::beacon).toSet()
        val bounds = Range.closed(0, max)

        for (y in 0..max) {
            val validXs = getInvalidXs(sensors, y)
                .complement()
                .subRangeSet(bounds)

            val range = validXs.asRanges().singleOrNull() ?: continue
            val set = ContiguousSet.create(range, DiscreteDomain.integers())

            val x = set.first()
            if (x == set.last() && !knownBeacons.contains(Vector2(x, y))) {
                return x.toLong() * 4000000 + y
            }
        }

        throw UnsolvableException()
    }

    override fun solvePart1(input: List<Sensor>): Int {
        return solveRow(input, 2000000)
    }

    override fun solvePart2(input: List<Sensor>): Long {
        return solve(input, 4000000)
    }
}
