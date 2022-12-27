package com.vyshas.newsapp.features.home.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.vyshas.newsapp.R
import com.vyshas.newsapp.features.home.domain.entity.TopEntertainmentHeadlinesEntity
import com.vyshas.newsapp.features.home.domain.entity.previewTopEntertainmentHeadlinesEntities
import com.vyshas.newsapp.ui.theme.NewsAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeListScreen(
    topHeadlinesList: List<TopEntertainmentHeadlinesEntity>,
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
    topHeadlinesList: List<TopEntertainmentHeadlinesEntity>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    state: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        state = state,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            HomeNewsItemSection(topHeadlinesList = topHeadlinesList)
        }
    }
}

@Composable
fun HomeNewsItemSection(
    topHeadlinesList: List<TopEntertainmentHeadlinesEntity>
) {
    Column {
        topHeadlinesList.forEach { topHeadlinesEntity ->
            NewsCardSimple(
                topHeadlinesEntity = topHeadlinesEntity
            )
        }
    }
}

@Composable
fun NewsCardSimple(
    modifier: Modifier = Modifier,
    topHeadlinesEntity: TopEntertainmentHeadlinesEntity
) {
    OutlinedCard(
        modifier = modifier.padding(8.dp)
    ) {
        Column(modifier.padding(8.dp)) {
            NewsHeaderImage(
                headerImageUrl = topHeadlinesEntity.urlToImage,
                contentDescription = topHeadlinesEntity.description
            )
            Spacer(modifier = modifier.height(8.dp))
            Column {
                CategoriesText(R.string.categories_text, modifier)
                TitleText(titleText = topHeadlinesEntity.title)
            }
            Row {
                NewsSourceContainer(
                    modifier = modifier,
                    sourceText = topHeadlinesEntity.source,
                    publishedDateText = topHeadlinesEntity.publishedAt
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
                    height = 14.sp,
                    width = 14.sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                )
            ) {
                Icon(painterResource(id = R.drawable.ic_time), "")
            }
        )
    )

    Text(
        text = text,
        inlineContent = inlineContent,
        maxLines = 1,
        fontWeight = FontWeight.W400,
        fontSize = 13.sp,
        textAlign = TextAlign.Start
    )
}

@Composable
fun SourceLabelText(modifier: Modifier, sourceText: String) {
    Text(
        modifier = modifier,
        text = sourceText,
        maxLines = 1,
        fontWeight = FontWeight.W500,
        fontSize = 13.sp,
        textAlign = TextAlign.Start
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

@Preview("Simple News card")
@Preview("Simple News card (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SimpleTopHeadlinesPreview() {
    NewsAppTheme() {
        Surface {
            NewsCardSimple(topHeadlinesEntity = previewTopEntertainmentHeadlinesEntities[0])
        }
    }
}

@Preview("Simple TopHeadlines List")
@Preview("Simple TopHeadlines List(dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SimpleTopHeadlinesListPreview() {
    NewsAppTheme() {
        HomeListScreen(
            topHeadlinesList = previewTopEntertainmentHeadlinesEntities
        )
    }
}
