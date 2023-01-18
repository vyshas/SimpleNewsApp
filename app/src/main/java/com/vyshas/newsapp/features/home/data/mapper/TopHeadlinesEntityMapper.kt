package com.vyshas.newsapp.features.home.data.mapper

import com.vyshas.newsapp.core.presentation.mappers.StringUtils
import com.vyshas.newsapp.features.home.data.model.entertainmentnews.TopEntertainmentNews
import com.vyshas.newsapp.features.home.domain.entity.TopEntertainmentHeadlinesEntity
import javax.inject.Inject

class TopHeadlinesEntityMapper @Inject constructor(
    private val stringUtils: StringUtils
) {

    fun mapToEntity(
        response: TopEntertainmentNews?
    ): List<TopEntertainmentHeadlinesEntity> {

        val topEntertainmentHeadlinesEntities = response?.articles?.filter { it.content != null && it.urlToImage != null }?.map {
            TopEntertainmentHeadlinesEntity(
                author = it.author ?: "",
                content = it.content,
                description = it.description ?: "",
                publishedAt = stringUtils.instantToAgoString(it.publishedAt),
                source = it.source.name ?: "",
                title = it.title,
                url = it.url,
                urlToImage = it.urlToImage
            )
        } ?: emptyList()

        return topEntertainmentHeadlinesEntities
    }
}
