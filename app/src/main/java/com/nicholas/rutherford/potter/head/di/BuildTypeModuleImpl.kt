package com.nicholas.rutherford.potter.head.di

import android.os.Build
import com.nicholas.rutherford.potter.head.BuildConfig
import com.nicholas.rutherford.potter.head.build.type.BuildType
import com.nicholas.rutherford.potter.head.build.type.BuildTypeImpl
import com.nicholas.rutherford.potter.head.build.type.di.BuildTypeModule

/**
 * Implementation of [BuildTypeModule].
 * Provides the build type dependency.
 *
 * @author Nicholas Rutherford
 */
class BuildTypeModuleImpl () : BuildTypeModule {
    override val buildType: BuildType by lazy {
        BuildTypeImpl(
            sdkValue = Build.VERSION.SDK_INT,
            versionName = BuildConfig.VERSION_NAME,
            buildTypeValue = "debug" // placeholder todo -> update this by passing in a manifest value Trello: https://trello.com/c/nompwO4w/47-add-build-type-functionality
        )
    }
}