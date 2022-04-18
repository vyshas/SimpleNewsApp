package com.vyshas.newsapp.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vyshas.newsapp.common.data.DataState
import com.vyshas.newsapp.data.MainCoroutinesRule
import com.vyshas.newsapp.data.TopHeadlinesMockTestUtil
import com.vyshas.newsapp.data.remote.ApiUtil.successCall
import com.vyshas.newsapp.features.home.data.mapper.TopHeadlinesEntityMapper
import com.vyshas.newsapp.features.home.data.repository.TopHeadlinesRepositoryImpl
import com.vyshas.newsapp.features.home.data.repository.remote.TopHeadlinesRemoteDataSource
import com.vyshas.newsapp.features.home.domain.entity.TopHeadlinesEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class ImagineRepositoryTest {

    // Subject under test
    private lateinit var repository: TopHeadlinesRepositoryImpl

    @MockK
    private lateinit var topHeadlinesRemoteDataSource: TopHeadlinesRemoteDataSource

    @MockK
    private lateinit var topHeadlinesEntityMapper: TopHeadlinesEntityMapper

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
        val givenTopHeadlinesResponse = TopHeadlinesMockTestUtil.createTopHeadlinesResponse(3)
        val givenTopHeadlinesEntityList = TopHeadlinesMockTestUtil.createTopHeadlinesEntity(3)
        val apiCall = successCall(givenTopHeadlinesResponse)

        // When
        coEvery { topHeadlinesRemoteDataSource.getTopHeadlines(any()) }.returns(apiCall)
        // When
        coEvery { topHeadlinesEntityMapper.mapToEntity(any()) }.returns(givenTopHeadlinesEntityList)

        // Invoke
        val topHeadlinesEntityDateState: DataState<List<TopHeadlinesEntity>> = repository.getTopHeadlines(1)

        // Then
        MatcherAssert.assertThat(topHeadlinesEntityDateState, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(topHeadlinesEntityDateState, CoreMatchers.instanceOf(DataState.Success::class.java))

        val topHeadlinesEntityList: List<TopHeadlinesEntity> = (topHeadlinesEntityDateState as DataState.Success).data
        MatcherAssert.assertThat(topHeadlinesEntityList, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(topHeadlinesEntityList.size, CoreMatchers.`is`(givenTopHeadlinesEntityList.size))

        coVerify(exactly = 1) { topHeadlinesRemoteDataSource.getTopHeadlines(any()) }
        confirmVerified(topHeadlinesRemoteDataSource)
    }
}
