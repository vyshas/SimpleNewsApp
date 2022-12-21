package com.vyshas.newsapp.data.remote

import com.vyshas.newsapp.common.data.ApiResponse
import com.vyshas.newsapp.common.data.NewsApiService
import com.vyshas.newsapp.data.MainCoroutinesRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class NewApiServiceTest : ApiAbstract<NewsApiService>() {

    private lateinit var apiService: NewsApiService

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var coroutineRule = MainCoroutinesRule()

    @Before
    fun setUp() {
        apiService = createService(NewsApiService::class.java)
    }

    @After
    fun tearDown() {
    }

    @Throws(IOException::class)
    @Test
    fun `test load top entertain headlines returns list of entertainment news`() = runBlocking {
        // Given
        enqueueResponse("/topentertainmentnews.json")

        // Invoke
        val response = apiService.getTopEntertainmentHeadlines(10, 1, category = "entertainment")
        val responseBody = requireNotNull((response as ApiResponse.ApiSuccessResponse).data)
        mockWebServer.takeRequest()

        // Then
        assertThat(responseBody.totalResults, `is`(70))
    }
}
