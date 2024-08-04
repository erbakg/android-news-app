package com.example.newsapp.presentation.details

import androidx.compose.ui.platform.LocalContext
import com.example.newsapp.domain.model.Article

sealed class DetailsEvent {
    object SaveArticle : DetailsEvent()

}