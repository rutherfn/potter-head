package com.nicholas.rutherford.potter.head.test.utils.data

import com.nicholas.rutherford.potter.head.model.network.CharacterResponse

/**
 * Test utility object for creating [CharacterResponse] instances for testing purposes.
 *
 * This object provides factory methods to create test data for character responses,
 * which are used in unit tests. It provides the following:
 * - Default character data
 * - Methods to create lists of character responses
 * - Methods to create varied character responses representing different houses and scenarios
 *
 * @see CharacterResponse for the actual data model
 * @see TestWandResponse for creating wand data used in character responses
 *
 * @author Nicholas Rutherford
 */
object TestCharacterResponse {

    /**
     * Creates a default [CharacterResponse] instance with test data.
     *
     * @return A [CharacterResponse] instance with default test values representing Harry Potter.
     */
    fun build(): CharacterResponse =
        CharacterResponse(
            id = CHARACTER_RESPONSE_ID,
            name = CHARACTER_RESPONSE_NAME,
            alternateNames = CHARACTER_RESPONSE_ALTERNATE_NAMES,
            species = CHARACTER_RESPONSE_SPECIES,
            gender = CHARACTER_RESPONSE_GENDER,
            house = CHARACTER_RESPONSE_HOUSE,
            dateOfBirth = CHARACTER_RESPONSE_DATE_OF_BIRTH,
            yearOfBirth = CHARACTER_RESPONSE_YEAR_OF_BIRTH,
            isWizard = CHARACTER_RESPONSE_IS_WIZARD,
            ancestry = CHARACTER_RESPONSE_ANCESTRY,
            eyeColour = CHARACTER_RESPONSE_EYE_COLOUR,
            hairColour = CHARACTER_RESPONSE_HAIR_COLOUR,
            wand = TestWandResponse.build(),
            patronus = CHARACTER_RESPONSE_PATRONUS,
            isHogwartsStudent = CHARACTER_RESPONSE_IS_HOGWARTS_STUDENT,
            isHogwartsStaff = CHARACTER_RESPONSE_IS_HOGWARTS_STAFF,
            actor = CHARACTER_RESPONSE_ACTOR_NAME,
            alternateActors = emptyList(),
            isAlive = CHARACTER_RESPONSE_IS_ALIVE,
            image = CHARACTER_RESPONSE_IMAGE
        )

    /**
     * Creates a list of [CharacterResponse] instances with default test data.
     *
     * @param count The number of character responses to create. Defaults to 3.
     * @return A list of [CharacterResponse] instances, each with unique IDs and names.
     */
    fun buildList(count: Int = 3): List<CharacterResponse> = List(size = count) { index ->
        build().copy(
            id = "$CHARACTER_RESPONSE_ID-$index",
            name = "$CHARACTER_RESPONSE_NAME $index"
        )
    }

    /**
     * Creates a list of [CharacterResponse] instances with varied test data.
     *
     * @return A list of [CharacterResponse] instances representing different characters
     *         from all four Hogwarts houses with varied properties.
     */
    fun buildVariedList(): List<CharacterResponse> {
        return listOf(
            build(), // Default: Harry Potter
            build().copy(
                id = "hermione-granger-id",
                name = "Hermione Granger",
                house = "Gryffindor",
                yearOfBirth = 1979,
                patronus = "otter",
                actor = "Emma Watson"
            ),
            build().copy(
                id = "draco-malfoy-id",
                name = "Draco Malfoy",
                house = "Slytherin",
                yearOfBirth = 1980,
                ancestry = "pure-blood",
                patronus = null,
                actor = "Tom Felton"
            ),
            build().copy(
                id = "luna-lovegood-id",
                name = "Luna Lovegood",
                house = "Ravenclaw",
                yearOfBirth = 1981,
                patronus = "hare",
                actor = "Evanna Lynch"
            ),
            build().copy(
                id = "cedric-diggory-id",
                name = "Cedric Diggory",
                house = "Hufflepuff",
                yearOfBirth = 1977,
                isAlive = false,
                actor = "Robert Pattinson"
            )
        )
    }
}

const val CHARACTER_RESPONSE_ID = "harry-potter-id"
const val CHARACTER_RESPONSE_NAME = "Harry Potter"
val CHARACTER_RESPONSE_ALTERNATE_NAMES = listOf("The Bou Who Lived", "The Chosen One", "Undesirable No. 1", "Potty")
const val CHARACTER_RESPONSE_SPECIES = "human"
const val CHARACTER_RESPONSE_GENDER = "male"
const val CHARACTER_RESPONSE_HOUSE = "Gryffindor"
const val CHARACTER_RESPONSE_DATE_OF_BIRTH = "31-07-1980"
const val CHARACTER_RESPONSE_YEAR_OF_BIRTH = 1980
const val CHARACTER_RESPONSE_IS_WIZARD = true
const val CHARACTER_RESPONSE_ANCESTRY = "half-blood"
const val CHARACTER_RESPONSE_EYE_COLOUR = "green"
const val CHARACTER_RESPONSE_HAIR_COLOUR = "black"
const val CHARACTER_RESPONSE_PATRONUS = "stag"
const val CHARACTER_RESPONSE_IS_HOGWARTS_STUDENT = true
const val CHARACTER_RESPONSE_IS_HOGWARTS_STAFF = false
const val CHARACTER_RESPONSE_ACTOR_NAME = "Daniel Radcliffe"
const val CHARACTER_RESPONSE_IS_ALIVE = true
const val CHARACTER_RESPONSE_IMAGE = "https://ik.imagekit.io/hpapi/harry.jpg"

