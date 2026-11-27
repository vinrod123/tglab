plugins {
    id("tglab.android.library")
    id("tglab.android.compose")
}

android {
    namespace = "com.app.max.tglab.core.designsystem"
}

dependencies {
    implementation(project(":core:common"))
}
