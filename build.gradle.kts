// Top-level build file where you can add configuration options common to all sub-projects/modules

import com.android.build.api.dsl.LibraryExtension

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.dependency.analysis)
}

dependencyAnalysis {
    issues {
        all {
            onUnusedDependencies {
                severity("fail")
            }
            onUsedTransitiveDependencies {
                severity("warn")
            }
            onIncorrectConfiguration {
                severity("fail")
            }
            onUnusedAnnotationProcessors {
                severity("fail")
            }
        }
    }
}

/**
 * Kermit publishes separate Maven variants per Android build type (debug vs release).
 * Custom "stage" cannot consume kermit-android-debug, so stage uses release Kermit.
 * Any module that already declares debug Kermit gets matching stage deps automatically.
 */
private fun Project.declaresKermitDebugDependency(): Boolean {
    val debugConfigurations = listOf("debugApi", "debugImplementation")
    return debugConfigurations.any { configurationName ->
        configurations.findByName(configurationName)
            ?.allDependencies
            ?.any { dependency ->
                dependency.group == "co.touchlab" && dependency.name.startsWith("kermit")
            } == true
    }
}

private fun Project.configureStageKermitDependencies() {
    if (!declaresKermitDebugDependency()) {
        return
    }

    if (configurations.findByName("stageApi") != null) {
        dependencies.add("stageApi", libs.kermit.android)
        dependencies.add("stageImplementation", libs.kermit.core.android)
    } else if (configurations.findByName("stageImplementation") != null) {
        dependencies.add("stageImplementation", libs.kermit.android)
        dependencies.add("stageImplementation", libs.kermit.core.android)
    }
}

subprojects {
    afterEvaluate {
        val isAndroidModule = plugins.hasPlugin("com.android.library") ||
            plugins.hasPlugin("com.android.application")

        if (!isAndroidModule) {
            return@afterEvaluate
        }

        if (plugins.hasPlugin("com.android.library")) {
            extensions.configure<LibraryExtension>("android") {
                buildTypes {
                    if (findByName("stage") == null) {
                        create("stage") {
                            initWith(getByName("debug"))
                            isMinifyEnabled = false
                        }
                    }
                }
            }
        }

        configureStageKermitDependencies()
    }
}