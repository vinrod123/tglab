import com.app.max.tglab.buildlogic.library
import com.app.max.tglab.buildlogic.libs

plugins {
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

dependencies {
    add("implementation", libs.library("hilt-android"))
    add("ksp", libs.library("hilt-android-compiler"))
}
