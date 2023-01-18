package com.vyshas.newsapp.features.home.data.model.entertainmentnews

import kotlinx.serialization.Serializable

@Serializable
data class TopEntertainmentNews(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)