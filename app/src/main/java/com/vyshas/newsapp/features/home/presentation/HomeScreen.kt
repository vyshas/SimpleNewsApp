package com.vyshas.newsapp.features.home.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.vyshas.newsapp.features.home.domain.entity.TopHeadlinesEntity
import com.vyshas.newsapp.features.home.domain.entity.previewTopHeadlinesEntity
import com.vyshas.newsapp.ui.theme.NewsAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeListScreen(
    topHeadlinesList: List<TopHeadlinesEntity>,
    modifier: Modifier = Modifier
) {
    val topAppBarState = rememberTopAppBarState()
    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        HomeListContent(
            modifier = modifier,
            contentPadding = innerPadding,
            topHeadlinesList = topHeadlinesList
        )
    }
}

@Composable
fun HomeListContent(
    topHeadlinesList: List<TopHeadlinesEntity>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    state: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        state = state
    ) {
        item {
            HomeNewsItemSection(topHeadlinesList = topHeadlinesList)
        }
    }
}

@Composable
fun HomeNewsItemSection(
    topHeadlinesList: List<TopHeadlinesEntity>
) {
    Column {
        topHeadlinesList.forEach { topHeadlinesEntity ->
            NewsCardSimple(
                topHeadlinesEntity = topHeadlinesEntity
            )
            NewsListDivider()
        }
    }
}

@Composable
fun NewsListDivider() {
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsCardSimple(
    modifier: Modifier = Modifier,
    topHeadlinesEntity: TopHeadlinesEntity
) {
    ListItem(
        headlineText = {
            Title(topHeadlinesEntity = topHeadlinesEntity)
        },
        supportingText = {
            SupportingText(topHeadlinesEntity)
        }
    )
}

@Composable
fun SupportingText(topHeadlinesEntity: TopHeadlinesEntity) {
    Text(
        text = topHeadlinesEntity.description,
        style = MaterialTheme.typography.bodyMedium,
        maxLines = 3,
        overflow = TextOverflow.Visible,
    )
}

@Composable
fun Title(topHeadlinesEntity: TopHeadlinesEntity) {
    Text(
        text = topHeadlinesEntity.name,
        style = MaterialTheme.typography.titleMedium,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
    )
}

/**
 * Display an initial empty state or swipe to refresh content.
 *
 * @param empty (state) when true, display [emptyContent]
 * @param emptyContent (slot) the content to display for the empty state
 * @param loading (state) when true, display a loading spinner over [content]
 * @param onRefresh (event) event to request refresh
 * @param content (slot) the main content to show
 */
@Composable
private fun LoadingContent(
    empty: Boolean,
    emptyContent: @Composable () -> Unit,
    loading: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    if (empty) {
        emptyContent()
    } else {
        SwipeRefresh(
            state = rememberSwipeRefreshState(loading),
            onRefresh = onRefresh,
            content = content,
        )
    }
}

@Preview("Simple News card")
@Preview("Simple News card (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SimpleTopHeadlinesPreview() {
    NewsAppTheme() {
        Surface {
            NewsCardSimple(topHeadlinesEntity = previewTopHeadlinesEntity[0])
        }
    }
}
