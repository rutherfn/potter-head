package com.nicholas.rutherford.potter.head.entry.point.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.nicholas.rutherford.potter.head.core.Constants

object NavArguments {

    val characterDetail = listOf(
        navArgument(Constants.NamedArguments.ID) { type = NavType.StringType }
    )
}