package com.app.max.tglab.core.testing

import com.app.max.tglab.core.common.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

class TestDispatcherProvider
    @OptIn(ExperimentalCoroutinesApi::class)
    constructor(
        dispatcher: CoroutineDispatcher = UnconfinedTestDispatcher(),
    ) : DispatcherProvider {
        override val main: CoroutineDispatcher = dispatcher
        override val io: CoroutineDispatcher = dispatcher
        override val default: CoroutineDispatcher = dispatcher
    }
