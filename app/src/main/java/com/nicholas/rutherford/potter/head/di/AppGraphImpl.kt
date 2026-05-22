package com.nicholas.rutherford.potter.head.di

import android.content.Context
import com.nicholas.rutherford.potter.head.build.type.di.BuildTypeModule
import com.nicholas.rutherford.potter.head.database.di.DatabaseModule
import com.nicholas.rutherford.potter.head.entry.point.di.AppBarFactoryModule
import com.nicholas.rutherford.potter.head.feature.data.store.di.DataStoreModule
import com.nicholas.rutherford.potter.head.navigation.di.NavigatorModule
import com.nicholas.rutherford.potter.head.network.di.NetworkModule
import com.nicholas.rutherford.potter.head.scope.di.ScopeModule

/**
 * Implementation of [AppGraph].
 * Aggregates all feature modules and provides access to their dependencies.
 *
 * @param context The application context for creating modules that require it.
 *
 * @author Nicholas Rutherford
 */
class AppGraphImpl(
    private val context: Context
) : AppGraph {
    override val buildTypeModule: BuildTypeModule by lazy { BuildTypeModuleImpl() }

    override val dataStoreModule: DataStoreModule by lazy { DataStoreModuleImpl(context = context) }

    override val networkModule: NetworkModule by lazy { NetworkModuleImpl(context = context) }

    override val navigatorModule: NavigatorModule by lazy { NavigatorModuleImpl() }

    override val scopeModule: ScopeModule by lazy { ScopeModuleImpl() }

    override val databaseModule: DatabaseModule by lazy { DatabaseModuleImpl(context = context, scopeModule = scopeModule) }

    override val appBarModule: AppBarFactoryModule by lazy { AppBarFactoryModuleImpl() }
}
