package com.example.newsapp.presentation.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newsapp.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel() {
    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state


    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.FetchNews -> {
                fetchNews()
            }

            is HomeEvent.UpdateNews -> {
                updateNews()
            }
        }
    }

    private fun fetchNews() {

        val news = newsUseCases.getNews(
            sources = listOf("bbc-news", "abs-news", "al-jazeera-english")
        ).cachedIn(viewModelScope)

        _state.value = state.value.copy(articles = news)

    }

    private fun updateNews() {
        try {
            _state.value = state.value.copy(isRefreshing = true)
            val news = newsUseCases.getNews(
                sources = listOf("bbc-news", "abs-news", "al-jazeera-english")
            ).cachedIn(viewModelScope)
            _state.value = state.value.copy(articles = news)
        } finally {
            viewModelScope.launch {
                delay(2000L)
                _state.value = state.value.copy(isRefreshing = false)
            }

        }

    }
}