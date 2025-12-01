package com.nicholas.rutherford.potter.head.entry.point.navigation

import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.test.utils.ext.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ScreensTest {

    @Nested
    inner class Characters {

        @Test
        fun `route should be set to correct navigation destination`() {
            Screens.Characters.route shouldBe Constants.NavigationDestinations.CHARACTERS_SCREEN
        }

        @Test
        fun `title should be set to correct screen title`() {
            Screens.Characters.title shouldBe Constants.ScreenTitles.CHARACTERS
        }
    }

    @Nested
    inner class CharactersDetail {

        @Test
        fun `route should be set to correct navigation destination with parameters`() {
            Screens.CharactersDetail.route shouldBe Constants.NavigationDestinations.CHARACTER_DETAIL_SCREEN_WITH_PARAMS
        }

        @Test
        fun `title should be set to correct screen title`() {
            Screens.CharactersDetail.title shouldBe Constants.ScreenTitles.CHARACTER_DETAIL
        }
    }

    @Nested
    inner class Quizzes {

        @Test
        fun `route should be set to correct navigation destination`() {
            Screens.Quizzes.route shouldBe Constants.NavigationDestinations.QUIZZES_SCREEN
        }

        @Test
        fun `title should be set to correct screen title`() {
            Screens.Quizzes.title shouldBe Constants.ScreenTitles.QUIZZES
        }
    }

    @Nested
    inner class Settings {

        @Test
        fun `route should be set to correct navigation destination`() {
            Screens.Settings.route shouldBe Constants.NavigationDestinations.SETTINGS_SCREEN
        }

        @Test
        fun `title should be set to correct screen title`() {
            Screens.Settings.title shouldBe Constants.ScreenTitles.SETTINGS
        }
    }
}

