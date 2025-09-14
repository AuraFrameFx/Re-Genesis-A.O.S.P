@file:Suppress("UnstableApiUsage")

// ===== AOSP-Re:Genesis - SETTINGS =====
// The pluginManagement block MUST be the first block in the file.
// It defines the versions for all plugins used in the build.

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    buildscript {
        repositories {
            google()
            mavenCentral()
        }
        dependencies {

        }
    }

    plugins {
        id("com.android.application") version "9.0.0-alpha02" apply false
        id("com.android.library") version "9.0.0-alpha02" apply false
        id("org.jetbrains.kotlin.android") version "2.2.20" apply false
        id("org.jetbrains.kotlin.plugin.compose") version "2.2.20" apply false
        id("com.google.devtools.ksp") version "2.2.20-2.0.3" apply false
    }
}

// Manages dependency repositories for all modules.
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral() // Simplified JitPack declaration
        // Custom repositories for specific libraries
        maven { url = uri("https://androidx.dev/storage/compose-compiler/repository/") }
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
        maven { url = uri("https://s01.oss.sonatype.org/content/repositories/releases/") }
        maven { url = uri("https://s01.oss.sonatype.org/content/groups/public/") }
    }


// Enable modern Gradle features for performance and reliability.
    enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
    enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

    rootProject.name = "ReGenesis"

// ===== MODULE INCLUSION =====
    include(
        ":app",
        ":core-module",
        ":feature-module",
        ":datavein-oracle-native",
        ":oracle-drive-integration",
        ":secure-comm",
        ":sandbox-ui",
        ":collab-canvas",
        ":colorblendr",
        ":romtools",
        ":module-a",
        ":module-b",
        ":module-c",
        ":module-d",
        ":module-e",
        ":module-f",
        ":benchmark",
        ":screenshot-tests",
        ":jvm-test",
        ":list",
        ":utilities"
    )
    includeBuild("build-logic")
    rootProject.name = "build-logic"

}
