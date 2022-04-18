package com.vyshas.newsapp.features.home.domain.repository

import com.vyshas.newsapp.common.data.DataState
import com.vyshas.newsapp.features.home.domain.entity.TopHeadlinesEntity

interface TopHeadlinesRepository {

    suspend fun getTopHeadlines(pageSize: Int): DataState<List<TopHeadlinesEntity>>
}
