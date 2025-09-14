// AOSP-ReGenesis/build-logic/build.gradle.kts

plugins {
    `kotlin-dsl`
    id("org.jetbrains.compose") version "1.8.2"
    id("org.jetbrains.kotlin.plugin.compose") version "2.2.20"
}

group = "dev.aurakai.auraframefx.buildlogic"

// Repositories required for resolving external dependencies.
repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    maven {
        url = uri("https://repo.gradle.org/gradle/libs-releases")
        name = "Gradle Releases"
    }
}

// Dependencies required for the convention plugins themselves.
dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.20")
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.57.1")
    implementation("org.junit.jupiter:junit-jupiter-params:5.13.4")
    implementation("org.junit.jupiter:junit-jupiter-engine:5.13.4")
    implementation("org.junit.jupiter:junit-jupiter-api:5.13.4")
    implementation("com.android.tools.build:gradle:9.0.0-alpha02")
    implementation("org.jetbrains.compose.runtime:runtime:1.10.0+dev2947")
    // Test dependencies

}

// Configure test execution (temporarily disabled for bleeding-edge compatibility)
tasks.test {
    useJUnitPlatform()
    enabled = false // Disable until AGP 9.0 test compatibility is resolved
}

// Disable test compilation temporarily
tasks.compileTestKotlin {
    enabled = false
}

tasks.withType<ProcessResources> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

gradlePlugin {
    plugins {
        register("genesisAndroidApplication") {
            id = "genesis.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "genesis.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        // This is the new registration for the Hilt convention plugin
        register("androidHilt") {
            id = "genesis.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidCompose") {
            id = "genesis.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }
        register("androidNative") {
            id = "genesis.android.native"
            implementationClass = "AndroidNativeConventionPlugin"
        }
    }
}
