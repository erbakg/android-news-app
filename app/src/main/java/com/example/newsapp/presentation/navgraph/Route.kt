package com.example.newsapp.presentation.navgraph

import com.example.newsapp.util.NavList

sealed class Route(
    val route: String
){
    object OnboardingScreen: Route(NavList.ONBOARDING_SCREEN)
    object HomeScreen: Route(NavList.HOME_SCREEN)
    object SearchScreen: Route(NavList.SEARCH_SCREEN)
    object BookmarkScreen: Route(NavList.BOOKMARK_SCREEN)
    object DetailsScreen: Route(NavList.DETAILS_SCREEN)
    object AppStartNavigation: Route(NavList.APP_START_NAV)
    object NewsNavigation: Route(NavList.NEWS_NAV)
    object NewsNavigatorScreen: Route(NavList.NEWS_NAV_SCREEN)
}