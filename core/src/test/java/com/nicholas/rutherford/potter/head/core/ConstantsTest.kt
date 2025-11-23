package com.nicholas.rutherford.potter.head.core

import com.nicholas.rutherford.potter.head.test.utils.shouldBe
import org.junit.jupiter.api.Test

class ConstantsTest {

    @Test
    fun `BASE_API_URL should be set to the correct Harry Potter API URL`() {
        Constants.BASE_API_URL shouldBe "https://hp-api.onrender.com/api/"
    }
}

