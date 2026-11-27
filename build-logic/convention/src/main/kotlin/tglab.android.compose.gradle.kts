import com.app.max.tglab.buildlogic.library
import com.app.max.tglab.buildlogic.libs

plugins {
    id("org.jetbrains.kotlin.plugin.compose")
}

dependencies {
    val composeBom = platform(libs.library("compose-bom"))
    add("implementation", composeBom)
    add("androidTestImplementation", composeBom)
    add("implementation", "androidx.compose.ui:ui")
    add("implementation", "androidx.compose.ui:ui-graphics")
    add("implementation", "androidx.compose.ui:ui-tooling-preview")
    add("implementation", "androidx.compose.material3:material3")
    add("debugImplementation", "androidx.compose.ui:ui-tooling")
    add("debugImplementation", "androidx.compose.ui:ui-test-manifest")
    add("androidTestImplementation", "androidx.compose.ui:ui-test-junit4")
}
