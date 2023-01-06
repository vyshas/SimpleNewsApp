package com.vyshas.newsapp.features.home.domain.entity

data class TopEntertainmentHeadlinesEntity(
    val author: String,
    val content: String?,
    val description: String,
    val publishedAt: String,
    val source: String,
    val title: String,
    val url: String,
    val urlToImage: String?
)

val previewTopEntertainmentHeadlinesEntities = listOf(
    TopEntertainmentHeadlinesEntity(
        source = "India Today",
        author = "Team India Forums",
        title = "Bigg Boss 16: Priyanka saves Ankit from nomination - India Forums",
        description = "As per latest buzz, we hear that Ankit Gupta is saved from this week's nomination.",
        url = "https=//www.indiaforums.com/article/bigg-boss-16-priyanka-saves-ankit-from-nomination_192784",
        urlToImage = "https://img.indiaforums.com/article/1200x675/19/2784-bigg-boss-16-priyanka-saves-ankit-from-nomination.jpg",
        publishedAt = "4h ago",
        content = "Bigg Boss 16 has been keeping its audience glued to the show with its high voltage drama. The latest promo has seen MC Stan nominate Tina Datta for elimination from the show. While the two took a dig… [+623 chars]"
    ),

    TopEntertainmentHeadlinesEntity(
        source = "BBC News",
        author = "NNC FORUMs",
        title = "Bigg Boss 19: Priyanka saves Ankit from nomination - India Forums",
        description = "As per latest buzz, we hear that Ankit Gupta is saved from this week's nomination.",
        url = "https=//www.indiaforums.com/article/bigg-boss-16-priyanka-saves-ankit-from-nomination_192784",
        urlToImage = "https://img.indiaforums.com/article/1200x675/19/2784-bigg-boss-16-priyanka-saves-ankit-from-nomination.jpg",
        publishedAt = "2022-12-20T13:48:00Z",
        content = "Bigg Boss 16 has been keeping its audience glued to the show with its high voltage drama."
    )
)