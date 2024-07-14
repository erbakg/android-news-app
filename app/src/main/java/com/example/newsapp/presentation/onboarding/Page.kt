package com.example.newsapp.presentation.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.example.newsapp.R
import com.example.newsapp.presentation.Utils

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)

val pages = listOf(
    Page(
        title = "First Onboarding",
        description = Utils.generateRandomWords(12),
        image = R.drawable.onboarding1
    ),
    Page(
        title = "Second Onboarding",
        description = Utils.generateRandomWords(12),
        image = R.drawable.onboarding2
    ),
    Page(
        title = "Third Onboarding",
        description = Utils.generateRandomWords(12),
        image = R.drawable.onboarding3
    ),
)
