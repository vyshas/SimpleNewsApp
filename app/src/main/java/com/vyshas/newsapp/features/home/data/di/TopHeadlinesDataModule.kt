package com.vyshas.newsapp.features.home.data.di

import com.vyshas.newsapp.features.home.data.repository.TopHeadlinesRepositoryImpl
import com.vyshas.newsapp.features.home.domain.repository.TopHeadlinesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TopHeadlinesDataModule {

    @Binds
    abstract fun provideMarketRepository(
        topHeadlinesRepositoryImpl: TopHeadlinesRepositoryImpl
    ): TopHeadlinesRepository
}
