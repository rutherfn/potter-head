package com.nicholas.rutherford.potter.head.build.type

/**
 * Interface representing the type of the current app build, build version name, and its SDK version.
 * Provides helper functions to check if the current build is debug, release, or stage.
 *
 * @author Nicholas Rutherford
 */
interface BuildType {
    val versionNameValue: String
    val sdk: Int
    fun isDebug(): Boolean
    fun isRelease(): Boolean
    fun isStage(): Boolean
}