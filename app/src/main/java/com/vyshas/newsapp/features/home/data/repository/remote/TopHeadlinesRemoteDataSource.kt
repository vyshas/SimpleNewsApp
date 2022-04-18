package com.vyshas.newsapp.features.home.data.repository.remote

import com.vyshas.newsapp.common.data.ApiResponse
import com.vyshas.newsapp.common.data.NewsApiService
import com.vyshas.newsapp.features.home.data.model.TopHeadlines
import javax.inject.Inject

class TopHeadlinesRemoteDataSource @Inject constructor(
    private val newsApiService: NewsApiService
) {

    suspend fun getTopHeadlines(pageSize: Int): ApiResponse<TopHeadlines> =
         newsApiService.getTopHeadlines(pageSize = pageSize)
}
