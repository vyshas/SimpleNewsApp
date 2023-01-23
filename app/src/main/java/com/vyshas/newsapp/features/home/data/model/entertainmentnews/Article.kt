package com.vyshas.newsapp.features.home.data.model.entertainmentnews

import com.vyshas.newsapp.core.data.utils.InstantSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    @Serializable(InstantSerializer::class)
    val publishedAt: Instant,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String?
)
