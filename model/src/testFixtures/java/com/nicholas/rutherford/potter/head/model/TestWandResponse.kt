package com.nicholas.rutherford.potter.head.model

import com.nicholas.rutherford.potter.head.model.network.WandResponse

/**
 * Test fixtures for creating [WandResponse] objects in tests.
 * These can be used by other modules via testImplementation.
 */
object TestWandResponse {

    fun build(): WandResponse = WandResponse(
        wood = WAND_RESPONSE_WOOD,
        core = WAND_RESPONSE_CORE,
        length = WAND_RESPONSE_LENGTH
    )

    /**
     * Creates a list of [WandResponse] objects for testing.
     *
     * @param count The number of wand responses to create (default: 3).
     * @return A list of [WandResponse] objects.
     */
    fun buildList(count: Int = 3): List<WandResponse> {
        return List(count) { build() }
    }

    /**
     * Creates a list of [WandResponse] objects with custom variations.
     * Useful when you need different wand configurations for testing.
     */
    fun buildVariedList(): List<WandResponse> {
        return listOf(
            build(), // Default wand
            build().copy(wood = "oak", core = "dragon heartstring", length = 10.0),
            build().copy(wood = "willow", core = "unicorn tail hair", length = 12.5)
        )
    }
}

const val WAND_RESPONSE_WOOD = "holly"
const val WAND_RESPONSE_CORE = "phoenix feather"
const val WAND_RESPONSE_LENGTH = 11.0

