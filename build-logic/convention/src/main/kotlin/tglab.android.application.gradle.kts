import com.android.build.api.dsl.ApplicationExtension
import com.app.max.tglab.buildlogic.libs
import com.app.max.tglab.buildlogic.version
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("com.android.application")
}

extensions.configure<ApplicationExtension> {
    compileSdk {
        version = release(libs.version("compileSdk").toInt())
    }
    compileSdkMinor = libs.version("compileSdkMinor").toInt()

    defaultConfig {
        minSdk = libs.version("minSdk").toInt()
        targetSdk = libs.version("targetSdk").toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    lint {
        abortOnError = true
        warningsAsErrors = false
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_11)
    }
}

