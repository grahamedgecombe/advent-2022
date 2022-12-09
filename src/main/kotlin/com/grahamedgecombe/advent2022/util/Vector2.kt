package com.grahamedgecombe.advent2022.util

data class Vector2(val x: Int, val y: Int) {
    fun add(dx: Int, dy: Int): Vector2 {
        return Vector2(x + dx, y + dy)
    }

    override fun toString(): String {
        return "($x, $y)"
    }

    companion object {
        val ZERO = Vector2(0, 0)
    }
}
