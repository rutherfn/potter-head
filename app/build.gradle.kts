plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.metro)
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

metro {
    enabled = true
    debug = false
}

dependencies {
    implementation(project(path = ":base:view-model"))
    implementation(project(path = ":entry-point"))
    implementation(project(path = ":network"))

    // Compose Runtime required for Compose Compiler
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.runtime)

    // Lifecycle ViewModel for ViewModelFactory
    implementation(libs.androidx.lifecycle.viewmodel)
}
