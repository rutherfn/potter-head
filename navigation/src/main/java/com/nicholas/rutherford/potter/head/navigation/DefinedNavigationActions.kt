package com.nicholas.rutherford.potter.head.navigation

import androidx.navigation.NavOptions
import com.nicholas.rutherford.potter.head.core.Constants

object DefinedNavigationActions {

    object Characters {

        fun characterDetails(id: String) = object : NavigationAction {
            override val destination: String = "${Constants.NavigationDestinations.CHARACTER_DETAIL_SCREEN}?id=$id"
            override val navOptions = NavOptions.Builder().build()
            }
        }
    }