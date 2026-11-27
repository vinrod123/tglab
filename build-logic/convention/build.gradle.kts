import com.app.max.tglab.buildlogic.quality.DetektCliTask
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.app.max.tglab.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.compose.compiler.gradlePlugin)
    compileOnly(libs.kotlin.jvm.gradlePlugin)
    compileOnly(libs.kotlin.serialization.gradlePlugin)
    compileOnly(libs.hilt.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.ktlint.gradlePlugin)
}

val ktlintCli =
    configurations.create("ktlintCli") {
        isCanBeConsumed = false
        isCanBeResolved = true
    }

val detektCli =
    configurations.create("detektCli") {
        isCanBeConsumed = false
        isCanBeResolved = true
    }

dependencies {
    add("ktlintCli", "com.pinterest.ktlint:ktlint-cli:${libs.versions.ktlintCli.get()}:all")
    add("detektCli", "io.gitlab.arturbosch.detekt:detekt-cli:${libs.versions.detekt.get()}:all")
}

tasks.register<JavaExec>("ktlintCheck") {
    group = "verification"
    description = "Roda o ktlint-cli isolado sobre o codigo de :build-logic:convention."

    classpath = ktlintCli
    mainClass.set("com.pinterest.ktlint.Main")

    args =
        listOf(
            "${projectDir.resolve("src").absolutePath}/**/*.kt",
            "${projectDir.resolve("src").absolutePath}/**/*.kts",
        )
}

tasks.register<DetektCliTask>("detekt") {
    group = "verification"
    description = "Roda o detekt-cli isolado em JDK 21 (ADR 0007) sobre o codigo de :build-logic:convention."

    javaLauncher.set(
        detektJavaToolchainService.launcherFor {
            languageVersion.set(JavaLanguageVersion.of(21))
        },
    )

    classpath = detektCli
    mainClass.set("io.gitlab.arturbosch.detekt.cli.Main")

    args =
        listOf(
            "--input",
            projectDir.resolve("src").absolutePath,
            "--config",
            rootDir.resolveSibling("config/detekt/detekt.yml").absolutePath,
            "--build-upon-default-config",
            "--jvm-target",
            "17",
            "--report",
            "txt:${layout.buildDirectory.get().asFile}/reports/detekt/detekt.txt",
        )
}
