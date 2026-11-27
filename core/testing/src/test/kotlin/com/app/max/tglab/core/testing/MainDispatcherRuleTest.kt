package com.app.max.tglab.core.testing

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Rule
import org.junit.Test

class MainDispatcherRuleTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `should run code dispatched to Main without a real Android looper`() = runTest {
        var executed = false

        withContext(Dispatchers.Main) {
            executed = true
        }

        assertThat(executed).isTrue()
    }
}
