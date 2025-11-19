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
    implementation(project(path = ":compose:ui-theme"))
    implementation(libs.kotlin.stdlib)

    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    implementation(libs.androidx.activity)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui.text)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation("androidx.compose.material:material-icons-extended:1.7.8")

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.test.monitor)
    androidTestImplementation(libs.junit)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugRuntimeOnly(libs.androidx.compose.ui.test.manifest)
}
