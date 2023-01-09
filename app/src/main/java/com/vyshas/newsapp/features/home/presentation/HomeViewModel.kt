package com.vyshas.newsapp.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vyshas.newsapp.common.data.DataState
import com.vyshas.newsapp.common.schedulers.DispatcherProvider
import com.vyshas.newsapp.features.home.domain.usecase.GetTopEntertainmentNews
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTopEntertainmentNews: GetTopEntertainmentNews,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val mutableUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)

    // UI state exposed to the UI
    val uiState: StateFlow<HomeUiState> = mutableUiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = HomeUiState.Loading
    )

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch(dispatcherProvider.main()) {
            getTopEntertainmentNews()
                .catch { ex ->
                    mutableUiState.update { HomeUiState.Error(ex.message) }
                }.collect { dataState ->
                    mutableUiState.update {
                        when (dataState) {
                            is DataState.Error -> {
                                HomeUiState.Error(dataState.message)
                            }
                            is DataState.Success -> {
                                if (dataState.data.isEmpty()) {
                                    HomeUiState.EmptyContent
                                } else {
                                    HomeUiState.HasContent(data = dataState.data)
                                }
                            }
                        }
                    }
                }
        }
    }

    fun onErrorConsumed() {
        //TODO
    }
}