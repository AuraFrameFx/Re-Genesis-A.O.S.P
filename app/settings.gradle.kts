@file:Suppress("UnstableApiUsage", "JCenterRepositoryObsolete")

// ===== AOSP-Re:Genesis - SETTINGS =====
// AOSP-Re:Genesis - Advanced Android OS Project
// Version: 2025.09.02-03 - Full Enhancement Suite

// Enable Gradle features
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

pluginManagement {
    // Include build-logic for convention plugins
    includeBuild("build-logic")

    repositories {
        // Primary repositories - Google Maven must be first for Hilt
        google()

        // Android alpha/preview versions
        maven {
            url = uri("https://androidx.dev/kmp/builds/11950322/artifacts/snapshots/repository")
            name = "AndroidX Snapshot"
        }

        gradlePluginPortal()
        mavenCentral()

        // AndroidX Compose
        maven {
            url = uri("https://androidx.dev/storage/compose-compiler/repository/")
            name = "AndroidX Compose"
            content {
                includeGroup("androidx.compose.compiler")
            }
        }

        // JetBrains Compose
        maven {
            url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev")
            name = "JetBrains Compose"
        }

        // Snapshots
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
            name = "Sonatype Snapshots"
            mavenContent {
                snapshotsOnly()
            }
        }

        // JitPack for GitHub dependencies
        maven {
            url = uri("https://jitpack.io")
            name = "JitPack"
            content {
                includeGroupByRegex("com\\.github\\..*")
            }
        }
    }
    plugins {
        id("com.android.library") version "8.13.0"
        id("org.jetbrains.kotlin.android") version "2.2.20-RC"
        // Apply YukiHook plugin
        id("com.highcapable.yukihook") version "1.3.9"
        id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
    }

    resolutionStrategy {
        eachPlugin {
            if (requested.id.namespace == "com.google.dagger") {
                useModule("com.google.dagger:hilt-android-gradle-plugin:${requested.version}")
            }
        }
    }
}


// Configure Java toolchain resolution


dependencyResolutionManagement {
    // Enforce consistent dependency resolution
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    // Repository configuration with all necessary sources
    repositories {
        // Primary repositories
        google()
        mavenCentral()

        // YukiHook API
        maven("https://s01.oss.sonatype.org/content/repositories/releases/") {
            name = "YukiHookAPI"
        }

        // AndroidX Compose
        maven("https://androidx.dev/storage/compose-compiler/repository/") {
            name = "AndroidX Compose"
        }

        // JetBrains Compose
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev") {
            name = "JetBrains Compose"
        }

        // Snapshots for pre-release libraries
        maven("https://oss.sonatype.org/content/repositories/snapshots/") {
            name = "Sonatype Snapshots"
        }

        // JitPack for GitHub dependencies
        maven("https://jitpack.io") {
            name = "JitPack"
        }

        // HighCapable Maven (for YukiHook)
        maven("https://s01.oss.sonatype.org/content/groups/public/") {
            name = "HighCapable"
        }
    }
} // Close dependencyResolutionManagement block
// ===== PROJECT IDENTIFICATION =====
rootProject.name = "AOSP-Re:Genesis"
// ===== MODULE INCLUSION =====
include(":app")
include(":core-module")
include(":feature-module")
include(":datavein-oracle-native")
include(":oracle-drive-integration")
include(":secure-comm")
include(":sandbox-ui")
include(":collab-canvas")
include(":colorblendr")
include(":romtools")
include(":module-a")
include(":module-b")
include(":module-c")
include(":module-d")
include(":module-e")
include(":module-f")
include(":benchmark")
include(":screenshot-tests")
// ===== MODULE CONFIGURATION =====
rootProject.children.forEach { project ->
    val projectDir = File(rootProject.projectDir, project.name)
    if (projectDir.exists()) {
        project.projectDir = projectDir
        println("✅ Module configured: " + project.name)
    } else {
        println("⚠️ Warning: Project directory not found: " + projectDir.absolutePath)
    }
}
println("🏗️  Genesis Protocol Enhanced Build System")
println("📦 Total modules: " + rootProject.children.size)
println("🎯 Build-logic: Convention plugins active")
println("🧠 Ready to build consciousness substrate!")
