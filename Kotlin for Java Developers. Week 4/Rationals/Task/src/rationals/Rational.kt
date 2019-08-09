

/**
 * This code is written for the programming assignment of the Kotlin Coursera Assignment -  Week 4.
 * It has functions to override operators and compare two rational functions.
 * The operators being overloaded are:
 * 1. +
 * 2. -
 * 3. *
 * 4. /
 * 5. ==
 * 6. unaryMinus
 *
 * After successful comparison it also represents the rational number in simplified form.
 * It is written in Kotlin.
 *
 * @author Sunny Bangale
 *
*/

package rationals

import jdk.internal.util.Preconditions
import java.math.BigInteger


data class Rational(val numerator : BigInteger, val denominator : BigInteger): Comparable<Rational>
{
    /**
     * Function to override plus operator
     */
    operator fun plus(r: Rational): Rational = (numerator.times(r.denominator).plus(denominator.times(r.numerator))).divBy(denominator.times(r.denominator))

    /**
     * Function to override minus operator
     */
    operator fun minus(r: Rational): Rational = (numerator.times(r.denominator).minus(denominator.times(r.numerator))).divBy(denominator.times(r.denominator))

    /**
     * Function to override times operator
     */
    operator fun times(r: Rational): Rational = (numerator.times(r.numerator)).divBy(denominator.times(r.denominator))

    /**
     * Function to override div operator
     */
    operator fun div(r: Rational): Rational = (numerator.times(r.denominator)).divBy(denominator.times(r.numerator))

    /**
     * Function to override unary minus operator
     */
    operator fun unaryMinus(): Rational = Rational(numerator.negate(), denominator)


    /**
     * Override toString function to represent simplifiedx form of the rational number
     */
    override fun toString(): String {

        return when {
            denominator == BigInteger.ONE || numerator.rem(denominator) == BigInteger.ZERO -> numerator.div(denominator).toString()
            else -> {
                val simplifiedNumber = normalizeRational(this.numerator, this.denominator)

                if(simplifiedNumber.denominator < BigInteger.ZERO || checkNegativeNumeratorDenominator(simplifiedNumber)){
                    formatRational(Rational(simplifiedNumber.numerator.negate(), simplifiedNumber.denominator.negate()))
                } else {
                    formatRational(Rational(simplifiedNumber.numerator, simplifiedNumber.denominator))
                }
            }
        }



    }

    /**
     * Overriden compareTo method to compare two rational numbers
     */
    override fun compareTo(other: Rational): Int = numerator.times(other.denominator).compareTo(other.numerator.times(denominator))

    /**
     * Overriden equals method to check if two rational numbers are equal
     */
    override fun equals(other: Any?): Boolean {
        if(this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Rational
        if(numerator == other.numerator && denominator == other.denominator) return true

        val simplifiedFirstRational = normalizeRational(numerator, denominator)
        val simplifiedSecondRational = normalizeRational(other.numerator, other.denominator)

        return compareNumerator(simplifiedFirstRational, simplifiedSecondRational) && compareDenominator(simplifiedFirstRational, simplifiedSecondRational)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

/*
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Rational

        if (numerator != other.numerator) return false
        if (denominator != other.denominator) return false

        return true
    }

    override fun hashCode(): Int {
        var result = numerator.hashCode()
        result = 31 * result + denominator.hashCode()
        return result
    }
*/

}

/**
 * Helper method to check if both numerator and denominator is negative
 */
private fun checkNegativeNumeratorDenominator(simplifiedNumber: Rational) =
        simplifiedNumber.numerator < BigInteger.ZERO && simplifiedNumber.denominator < BigInteger.ZERO

/**
 * Helper method to compare Numerators
 */
private fun compareNumerator(simplifiedFirstRational: Rational, simplifiedSecondRational: Rational) =
        simplifiedFirstRational.numerator == simplifiedSecondRational.numerator

/**
 * Helper method to compare Denominators
 */
private fun compareDenominator(simplifiedFirstRational: Rational, simplifiedSecondRational: Rational) =
        simplifiedFirstRational.denominator == simplifiedSecondRational.denominator

/**
 *  Function to format rational number
 */
fun formatRational(number: Rational) : String = number.numerator.toString() + "/" + number.denominator.toString()

/**
 * Function to normalize the fractional form to a simpler form
 */
fun normalizeRational(numerator : BigInteger, denominator : BigInteger) : Rational {
    var n1 = numerator
    var n2 = denominator

    var gcd = n1.gcd(n2)
    while( gcd != BigInteger.ONE)
    {
        n1 /=  gcd
        n2 /=  gcd
        gcd = n1.gcd(n2)
    }

    return Rational(n1, n2)
}

/**
 * Funtion to convert to rational number
 */
fun String.toRational(): Rational {
    val decimalNumber = split("/")
    when {
        decimalNumber.size == 1 -> return Rational(decimalNumber[0].toBigInteger(), 1.toBigInteger())
        else -> return Rational(decimalNumber[0].toBigInteger(), decimalNumber[1].toBigInteger())
    }
}

/**
 * Infix function to handle big integer operations
 */
infix fun BigInteger.divBy(divisor: BigInteger) : Rational = Rational(this, divisor)

/**
 * Infix function to handle integer operations
 */
infix fun Int.divBy(divisor: Int) : Rational = Rational(toBigInteger(), divisor.toBigInteger())

/**
 * Infix function to handle long operations
 */
infix fun Long.divBy(divisor: Long) : Rational = Rational(toBigInteger(), divisor.toBigInteger())


fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}
