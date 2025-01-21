import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
    alias(libs.plugins.kotlin)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

tasks{
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += listOf("-Xskip-prerelease-check")
        }
    }
}

android {
    namespace = "video.generator.ai"
    compileSdk = 34

    defaultConfig {
        applicationId = "video.generator.ai"
        minSdk = 26
        targetSdk = 34
        versionCode = 3
        versionName = "1.0.0"

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
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
}

dependencies {

    //Ktx
    implementation(libs.androidx.ktx)
    implementation(libs.runtime.ktx)

    //Compose
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)

    //Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.test.espresso)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.test.compose.junit)

    //Debug
    debugImplementation(libs.compose.tooling)
    debugImplementation(libs.compose.test.manifest)

    //Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation)

    //Datastore
    implementation(libs.androidx.datastore.preferences)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.gson)

    //Room
    implementation(libs.room)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)

    //OkHttp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    //ExoPlayer
    implementation ("com.google.android.exoplayer:exoplayer:2.19.1")

    //Haze
    implementation("dev.chrisbanes.haze:haze:1.0.2")

    //Blur
    implementation("com.github.prime-zs.toolkit:core-ktx:2.0.2-alpha")
    implementation("com.github.skydoves:cloudy:0.2.3")

    //Coil
    implementation("io.coil-kt:coil-compose:2.4.0")

    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation ("com.google.firebase:firebase-crashlytics-buildtools:2.9.8")

    //Billing
    implementation("com.android.billingclient:billing-ktx:6.0.1")


    //AppMetrica
    implementation("io.appmetrica.analytics:analytics:7.3.0")


}