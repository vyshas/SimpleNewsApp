package com.vyshas.newsapp.features.home.domain.entity

data class TopHeadlinesEntity(
    val category: String,
    val country: String,
    val description: String,
    val id: String,
    val language: String,
    val name: String,
    val url: String
)