package com.nicholas.rutherford.potter.head.model.network

import com.google.gson.annotations.SerializedName

/**
 * Data class representing a character response from the [Harry Potter API](https://hp-api.onrender.com/).
 *
 * This class is used to deserialize JSON responses from the API.
 * All properties are mapped using [SerializedName] annotations to match the API's JSON structure.
 *
 * @property id The unique identifier of the character.
 * @property name The character's name.
 * @property alternateNames List of alternate names for the character.
 * @property species The species of the character (e.g., "human", "half-giant").
 * @property gender The gender of the character.
 * @property house The Hogwarts house the character belongs to (Gryffindor, Slytherin, etc.), or null if not applicable.
 * @property dateOfBirth The character's date of birth in DD-MM-YYYY format, or null if unknown.
 * @property yearOfBirth The year the character was born, or null if unknown.
 * @property isWizard Whether the character is a wizard.
 * @property ancestry The character's ancestry (e.g., "pure-blood", "half-blood", "muggle-born").
 * @property eyeColour The character's eye colour, or null if unknown.
 * @property hairColour The character's hair colour, or null if unknown.
 * @property wand The character's wand information, or null if unknown.
 * @property patronus The character's patronus, or null if unknown or not applicable.
 * @property isHogwartsStudent Whether the character is or was a Hogwarts student.
 * @property isHogwartsStaff Whether the character is or was a Hogwarts staff member.
 * @property actor The actor who portrayed the character, or null if not applicable.
 * @property alternateActors List of alternate actors who portrayed the character.
 * @property isAlive Whether the character is alive.
 * @property image URL to the character's image, or null if not available.
 *
 * @author Nicholas Rutherford
 */
data class CharacterResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("alternate_names")
    val alternateNames: List<String>,
    @SerializedName("species")
    val species: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("house")
    val house: String?,
    @SerializedName("dateOfBirth")
    val dateOfBirth: String?,
    @SerializedName("yearOfBirth")
    val yearOfBirth: Int?,
    @SerializedName("wizard")
    val isWizard: Boolean,
    @SerializedName("ancestry")
    val ancestry: String?,
    @SerializedName("eyeColour")
    val eyeColour: String?,
    @SerializedName("hairColour")
    val hairColour: String?,
    @SerializedName("wand")
    val wand: WandResponse?,
    @SerializedName("patronus")
    val patronus: String?,
    @SerializedName("hogwartsStudent")
    val isHogwartsStudent: Boolean,
    @SerializedName("hogwartsStaff")
    val isHogwartsStaff: Boolean,
    @SerializedName("actor")
    val actor: String?,
    @SerializedName("alternate_actors")
    val alternateActors: List<String>,
    @SerializedName("alive")
    val isAlive: Boolean,
    @SerializedName("image")
    val image: String?
)