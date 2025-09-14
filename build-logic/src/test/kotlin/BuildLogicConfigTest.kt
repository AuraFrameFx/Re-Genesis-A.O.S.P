/*
  BuildLogicConfigTest
  Purpose: Validate the configuration of build-logic/build.gradle.kts.

  Testing library/framework: JUnit 5 (Jupiter) with Kotlin.
  Note: As configured in build-logic/build.gradle.kts (lines with tasks.test and tasks.compileTestKotlin),
  test compilation/execution are disabled at the moment. These tests provide value immediately by
  statically validating the Gradle script contents and will execute once those tasks are re-enabled.
*/

package dev.aurakai.auraframefx.buildlogic

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.text.RegexOption

class BuildLogicConfigTest {

    private val scriptPath = Paths.get("build.gradle.kts")
    private val content: String by lazy {
        assertTrue(Files.exists(scriptPath), "Expected build.gradle.kts to exist at: ${scriptPath.toAbsolutePath()}")
        Files.readString(scriptPath)
    }

    @Test
    @DisplayName("plugins block includes `kotlin-dsl`")
    fun pluginsBlockHasKotlinDsl() {
        val regex = Regex("""plugins\s*\{\s*`kotlin-dsl`\s*\}""", setOf(RegexOption.DOT_MATCHES_ALL))
        assertTrue(regex.containsMatchIn(content), "Expected plugins { `kotlin-dsl` } block.")
    }

    @Test
    @DisplayName("Group is set to dev.aurakai.auraframefx.buildlogic")
    fun groupIsConfigured() {
        assertTrue(
            Regex("""\bgroup\s*=\s*["']dev\.aurakai\.auraframefx\.buildlogic["']""").containsMatchIn(content),
            "Expected group = \"dev.aurakai.auraframefx.buildlogic\""
        )
    }

    @Test
    @DisplayName("Repositories include google(), mavenCentral(), gradlePluginPortal(), and Gradle Releases repo")
    fun repositoriesConfigured() {
        assertTrue(content.contains("google()"), "Missing google() repository")
        assertTrue(content.contains("mavenCentral()"), "Missing mavenCentral() repository")
        assertTrue(content.contains("gradlePluginPortal()"), "Missing gradlePluginPortal() repository")

        val gradleReleasesRepo = Regex(
            """maven\s*\{\s*[^}]*url\s*=\s*uri\(['"]https://repo\.gradle\.org/gradle/libs-releases['"]\)\s*[^}]*name\s*=\s*['"]Gradle Releases['"]\s*[^}]*\}""",
            setOf(RegexOption.DOT_MATCHES_ALL)
        )
        assertTrue(gradleReleasesRepo.containsMatchIn(content), "Missing Gradle Releases maven repository block")
    }

    @Test
    @DisplayName("Dependencies include expected implementations (Kotlin, Hilt, JUnit, AGP)")
    fun dependenciesConfigured() {
        val expected = listOf(
            "org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.20",
            "com.google.dagger:hilt-android-gradle-plugin:2.57.1",
            "org.junit.jupiter:junit-jupiter-params:5.13.4",
            "org.junit.jupiter:junit-jupiter-engine:5.13.4",
            "org.junit.jupiter:junit-jupiter-api:5.13.4",
            "com.android.tools.build:gradle:9.0.0-alpha02"
        )

        expected.forEach { gav ->
            val pattern = Regex("""implementation\s*\(\s*['"]${Regex.escape(gav)}['"]\s*\)""")
            assertTrue(pattern.containsMatchIn(content), "Missing dependency implementation(\"$gav\")")
        }
    }

    @Test
    @DisplayName("tasks.test uses JUnit Platform and is disabled")
    fun testTaskConfigured() {
        val blockRegex = Regex(
            """tasks\.test\s*\{\s*[^}]*useJUnitPlatform\(\)\s*[^}]*enabled\s*=\s*false\s*[^}]*\}""",
            setOf(RegexOption.DOT_MATCHES_ALL)
        )
        assertTrue(blockRegex.containsMatchIn(content), "Expected tasks.test block with useJUnitPlatform() and enabled=false")
    }

    @Test
    @DisplayName("tasks.compileTestKotlin is disabled")
    fun compileTestKotlinDisabled() {
        val regex = Regex(
            """tasks\.compileTestKotlin\s*\{\s*[^}]*enabled\s*=\s*false\s*[^}]*\}""",
            setOf(RegexOption.DOT_MATCHES_ALL)
        )
        assertTrue(regex.containsMatchIn(content), "Expected tasks.compileTestKotlin block with enabled=false")
    }

    @Test
    @DisplayName("gradlePlugin registers expected plugin ids with implementation classes")
    fun gradlePluginIdsAndImplClasses() {
        val pairs = listOf(
            "genesis.android.application" to "AndroidApplicationConventionPlugin",
            "genesis.android.library" to "AndroidLibraryConventionPlugin",
            "genesis.android.hilt" to "AndroidHiltConventionPlugin",
            "genesis.android.compose" to "AndroidComposeConventionPlugin",
            "genesis.android.native" to "AndroidNativeConventionPlugin",
        )
        pairs.forEach { (id, impl) ->
            val idPattern = Regex("""id\s*=\s*['"]${Regex.escape(id)}['"]""")
            val implPattern = Regex("""implementationClass\s*=\s*['"]${Regex.escape(impl)}['"]""")
            assertTrue(idPattern.containsMatchIn(content), "Missing plugin id $id")
            assertTrue(implPattern.containsMatchIn(content), "Missing implementationClass $impl for plugin $id")
        }
    }

    @Test
    @DisplayName("androidHilt convention plugin registration exists (focus on diff)")
    fun hiltPluginRegistered() {
        val blockRegex = Regex(
            """register\(\s*['"]androidHilt['"]\s*\)\s*\{\s*[^}]*id\s*=\s*['"]genesis\.android\.hilt['"]\s*[^}]*implementationClass\s*=\s*['"]AndroidHiltConventionPlugin['"]\s*[^}]*\}""",
            setOf(RegexOption.DOT_MATCHES_ALL)
        )
        assertTrue(blockRegex.containsMatchIn(content), "Expected register(\"androidHilt\") block with proper id and implementationClass")
    }
}