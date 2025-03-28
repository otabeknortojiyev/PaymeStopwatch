package uz.payme.otabek.presentation.screens.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import uz.payme.otabek.presentation.screens.news.NewsScreenContract.NewsUiStates

@Composable
fun FavoriteScreen(uiState: State<NewsUiStates>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
    ) {

    }
}