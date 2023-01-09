package com.vyshas.newsapp.features.home.domain.usecase

import com.vyshas.newsapp.core.data.DataState
import com.vyshas.newsapp.features.home.domain.entity.TopEntertainmentHeadlinesEntity
import com.vyshas.newsapp.features.home.domain.repository.TopHeadlinesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetTopEntertainmentNews @Inject constructor(
    private val topHeadlinesRepository: TopHeadlinesRepository
) {

    operator fun invoke(): Flow<DataState<List<TopEntertainmentHeadlinesEntity>>> {
        return topHeadlinesRepository.getTopEntertainmentHeadlines(100).onEach { print(it) }
    }
}