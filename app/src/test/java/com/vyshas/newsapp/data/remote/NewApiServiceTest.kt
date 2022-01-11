package com.vyshas.newsapp.data.remote

import com.vyshas.newsapp.common.data.ApiResponse
import com.vyshas.newsapp.common.data.NewsApiService
import com.vyshas.newsapp.data.MainCoroutinesRule
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
    fun `test load top headlines returns list of news with first one being general`() = runBlocking {
        // Given
        enqueueResponse("/topheadlines.json")

        // Invoke
        val response = apiService.getTopHeadlines( 10, 1)
        val responseBody = requireNotNull((response as ApiResponse.ApiSuccessResponse).data)

        mockWebServer.takeRequest()

        // Then
        assertThat(responseBody.sources[0].category, `is`("general"))
    }
}
