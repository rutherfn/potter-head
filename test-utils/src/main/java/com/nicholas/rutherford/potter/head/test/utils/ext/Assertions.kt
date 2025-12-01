package com.nicholas.rutherford.potter.head.test.utils.ext

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertNotEquals

/**
 * Infix extension function to assert that two values are equal.
 * 
 * Usage:
 * ```
 * val value1 = "value1"
 * val value2 = "value1"
 * value1 shouldBe value2
 * ```
 * 
 * @param expected The expected value to compare against.
 * @throws AssertionError if the values are not equal.
 *
 * @author Nicholas Rutherford
 */
infix fun <T> T.shouldBe(expected: T) {
    assertEquals(expected, this, "Expected values to be equal")
}

/**
 * Infix extension function to assert that two values are not equal.
 * 
 * Usage:
 * ```
 * val value1 = "value1"
 * val value2 = "value2"
 * value1 shouldNotBe value2
 * ```
 * 
 * @param other The value to compare against.
 * @throws AssertionError if the values are equal.
 *
 * @author Nicholas Rutherford
 */
infix fun <T> T.shouldNotBe(other: T) {
    assertNotEquals(other, this, "Expected values to not be equal")
}

/**
 * Extension function to assert that a value is an instance of the specified type.
 * Uses reified generic type parameter for cleaner syntax.
 * 
 * Usage:
 * ```
 * val exception = IllegalStateException("error")
 * exception.shouldBeInstanceOf<IllegalStateException>()
 * ```
 * 
 * @throws AssertionError if the value is not an instance of the specified type.
 *
 * @author Nicholas Rutherford
 */
inline fun <reified T> Any?.shouldBeInstanceOf() {
    assertInstanceOf(T::class.java, this, "Expected value to be an instance of ${T::class.simpleName}")
}

