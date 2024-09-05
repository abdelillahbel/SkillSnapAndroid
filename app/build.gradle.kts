/*
 * Copyright (c) 2024. DevUnion Foundation.
 * GitHub: https://github.com/devunionorg
 * All rights reserved.
 *
 * This project was conceptualized and developed by @abdelillahbel.
 * GitHub: https://github.com/abdelillahbel
 */

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "dev.devunion.skillsnap"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.devunion.skillsnap"
        minSdk = 24
        targetSdk = 34
        versionCode = 7
        versionName = "0.1.3"

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

    // Firebase
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)


    val navVersion = "2.7.2"

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    implementation("androidx.compose.material3:material3-window-size-class:1.1.1")
    implementation("androidx.compose.foundation:foundation:1.5.1")
    implementation("androidx.compose.runtime:runtime-livedata:1.5.1")

    // system UI Controller
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.27.0")

    // Extended Icons
    implementation("androidx.compose.material:material-icons-extended:1.6.7")

    // accompanist
    implementation("com.google.accompanist:accompanist-pager:0.33.1-alpha")
    implementation("com.google.accompanist:accompanist-swiperefresh:0.33.1-alpha")
    implementation("com.google.accompanist:accompanist-placeholder-material:0.33.1-alpha")

    // view model
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    // preference
    implementation("androidx.preference:preference-ktx:1.2.1")

    // eventbus
    implementation("org.greenrobot:eventbus:3.3.1")

    // navigation
    implementation("androidx.navigation:navigation-compose:$navVersion")
    // firebase
//    implementation("com.google.firebase:firebase-auth-ktx:22.1.1")
//    implementation("com.google.firebase:firebase-firestore-ktx:24.7.1")
//    implementation("com.google.firebase:firebase-storage-ktx:20.2.1")
    implementation("com.google.firebase:firebase-database-ktx:20.2.2")
    // google fitness
    implementation("com.google.android.gms:play-services-fitness:21.1.0")
    // google play auth
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    // coil
    implementation("io.coil-kt:coil:2.4.0")
    implementation("io.coil-kt:coil-compose:2.4.0")
    // Hilt
    implementation("com.google.dagger:hilt-android:2.44")
    // kapt("com.google.dagger:hilt-android-compiler:2.44")
    implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    implementation ("androidx.core:core-splashscreen:1.0.1")



}
