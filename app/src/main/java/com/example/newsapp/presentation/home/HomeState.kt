package com.example.newsapp.presentation.home

import androidx.paging.PagingData
import com.example.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

data class HomeState(
    val articles: Flow<PagingData<Article>>? = null,
    val isRefreshing: Boolean = false
)
