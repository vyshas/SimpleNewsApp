package com.vyshas.newsapp.common.schedulers

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    fun io(): CoroutineDispatcher
    fun default(): CoroutineDispatcher
    fun main(): CoroutineDispatcher
    fun mainImmediate(): CoroutineDispatcher
    fun unconfined(): CoroutineDispatcher
}
