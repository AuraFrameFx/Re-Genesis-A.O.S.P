// ==== GENESIS PROTOCOL - MAIN APPLICATION ====
// This build script now uses the custom convention plugins for a cleaner setup.

plugins {
    id("com.android.application")
    kotlin("android")
    id("org.openapi.generator") version "7.15.0"
    id("com.google.devtools.ksp")
    alias(libs.plugins.hilt)
    // Add other plugins as needed
}

android {
    namespace = "dev.aurakai.auraframefx"
    compileSdkPreview = "CANARY"// Updated to 36 as required
    defaultConfig {
        applicationId = "dev.aurakai.auraframefx"
        minSdk = 34 // Kept at 34 as required
        targetSdk = 36 // Updated to 36 as required
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    // Additional build type configuration
    buildTypes {
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
        }
    }

    // Enable AIDL for the app module
    buildFeatures {
        aidl = true
    }

    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }

    sourceSets["main"].java.srcDir(layout.buildDirectory.dir("generated/openapi/src/main/kotlin"))

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_24
        targetCompatibility = JavaVersion.VERSION_24
    }
}

tasks.named<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>("openApiGenerate") {
    generatorName.set("kotlin")
    // Use the correct file URI for the OpenAPI spec
    inputSpec.set("file:///C:/Users/Wehtt/OneDrive/Desktop/ReGenesis-fix-dependabot-compose-plugin/ReGenesis-patch1/app/api/system-api.yml")
    outputDir.set(layout.buildDirectory.dir("generated/openapi").get().asFile.absolutePath)
    apiPackage.set("dev.aurakai.auraframefx.openapi.api")
    modelPackage.set("dev.aurakai.auraframefx.openapi.model")
    invokerPackage.set("dev.aurakai.auraframefx.openapi.invoker")
    configOptions.set(
        mapOf(
            "dateLibrary" to "java8", "library" to "jvm-ktor"
        )
    )
}

dependencies {
    // ===== MODULE DEPENDENCIES =====
    implementation(project(":core-module"))
    implementation(project(":feature-module"))
    implementation(project(":oracle-drive-integration"))
    implementation(project(":romtools"))  // Temporarily disabled - Hilt/KSP issues
    implementation(project(":secure-comm"))
    implementation(project(":collab-canvas"))  // Temporarily disabled - YukiHookAPI issues
    implementation(project(":colorblendr"))
    implementation(project(":sandbox-ui"))  // Temporarily disabled - Compose compilation issues
    implementation(project(":datavein-oracle-native"))

    // ===== ANDROIDX & COMPOSE =====
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose.ui)
    implementation(libs.androidx.core.ktx)
    debugImplementation(libs.bundles.compose.debug)

    // ===== LIFECYCLE =====
    implementation(libs.bundles.lifecycle)

    // ===== DATABASE - ROOM =====
    implementation(libs.bundles.room)

    // ===== DATASTORE =====
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)

    // ===== KOTLIN & COROUTINES =====
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
    implementation(libs.bundles.coroutines)

    // ===== NETWORKING =====
    implementation(libs.bundles.network)
    implementation("com.squareup.moshi:moshi:1.15.0")

    // ===== WORKMANAGER & HILT WORKER =====
    implementation("androidx.hilt:hilt-work:1.3.0")
    implementation("androidx.work:work-runtime-ktx:2.10.4")

    // ===== FIREBASE =====
    // By implementing the BOM, we can specify Firebase SDKs without versions
    implementation(platform(libs.firebase.bom))
    // This bundle includes Analytics, Crashlytics, Performance, Auth, Firestore, Messaging, and Config
    implementation(libs.bundles.firebase)

    // Alternative: Use specific Firebase bundles for modular approach
    // implementation(libs.bundles.firebase.core)     // Analytics, Crashlytics, Performance only
    // implementation(libs.bundles.firebase.auth)     // Authentication
    // implementation(libs.bundles.firebase.database) // Firestore, Realtime Database, Storage
    // implementation(libs.bundles.firebase.messaging) // FCM, Remote Config

    // ===== HILT DEPENDENCY INJECTION =====
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // ===== UTILITIES =====
    implementation(libs.timber)
    implementation(libs.coil.compose)

    // ===== SECURITY =====
    implementation(libs.androidx.security.crypto)

    // ===== CORE LIBRARY DESUGARING =====
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    // ===== XPOSED/LSPosed Integration =====
    compileOnly(files("../Libs/api-82.jar"))
    compileOnly(files("../Libs/api-82-sources.jar"))

    // --- TESTING ---
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(libs.bundles.testing.android)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.hilt.android.testing) // For Hilt in Android tests

    // --- DEBUGGING ---
    debugImplementation(libs.leakcanary.android)
    implementation(libs.kotlinStdlibJdk8)
    implementation(libs.kotlinReflect)
}
