import com.app.max.tglab.buildlogic.libs
import com.app.max.tglab.buildlogic.library

plugins {
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

dependencies {
    add("implementation", libs.library("hilt-android"))
    add("ksp", libs.library("hilt-android-compiler"))
}
