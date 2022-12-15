package com.grahamedgecombe.advent2022.util

import kotlin.math.abs

data class Vector2(val x: Int, val y: Int) {
    val magnitudeSquared
        get() = abs(x) + abs(y)

    fun add(dx: Int, dy: Int): Vector2 {
        return Vector2(x + dx, y + dy)
    }

    operator fun plus(v: Vector2): Vector2 {
        return add(v.x, v.y)
    }

    operator fun minus(v: Vector2): Vector2 {
        return add(-v.x, -v.y)
    }

    override fun toString(): String {
        return "($x, $y)"
    }

    companion object {
        val ZERO = Vector2(0, 0)
    }
}
