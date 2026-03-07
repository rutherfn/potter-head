package com.nicholas.rutherford.potter.head.core

/**
 * Created by Nicholas Rutherford, last edited on 2025-08-16
 *
 * Collection of extension and helper functions for safe operations, date parsing/formatting,
 * player position conversions, image handling, and permission checks.
 */

/** Safe call with two non-null parameters */
inline fun <T1 : Any, T2 : Any, R : Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
    return if (p1 != null && p2 != null) {
        block(p1, p2)
    } else {
        null
    }
}

/** Safe call with three non-null parameters */
inline fun <T1 : Any, T2 : Any, T3 : Any, R : Any> safeLet(p1: T1?, p2: T2?, p3: T3?, block: (T1, T2, T3) -> R?): R? {
    return if (p1 != null && p2 != null && p3 != null) {
        block(p1, p2, p3)
    } else {
        null
    }
}

/** Safe call with four non-null parameters */
inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, R : Any> safeLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    p4: T4?,
    block: (T1, T2, T3, T4) -> R?
): R? {
    return if (p1 != null && p2 != null && p3 != null && p4 != null) {
        block(p1, p2, p3, p4)
    } else {
        null
    }
}

/** Safe call with five non-null parameters */
inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, R : Any> safeLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    p4: T4?,
    p5: T5?,
    block: (T1, T2, T3, T4, T5) -> R?
): R? {
    return if (p1 != null && p2 != null && p3 != null && p4 != null && p5 != null) {
        block(p1, p2, p3, p4, p5)
    } else {
        null
    }
}