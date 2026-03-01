plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ktlint)
}

android {
    namespace = "com.nicholas.rutherford.potter.head"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.nicholas.rutherford.potter.head"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {
    implementation(project(path = ":base:view-model"))
    implementation(project(path = ":entry-point"))
    implementation(project(path = ":feature:characters"))
    implementation(project(path = ":feature:quizzes"))
    implementation(project(path = ":feature:settings"))
    implementation(project(path = ":navigation"))
    implementation(project(path = ":network"))
    implementation(project(path = ":saved-state"))
    implementation(project(path = ":database"))
    implementation(project(path = ":scope"))
    implementation(project(path = ":core"))

    debugImplementation(libs.kermit.android.debug)
    debugImplementation(libs.kermit.core.android.debug)

    releaseImplementation(libs.kermit.android)
    releaseImplementation(libs.kermit.core.android)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.runtime.android)

    implementation(libs.androidx.lifecycle.viewmodel.android)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate.android)

    implementation(libs.kotlin.stdlib.version)
}
