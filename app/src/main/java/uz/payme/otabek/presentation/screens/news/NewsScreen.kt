package uz.payme.otabek.presentation.screens.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import uz.payme.otabek.presentation.screens.news.NewsScreenContract.NewsUiStates

@Composable
fun NewsScreen(uiState: State<NewsUiStates>, eventDispatcher: (NewsScreenContract.Intent) -> Unit) {

    LaunchedEffect(Unit) {
        eventDispatcher(NewsScreenContract.Intent.Init())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            items(items = uiState.value.news) { model ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = model.author ?: "No author")
                    Text(text = model.title ?: "No title")
                    Text(text = model.description ?: "No description")
                    Text(text = model.publishedAt ?: "No publishedAt")
                    Text(text = model.content ?: "No content")
                }
            }
        }
    }
}