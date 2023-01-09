package com.vyshas.newsapp.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vyshas.newsapp.core.data.DataState
import com.vyshas.newsapp.core.presentation.mappers.UIErrorMapper
import com.vyshas.newsapp.core.schedulers.DispatcherProvider
import com.vyshas.newsapp.features.home.domain.usecase.GetTopEntertainmentNews
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTopEntertainmentNews: GetTopEntertainmentNews,
    private val dispatcherProvider: DispatcherProvider,
    private val uiErrorMapper: UIErrorMapper
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
                .onStart {
                    mutableUiState.update {
                        HomeUiState.Loading
                    }
                }
                .catch { ex ->
                    mutableUiState.update {
                        val errorMessage = ex.message
                        Timber.d(errorMessage)
                        HomeUiState.Error(errorMessage)
                    }
                }.collect { dataState ->
                    mutableUiState.update {
                        when (dataState) {
                            is DataState.Error -> {
                                val mapToUiMsg = uiErrorMapper.mapToUiMsg(dataState.message)
                                HomeUiState.Error(mapToUiMsg)
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
        Timber.d("error consumed called")
    }
}