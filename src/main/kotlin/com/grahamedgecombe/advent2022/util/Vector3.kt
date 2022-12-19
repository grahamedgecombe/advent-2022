package com.grahamedgecombe.advent2022.util

data class Vector3(val x: Int, val y: Int, val z: Int) {
    fun add(dx: Int, dy: Int, dz: Int): Vector3 {
        return Vector3(x + dx, y + dy, z + dz)
    }

    operator fun plus(v: Vector3): Vector3 {
        return add(v.x, v.y, v.z)
    }

    operator fun minus(v: Vector3): Vector3 {
        return add(-v.x, -v.y, -v.z)
    }

    override fun toString(): String {
        return "($x, $y, $z)"
    }

    companion object {
        val ZERO = Vector3(0, 0, 0)
    }
}
