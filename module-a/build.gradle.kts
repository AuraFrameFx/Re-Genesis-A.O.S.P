// GENESIS PROTOCOL - MODULE A
plugins {
    id("org.jetbrains.kotlin.android") // Add this line to explicitly apply the Kotlin Android plugin
    id("genesis.android.library")
    id("genesis.android.compose")
    alias(libs.plugins.ksp)
}

android {
    namespace = "dev.aurakai.auraframefx.module.a"
}

dependencies {
    implementation(project(":core-module"))
    implementation(libs.androidx.core.ktx)
    
    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    
    // Add other module-specific dependencies here
    implementation(kotlin("stdlib-jdk8"))
}

tasks.register("moduleAStatus") {
    group = "aegenesis"
    doLast { println("📦 MODULE A - Ready (Java 24)") }
}

kotlin {
    jvmToolchain(24)
}
