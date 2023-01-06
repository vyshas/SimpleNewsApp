package com.vyshas.newsapp.common.data

import com.vyshas.newsapp.features.home.data.model.entertainmentnews.TopEntertainmentNews
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines/")
    suspend fun getTopEntertainmentHeadlines(
        @Query("category") category: String,
        @Query("country") country: String? = null,
        @Query("pagesize") pageSize: Int?,
        @Query("page") page: Int? = null
    ): ApiResponse<TopEntertainmentNews>


    companion object {
        const val BASE_API_URL = "https://newsapi.org/v2/"
    }
}