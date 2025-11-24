package com.nicholas.rutherford.potter.head.navigation

import androidx.navigation.NavOptions

/**
 * Simple data class implementation of [NavigationAction] for basic navigation.
 * Use this when you just need to navigate to a route without custom NavOptions.
 *
 * @param destination The route to navigate to.
 * @param navOptions Optional custom NavOptions. If not provided, uses default NavOptions.
 *
 * @author Nicholas Rutherford
 */
data class SimpleNavigationAction(
    override val destination: String,
    override val navOptions: NavOptions = NavOptions.Builder().build()
) : NavigationAction

