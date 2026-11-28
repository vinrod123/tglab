plugins {
    id("tglab.android.feature")
}

android {
    namespace = "com.app.max.tglab.feature.teamdetail.presentation"

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    implementation(project(":feature:teamdetail:domain"))
    implementation(project(":core:common"))
    implementation(project(":core:ui"))
    implementation(project(":core:designsystem"))
    implementation("androidx.compose.material:material-icons-core")

    testImplementation(project(":core:testing"))
    testImplementation(libs.robolectric)
    testImplementation(platform(libs.compose.bom))
    testImplementation("androidx.compose.ui:ui-test-junit4")
}
