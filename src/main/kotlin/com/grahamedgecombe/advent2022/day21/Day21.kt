package com.grahamedgecombe.advent2022.day21

import com.grahamedgecombe.advent2022.Puzzle
import com.grahamedgecombe.advent2022.UnsolvableException

object Day21 : Puzzle<Map<String, Day21.Expr>>(21) {
    enum class Operator {
        ADD,
        SUB,
        MUL,
        DIV;

        fun evaluate(left: Long, right: Long): Long {
            return when (this) {
                ADD -> left + right
                SUB -> left - right
                MUL -> left * right
                DIV -> left / right
            }
        }

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

            override fun simplify(context: Map<String, Expr>): Expr {
                return this
            }

            override fun solve(value: Long): Long {
                throw UnsolvableException()
            }
        }

        data class Monkey(val k: String) : Expr {
            override fun evaluate(context: Map<String, Expr>): Long {
                val expr = context[k] ?: throw UnsolvableException()
                return expr.evaluate(context)
            }

            override fun simplify(context: Map<String, Expr>): Expr {
                return if (k != "humn") {
                    val expr = context[k] ?: throw UnsolvableException()
                    return expr.simplify(context)
                } else {
                    this
                }
            }

            override fun solve(value: Long): Long {
                if (k != "humn") {
                    throw UnsolvableException()
                }

                return value
            }
        }

        data class BinaryOp(val left: Expr, val op: Operator, val right: Expr) : Expr {
            override fun evaluate(context: Map<String, Expr>): Long {
                return op.evaluate(left.evaluate(context), right.evaluate(context))
            }

            override fun simplify(context: Map<String, Expr>): Expr {
                val simpleLeft = left.simplify(context)
                val simpleRight = right.simplify(context)

                return if (simpleLeft is Literal && simpleRight is Literal) {
                    Literal(op.evaluate(simpleLeft.n, simpleRight.n))
                } else {
                    BinaryOp(simpleLeft, op, simpleRight)
                }
            }

            override fun solve(value: Long): Long {
                return when {
                    left is Literal -> when (op) {
                        Operator.ADD -> right.solve(value - left.n)
                        Operator.SUB -> right.solve(left.n - value)
                        Operator.MUL -> right.solve(value / left.n)
                        Operator.DIV -> right.solve(left.n / value)
                    }
                    right is Literal -> when (op) {
                        Operator.ADD -> left.solve(value - right.n)
                        Operator.SUB -> left.solve(value + right.n)
                        Operator.MUL -> left.solve(value / right.n)
                        Operator.DIV -> left.solve(value * right.n)
                    }
                    else -> throw UnsolvableException()
                }
            }
        }

        fun evaluate(context: Map<String, Expr>): Long
        fun simplify(context: Map<String, Expr>): Expr
        fun solve(value: Long): Long

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

    override fun solvePart2(input: Map<String, Expr>): Long {
        val root = input["root"]?.simplify(input) ?: throw UnsolvableException()
        if (root !is Expr.BinaryOp) {
            throw UnsolvableException()
        }

        return if (root.left is Expr.Literal) {
            root.right.solve(root.left.n)
        } else if (root.right is Expr.Literal) {
            root.left.solve(root.right.n)
        } else {
            throw UnsolvableException()
        }
    }
}
