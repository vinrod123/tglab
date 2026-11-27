plugins {
    id("tglab.android.library")
}

android {
    namespace = "com.app.max.tglab.core.testing"
}

dependencies {
    api(libs.kotlinx.coroutines.test)
    api(libs.junit)
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.truth)
}
