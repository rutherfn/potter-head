package com.nicholas.rutherford.potter.head.core

import com.nicholas.rutherford.potter.head.test.utils.shouldBe
import org.junit.jupiter.api.Test

class ConstantsTest {

    @Test
    fun `BASE_API_URL should be set to the correct Harry Potter API URL`() {
        Constants.BASE_API_URL shouldBe "https://hp-api.onrender.com/api/"
    }

    @Test
    fun `APP_GRAPH_METRO_GRAPH_CLASS_NAME should be set to the correct Metro-generated class name`() {
        val expectedClassName = "com.nicholas.rutherford.potter.head.di.AppGraph$$\$MetroGraph"
        Constants.APP_GRAPH_METRO_GRAPH_CLASS_NAME shouldBe expectedClassName
    }

    @Test
    fun `NETWORK_MODULE_CLASS_NAME should be set to the correct NetworkModule class name`() {
        val expectedClassName = "com.nicholas.rutherford.potter.head.network.di.NetworkModule"
        Constants.NETWORK_MODULE_CLASS_NAME shouldBe expectedClassName
    }

    @Test
    fun `NETWORK_MODULE_METRO_GRAPH_CLASS_NAME should be set to the correct Metro-generated class name`() {
        val expectedClassName = "com.nicholas.rutherford.potter.head.network.di.NetworkModule$$\$MetroGraph"
        Constants.NETWORK_MODULE_METRO_GRAPH_CLASS_NAME shouldBe expectedClassName
    }
}

