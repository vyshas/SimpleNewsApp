package com.vyshas.newsapp.features.home.presentation

import com.vyshas.newsapp.features.home.domain.entity.TopEntertainmentHeadlinesEntity

/**
 * A sealed hierarchy describing the state of the feed of news resources.
 */
sealed interface HomeUiState {
    object Loading : HomeUiState

    data class HasContent(
        val data: List<TopEntertainmentHeadlinesEntity>
    ) : HomeUiState

    object EmptyContent : HomeUiState

    data class Error(val errorMsg: String? = null) : HomeUiState
}
