These tests validate key aspects of build-logic/build.gradle.kts introduced in the PR diff.

- Framework: JUnit Jupiter (JUnit 5)
- Scope:
  1) Repository entry for Gradle Releases
  2) Plugin registration for genesis.android.hilt -> AndroidHiltConventionPlugin
  3) Disabled tasks: test and compileTestKotlin
- Functional TestKit scaffold is provided but marked @Disabled due to current module settings. Remove @Disabled
  and the disabling toggles in build.gradle.kts when compatibility allows, to enable full plugin functional tests.