package com.vyshas.newsapp.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vyshas.newsapp.core.data.model.ApiResponse
import com.vyshas.newsapp.core.domain.entity.DataState
import com.vyshas.newsapp.core.domain.entity.mapErrorOrException
import com.vyshas.newsapp.core.domain.exceptions.ExceptionEntity
import com.vyshas.newsapp.data.MainCoroutinesRule
import com.vyshas.newsapp.data.TopHeadlinesMockTestUtil
import com.vyshas.newsapp.data.remote.ApiUtil.successCall
import com.vyshas.newsapp.features.home.data.mapper.TopHeadlinesEntityMapper
import com.vyshas.newsapp.features.home.data.model.entertainmentnews.TopEntertainmentNews
import com.vyshas.newsapp.features.home.data.repository.TopHeadlinesRepositoryImpl
import com.vyshas.newsapp.features.home.data.repository.remote.TopHeadlinesRemoteDataSource
import com.vyshas.newsapp.features.home.domain.entity.TopEntertainmentHeadlinesEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TopHeadlinesRepositoryImplTest {

    // Subject under test
    private lateinit var repository: TopHeadlinesRepositoryImpl

    @MockK
    private lateinit var topHeadlinesRemoteDataSource: TopHeadlinesRemoteDataSource

    @MockK
    private lateinit var topHeadlinesEntityMapper: TopHeadlinesEntityMapper

    @MockK
    private lateinit var throwable: Throwable

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test getTopHeadlines() gives list of headlines`() = runBlocking {
        // Given
        repository = TopHeadlinesRepositoryImpl(
            topHeadlinesEntityMapper = topHeadlinesEntityMapper,
            topHeadlinesRemoteDataSource = topHeadlinesRemoteDataSource
        )
        val givenTopEntertainmentNewsResponse = TopHeadlinesMockTestUtil.createTopEntertainmentNewsResponse(3)
        val givenTopHeadlinesEntityList = TopHeadlinesMockTestUtil.createTopEntertainmentNewsEntity(3)
        val apiCall = successCall(givenTopEntertainmentNewsResponse)

        // When
        coEvery { topHeadlinesRemoteDataSource.getTopEntertainmentHeadlines(any()) }.returns(apiCall)
        // When
        coEvery { topHeadlinesEntityMapper.mapToEntity(any()) }.returns(givenTopHeadlinesEntityList)


        val apiResponseFlow = repository.getTopEntertainmentHeadlines(1)
        // Then
        MatcherAssert.assertThat(apiResponseFlow, CoreMatchers.notNullValue())

        // Invoke
        val topHeadlinesEntityDateState = apiResponseFlow.first()
        // Then
        MatcherAssert.assertThat(topHeadlinesEntityDateState, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(topHeadlinesEntityDateState, CoreMatchers.instanceOf(DataState.Success::class.java))

        val topHeadlinesEntityList: List<TopEntertainmentHeadlinesEntity> = (topHeadlinesEntityDateState as DataState.Success).data
        MatcherAssert.assertThat(topHeadlinesEntityList, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(topHeadlinesEntityList.size, CoreMatchers.`is`(givenTopHeadlinesEntityList.size))

        coVerify(exactly = 1) { topHeadlinesRemoteDataSource.getTopEntertainmentHeadlines(any()) }
        confirmVerified(topHeadlinesRemoteDataSource)
    }

    @Test
    fun `test getTopHeadlines() gives Api Exception`() = runBlocking {
        // Given
        repository = TopHeadlinesRepositoryImpl(
            topHeadlinesEntityMapper = topHeadlinesEntityMapper,
            topHeadlinesRemoteDataSource = topHeadlinesRemoteDataSource
        )
        val givenErrorMessage = "Error Message"
        val exception = Exception(givenErrorMessage)
        val givenApiErrorEntity = ExceptionEntity.ApiErrorEntity(givenErrorMessage)
        val apiResponse = ApiResponse.exception<TopEntertainmentNews>(exception)

        // When
        coEvery { topHeadlinesRemoteDataSource.getTopEntertainmentHeadlines(any()) }.returns(apiResponse)
        coEvery { throwable.mapErrorOrException() }.returns(givenApiErrorEntity)

        // Invoke
        val apiResponseFlow = repository.getTopEntertainmentHeadlines(1)
        // Then
        MatcherAssert.assertThat(apiResponseFlow, CoreMatchers.notNullValue())

        val topHeadlinesEntityDateState = apiResponseFlow.first()
        MatcherAssert.assertThat(topHeadlinesEntityDateState, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(topHeadlinesEntityDateState, CoreMatchers.instanceOf(DataState.Error::class.java))

        val errorMessage = (topHeadlinesEntityDateState as DataState.Error).message
        MatcherAssert.assertThat(errorMessage, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(errorMessage, CoreMatchers.equalTo(givenApiErrorEntity))

        coVerify(atLeast = 1) { topHeadlinesRemoteDataSource.getTopEntertainmentHeadlines(any()) }
        confirmVerified(topHeadlinesRemoteDataSource)
    }

}
