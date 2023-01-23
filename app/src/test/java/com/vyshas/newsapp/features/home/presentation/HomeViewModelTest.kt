package com.vyshas.newsapp.features.home.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.vyshas.newsapp.core.domain.MainCoroutinesRule
import com.vyshas.newsapp.core.domain.TestCoroutineDispatcherProvider
import com.vyshas.newsapp.core.domain.entity.DataState
import com.vyshas.newsapp.core.domain.exceptions.ExceptionEntity
import com.vyshas.newsapp.core.presentation.mappers.UiErrorMapper
import com.vyshas.newsapp.features.home.domain.TopHeadlinesMockTestUtil
import com.vyshas.newsapp.features.home.domain.entity.TopEntertainmentHeadlinesEntity
import com.vyshas.newsapp.features.home.domain.usecase.GetTopEntertainmentNews
import com.vyshas.newsapp.features.home.presentation.list.HomeUiState
import com.vyshas.newsapp.features.home.presentation.list.HomeViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HomeViewModelTest {

    // Subject under test
    private lateinit var viewModel: HomeViewModel

    @MockK
    private lateinit var getTopEntertainmentNews: GetTopEntertainmentNews

    @MockK
    private lateinit var uiErrorMapper: UiErrorMapper

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = HomeViewModel(
            getTopEntertainmentNews = getTopEntertainmentNews,
            dispatcherProvider = TestCoroutineDispatcherProvider(),
            uiErrorMapper = uiErrorMapper
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun stateIsInitiallyLoading() = runTest {

        val givenTopHeadlinesEntity = TopHeadlinesMockTestUtil.createTopEntertainmentNewsEntity(3)
        val givenTopEntertainmentNewsSuccess: Flow<DataState.Success<List<TopEntertainmentHeadlinesEntity>>> = flow {
            emit(DataState.Success(givenTopHeadlinesEntity))
        }

        coEvery { getTopEntertainmentNews.invoke() }.returns(givenTopEntertainmentNewsSuccess)

        assertEquals(
            HomeUiState.Loading,
            viewModel.uiState.value
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test data is returned successfully when refresh is clicked`() = runTest {

        val givenTopHeadlinesEntity = TopHeadlinesMockTestUtil.createTopEntertainmentNewsEntity(3)
        val givenTopEntertainmentNewsSuccess: Flow<DataState.Success<List<TopEntertainmentHeadlinesEntity>>> = flowOf(
            DataState.Success(givenTopHeadlinesEntity)
        )

        coEvery { getTopEntertainmentNews.invoke() }.returns(givenTopEntertainmentNewsSuccess)

        viewModel.refresh()

        viewModel.uiState.test {
            awaitItem()

            val dataState = awaitItem()

            assertEquals(
                HomeUiState.HasContent(data = givenTopHeadlinesEntity),
                dataState
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test api error state when refresh is clicked`() = runTest {

        viewModel.refresh()

        val errorMsg = "api error"
        coEvery { uiErrorMapper.mapToUiMsg(any()) }.returns(errorMsg)

        val givenTopEntertainmentNewsError: Flow<DataState<List<TopEntertainmentHeadlinesEntity>>> = flowOf(
            DataState.Error(ExceptionEntity.ApiErrorEntity(errorMsg))
        )

        coEvery { getTopEntertainmentNews.invoke() }.returns(givenTopEntertainmentNewsError)

        viewModel.uiState.test {
            awaitItem()

            assertEquals(
                HomeUiState.Error(errorMsg),
                awaitItem()
            )

            cancelAndIgnoreRemainingEvents()
        }
    }
}
