package com.vyshas.newsapp.common.data

import com.vyshas.newsapp.features.home.data.model.TopHeadlines
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines/sources")
    suspend fun getTopHeadlines(
        @Query("pagesize") pageSize: Int?,
        @Query("page") page: Int? = null
    ): ApiResponse<TopHeadlines>


    companion object {
        const val BASE_API_URL = "https://newsapi.org/v2/"
    }
}