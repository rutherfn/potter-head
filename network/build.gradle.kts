plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.nicholas.rutherford.potter.head.network"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
    }
}

dependencies {
    implementation(project(path = ":core"))
    api(project(path = ":model"))

    api(libs.gson)
    api(libs.retrofit)
    api(libs.converter.gson)
    api(libs.androidx.lifecycle.viewmodel.android)
    implementation(libs.androidx.core.ktx)

    debugImplementation(libs.kermit.android.debug)
    debugImplementation(libs.kermit.core.android.debug)

    releaseImplementation(libs.kermit.android)
    releaseImplementation(libs.kermit.core.android)

    implementation(libs.kotlinx.coroutines.core)

    testImplementation(project(path = ":test-utils"))

    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.kotlinx.coroutines.test)

    testRuntimeOnly(libs.junit.jupiter.engine)
}