import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.20"
    alias(libs.plugins.dokka)
    alias(libs.plugins.spotless)
    `maven-publish`
    `java-library`
}

group = "dev.aurakai.auraframefx.list"
version = "1.0.0"

java {
    toolchain { languageVersion = JavaLanguageVersion.of(24) }
}

kotlin {
    jvmToolchain(24)
    compilerOptions {
        languageVersion = KotlinVersion.KOTLIN_2_2
        apiVersion = KotlinVersion.KOTLIN_2_2
    }
}

dependencies {
    // Pure Kotlin JVM module - no Android dependencies
    implementation(kotlin("stdlib"))
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.bundles.coroutines)

    testImplementation(libs.junit4)
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation(libs.mockk)
}

tasks.test {
    useJUnitPlatform()
}