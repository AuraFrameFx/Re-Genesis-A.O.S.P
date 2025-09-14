package dev.aurakai.auraframefx.buildlogic

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.MethodOrderer

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

/**
 * Testing framework: JUnit Jupiter (JUnit 5).
 *
 * These tests assert key configuration introduced in the diff for build-logic/build.gradle.kts.
 * They parse the Gradle Kotlin DSL file as text to confirm the presence of:
 *  - Gradle Releases repository declaration with the expected URL and name
 *  - gradlePlugin registrations, specifically the new "androidHilt" plugin
 *  - Disabled test tasks configuration (test and compileTestKotlin)
 *
 * Although functional Gradle TestKit tests are preferable for plugin behavior,
 * they are provided separately as a disabled scaffold due to tasks being disabled in the module.
 */
@TestMethodOrder(MethodOrderer.MethodName::class)
class BuildLogicGradleFileAssertionsTest {

    private fun readBuildLogicScript(): String {
        val path: Path = Paths.get("build-logic", "build.gradle.kts")
        assertTrue(Files.exists(path), "Expected build-logic/build.gradle.kts to exist")
        return Files.readString(path)
    }

    @Nested
    @DisplayName("Repositories configuration assertions")
    inner class RepositoriesAssertions {
        @Test
        fun `repositories include Gradle Releases repo with correct URL and name`() {
            val text = readBuildLogicScript()

            // Assert presence of maven block with expected URL and name
            assertTrue(
                text.contains("maven {", ignoreCase = false),
                "Expected a custom maven repository block in repositories{}"
            )
            assertTrue(
                text.contains("""url = uri("https://repo.gradle.org/gradle/libs-releases")"""),
                "Expected Gradle Releases repository URL to be declared"
            )
            assertTrue(
                text.contains("""name = "Gradle Releases""""),
                "Expected Gradle Releases repository name to be declared"
            )
        }
    }

    @Nested
    @DisplayName("Gradle plugin registrations")
    inner class GradlePluginRegistrations {
        @Test
        fun `androidHilt plugin is registered with expected id and implementationClass`() {
            val text = readBuildLogicScript()

            // Ensure gradlePlugin block is present
            assertTrue(text.contains("gradlePlugin {"), "Expected gradlePlugin { ... } block")

            // Verify registration stanza for androidHilt
            assertTrue(
                text.contains("""register("androidHilt")"""),
                "Expected register(\"androidHilt\") in gradlePlugin block"
            )
            assertTrue(
                text.contains("""id = "genesis.android.hilt""""),
                "Expected id = \"genesis.android.hilt\" for the androidHilt plugin"
            )
            assertTrue(
                text.contains("""implementationClass = "AndroidHiltConventionPlugin""""),
                "Expected implementationClass = \"AndroidHiltConventionPlugin\" for androidHilt"
            )
        }

        @Test
        fun `other convention plugins remain registered`() {
            val t = readBuildLogicScript()
            listOf(
                "androidApplication" to ("genesis.android.application" to "AndroidApplicationConventionPlugin"),
                "androidLibrary" to ("genesis.android.library" to "AndroidLibraryConventionPlugin"),
                "androidCompose" to ("genesis.android.compose" to "AndroidComposeConventionPlugin"),
                "androidNative" to ("genesis.android.native" to "AndroidNativeConventionPlugin")
            ).forEach { (shortName, pair) ->
                val (id, impl) = pair
                assertTrue(t.contains("""register("$shortName")"""), "Expected register(\"$shortName\") to exist")
                assertTrue(t.contains("""id = "$id""""),
                    "Expected plugin id \"$id\" for \"$shortName\" registration")
                assertTrue(t.contains("""implementationClass = "$impl""""),
                    "Expected implementationClass \"$impl\" for \"$shortName\" registration")
            }
        }
    }

    @Nested
    @DisplayName("Task configuration toggles")
    inner class TaskToggles {
        @Test
        fun `test task is configured to use JUnit Platform and disabled`() {
            val text = readBuildLogicScript()
            assertTrue(text.contains("tasks.test {"), "Expected tasks.test { ... } block")
            assertTrue(text.contains("useJUnitPlatform()"), "Expected useJUnitPlatform() in test task configuration")
            assertTrue(
                text.contains("enabled = false"),
                "Expected tests to be disabled per diff (enabled = false)"
            )
        }

        @Test
        fun `compileTestKotlin task is disabled`() {
            val text = readBuildLogicScript()
            assertTrue(text.contains("tasks.compileTestKotlin {"), "Expected tasks.compileTestKotlin { ... } block")
            assertTrue(
                text.contains("enabled = false"),
                "Expected compileTestKotlin to be disabled per diff (enabled = false)"
            )
        }
    }
}