package com.vyshas.newsapp.core.domain

import com.vyshas.newsapp.core.schedulers.DispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import javax.inject.Inject

@ExperimentalCoroutinesApi
class TestCoroutineDispatcherProvider @Inject constructor() : DispatcherProvider {

    override fun io() = StandardTestDispatcher()

    override fun default() = StandardTestDispatcher()

    override fun main() = StandardTestDispatcher()

    override fun mainImmediate() = StandardTestDispatcher()

    override fun unconfined() = StandardTestDispatcher()
}
