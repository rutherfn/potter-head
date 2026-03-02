package com.nicholas.rutherford.potter.head.database.converter

import com.nicholas.rutherford.potter.head.database.entity.WandEntity
import com.nicholas.rutherford.potter.head.model.network.WandResponse

/**
 * Converter data class for WandEntity.
 * Used to convert between entity and domain model representations.
 *
 * @property core The core of the wand.
 * @property wood The wood type of the wand.
 * @property length The length of the wand.
 *
 * @author Nicholas Rutherford
 */
data class WandConverter(
    val core: String?,
    val wood: String?,
    val length: Double?
) {
    /**
     * Converts this converter to a WandEntity.
     */
    fun toEntity(): WandEntity = WandEntity(
        core = core,
        wood = wood,
        length = length
    )

    companion object {
        /**
         * Creates a WandConverter from a WandEntity.
         */
        fun fromEntity(entity: WandEntity): WandConverter =
            WandConverter(
                core = entity.core,
                wood = entity.wood,
                length = entity.length
            )

        /**
         * Creates a WandConverter from a WandResponse.
         */
        fun fromResponse(response: WandResponse): WandConverter =
            WandConverter(
                core = response.core,
                wood = response.wood,
                length = response.length
            )
    }
}