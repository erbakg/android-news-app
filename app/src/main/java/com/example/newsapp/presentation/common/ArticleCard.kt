package com.example.newsapp.presentation.common

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsapp.R
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.model.Source
import com.example.newsapp.presentation.Dimensions.ArticleCardSize
import com.example.newsapp.presentation.Dimensions.PaddingXS
import com.example.newsapp.presentation.Dimensions.PaddingXXS
import com.example.newsapp.presentation.Dimensions.SmallIconSize
import com.example.newsapp.ui.theme.NewsAppTheme
import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone


@Composable
fun ArticleCard(
    article: Article,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    Row(modifier = modifier.clickable { onClick() }) {
        AsyncImage(
            model = ImageRequest.Builder(context).data(article.urlToImage).build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(ArticleCardSize)
                .clip(MaterialTheme.shapes.medium)
        )

        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(horizontal = PaddingXXS)
                .height(ArticleCardSize)
        ) {
            Text(
                text = article.title,
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.text_title),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = article.source.name,
                    style = MaterialTheme.typography.labelMedium
                        .copy(fontWeight = FontWeight.Bold),
                    color = colorResource(id = R.color.body)
                )
                Spacer(modifier = Modifier.width(PaddingXS))
                Icon(
                    painter = painterResource(id = R.drawable.ic_time),
                    contentDescription = null,
                    modifier = Modifier.size(SmallIconSize),
                    tint = colorResource(id = R.color.body)
                )
                Spacer(modifier = Modifier.width(PaddingXS))
                val prettyTime = PrettyTime()
                val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                dateFormat.timeZone = TimeZone.getTimeZone("UTC")
                val date: Date? = dateFormat.parse(article.publishedAt)
                Text(
                    text = prettyTime.format(date),
                    style = MaterialTheme.typography.labelMedium
                        .copy(fontWeight = FontWeight.Bold),
                    color = colorResource(id = R.color.body)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ArticleCardPreview() {
    NewsAppTheme {
        ArticleCard(
            article = Article(
                author = "Antje Erhard",
                content = "Stand: 11.07.2024 08:15 Uhr\r\nDer Bitcoin ist in den vergangenen Wochen auffällig stark gefallen. Ein Grund sind offensichtlich beschlagnahmte Kryptowährungs-Bestände, die der Freistaat Sachsen verkau… [+4942 chars]",
                description = "Der Bitcoin ist in den vergangenen Wochen auffällig stark gefallen. Ein Grund sind offensichtlich beschlagnahmte Kryptowährungs-Bestände, die der Freistaat Sachsen verkauft hat. Es gibt weitere Ursachen. Von Antje Erhard.",
                publishedAt = "2024-07-11T06:15:49Z",
                source = Source(
                    id = "",
                    name = "tagesschau.de"
                ),
                title = "Was Sachsen mit dem Bitcoin-Kursrutsch zu tun hat",
                url = "https://www.tagesschau.de/wirtschaft/boerse/bitcoin-kursverluste-sachsen-verkaeufe-japan-100.html",
                urlToImage = "\"https://images.tagesschau.de/image/ca5b3fa9-8f9b-4788-8005-884b9102f5eb/AAABhnP95yA/AAABjwnlFvA/16x9-1280/bitcoin-173.jpg\","
            ), onClick = {}, modifier = Modifier
        )
    }

}