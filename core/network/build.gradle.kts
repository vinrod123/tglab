plugins {
    id("tglab.android.library")
    id("tglab.android.hilt")
}

android {
    namespace = "com.app.max.tglab.core.network"
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx.serialization.converter)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.timber)
}
