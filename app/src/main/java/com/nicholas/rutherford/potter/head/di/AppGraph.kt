package com.nicholas.rutherford.potter.head.di

import com.nicholas.rutherford.potter.head.database.di.DatabaseModule
import com.nicholas.rutherford.potter.head.entry.point.di.AppBarFactoryModule
import com.nicholas.rutherford.potter.head.navigation.di.NavigatorModule
import com.nicholas.rutherford.potter.head.network.di.NetworkModule
import com.nicholas.rutherford.potter.head.scope.di.ScopeModule

/**
 * Root dependency graph for the application.
 * Aggregates all feature modules and provides access to their dependencies.
 *
 * @author Nicholas Rutherford
 */
interface AppGraph {
    val networkModule: NetworkModule
    val navigatorModule: NavigatorModule
    val appBarModule: AppBarFactoryModule
    val databaseModule: DatabaseModule
    val scopeModule: ScopeModule
}
