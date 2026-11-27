import com.app.max.tglab.buildlogic.libs
import com.app.max.tglab.buildlogic.library

plugins {
    id("tglab.android.library")
    id("tglab.android.compose")
    id("tglab.android.hilt")
    id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
    add("implementation", libs.library("androidx-navigation-compose"))
    add("implementation", libs.library("androidx-lifecycle-viewmodel-compose"))
    add("implementation", libs.library("androidx-hilt-navigation-compose"))
    add("implementation", libs.library("kotlinx-serialization-core"))
    add("testImplementation", libs.library("junit"))
    add("testImplementation", libs.library("kotlinx-coroutines-test"))
    add("testImplementation", libs.library("turbine"))
    add("testImplementation", libs.library("truth"))
    add("androidTestImplementation", libs.library("androidx-junit"))
    add("androidTestImplementation", libs.library("androidx-espresso-core"))
}
