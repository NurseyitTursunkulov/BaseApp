import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.application")
    id("kotlin-android")
    id( "kotlin-kapt")
}

android {
    compileSdk = 30
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.example.hellocompose"
        minSdk = 21
        targetSdk = 30
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra["compose_version"] as String
        kotlinCompilerVersion = "1.4.30"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.compose.ui:ui:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.material:material:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.ui:ui-tooling:${rootProject.extra["compose_version"]}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.activity:activity-compose:1.3.0-alpha07")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.extra["compose_version"]}")
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.8.1")
    implementation ("androidx.navigation:navigation-compose:1.0.0-alpha10")
    implementation ("androidx.compose.runtime:runtime-livedata:1.0.0-beta06")
    implementation ("androidx.compose.material:material-icons-extended:${rootProject.extra["compose_version"]}")
    // Koin main features for Android (Scope,ViewModel ...)
    val koin_version = "3.0.1"
    implementation ("io.insert-koin:koin-android:$koin_version")
// Koin Android - experimental builder extensions
    implementation( "io.insert-koin:koin-android-ext:$koin_version")
// Koin for Jetpack WorkManager
// Koin for Jetpack Compose (unstable version)
    implementation ("io.insert-koin:koin-androidx-compose:$koin_version")

    //Retrofit2
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
    //Okhttp3()
    implementation ("com.squareup.okhttp3:okhttp:4.9.1")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.1")

    //Kotlin Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")
    implementation ("com.google.accompanist:accompanist-insets:0.9.1")
    implementation ("io.github.vanpra.compose-material-dialogs:datetime:0.4.0")

    testImplementation ("io.mockk:mockk:1.10.6")
    androidTestImplementation ("io.mockk:mockk:1.10.6")
    testImplementation ("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8")
    testImplementation ("org.mockito.kotlin:mockito-kotlin:3.2.0")

}