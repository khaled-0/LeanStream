import java.io.BufferedReader

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization") version "1.9.22"
}

val gitCommitHash = Runtime.getRuntime().exec("git rev-parse --short HEAD").let { process ->
    process.waitFor()
    val output = process.inputStream.use {
        it.bufferedReader().use(BufferedReader::readText)
    }
    process.destroy()
    output.trim()
}

android {
    namespace = "dev.khaled.leanstream"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.khaled.leanstream"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        vectorDrawables {
            useSupportLibrary = true
        }

    }

    signingConfigs {
        create("release") {
            keyAlias = "leanstream"
            keyPassword = System.getenv("LEANSTREAM_KEY_PASSWORD")
            storeFile = file("../leanstream.jks")
            storePassword = System.getenv("LEANSTREAM_KEY_PASSWORD")
        }
    }

    buildTypes {
        named("debug") {
            applicationIdSuffix = ".debug"
        }

        create("preview") {
            initWith(getByName("release"))

            signingConfig = signingConfigs.getByName("debug")
            val debugType = getByName("debug")
            versionNameSuffix = gitCommitHash
            applicationIdSuffix = debugType.applicationIdSuffix
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.6.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-cbor:1.6.3")

    implementation("androidx.core:core-splashscreen:1.0.1")

    implementation(platform("androidx.compose:compose-bom:2024.02.02"))
    implementation("androidx.compose.material3:material3")
    implementation("androidx.tv:tv-material:1.0.0-alpha10")


    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.compose.material:material-icons-extended:1.6.3")
    
    implementation("androidx.media3:media3-exoplayer:1.3.0")
    implementation("androidx.media3:media3-exoplayer-hls:1.3.0")
    implementation("androidx.media3:media3-exoplayer-dash:1.3.0")
    implementation("androidx.media3:media3-ui:1.3.0")

    implementation("com.github.bjoernpetersen:m3u-parser:1.4.0")
}