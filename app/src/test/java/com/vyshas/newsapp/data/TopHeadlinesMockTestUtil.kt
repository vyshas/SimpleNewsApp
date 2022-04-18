package com.vyshas.newsapp.data

import com.vyshas.newsapp.features.home.data.model.Source
import com.vyshas.newsapp.features.home.data.model.TopHeadlines
import com.vyshas.newsapp.features.home.domain.entity.TopHeadlinesEntity

class TopHeadlinesMockTestUtil {
    companion object {
        fun createTopHeadlinesEntity(count: Int): List<TopHeadlinesEntity> {
            return (0 until count).map {
                TopHeadlinesEntity(
                    id = "$it",
                    country = "us",
                    category = "general",
                    description = "description",
                    language = "en",
                    name = "ABC News",
                    url = "https://abcnews.go.com"
                )
            }
        }

        fun createTopHeadlinesResponse(count: Int): TopHeadlines {
            return TopHeadlines(
                sources = createSources(count),
                status = "ok"
            )
        }

        private fun createSources(count: Int): List<Source> {
            return (0 until count).map {
                Source(
                    id = "$it",
                    country = "us",
                    category = "general",
                    description = "description",
                    language = "en",
                    name = "ABC News",
                    url = "https://abcnews.go.com"
                )
            }
        }
    }
}
