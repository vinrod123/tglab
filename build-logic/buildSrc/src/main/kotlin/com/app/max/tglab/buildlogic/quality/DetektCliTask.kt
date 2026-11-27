package com.app.max.tglab.buildlogic.quality

import org.gradle.api.tasks.JavaExec
import org.gradle.jvm.toolchain.JavaToolchainService
import javax.inject.Inject

abstract class DetektCliTask : JavaExec() {

    @get:Inject
    abstract val detektJavaToolchainService: JavaToolchainService
}
