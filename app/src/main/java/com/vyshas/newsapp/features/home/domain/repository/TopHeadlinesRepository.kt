package com.vyshas.newsapp.features.home.domain.repository

import com.vyshas.newsapp.common.data.DataState
import com.vyshas.newsapp.features.home.domain.entity.TopEntertainmentHeadlinesEntity

interface TopHeadlinesRepository {

    suspend fun getTopEntertainmentHeadlines(pageSize: Int): DataState<List<TopEntertainmentHeadlinesEntity>>
}
