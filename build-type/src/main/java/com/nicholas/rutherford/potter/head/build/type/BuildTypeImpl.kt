package com.nicholas.rutherford.potter.head.build.type

import com.nicholas.rutherford.potter.head.core.Constants

/**
 * Created by Nicholas Rutherford, last edited on 2025-08-16
 *
 * Implementation of the [BuildType] interface, providing information about
 * the current build configuration, version namee, and SDK version.
 *
 * @property sdkValue The SDK version of the current build.
 * @property buildTypeValue The type of the build as a string (debug, release, or stage).
 */
class BuildTypeImpl(
    sdkValue: Int,
    versionName: String,
    private val buildTypeValue: String
) : BuildType {

    override val versionNameValue = versionName

    override val sdk = sdkValue

    override fun isDebug(): Boolean = buildTypeValue == Constants.DEBUG_VERSION_NAME

    override fun isRelease(): Boolean = buildTypeValue == Constants.RELEASE_VERSION_NAME

    override fun isStage(): Boolean = buildTypeValue == Constants.STAGE_VERSION_NAME
}