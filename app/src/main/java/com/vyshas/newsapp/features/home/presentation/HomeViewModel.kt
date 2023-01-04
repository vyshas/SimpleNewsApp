package com.vyshas.newsapp.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vyshas.newsapp.common.data.DataState
import com.vyshas.newsapp.common.schedulers.DispatcherProvider
import com.vyshas.newsapp.features.home.domain.entity.TopEntertainmentHeadlinesEntity
import com.vyshas.newsapp.features.home.domain.usecase.GetTopEntertainmentNews
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTopEntertainmentNews: GetTopEntertainmentNews,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    // UI state exposed to the UI
    private val mutableUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = mutableUiState.asStateFlow()

    init {
        refresh()
    }

    private fun refresh() {
        mutableUiState.update { loading -> loading }

        viewModelScope.launch(dispatcherProvider.main()) {
            // Trigger repository requests
            getTopEntertainmentNews().collect { dataState: DataState<List<TopEntertainmentHeadlinesEntity>> ->
                when (dataState) {
                    is DataState.Error -> mutableUiState.update { HomeUiState.Error }
                    is DataState.Success -> mutableUiState.update {
                        HomeUiState.Success(dataState.data)
                    }
                }
            }
        }
    }
}