package com.vyshas.newsapp.features.home.data.mapper

import com.vyshas.newsapp.core.presentation.mappers.StringUtils
import com.vyshas.newsapp.features.home.data.model.entertainmentnews.Article
import com.vyshas.newsapp.features.home.data.model.entertainmentnews.TopEntertainmentNews
import com.vyshas.newsapp.features.home.domain.TopHeadlinesMockTestUtil
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TopHeadlinesEntityMapperTest {

    // Subject under test
    private lateinit var topHeadlinesEntityMapper: TopHeadlinesEntityMapper

    @MockK
    private lateinit var stringUtils: StringUtils

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { stringUtils.instantToAgoString(any()) } returns "4h ago"

        topHeadlinesEntityMapper = TopHeadlinesEntityMapper(
            stringUtils
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test entity mapper when content is not present`() {
        val articleWithNoContent = TopHeadlinesMockTestUtil.createArticle().copy(content = null)
        val articlesWithContent = TopHeadlinesMockTestUtil.createArticles(2)
        val articleList: List<Article> = listOf(
            articleWithNoContent,
            articlesWithContent[0],
            articlesWithContent[1]
        )

        val givenTopEntertainmentNewsResponse = TopEntertainmentNews(
            articles = articleList,
            status = "ok",
            totalResults = 70
        )

        val result = topHeadlinesEntityMapper.mapToEntity(givenTopEntertainmentNewsResponse)
        MatcherAssert.assertThat(result.size, CoreMatchers.`is`(2))
    }

    @Test
    fun `test entity mapper when url Image is not present`() {
        val articleWithNoContent = TopHeadlinesMockTestUtil.createArticle().copy(urlToImage = null)
        val articlesWithContent = TopHeadlinesMockTestUtil.createArticles(2)
        val articleList: List<Article> = listOf(
            articleWithNoContent,
            articlesWithContent[0],
            articlesWithContent[1]
        )

        val givenTopEntertainmentNewsResponse = TopEntertainmentNews(
            articles = articleList,
            status = "ok",
            totalResults = 70
        )

        val result = topHeadlinesEntityMapper.mapToEntity(givenTopEntertainmentNewsResponse)
        MatcherAssert.assertThat(result.size, CoreMatchers.`is`(2))
    }

}