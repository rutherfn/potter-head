package com.nicholas.rutherford.potter.head.model.network

import com.google.gson.annotations.SerializedName

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