package com.app.max.tglab.core.testing

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Test

class TestDispatcherProviderTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should expose the same dispatcher for main, io and default`() {
        val dispatcher = UnconfinedTestDispatcher()
        val provider = TestDispatcherProvider(dispatcher)

        assertThat(provider.main).isSameInstanceAs(dispatcher)
        assertThat(provider.io).isSameInstanceAs(dispatcher)
        assertThat(provider.default).isSameInstanceAs(dispatcher)
    }
}
