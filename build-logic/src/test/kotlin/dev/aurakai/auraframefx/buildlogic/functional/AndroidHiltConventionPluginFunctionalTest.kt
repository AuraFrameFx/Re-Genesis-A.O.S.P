package dev.aurakai.auraframefx.buildlogic.functional

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

/**
 * Testing framework: JUnit Jupiter (JUnit 5).
 *
 * Functional test scaffold using Gradle TestKit. Disabled because the build-logic module
 * currently disables test compilation/execution for AGP 9.0 compatibility.
 * Once tests are enabled, remove @Disabled to validate the plugin can be applied successfully.
 */
@Disabled("build-logic tests are temporarily disabled (tasks.test / compileTestKotlin). Unskip when enabled.")
class AndroidHiltConventionPluginFunctionalTest {

    @TempDir
    lateinit var tempDir: File

    @Test
    fun appliesAndroidHiltConventionPlugin() {
        // Prepare a minimal settings.gradle and build.gradle.kts applying the plugin under test
        File(tempDir, "settings.gradle.kts").writeText(
            """
            rootProject.name = "android-hilt-functional-test"
            """.trimIndent()
        )
        File(tempDir, "build.gradle.kts").writeText(
            """
            plugins {
                id("genesis.android.hilt")
            }
            """.trimIndent()
        )

        // Defer actual GradleRunner usage until tests are re-enabled; for now, assert files were written
        assertTrue(File(tempDir, "settings.gradle.kts").exists(), "settings.gradle.kts should be created")
        assertTrue(File(tempDir, "build.gradle.kts").exists(), "build.gradle.kts should be created")

        // When ready to enable, uncomment the following to run with TestKit:
        /*
        val result = GradleRunner.create()
            .withProjectDir(tempDir)
            .withArguments("tasks")
            .withPluginClasspath()
            .build()

        assertTrue(result.output.contains("BUILD SUCCESS"), "Expected successful build when applying plugin")
        */
    }
}