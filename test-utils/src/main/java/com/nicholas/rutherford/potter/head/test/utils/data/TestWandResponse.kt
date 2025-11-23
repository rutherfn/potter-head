package com.nicholas.rutherford.potter.head.test.utils.data

import com.nicholas.rutherford.potter.head.model.network.WandResponse

object TestWandResponse {

    fun build(): WandResponse = WandResponse(
        wood = WAND_RESPONSE_WOOD,
        core = WAND_RESPONSE_CORE,
        length = WAND_RESPONSE_LENGTH
    )

    fun buildList(count: Int = 3): List<WandResponse> = List(count) { build() }

    fun buildVariedList(): List<WandResponse> = listOf(
        build(), // Default wand
        build().copy(wood = "oak", core = "dragon heart-string", length = 10.0),
        build().copy(wood = "willow", core = "unicorn tail hair", length = 12.5)
    )
}

const val WAND_RESPONSE_WOOD = "holly"
const val WAND_RESPONSE_CORE = "phoenix feather"
const val WAND_RESPONSE_LENGTH = 11.0

