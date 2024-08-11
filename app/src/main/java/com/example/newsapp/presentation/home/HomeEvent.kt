package com.example.newsapp.presentation.home


sealed class HomeEvent {
    object FetchNews : HomeEvent()
    object UpdateNews : HomeEvent()
}