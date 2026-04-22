package com.nicholas.rutherford.potter.head.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing a character in the Room database.
 * The character's name serves as the primary key.
 *
 * @property name The name of the character.
 * @property alternateNames List of alternate names for the character.
 * @property species The species of the character.
 * @property gender The gender of the character.
 * @property house The house to which the character belongs.
 * @property dateOfBirth The date of birth of the character.
 * @property yearOfBirth The year of birth of the character.
 * @property isWizard Whether the character is a wizard.
 * @property ancestry The ancestry of the character.
 * @property eyeColour The eye colour of the character.
 * @property hairColour The hair colour of the character.
 * @property wand The wand of the character.
 * @property patronus The patronus of the character.
 * @property isHogwartsStudent Whether the character is a Hogwarts student.
 * @property isHogwartsStaff Whether the character is a Hogwarts staff member.
 * @property actor The actor associated with the character.
 * @property alternateActors List of alternate actors for the character.
 * @property isAlive Whether the character is alive.
 * @property image The URL of the character's image.
 *
 * @author Nicholas Rutherford
 */
@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey
    val name: String,
    val alternateNames: List<String>,
    val species: String,
    val gender: String,
    val house: String?,
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
