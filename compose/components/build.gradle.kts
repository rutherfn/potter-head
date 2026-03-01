plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.nicholas.rutherford.potter.head.compose.components"
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
    buildFeatures {
        compose = true
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
    }
}

dependencies {
    implementation(project(path = ":compose:ui-theme"))

    api(libs.androidx.compose.material3.android)
    api(libs.androidx.compose.runtime.android)
    api(libs.androidx.lifecycle.common)
    api(libs.kotlinx.coroutines.core)

    implementation(libs.androidx.compose.foundation.android)
    implementation(libs.androidx.compose.ui.android)
    implementation(libs.androidx.compose.ui.graphics.android)
    implementation(libs.androidx.compose.ui.text.android)
    implementation(libs.androidx.compose.ui.tooling.preview.android)
    implementation(libs.androidx.compose.ui.unit.android)
    implementation(libs.androidx.lifecycle.runtime.compose.android)
    implementation(libs.androidx.compose.material.icons.extended.android)

    androidTestImplementation(platform(libs.androidx.compose.bom))

    debugRuntimeOnly(libs.androidx.compose.ui.test.manifest)
}