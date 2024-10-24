plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.jetbrains.kotlin.plugin.compose)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    id("kotlin-parcelize")
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.myadmi"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myadmi"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.database)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    implementation( "androidx.compose.ui:ui:1.5.0")
    implementation ("androidx.compose.material3:material3:1.0.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.0")
    implementation ("androidx.compose.runtime:runtime-livedata:1.5.0")
    implementation ("androidx.compose.runtime:runtime:1.5.0")

    //implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    //implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")


    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.coil.compose)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Retrofit for network requests
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.androidx.material.icons.extended)

    implementation ("androidx.navigation:navigation-compose:2.7.1")

    implementation ("androidx.compose.material3:material3:1.1.0")
    implementation ("com.google.accompanist:accompanist-swiperefresh:0.30.1")


    implementation ("androidx.compose.animation:animation:1.0.5")
}