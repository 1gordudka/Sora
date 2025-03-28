import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}

tasks{
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += listOf("-Xskip-prerelease-check")
        }
    }
}