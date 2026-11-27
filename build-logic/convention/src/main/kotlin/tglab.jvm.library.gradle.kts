import com.app.max.tglab.buildlogic.libs
import com.app.max.tglab.buildlogic.version
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `java-library`
    id("org.jetbrains.kotlin.jvm")
}

val jdkTarget = JavaVersion.toVersion(libs.version("jdkTarget"))

java {
    sourceCompatibility = jdkTarget
    targetCompatibility = jdkTarget
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.fromTarget(libs.version("jdkTarget")))
    }
}
