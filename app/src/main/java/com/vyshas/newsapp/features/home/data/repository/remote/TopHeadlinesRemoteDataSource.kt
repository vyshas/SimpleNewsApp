package com.vyshas.newsapp.features.home.data.repository.remote

import com.vyshas.newsapp.core.data.ApiResponse
import com.vyshas.newsapp.core.data.NewsApiService
import com.vyshas.newsapp.features.home.data.model.entertainmentnews.TopEntertainmentNews
import javax.inject.Inject

class TopHeadlinesRemoteDataSource @Inject constructor(
    private val newsApiService: NewsApiService
) {
    suspend fun getTopEntertainmentHeadlines(pageSize: Int): ApiResponse<TopEntertainmentNews> =
        newsApiService.getTopEntertainmentHeadlines(
            category = CATEGORY,
            country = COUNTRY,
            pageSize = pageSize
        )

    private companion object {
        const val CATEGORY = "entertainment"
        const val COUNTRY = "in"
    }
}
