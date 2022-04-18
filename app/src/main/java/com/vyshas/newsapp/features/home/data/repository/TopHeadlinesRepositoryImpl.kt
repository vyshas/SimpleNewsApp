package com.vyshas.newsapp.features.home.data.repository

import com.vyshas.newsapp.common.data.ApiResponse
import com.vyshas.newsapp.common.data.DataState
import com.vyshas.newsapp.common.data.message
import com.vyshas.newsapp.features.home.data.mapper.TopHeadlinesEntityMapper
import com.vyshas.newsapp.features.home.data.repository.remote.TopHeadlinesRemoteDataSource
import com.vyshas.newsapp.features.home.domain.entity.TopHeadlinesEntity
import com.vyshas.newsapp.features.home.domain.repository.TopHeadlinesRepository
import javax.inject.Inject

class TopHeadlinesRepositoryImpl @Inject constructor(
    private val topHeadlinesRemoteDataSource: TopHeadlinesRemoteDataSource,
    private val topHeadlinesEntityMapper: TopHeadlinesEntityMapper
) : TopHeadlinesRepository {

    override suspend fun getTopHeadlines(
        pageSize: Int
    ): DataState<List<TopHeadlinesEntity>> {

        return when (val topHeadlines = topHeadlinesRemoteDataSource.getTopHeadlines(pageSize = pageSize)) {
            is ApiResponse.ApiSuccessResponse -> {
                DataState.Success(topHeadlinesEntityMapper.mapToEntity(topHeadlines.data))
            }
            is ApiResponse.ApiFailureResponse.Error -> DataState.Error(topHeadlines.message())
            is ApiResponse.ApiFailureResponse.Exception -> DataState.Error(topHeadlines.message())
        }
    }
}