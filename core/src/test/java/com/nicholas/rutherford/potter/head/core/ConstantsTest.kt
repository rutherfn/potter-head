package com.nicholas.rutherford.potter.head.core

import com.nicholas.rutherford.potter.head.test.utils.ext.shouldBe
import org.junit.jupiter.api.Test

class ConstantsTest {

    @Test
    fun `BASE_API_URL should be set to the correct Harry Potter API URL`() {
        Constants.BASE_API_URL shouldBe "https://hp-api.onrender.com/api/"
    }

    @Test
    fun `NETWORK_MODULE_CLASS_NAME should be set to the correct NetworkModule class name`() {
        Constants.NETWORK_MODULE_CLASS_NAME shouldBe "com.nicholas.rutherford.potter.head.network.di.NetworkModule"
    }

    @Test
    fun `NETWORK_MODULE_NAME should be set to correct expected name`() {
        Constants.NETWORK_MODULE_NAME shouldBe "NetworkModule"
    }

    @Test
    fun `NAVIGATOR_MODULE_NAME should be set to correct expected name`() {
        Constants.NAVIGATOR_MODULE_NAME shouldBe "NavigatorModule"
    }

    @Test
    fun `NAVIGATOR_MODULE_CLASS_NAME should be set to the correct NavigatorModule class name`() {
        Constants.NAVIGATOR_MODULE_CLASS_NAME shouldBe "com.nicholas.rutherford.potter.head.navigation.di.NavigatorModule"
    }

    @Test
    fun `NavigationDestinations CHARACTERS_SCREEN should be set to correct expected value`() {
        Constants.NavigationDestinations.CHARACTERS_SCREEN shouldBe "charactersScreen"
    }

    @Test
    fun `NavigationDestinations CHARACTER_DETAIL_SCREEN should be set to correct expected value`() {
        Constants.NavigationDestinations.CHARACTER_DETAIL_SCREEN shouldBe "characterDetailScreen"
    }

    @Test
    fun `NavigationDestinations CHARACTER_DETAIL_SCREEN_WITH_PARAMS should be set to correct expected value`() {
        Constants.NavigationDestinations.CHARACTER_DETAIL_SCREEN_WITH_PARAMS shouldBe "characterDetailScreen/{name}"
    }

    @Test
    fun `NavigationDestinations QUIZZES_SCREEN should be set to correct expected value`() {
        Constants.NavigationDestinations.QUIZZES_SCREEN shouldBe "quizzesScreen"
    }

    @Test
    fun `NavigationDestinations QUIZ_SELECTED_DETAIL_SCREEN should be set to correct expected value`() {
        Constants.NavigationDestinations.QUIZ_SELECTED_DETAIL_SCREEN shouldBe "quizSelectedDetailScreen"
    }

    @Test
    fun `NavigationDestinations TOUR_THE_APP_SCREEN should be set to correct expected value`() {
        Constants.NavigationDestinations.TOUR_THE_APP_SCREEN shouldBe "tourTheAppScreen"
    }

    @Test
    fun `NavigationDestinations SETTINGS_SCREEN should be set to correct expected value`() {
        Constants.NavigationDestinations.SETTINGS_SCREEN shouldBe "settingsScreen"
    }

    @Test
    fun `ScreenTitles CHARACTERS should be set to correct expected value`() {
        Constants.ScreenTitles.CHARACTERS shouldBe "Characters"
    }

    @Test
    fun `ScreenTitles CHARACTER_DETAIL should be set to correct expected value`() {
        Constants.ScreenTitles.CHARACTER_DETAIL shouldBe "Details"
    }

    @Test
    fun `ScreenTitles QUIZZES should be set to correct expected value`() {
        Constants.ScreenTitles.QUIZZES shouldBe "Quizzes"
    }

    @Test
    fun `ScreenTitles SETTINGS should be set to correct expected value`() {
        Constants.ScreenTitles.SETTINGS shouldBe "Settings"
    }

    @Test
    fun `NamedArguments CHARACTER_NAME should be set to correct expected value`() {
        Constants.NamedArguments.CHARACTER_NAME shouldBe "name"
    }
}
