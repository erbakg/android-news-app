package com.example.newsapp.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapp.R
import com.example.newsapp.domain.model.Article
import com.example.newsapp.presentation.Dimensions.PaddingL
import com.example.newsapp.presentation.common.ArticlesList
import com.example.newsapp.presentation.common.SearchBar

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    event: (HomeEvent) -> Unit,
    navigateToSearch: () -> Unit,
    navigateToDetails: (Article) -> Unit
) {
    LaunchedEffect(Unit) {
        event(HomeEvent.FetchNews)
    }

    val loadedArticles = state.articles?.collectAsLazyPagingItems()
    val isRefreshing = state.isRefreshing

    val titles by remember(state.articles) {
        derivedStateOf {
            loadedArticles?.let { articles ->
                if (articles.itemCount > 10) {
                    articles.itemSnapshotList.items.slice(0..9)
                        .joinToString(separator = " \uD83d\uDFE5 ") {
                            it.title
                        }
                } else {
                    ""
                }
            } ?: ""
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = PaddingL)
            .statusBarsPadding()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier
                .width(150.dp)
                .height(30.dp)
                .padding(horizontal = PaddingL)
        )
        Spacer(modifier = Modifier.height(PaddingL))

        SearchBar(
            text = "",
            readOnly = true,
            onValueChange = {},
            onSearch = { /*TODO*/ },
            modifier = Modifier.padding(horizontal = PaddingL),
            onClick = {
                navigateToSearch()
            }

        )
        Spacer(modifier = Modifier.height(PaddingL))
        Text(
            text = titles,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = PaddingL)
                .basicMarquee(),
            fontSize = 12.sp,
            color = colorResource(id = R.color.placeholder)
        )

        Spacer(modifier = Modifier.height(PaddingL))
        val pullToRefreshState = rememberPullToRefreshState()
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(modifier = Modifier.nestedScroll(pullToRefreshState.nestedScrollConnection)) {
                LaunchedEffect(pullToRefreshState.isRefreshing) {
                    if (pullToRefreshState.isRefreshing) {
                        event(HomeEvent.UpdateNews)
                    }

                }
                LaunchedEffect(isRefreshing) {
                    if (isRefreshing == true) {
                        pullToRefreshState.startRefresh()
                    } else {
                        pullToRefreshState.endRefresh()
                    }
                }
                PullToRefreshContainer(
                    state = pullToRefreshState,
                    modifier = Modifier.align(
                        Alignment.TopCenter
                    ),
                )
                if (loadedArticles != null) {
                    ArticlesList(
                        articles = loadedArticles,
                        onClick = {
                            navigateToDetails(it)
                        },
                        modifier = Modifier.padding(horizontal = PaddingL)
                    )
                }

            }
        }

    }


}