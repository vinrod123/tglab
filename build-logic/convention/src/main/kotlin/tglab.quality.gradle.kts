import com.app.max.tglab.buildlogic.DetektCliTask
import com.app.max.tglab.buildlogic.libs
import com.app.max.tglab.buildlogic.version
import org.gradle.jvm.toolchain.JavaLanguageVersion

subprojects {
    pluginManager.apply("org.jlleitschuh.gradle.ktlint")
}

val detektCli =
    configurations.create("detektCli") {
        isCanBeConsumed = false
        isCanBeResolved = true
    }

dependencies {
    add("detektCli", "io.gitlab.arturbosch.detekt:detekt-cli:${libs.version("detekt")}:all")
}

val detektInputPaths =
    subprojects
        .map { it.projectDir.resolve("src") }
        .filter { it.exists() }
        .joinToString(",") { it.absolutePath }

val detektReportPath = layout.buildDirectory.file("reports/detekt/detekt.txt")

tasks.register<DetektCliTask>("detekt") {
    group = "verification"
    description = "Roda o detekt-cli isolado em JDK 21 (ADR 0007), fora do processo do daemon (JDK 25)."

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
            detektInputPaths,
            "--config",
            rootDir.resolve("config/detekt/detekt.yml").absolutePath,
            "--build-upon-default-config",
            "--jvm-target",
            "11",
            "--report",
            "txt:${detektReportPath.get().asFile.absolutePath}",
        )
}
