package com.vyshas.newsapp.features.home.data.model.entertainmentnews

data class TopEntertainmentNews(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)