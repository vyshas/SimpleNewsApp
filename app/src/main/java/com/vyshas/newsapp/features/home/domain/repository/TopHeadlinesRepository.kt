package com.vyshas.newsapp.features.home.domain.repository

import com.vyshas.newsapp.common.data.DataState
import com.vyshas.newsapp.features.home.domain.entity.TopEntertainmentHeadlinesEntity
import kotlinx.coroutines.flow.Flow

interface TopHeadlinesRepository {

    suspend fun getTopEntertainmentHeadlines(pageSize: Int): Flow<DataState<List<TopEntertainmentHeadlinesEntity>>>
}
