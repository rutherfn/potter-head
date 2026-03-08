package com.nicholas.rutherford.potter.head.entry.point.navigation

import androidx.navigation.NavType
import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.test.utils.ext.shouldBe
import com.nicholas.rutherford.potter.head.test.utils.ext.shouldNotBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class NavArgumentsTest {

    @Nested
    inner class CharacterDetail {

        @Test
        fun `should not be null`() {
            NavArguments.characterDetail shouldNotBe null
        }

        @Test
        fun `should contain exactly one argument`() {
            NavArguments.characterDetail.size shouldBe 1
        }

        @Test
        fun `should have argument with correct name`() {
            NavArguments.characterDetail.first().name shouldBe Constants.NamedArguments.CHARACTER_NAME
        }

        @Test
        fun `should have argument with correct type`() {
            NavArguments.characterDetail.first().argument.type shouldBe NavType.StringType
        }
    }
}

