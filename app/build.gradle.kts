import org.jetbrains.kotlin.gradle.internal.kapt.incremental.UnknownSnapshot

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)



    id("com.google.devtools.ksp") version "1.9.10-1.0.13" // KSP plugin for annotation processing
    id("kotlin-kapt") // Kapt plugin for annotation processing
}

android {
    namespace = "com.example.meals"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.meals"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }


    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
    }
}



dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.filament.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // Versions
    val nav_version = "2.3.5"
    val lifecycle_version = "2.4.0"


    // Core Libraries
    implementation ("com.google.android.material:material:1.9.0")
    implementation ("androidx.core:core-ktx:1.7.0")
    implementation ("androidx.appcompat:appcompat:1.3.1")
    implementation ("com.google.android.material:material:1.4.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.1")
    implementation ("com.intuit.sdp:sdp-android:1.0.6")
// For SSP, use this:
    implementation ("com.intuit.ssp:ssp-android:1.0.6")


    // Navigation Component and ViewModel
    implementation ("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation ("androidx.navigation:navigation-ui-ktx:$nav_version")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation ("androidx.lifecycle:lifecycle-extensions:2.1.0")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // Glide
    implementation ("com.github.bumptech.glide:glide:4.12.0")

    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0-alpha01")
    // Room
    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$room_version")

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")
    implementation ("androidx.room:room-runtime:$room_version")
    implementation ("androidx.room:room-runtime:2.5.2")

    implementation ("androidx.room:room-ktx:2.5.2")

    // GIF Support
    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.17")


}


