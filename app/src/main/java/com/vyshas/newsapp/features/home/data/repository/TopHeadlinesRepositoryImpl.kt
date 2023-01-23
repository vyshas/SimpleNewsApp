package com.vyshas.newsapp.features.home.data.repository

import com.vyshas.newsapp.core.data.model.ApiResponse
import com.vyshas.newsapp.core.data.model.message
import com.vyshas.newsapp.core.domain.entity.DataState
import com.vyshas.newsapp.core.domain.entity.mapErrorOrException
import com.vyshas.newsapp.core.domain.exceptions.ExceptionEntity
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
    override fun getTopEntertainmentHeadlines(
        pageSize: Int
    ): Flow<DataState<List<TopEntertainmentHeadlinesEntity>>> {

        return flow {
            when (val topHeadlines = topHeadlinesRemoteDataSource.getTopEntertainmentHeadlines(pageSize = pageSize)) {
                is ApiResponse.ApiSuccessResponse -> {
                    emit(DataState.Success(topHeadlinesEntityMapper.mapToEntity(topHeadlines.data)))
                }
                is ApiResponse.ApiFailureResponse.Error -> {
                    val mapError = ExceptionEntity.ApiErrorEntity(topHeadlines.message())
                    emit(DataState.Error(mapError))
                }
                is ApiResponse.ApiFailureResponse.Exception -> {
                    val mapException = topHeadlines.exception.mapErrorOrException()
                    emit(DataState.Error(mapException))
                }
            }
        }
    }
}
