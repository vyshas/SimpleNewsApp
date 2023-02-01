package com.vyshas.newsapp.features.home.presentation.list

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.vyshas.newsapp.R
import com.vyshas.newsapp.features.home.domain.entity.TopEntertainmentHeadlinesEntity
import com.vyshas.newsapp.features.home.domain.entity.previewTopEntertainmentHeadlinesEntities
import com.vyshas.newsapp.ui.theme.NewsAppTheme

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    onNewsItemClick: (String) -> Unit
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    HomeListScreen(
        uiState = uiState,
        onRefresh = { homeViewModel.refresh() },
        onErrorConsumed = { homeViewModel.onErrorConsumed() },
        onNewsItemClick = {
            homeViewModel.onNewsItemClick(it)
            onNewsItemClick(it)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeListScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    onRefresh: () -> Unit,
    onErrorConsumed: () -> Unit,
    onNewsItemClick: (String) -> Unit
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)
    val isFeedLoading = uiState is HomeUiState.Loading
    val isEmpty = uiState is HomeUiState.EmptyContent

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopBar(scrollBehavior, stringResource(id = R.string.home))
        },
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        val contentModifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)

        LoadingContent(
            empty = isEmpty,
            emptyContent = { FullScreenLoading() },
            loading = isFeedLoading,
            onRefresh = { onRefresh() }
        ) {
            when (uiState) {
                HomeUiState.Loading -> Unit
                is HomeUiState.HasContent -> HomeListContent(
                    topHeadlinesList = uiState.data,
                    modifier = contentModifier,
                    contentPadding = innerPadding,
                    onNewsItemClick = onNewsItemClick
                )
                is HomeUiState.Error -> ShowErrorMessage(
                    snackbarHostState = snackbarHostState,
                    onRefresh = onRefresh,
                    onErrorConsumed = onErrorConsumed
                )
                HomeUiState.EmptyContent -> FullScreenLoading()
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    titleText: String
) {
    CenterAlignedTopAppBar(
        title = { Text(text = titleText) },
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun ShowErrorMessage(
    snackbarHostState: SnackbarHostState,
    onRefresh: () -> Unit,
    onErrorConsumed: () -> Unit
) {
    // Get the text to show on the message from resources
    val errorMessageText: String = stringResource(R.string.error_generic)
    val retryMessageText = stringResource(id = R.string.retry)

    LaunchedEffect(Unit) {
        val snackbarResult = snackbarHostState.showSnackbar(
            message = errorMessageText,
            actionLabel = retryMessageText,
        )

        when (snackbarResult) {
            SnackbarResult.Dismissed -> Unit
            SnackbarResult.ActionPerformed -> onRefresh()
        }
        onErrorConsumed()
    }
}

@Composable
fun HomeListContent(
    topHeadlinesList: List<TopEntertainmentHeadlinesEntity>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    state: LazyListState = rememberLazyListState(),
    onNewsItemClick: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        state = state,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            HomeNewsItemSection(topHeadlinesList = topHeadlinesList, onNewsItemClick = onNewsItemClick)
        }
    }
}

@Composable
fun HomeNewsItemSection(
    topHeadlinesList: List<TopEntertainmentHeadlinesEntity>,
    onNewsItemClick: (String) -> Unit
) {
    Column {
        topHeadlinesList.forEach { topHeadlinesEntity ->
            NewsCardSimple(
                topHeadlinesEntity = topHeadlinesEntity,
                onNewsItemClick = onNewsItemClick
            )
        }
    }
}

@Composable
fun NewsCardSimple(
    modifier: Modifier = Modifier,
    topHeadlinesEntity: TopEntertainmentHeadlinesEntity,
    onNewsItemClick: (String) -> Unit
) {
    OutlinedCard(
        modifier = modifier
            .padding(8.dp)
            .clickable(onClick = { onNewsItemClick(topHeadlinesEntity.url) })
    ) {
        Column(modifier.padding(8.dp)) {
            NewsHeaderImage(
                headerImageUrl = topHeadlinesEntity.urlToImage, contentDescription = topHeadlinesEntity.description
            )
            Spacer(modifier = modifier.height(8.dp))
            Column {
                CategoriesText(R.string.categories_text, modifier)
                TitleText(titleText = topHeadlinesEntity.title)
            }
            Row {
                NewsSourceContainer(
                    modifier = modifier, sourceText = topHeadlinesEntity.source, publishedDateText = topHeadlinesEntity.publishedAt
                )
            }
        }
    }
}

@Composable
fun NewsSourceContainer(
    modifier: Modifier,
    sourceText: String,
    publishedDateText: String
) {
    Row {
        SourceLabelText(modifier = modifier, sourceText)
        Spacer(modifier = Modifier.width(12.dp))
        PublishedDateText(publishedDateText = publishedDateText)
    }
}

@Composable
fun PublishedDateText(publishedDateText: String) {
    val timeIconId = "timeIconId"
    val text = buildAnnotatedString {
        appendInlineContent(timeIconId, "timeIconText")
        append(publishedDateText)
    }
    val inlineContent = mapOf(
        Pair(
            timeIconId,
            InlineTextContent(
                Placeholder(
                    height = 14.sp, width = 14.sp, placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                )
            ) {
                Icon(painterResource(id = R.drawable.ic_time), "")
            }
        )
    )

    Text(
        text = text, inlineContent = inlineContent, maxLines = 1, fontWeight = FontWeight.W400, fontSize = 13.sp, textAlign = TextAlign.Start
    )
}

@Composable
fun SourceLabelText(modifier: Modifier, sourceText: String) {
    Text(
        modifier = modifier, text = sourceText, maxLines = 1, fontWeight = FontWeight.W500, fontSize = 13.sp, textAlign = TextAlign.Start
    )
}

@Composable
fun CategoriesText(categoriesStringRes: Int, modifier: Modifier) {
    Text(
        text = stringResource(id = categoriesStringRes),
        fontStyle = FontStyle.Normal,
        fontSize = 13.sp,
        lineHeight = 19.sp,
        textAlign = TextAlign.Start,
        modifier = modifier,
    )
}

@Composable
fun NewsHeaderImage(
    headerImageUrl: String?,
    contentDescription: String?
) {
    AsyncImage(
        placeholder = if (LocalInspectionMode.current) {
            painterResource(R.drawable.ic_placeholder_default)
        } else {
            // TODO b/228077205, show specific loading image visual
            null
        },
        modifier = Modifier
            .clip(shape = RoundedCornerShape(6.dp))
            .fillMaxWidth()
            .height(183.dp),
        contentScale = ContentScale.Crop,
        model = headerImageUrl,
        contentDescription = contentDescription // decorative image
    )
}

@Composable
fun TitleText(titleText: String) {
    Text(
        text = titleText,
        style = MaterialTheme.typography.titleMedium,
        maxLines = 2,
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

/**
 * Full screen circular progress indicator
 */
@Composable
private fun FullScreenLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}

@Preview("Simple News card")
@Preview("Simple News card (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SimpleTopHeadlinesPreview() {
    NewsAppTheme {
        Surface {
            NewsCardSimple(topHeadlinesEntity = previewTopEntertainmentHeadlinesEntities[0], onNewsItemClick = {})
        }
    }
}

@Preview("Simple TopHeadlines List")
@Preview("Simple TopHeadlines List(dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SimpleTopHeadlinesListPreview() {
    NewsAppTheme {
        HomeListContent(
            topHeadlinesList = previewTopEntertainmentHeadlinesEntities,
            onNewsItemClick = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Top Bar")
@Preview("TopBar (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TopBar() {
    NewsAppTheme {
        TopBar(
            scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
            titleText = "Home"
        )
    }
}
