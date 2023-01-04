package com.vyshas.newsapp.features.home.data.repository

import androidx.annotation.WorkerThread
import com.vyshas.newsapp.common.data.ApiResponse
import com.vyshas.newsapp.common.data.DataState
import com.vyshas.newsapp.common.data.message
import com.vyshas.newsapp.features.home.data.mapper.TopHeadlinesEntityMapper
import com.vyshas.newsapp.features.home.data.repository.remote.TopHeadlinesRemoteDataSource
import com.vyshas.newsapp.features.home.domain.entity.TopEntertainmentHeadlinesEntity
import com.vyshas.newsapp.features.home.domain.repository.TopHeadlinesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TopHeadlinesRepositoryImpl @Inject constructor(
    private val topHeadlinesRemoteDataSource: TopHeadlinesRemoteDataSource,
    private val topHeadlinesEntityMapper: TopHeadlinesEntityMapper
) : TopHeadlinesRepository {

    @WorkerThread
    override suspend fun getTopEntertainmentHeadlines(
        pageSize: Int
    ): Flow<DataState<List<TopEntertainmentHeadlinesEntity>>> {

        return flow {
            when (val topHeadlines =
                topHeadlinesRemoteDataSource.getTopEntertainmentHeadlines(pageSize = pageSize)) {
                is ApiResponse.ApiSuccessResponse -> {
                    emit(DataState.Success(topHeadlinesEntityMapper.mapToEntity(topHeadlines.data)))
                }
                is ApiResponse.ApiFailureResponse.Error -> emit(DataState.Error(topHeadlines.message()))
                is ApiResponse.ApiFailureResponse.Exception -> emit(DataState.Error(topHeadlines.message()))
            }
        }

    }
}
