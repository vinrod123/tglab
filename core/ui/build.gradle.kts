plugins {
    id("tglab.android.library")
    id("tglab.android.compose")
}

android {
    namespace = "com.app.max.tglab.core.ui"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation("androidx.compose.material:material-icons-core")
}
