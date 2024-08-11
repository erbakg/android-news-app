package com.example.newsapp.presentation.details

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsapp.R
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.model.Source
import com.example.newsapp.presentation.Dimensions.ArticleImageHeight
import com.example.newsapp.presentation.Dimensions.PaddingL
import com.example.newsapp.presentation.details.components.DetailsTopBar
import com.example.newsapp.ui.theme.NewsAppTheme
import kotlinx.coroutines.flow.StateFlow

@Composable
fun DetailsScreen(
    article: Article,
    isArticleExist: Boolean,
    event: (DetailsEvent) -> Unit,
    navigateUp: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        DetailsTopBar(
            isArticleExist = isArticleExist,
            onBrowsingClick = {
                Intent(Intent.ACTION_VIEW).also {
                    it.data = Uri.parse(article.url)
                    if (it.resolveActivity(context.packageManager) != null) {
                        context.startActivity(it)
                    }
                }
            },
            onShareClick = {
                Intent(Intent.ACTION_SEND).also {
                    it.putExtra(Intent.EXTRA_TEXT, article.url)
                    it.type = "text/plain"
                    if (it.resolveActivity(context.packageManager) != null) {
                        context.startActivity(it)
                    }
                }
            },
            onBookmarkClick = { event(DetailsEvent.UpsertDeleteArticle(article)) },
            onBackClick = navigateUp
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                start = PaddingL,
                end = PaddingL,
                top = PaddingL,
            )
        ) {
            item {
                AsyncImage(
                    model = ImageRequest.Builder(
                        context = context
                    ).data(article.urlToImage).build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(ArticleImageHeight)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(PaddingL))

                Text(
                    text = article.title,
                    style = MaterialTheme.typography.displaySmall,
                    color = colorResource(id = R.color.text_title)
                )
                Text(
                    text = article.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(id = R.color.body)
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DetailsScreenPreview() {
    NewsAppTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {

            DetailsScreen(article = Article(
                author = "Antje Erhard",
                content = "Stand: 11.07.2024 08:15 Uhr\r\nDer Bitcoin ist in den vergangenen Wochen auffällig stark gefallen. Ein Grund sind offensichtlich beschlagnahmte Kryptowährungs-Bestände, die der Freistaat Sachsen verkau… [+4942 chars]",
                description = "Der Bitcoin ist in den vergangenen Wochen auffällig stark gefallen. Ein Grund sind offensichtlich beschlagnahmte Kryptowährungs-Bestände, die der Freistaat Sachsen verkauft hat. Es gibt weitere Ursachen. Von Antje Erhard.",
                publishedAt = "2024-07-11T06:15:49Z",
                source = Source(
                    id = "", name = "tagesschau.de"
                ),
                title = "Was Sachsen mit dem Bitcoin-Kursrutsch zu tun hat",
                url = "https://www.tagesschau.de/wirtschaft/boerse/bitcoin-kursverluste-sachsen-verkaeufe-japan-100.html",
                urlToImage = "\"https://images.tagesschau.de/image/ca5b3fa9-8f9b-4788-8005-884b9102f5eb/AAABhnP95yA/AAABjwnlFvA/16x9-1280/bitcoin-173.jpg\","
            ), event = {},
                isArticleExist = true
                ) {

            }
        }
    }

}