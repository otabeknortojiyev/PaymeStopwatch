package uz.payme.otabek.presentation.screens.news.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import uz.payme.otabek.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import okhttp3.Dispatcher
import uz.payme.domain.models.NewsModel
import uz.payme.otabek.presentation.actions_handler.AppMainScreenAction
import uz.payme.otabek.presentation.screens.news.NewsScreenContract

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsItem(
    newsModel: NewsModel, itemClick: (AppMainScreenAction) -> Unit, eventDispatcher: (NewsScreenContract.Intent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { itemClick.invoke(AppMainScreenAction.OpenWebView(url = newsModel.url)) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            model = newsModel.urlToImage,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(shape = RoundedCornerShape(20.dp)),
            contentScale = ContentScale.Crop,
            failure = placeholder(R.drawable.news_placeholder)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Image(
                painter = if (newsModel.isFavorite) painterResource(R.drawable.favorite_blue) else painterResource(R.drawable.favorite_gray),
                contentDescription = ""
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = newsModel.title ?: "", color = Color.Black, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = newsModel.description ?: "", color = Color.Black, fontSize = 12.sp)
    }
}