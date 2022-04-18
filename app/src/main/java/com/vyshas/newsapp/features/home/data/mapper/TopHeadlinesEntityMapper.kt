package com.vyshas.newsapp.features.home.data.mapper

import com.vyshas.newsapp.features.home.data.model.TopHeadlines
import com.vyshas.newsapp.features.home.domain.entity.TopHeadlinesEntity
import javax.inject.Inject

class TopHeadlinesEntityMapper @Inject constructor() {

    fun mapToEntity(
        response: TopHeadlines?
    ): List<TopHeadlinesEntity> {
        return response?.sources?.map {
            TopHeadlinesEntity(
                category = it.category,
                country = it.country,
                description = it.description,
                id = it.id,
                language = it.language,
                name = it.name,
                url = it.url
            )
        } ?: emptyList()
    }
}
