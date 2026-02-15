package com.nicholas.rutherford.potter.head.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey
    val name: String,
    val alternateNames: List<String>,
    val species: String,
    val gender: String,
    val house: String,
    val dateOfBirth: String?,
    val yearOfBirth: Int?,
    val isWizard: Boolean,
    val ancestry: String?,
    val eyeColour: String?,
    val hairColour: String?,
    val wand: WandEntity?,
    val patronus: String?,
    val isHogwartsStudent: Boolean,
    val isHogwartsStaff: Boolean,
    val actor: String?,
    val alternateActors: List<String>,
    val isAlive: Boolean,
    val image: String?
)