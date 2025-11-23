package com.nicholas.rutherford.potter.head.model.network

import com.google.gson.annotations.SerializedName

/**
 * Data class representing wand information from the [Harry Potter API](https://hp-api.onrender.com/).
 *
 * This class is used to deserialize wand data from the API's character responses.
 * All properties are mapped using [SerializedName] annotations to match the API's JSON structure.
 *
 * @property wood The type of wood the wand is made from (e.g., "holly", "oak", "willow").
 * @property core The core material of the wand (e.g., "phoenix feather", "dragon heartstrings").
 * @property length The length of the wand in inches, or null if unknown.
 *
 * @author Nicholas Rutherford
 */
data class WandResponse(
    @SerializedName("wood")
    val wood: String?,
    @SerializedName("core")
    val core: String?,
    @SerializedName("length")
    val length: Double?
)