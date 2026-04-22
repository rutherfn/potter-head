package com.nicholas.rutherford.potter.head.test.utils.data

import com.nicholas.rutherford.potter.head.model.network.WandResponse

/**
 * Test utility object for creating [WandResponse] instances for testing purposes.
 *
 * This object provides methods to create test data for wand responses,
 * which are used in unit tests. It provides the following:
 * - Default wand data
 * - Methods to create lists of wand responses
 * - Methods to create varied wand responses for testing different scenarios
 *
 * @see WandResponse for the actual data model
 *
 * @author Nicholas Rutherford
 */
object TestWandResponse {

    fun build(): WandResponse = WandResponse(
        wood = WAND_RESPONSE_WOOD,
        core = WAND_RESPONSE_CORE,
        length = WAND_RESPONSE_LENGTH
    )

    fun buildList(count: Int = 3): List<WandResponse> = List(size = count) { build() }

    fun buildVariedList(): List<WandResponse> = listOf(
        build(), // Default wand
        build().copy(wood = "oak", core = "dragon heart-string", length = 10.0),
        build().copy(wood = "willow", core = "unicorn tail hair", length = 12.5)
    )
}

const val WAND_RESPONSE_WOOD = "holly"
const val WAND_RESPONSE_CORE = "phoenix feather"
const val WAND_RESPONSE_LENGTH = 11.0
