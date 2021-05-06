package rationals

import java.math.BigInteger

fun findGCD(a: BigInteger, b: BigInteger): BigInteger {
    if (b == BigInteger.ZERO) {
        return a
    }
    return findGCD(b, a % b)
}

fun findLCM(a: BigInteger, b: BigInteger): BigInteger {
    return (a * b).abs() / findGCD(a, b)
}

class Rational(numerator: BigInteger, denominator: BigInteger) {
    private var numerator: BigInteger
    private var denominator: BigInteger

    init {
        if (denominator == BigInteger.ZERO) {
            throw Exception("Denominator is Zero!")
        } else if (numerator == BigInteger.ZERO) {
            this.numerator = BigInteger.ZERO
            this.denominator = BigInteger.ONE
        } else {
            val gcd = findGCD(numerator, denominator)
            val normalizedNumerator = numerator.divide(gcd).abs()
            val sign = numerator.compareTo(BigInteger.ZERO) * denominator.compareTo(BigInteger.ZERO)

            this.numerator = normalizedNumerator * sign.toBigInteger()
            this.denominator = denominator.divide(gcd).abs()
        }
    }

    private fun getNumeratorBasedOnLCM(lcm: BigInteger): BigInteger {
        return this.numerator * (lcm / this.denominator)
    }

    operator fun plus(other: Rational): Rational {
        val lcm = findLCM(this.denominator, other.denominator)
        return Rational(this.getNumeratorBasedOnLCM(lcm) + other.getNumeratorBasedOnLCM(lcm), lcm)
    }

    operator fun minus(other: Rational): Rational {
        val lcm = findLCM(this.denominator, other.denominator)
        return Rational(this.getNumeratorBasedOnLCM(lcm) - other.getNumeratorBasedOnLCM(lcm), lcm)
    }

    operator fun times(other: Rational): Rational {
        return Rational(this.numerator * other.numerator, this.denominator * other.denominator)
    }

    operator fun div(other: Rational): Rational {
        return Rational(this.numerator * other.denominator, this.denominator * other.numerator)
    }

    operator fun compareTo(other: Rational): Int {
        val sub = this - other
        return sub.numerator.compareTo(BigInteger.ZERO)
    }

    operator fun unaryMinus(): Rational {
        return Rational(-this.numerator, this.denominator)
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Rational -> return this.numerator == other.numerator && this.denominator == other.denominator
            else -> false
        }
    }

    override fun toString(): String {
        if (this.denominator == BigInteger.ONE) {
            return "${this.numerator}"
        }
        return "${this.numerator}/${this.denominator}"
    }
}

fun Int.toRational(): Rational = Rational(this.toBigInteger(), BigInteger.ONE)
fun BigInteger.toRational(): Rational = Rational(this, BigInteger.ONE)
fun String.toRational(): Rational {
    val vals = this.split("/")
    return Rational(vals[0].toBigInteger(), if (vals.size == 2) vals[1].toBigInteger() else BigInteger.ONE)
}

infix fun Int.divBy(other: Int): Rational = Rational(this.toBigInteger(), other.toBigInteger())
infix fun String.divBy(other: String): Rational = Rational(this.toBigInteger(), other.toBigInteger())
infix fun BigInteger.divBy(other: BigInteger) = Rational(this, other)

fun main() {
    println((-2 divBy 4).toString() == "-1/2")
    println((2 divBy 1).toString() == "2")
    println("117/1098".toRational().toString() == "13/122")
    println("1/2".toRational() - "1/3".toRational() == "1/6".toRational())
    println("1/2".toRational() + "1/3".toRational() == "5/6".toRational())
    println(-(1 divBy 2) == (-1 divBy 2))
    println((1 divBy 2) * (1 divBy 3) == "1/6".toRational())
    println((1 divBy 2) / (1 divBy 4) == "2".toRational())
    println((1 divBy 2) < (2 divBy 3))
    println("912016490186296920119201192141970416029".toBigInteger() divBy "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}