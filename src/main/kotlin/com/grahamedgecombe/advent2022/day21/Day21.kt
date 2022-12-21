package com.grahamedgecombe.advent2022.day21

import com.grahamedgecombe.advent2022.Puzzle
import com.grahamedgecombe.advent2022.UnsolvableException

object Day21 : Puzzle<Map<String, Day21.Expr>>(21) {
    enum class Operator {
        ADD,
        SUB,
        MUL,
        DIV;

        companion object {
            fun parse(s: String): Operator {
                return when (s) {
                    "+" -> ADD
                    "-" -> SUB
                    "*" -> MUL
                    "/" -> DIV
                    else -> throw IllegalArgumentException()
                }
            }
        }
    }

    sealed interface Expr {
        data class Literal(val n: Long) : Expr {
            override fun evaluate(context: Map<String, Expr>): Long {
                return n
            }
        }

        data class Monkey(val k: String) : Expr {
            override fun evaluate(context: Map<String, Expr>): Long {
                val expr = context[k] ?: throw UnsolvableException()
                return expr.evaluate(context)
            }
        }

        data class BinaryOp(val left: Expr, val op: Operator, val right: Expr) : Expr {
            override fun evaluate(context: Map<String, Expr>): Long {
                return when (op) {
                    Operator.ADD -> left.evaluate(context) + right.evaluate(context)
                    Operator.SUB -> left.evaluate(context) - right.evaluate(context)
                    Operator.MUL -> left.evaluate(context) * right.evaluate(context)
                    Operator.DIV -> left.evaluate(context) / right.evaluate(context)
                }
            }
        }

        fun evaluate(context: Map<String, Expr>): Long

        companion object {
            private val BINARY_OP_REGEX = Regex("([^ ]+) ([+*/-]) ([^ ]+)")

            fun parse(s: String): Expr {
                val n = s.toLongOrNull()
                if (n != null) {
                    return Literal(n)
                }

                val m = BINARY_OP_REGEX.matchEntire(s) ?: throw IllegalArgumentException()
                val (left, op, right) = m.destructured
                return BinaryOp(Monkey(left), Operator.parse(op), Monkey(right))
            }
        }
    }

    override fun parse(input: Sequence<String>): Map<String, Expr> {
        return input.associate { s ->
            val (k, v) = s.split(": ", limit = 2)
            k to Expr.parse(v)
        }
    }

    override fun solvePart1(input: Map<String, Expr>): Long {
        val root = input["root"] ?: throw UnsolvableException()
        return root.evaluate(input)
    }
}
