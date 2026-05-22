package com.nicholas.rutherford.potter.head.build.type.di

import com.nicholas.rutherford.potter.head.build.type.BuildType

/**
 * Dependency graph interface for build type
 * Provides BuildType extension logic
 *
 * @author Nicholas Rutherford
 */
interface BuildTypeModule {
    val buildType: BuildType
}