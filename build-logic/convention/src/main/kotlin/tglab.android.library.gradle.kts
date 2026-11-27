import com.android.build.api.dsl.LibraryExtension
import com.app.max.tglab.buildlogic.libs
import com.app.max.tglab.buildlogic.version
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("com.android.library")
}

extensions.configure<LibraryExtension> {
    compileSdk {
        version = release(libs.version("compileSdk").toInt())
    }
    compileSdkMinor = libs.version("compileSdkMinor").toInt()

    defaultConfig {
        minSdk = libs.version("minSdk").toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.version("jdkTarget"))
        targetCompatibility = JavaVersion.toVersion(libs.version("jdkTarget"))
    }

    lint {
        abortOnError = true
        warningsAsErrors = false
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.fromTarget(libs.version("jdkTarget")))
    }
}
