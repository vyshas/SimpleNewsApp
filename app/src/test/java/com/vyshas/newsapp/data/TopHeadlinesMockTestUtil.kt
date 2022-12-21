package com.vyshas.newsapp.data

import com.vyshas.newsapp.features.home.data.model.entertainmentnews.Article
import com.vyshas.newsapp.features.home.data.model.entertainmentnews.Source
import com.vyshas.newsapp.features.home.data.model.entertainmentnews.TopEntertainmentNews
import com.vyshas.newsapp.features.home.domain.entity.TopEntertainmentHeadlinesEntity

class TopHeadlinesMockTestUtil {
    companion object {
        fun createTopEntertainmentNewsEntity(count: Int): List<TopEntertainmentHeadlinesEntity> {
            return (0 until count).map {
                TopEntertainmentHeadlinesEntity(
                    source = "India Today",
                    author = "Team India Forums",
                    title = "Bigg Boss 16: Priyanka saves Ankit from nomination - India Forums",
                    description = "As per latest buzz, we hear that Ankit Gupta is saved from this week's nomination.",
                    url = "https=//www.indiaforums.com/article/bigg-boss-16-priyanka-saves-ankit-from-nomination_192784",
                    urlToImage = "https://img.indiaforums.com/article/1200x675/19/2784-bigg-boss-16-priyanka-saves-ankit-from-nomination.jpg",
                    publishedAt = "2022-12-20T13:45:35Z",
                    content = "Bigg Boss 16 has been keeping its audience glued to the show with its high voltage drama. The latest promo has seen MC Stan nominate Tina Datta for elimination from the show. While the two took a dig… [+623 chars]"
                )
            }
        }

        fun createTopEntertainmentNewsResponse(count: Int): TopEntertainmentNews {
            return TopEntertainmentNews(
                articles = createArticles(count),
                status = "ok",
                totalResults = 70
            )
        }

        private fun createArticles(count: Int): List<Article> {
            return (0 until count).map {
                Article(
                    source = createSource(),
                    author = "Team India Forums",
                    title = "Bigg Boss 16: Priyanka saves Ankit from nomination - India Forums",
                    description = "As per latest buzz, we hear that Ankit Gupta is saved from this week's nomination.",
                    url = "https=//www.indiaforums.com/article/bigg-boss-16-priyanka-saves-ankit-from-nomination_192784",
                    urlToImage = "https://img.indiaforums.com/article/1200x675/19/2784-bigg-boss-16-priyanka-saves-ankit-from-nomination.jpg",
                    publishedAt = "2022-12-20T13:45:35Z",
                    content = "Bigg Boss 16 has been keeping its audience glued to the show with its high voltage drama. The latest promo has seen MC Stan nominate Tina Datta for elimination from the show. While the two took a dig… [+623 chars]"
                )
            }
        }

        private fun createSource(): Source {
            return Source(
                id = "123",
                name = "India Today"
            )
        }
    }
}
