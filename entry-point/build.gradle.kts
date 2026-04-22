plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.nicholas.rutherford.potter.head.entry.point"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }
    buildFeatures {
        compose = true
    }

    @Suppress("UnstableApiUsage")
    testOptions {
        unitTests {
            isIncludeAndroidResources = false
            all {
                it.useJUnitPlatform()
            }
        }
    }
}

dependencies {
    implementation(project(path = ":base:view-model"))
    implementation(project(path = ":compose:ui-theme"))
    implementation(project(path = ":navigation"))
    implementation(project(path = ":feature:characters"))
    implementation(project(path = ":feature:quizzes"))
    implementation(project(path = ":feature:spells"))
    implementation(project(path = ":feature:settings"))
    implementation(project(path = ":compose:components"))
    implementation(project(path = ":core"))

    api(libs.androidx.compose.runtime.android)
    api(libs.androidx.navigation.common)

    debugApi(libs.androidx.compose.animation.android)

    debugImplementation(libs.androidx.compose.foundation.android)
    debugImplementation(libs.androidx.compose.foundation.layout.android)

    implementation(libs.androidx.compose.material3.android)
    implementation(libs.androidx.compose.material.icons.core.android)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.ui.android)
    implementation(libs.androidx.compose.ui.text.android)
    implementation(libs.androidx.lifecycle.common)
    implementation(libs.androidx.lifecycle.runtime.compose.android)
    implementation(libs.androidx.lifecycle.viewmodel.android)
    implementation(libs.androidx.lifecycle.viewmodel.compose.android)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.runtime)
    implementation(libs.kotlinx.coroutines.core)

    releaseApi(libs.androidx.compose.animation.android)

    releaseImplementation(libs.androidx.compose.foundation.android)
    releaseImplementation(libs.androidx.compose.foundation.layout.android)

    api(libs.androidx.activity)
    api(libs.kotlin.stdlib.version)

    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(project(path = ":test-utils"))

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.test.monitor)
    androidTestImplementation(libs.junit)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugRuntimeOnly(libs.androidx.compose.ui.test.manifest)
}