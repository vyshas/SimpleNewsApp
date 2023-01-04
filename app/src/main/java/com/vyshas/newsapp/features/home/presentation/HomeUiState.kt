package com.vyshas.newsapp.features.home.presentation

import com.vyshas.newsapp.features.home.domain.entity.TopEntertainmentHeadlinesEntity

/**
 * A sealed hierarchy describing the state of the feed of news resources.
 */
sealed interface HomeUiState {
    object Loading : HomeUiState

    data class Success(
        val feed: List<TopEntertainmentHeadlinesEntity>
    ) : HomeUiState

    object Error : HomeUiState
}
