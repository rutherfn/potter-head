package com.nicholas.rutherford.potter.head.database.converter

import com.nicholas.rutherford.potter.head.database.entity.CharacterEntity
import com.nicholas.rutherford.potter.head.model.network.CharacterResponse

/**
 * Converter data class for CharacterEntity.
 * Used to convert between entity and domain model representations.
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
 * @property wandConverter Converter for the character's wand.
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
data class CharacterConverter(
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
    val wandConverter: WandConverter?,
    val patronus: String?,
    val isHogwartsStudent: Boolean,
    val isHogwartsStaff: Boolean,
    val actor: String?,
    val alternateActors: List<String>,
    val isAlive: Boolean,
    val image: String?
) {
    /**
     * Converts this converter to a CharacterEntity.
     */
    fun toEntity(): CharacterEntity = CharacterEntity(
        name = name,
        alternateNames = alternateNames,
        species = species,
        gender = gender,
        house = house,
        dateOfBirth = dateOfBirth,
        yearOfBirth = yearOfBirth,
        isWizard = isWizard,
        ancestry = ancestry,
        eyeColour = eyeColour,
        hairColour = hairColour,
        wand = wandConverter?.toEntity(),
        patronus = patronus,
        isHogwartsStudent = isHogwartsStudent,
        isHogwartsStaff = isHogwartsStaff,
        actor = actor,
        alternateActors = alternateActors,
        isAlive = isAlive,
        image = image
    )

    companion object {
        /**
         * Creates a CharacterConverter from a CharacterEntity.
         */
        fun fromEntity(entity: CharacterEntity): CharacterConverter = CharacterConverter(
            name = entity.name,
            alternateNames = entity.alternateNames,
            species = entity.species,
            gender = entity.gender,
            house = entity.house,
            dateOfBirth = entity.dateOfBirth,
            yearOfBirth = entity.yearOfBirth,
            isWizard = entity.isWizard,
            ancestry = entity.ancestry,
            eyeColour = entity.eyeColour,
            hairColour = entity.hairColour,
            wandConverter = entity.wand?.let { entity -> WandConverter.fromEntity(entity = entity) },
            patronus = entity.patronus,
            isHogwartsStudent = entity.isHogwartsStudent,
            isHogwartsStaff = entity.isHogwartsStaff,
            actor = entity.actor,
            alternateActors = entity.alternateActors,
            isAlive = entity.isAlive,
            image = entity.image
        )

        /**
         * Creates a CharacterConverter from a CharacterResponse.
         */
        fun fromResponse(response: CharacterResponse): CharacterConverter = CharacterConverter(
            name = response.name,
            alternateNames = response.alternateNames,
            species = response.species,
            gender = response.gender,
            house = response.house,
            dateOfBirth = response.dateOfBirth,
            yearOfBirth = response.yearOfBirth,
            isWizard = response.isWizard,
            ancestry = response.ancestry,
            eyeColour = response.eyeColour,
            hairColour = response.hairColour,
            wandConverter = response.wand?.let { wand -> WandConverter.fromResponse(response = wand) },
            patronus = response.patronus,
            isHogwartsStudent = response.isHogwartsStudent,
            isHogwartsStaff = response.isHogwartsStaff,
            actor = response.actor,
            alternateActors = response.alternateActors,
            isAlive = response.isAlive,
            image = response.image
        )
    }
}