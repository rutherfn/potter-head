package com.nicholas.rutherford.potter.head.feature.characters.characterdetail

import android.app.Application
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import com.nicholas.rutherford.potter.head.base.view.model.BaseViewModel
import com.nicholas.rutherford.potter.head.base.view.model.FlowCollectionTrigger
import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.core.StringIds
import com.nicholas.rutherford.potter.head.database.converter.CharacterConverter
import com.nicholas.rutherford.potter.head.database.dao.CharacterDao
import com.nicholas.rutherford.potter.head.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.collections.buildList

class CharacterDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val characterDao: CharacterDao,
    private val navigator: Navigator,
    private val application: Application
) : BaseViewModel() {

    private val characterDetailMutableStateFlow = MutableStateFlow(value = CharacterDetailState())
    val characterDetailStateFlow: StateFlow<CharacterDetailState> = characterDetailMutableStateFlow.asStateFlow()
    internal val characterNameParam: String = savedStateHandle.get<String>(Constants.NamedArguments.CHARACTER_NAME)?.let { value -> Uri.decode(value) } ?: ""

    init {
        loadCharacterDetail()
    }

    override fun getFlowCollectionTrigger(): FlowCollectionTrigger = FlowCollectionTrigger.INIT

    private fun loadCharacterDetail() {
        if (characterNameParam.isNotEmpty()) {
            collectFlow(
                characterDao.getCharacterByName(name = characterNameParam)
            ) { characterEntity ->
                val character = CharacterConverter.fromEntity(entity = characterEntity)
                characterDetailMutableStateFlow.update { state ->
                    state.copy(
                        character = character,
                        basicInfoItems = buildBasicInfoItems(character),
                        personalInfoItems = buildPersonalInfoItems(character),
                        hogwartsInfoItems = buildHogwartsInfoItems(character),
                        additionalInfoItems = buildAdditionalInfoItems(character)
                    )
                }
            }
        } else {
            onBackClicked()
        }
    }

    fun onBackClicked() = navigator.pop(routeAction = Constants.NavigationDestinations.CHARACTERS_SCREEN)

    private fun buildBasicInfoItems(character: CharacterConverter): List<CharacterDetailInfoItem> {
        return buildList {
            if (character.species.isNotEmpty()) {
                add(CharacterDetailInfoItem(
                    labelId = StringIds.species,
                    value = character.species.replaceFirstChar { firstChar -> firstChar.uppercaseChar() }
                ))
            }
            if (character.gender.isNotEmpty()) {
                add(CharacterDetailInfoItem(
                    labelId = StringIds.gender,
                    value = character.gender.replaceFirstChar { firstChar -> firstChar.uppercaseChar() }
                ))
            }
            add(CharacterDetailInfoItem(
                labelId = StringIds.wizard,
                value = if (character.isWizard) application.getString(StringIds.yes) else application.getString(StringIds.no)
            ))
            add(CharacterDetailInfoItem(
                labelId = StringIds.alive,
                value = if (character.isAlive) application.getString(StringIds.yes) else application.getString(StringIds.no)
            ))
        }
    }

    private fun buildPersonalInfoItems(character: CharacterConverter): List<CharacterDetailInfoItem> {
        return buildList {
            character.dateOfBirth?.let { dateOfBirth ->
                if (dateOfBirth.isNotEmpty()) {
                    add(CharacterDetailInfoItem(
                        labelId = StringIds.dateOfBirth,
                        value = dateOfBirth
                    ))
                }
            }
            character.ancestry?.let { ancestry ->
                if (ancestry.isNotEmpty()) {
                    add(CharacterDetailInfoItem(
                        labelId = StringIds.ancestry,
                        value =ancestry.replaceFirstChar { char -> char.uppercaseChar() }
                    ))
                }
            }
            character.eyeColour?.let { eyeColour ->
                if (eyeColour.isNotEmpty()) {
                    add(CharacterDetailInfoItem(
                        labelId = StringIds.eyeColour,
                        value = eyeColour.replaceFirstChar { char -> char.uppercaseChar() }
                    ))
                }
            }
            character.hairColour?.let { hairColour ->
                if (hairColour.isNotEmpty()) {
                    add(CharacterDetailInfoItem(
                        labelId = StringIds.hairColour,
                        value = hairColour.replaceFirstChar { char -> char.uppercaseChar() }
                    ))
                }
            }
        }
    }

    private fun buildHogwartsInfoItems(character: CharacterConverter): List<CharacterDetailInfoItem> {
        return buildList {
            if (character.isHogwartsStudent) {
                add(CharacterDetailInfoItem(
                    labelId = StringIds.status,
                    value = application.getString(StringIds.student)
                ))
            }
            if (character.isHogwartsStaff) {
                add(CharacterDetailInfoItem(
                    labelId = StringIds.status,
                    value = application.getString(StringIds.staff)
                ))
            }
            character.house?.let { house ->
                if (house.isNotEmpty()) {
                    add(CharacterDetailInfoItem(
                        labelId = StringIds.house,
                        value = house.replaceFirstChar { char -> char.uppercaseChar() }
                    ))
                }
            }
            character.patronus?.let { patronus ->
                if (patronus.isNotEmpty()) {
                    add(CharacterDetailInfoItem(
                        labelId = StringIds.patronus,
                        value = patronus.replaceFirstChar { char -> char.uppercaseChar() }
                    ))
                }
            }
        }
    }

    private fun buildAdditionalInfoItems(character: CharacterConverter): List<CharacterDetailInfoItem> {
        return buildList {
            character.actor?.let { actor ->
                if (actor.isNotEmpty()) {
                    add(CharacterDetailInfoItem(
                        labelId = StringIds.actor,
                        value = actor
                    ))
                }
            }
            if (character.alternateActors.isNotEmpty()) {
                add(CharacterDetailInfoItem(
                    labelId = StringIds.alternateActors,
                    value = character.alternateActors.joinToString(", ")
                ))
            }
            if (character.alternateNames.isNotEmpty()) {
                add(CharacterDetailInfoItem(
                    labelId = StringIds.alternateNames,
                    value = character.alternateNames.joinToString(", ")
                ))
            }
        }
    }
}