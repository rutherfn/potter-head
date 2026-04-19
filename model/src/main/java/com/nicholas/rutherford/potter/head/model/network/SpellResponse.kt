package com.nicholas.rutherford.potter.head.model.network

import com.google.gson.annotations.SerializedName

/**
 * Data class representing a spell response from the [Harry Potter API](https://hp-api.onrender.com/).
 *
 * This class is used to deserialize JSON responses from the API.
 * All properties are mapped using [SerializedName] annotations to match the API's JSON structure.
 *
 * @property id The unique identifier of the spell.
 * @property name The name of the spell.
 * @property description The description of the spell
 *
 * @author Nicholas Rutherford
 */
data class SpellResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String
)
