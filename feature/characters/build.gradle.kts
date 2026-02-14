plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.nicholas.rutherford.potter.head.feature.characters"
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

    api(project(path = ":database"))
    api(project(path = ":navigation"))
    api(project(path = ":network"))
    implementation(project(path = ":core"))

    androidTestImplementation(libs.androidx.test.monitor)
    api(libs.androidx.compose.runtime.android)
    api(libs.androidx.lifecycle.viewmodel.android)
    api(libs.androidx.lifecycle.viewmodel.savedstate.android)
    api(libs.kotlinx.coroutines.core)
    api(libs.androidx.compose.foundation.layout.android)
    implementation(libs.androidx.compose.foundation.android)
    implementation(libs.androidx.compose.material3.android)
    implementation(libs.androidx.compose.ui.android)
    implementation(libs.androidx.compose.ui.graphics.android)
    implementation(libs.androidx.compose.ui.text.android)
    implementation(libs.androidx.compose.ui.tooling.preview.android)
    implementation(libs.androidx.compose.ui.unit.android)
    implementation(libs.androidx.navigation.common)

    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))

    debugRuntimeOnly(libs.androidx.compose.ui.test.manifest)
}