package com.vyshas.newsapp.core.schedulers

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CoroutineDispatcher @Inject constructor() : DispatcherProvider {

    override fun io() = Dispatchers.IO

    override fun default() = Dispatchers.Default

    override fun main() = Dispatchers.Main

    override fun mainImmediate() = Dispatchers.Main.immediate

    override fun unconfined() = Dispatchers.Unconfined
}
