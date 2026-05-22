import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ktlint)
}

/**
 * Local: copy [keystore.properties.example] to keystore.properties with an absolute storeFile path.
 * CI: stage/release workflows set RELEASE_STORE_FILE and related env vars from GitHub Secrets.
 */
fun loadReleaseSigningProperties(rootDir: java.io.File): Properties? {
    val keystorePropertiesFile = rootDir.resolve("keystore.properties")
    if (keystorePropertiesFile.isFile) {
        return Properties().apply {
            keystorePropertiesFile.inputStream().use { load(it) }
        }
    }
    val storeFile = System.getenv("RELEASE_STORE_FILE")
    val storePassword = System.getenv("RELEASE_STORE_PASSWORD")
    val keyAlias = System.getenv("RELEASE_KEY_ALIAS")
    val keyPassword = System.getenv("RELEASE_KEY_PASSWORD")
    if (
        !storeFile.isNullOrBlank() &&
        !storePassword.isNullOrBlank() &&
        !keyAlias.isNullOrBlank() &&
        !keyPassword.isNullOrBlank()
    ) {
        return Properties().apply {
            setProperty("storeFile", storeFile)
            setProperty("storePassword", storePassword)
            setProperty("keyAlias", keyAlias)
            setProperty("keyPassword", keyPassword)
        }
    }
    return null
}

val releaseSigningProperties = loadReleaseSigningProperties(rootProject.projectDir)

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

    signingConfigs {
        if (releaseSigningProperties != null) {
            create("release") {
                val signing = releaseSigningProperties!!
                storeFile = file(signing.getProperty("storeFile"))
                storePassword = signing.getProperty("storePassword")
                keyAlias = signing.getProperty("keyAlias")
                keyPassword = signing.getProperty("keyPassword")
            }
        }
    }

    buildTypes {
        release {
            applicationIdSuffix = ".release"
            isMinifyEnabled = false
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            releaseSigningProperties?.let {
                signingConfig = signingConfigs.getByName("release")
            }
        }
        create("stage") {
            initWith(getByName("debug"))
            applicationIdSuffix = ".stage"
            isMinifyEnabled = false
            isDebuggable = true
            releaseSigningProperties?.let {
                signingConfig = signingConfigs.getByName("release")
            }
        }
        debug {
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
            isDebuggable = true
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
        buildConfig = true
        compose = true
    }
}

dependencies {
    implementation(project(path = ":base:view-model"))
    implementation(project(path = ":build-type"))
    implementation(project(path = ":data-store"))
    implementation(project(path = ":entry-point"))
    implementation(project(path = ":feature:characters"))
    implementation(project(path = ":feature:quizzes"))
    implementation(project(path = ":feature:spells"))
    implementation(project(path = ":feature:settings"))
    implementation(project(path = ":navigation"))
    implementation(project(path = ":network"))
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

    implementation(libs.androidx.appcompat)
    implementation(libs.kotlin.stdlib.version)
    implementation(libs.kotlinx.coroutines.android)
}
