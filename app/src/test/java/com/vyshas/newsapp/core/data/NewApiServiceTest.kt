package com.vyshas.newsapp.core.data

import com.vyshas.newsapp.core.data.model.ApiResponse
import com.vyshas.newsapp.core.data.repository.remote.NewsApiService
import com.vyshas.newsapp.core.domain.MainCoroutinesRule
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
        val response = apiService.getTopEntertainmentHeadlines(category = "entertainment", pageSize = 10, page = 1)
        val responseBody = requireNotNull((response as ApiResponse.ApiSuccessResponse).data)
        mockWebServer.takeRequest()

        // Then
        assertThat(responseBody.totalResults, `is`(70))
    }
}
