package com.vyshas.newsapp.features.home.data.repository.remote

import com.vyshas.newsapp.common.data.ApiResponse
import com.vyshas.newsapp.common.data.NewsApiService
import com.vyshas.newsapp.features.home.data.model.entertainmentnews.TopEntertainmentNews
import javax.inject.Inject

class TopHeadlinesRemoteDataSource @Inject constructor(
    private val newsApiService: NewsApiService
) {

    suspend fun getTopEntertainmentHeadlines(pageSize: Int): ApiResponse<TopEntertainmentNews> =
        newsApiService.getTopEntertainmentHeadlines(
            pageSize = pageSize,
            country = COUNTRY,
            category = CATEGORY
        )

    private companion object {
        const val CATEGORY = "entertainment"
        const val COUNTRY = "in"
    }
}
