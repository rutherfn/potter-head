package com.nicholas.rutherford.potter.head.feature.settings

import android.app.Application
import com.nicholas.rutherford.potter.head.base.view.model.BaseViewModel
import com.nicholas.rutherford.potter.head.base.view.model.FlowCollectionTrigger
import com.nicholas.rutherford.potter.head.build.type.BuildType
import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.core.StringIds
import com.nicholas.rutherford.potter.head.core.theme.ThemePreference
import com.nicholas.rutherford.potter.head.core.theme.buildThemePreferenceFromValue
import com.nicholas.rutherford.potter.head.database.repository.CharacterFilterRepository
import com.nicholas.rutherford.potter.head.database.repository.SavedQuizRepository
import com.nicholas.rutherford.potter.head.database.repository.getActiveFilterCount
import com.nicholas.rutherford.potter.head.feature.data.store.reader.DataStorePreferenceReader
import com.nicholas.rutherford.potter.head.feature.data.store.writer.DataStorePreferencesWriter
import com.nicholas.rutherford.potter.head.navigation.AlertAction
import com.nicholas.rutherford.potter.head.navigation.AlertConfirmAndDismissButton
import com.nicholas.rutherford.potter.head.navigation.Navigator
import com.nicholas.rutherford.potter.head.navigation.SimpleNavigationAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingsViewModel(
    private val application: Application,
    private val navigator: Navigator,
    private val buildType: BuildType,
    private val characterFilterRepository: CharacterFilterRepository,
    private val savedQuizRepository: SavedQuizRepository,
    private val dataStorePreferenceReader: DataStorePreferenceReader,
    private val dataStorePreferencesWriter: DataStorePreferencesWriter
) : BaseViewModel() {

    override val screenTitle: String = Constants.ScreenTitles.SETTINGS

    override fun getFlowCollectionTrigger(): FlowCollectionTrigger = FlowCollectionTrigger.INIT

    private val settingsMutableStateFlow = MutableStateFlow(SettingsState())
    val settingsStateFlow: StateFlow<SettingsState> = settingsMutableStateFlow.asStateFlow()

    init {
        setInitialState()
        collectDataStoreFlows()
    }

    internal fun setInitialState() {
        settingsMutableStateFlow.update { state ->
            state.copy(
                themeOptions = listOf(
                    application.getString(StringIds.light),
                    application.getString(StringIds.dark),
                    application.getString(StringIds.system)
                ),
                versionName = buildType.versionNameValue,
                isDebug = buildType.isDebug()
            )
        }
    }

    internal fun collectDataStoreFlows() {
        launch {
            collectFlows(
                flow1 = dataStorePreferenceReader.readThemePreferenceValueFlow(),
                flow2 = dataStorePreferenceReader.readShouldShuffleAnswerOrderFlow()
            ) { preferenceValue, shouldShuffleAnswerOrder ->
                settingsMutableStateFlow.update { state ->
                    state.copy(
                        themeOptions = listOf(
                            application.getString(StringIds.light),
                            application.getString(StringIds.dark),
                            application.getString(StringIds.system)
                        ),
                        versionName = buildType.versionNameValue,
                        selectedTheme = buildThemePreferenceFromValue(value = preferenceValue),
                        shouldShuffleAnswerOrderChecked = shouldShuffleAnswerOrder
                    )}
            }
        }
    }

    fun onThemePreferenceSelected(value: ThemePreference) {
        launch {
            dataStorePreferencesWriter.saveThemePreferenceValue(value = value.value)
        }
    }

    fun onShuffleAnswerOrderCheckedChanged(value: Boolean) {
        launch {
            dataStorePreferencesWriter.saveShouldShuffleAnswerOrder(value = value)
        }
    }

    fun onClearSavedQuizzesYesClicked() {
        launch {
            savedQuizRepository.deleteAllSavedQuizzes()
            navigator.toastAction(toastAction = application.getString(StringIds.savedQuizzesHaveBeenCleared))
        }
    }

    fun onClearCharacterFiltersYesClicked() {
        launch {
            characterFilterRepository.resetFilters()
            navigator.toastAction(toastAction = application.getString(StringIds.savedCharacterFiltersHaveBeenCleared))
        }
    }

    fun clearSavedQuizzesAlert(): AlertAction {
        return AlertAction(
            title = application.getString(StringIds.clearSavedQuizzes),
            description = application.getString(StringIds.areYouSureYouWantToClearAllSavedQuizzes),
            confirmButton = AlertConfirmAndDismissButton(
                buttonText = application.getString(StringIds.yes),
                onButtonClicked = { onClearSavedQuizzesYesClicked() }
            ),
            dismissButton = AlertConfirmAndDismissButton(
                buttonText = application.getString(StringIds.no)
            )
        )
    }

    fun clearCharacterFiltersAlert(): AlertAction {
        return AlertAction(
            title = application.getString(StringIds.clearCharacterFilters),
            description = application.getString(StringIds.areYouSureYouWantToClearCharacterFilters),
            confirmButton = AlertConfirmAndDismissButton(
                buttonText = application.getString(StringIds.yes),
                onButtonClicked = { onClearCharacterFiltersYesClicked() }
            ),
            dismissButton = AlertConfirmAndDismissButton(buttonText = application.getString(StringIds.no))
        )
    }

    fun onViewDataSourceClicked() = navigator.url(url = Constants.API_DOCUMENT_WEBSITE)

    fun onClearSavedQuizzesClick() {
        launch {
            val savedQuizCount = savedQuizRepository.getAllSavedQuizzesCount()

            if (savedQuizCount == 0 ) {
                navigator.toastAction(toastAction = application.getString(StringIds.noSavedQuizzesToReset))
            } else {
                navigator.alert(alertAction = clearSavedQuizzesAlert())
            }
        }
    }

    fun onResetCharacterFiltersClick() {
        launch {
            val filterCount = characterFilterRepository.getActiveFilterCount()

            if (filterCount == 0) {
                navigator.toastAction(toastAction = application.getString(StringIds.noCharacterFiltersToReset))
            } else {
                navigator.alert(alertAction = clearCharacterFiltersAlert())
            }
        }
    }

    fun onOpenQuizResultUrls() {
        navigator.navigate(navigationAction = SimpleNavigationAction(destination = Constants.NavigationDestinations.QUIZ_RESULT_URLS_SCREEN))
    }

}
