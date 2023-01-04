package com.vyshas.newsapp.common.di.modules

import com.vyshas.newsapp.common.schedulers.CoroutineDispatcher
import com.vyshas.newsapp.common.schedulers.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DispatcherModule {

    @Provides
    @Singleton
    fun provideCoroutineDispatcher(): DispatcherProvider {
        return CoroutineDispatcher()
    }
}