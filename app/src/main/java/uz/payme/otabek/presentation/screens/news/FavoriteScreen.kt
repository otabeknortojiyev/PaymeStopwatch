package uz.payme.otabek.presentation.screens.news

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.payme.otabek.R
import uz.payme.otabek.presentation.actions_handler.AppMainScreenAction
import uz.payme.otabek.presentation.screens.news.NewsScreenContract.NewsUiStates
import uz.payme.otabek.presentation.screens.news.items.NewsItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavoriteScreen(
    uiState: State<NewsUiStates>,
    eventDispatcher: (NewsScreenContract.Intent) -> Unit,
    actions: (AppMainScreenAction) -> Unit
) {
    LaunchedEffect(Unit) {
        eventDispatcher(NewsScreenContract.Intent.GetFavorites)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        Text(
            text = stringResource(R.string.saved),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Light
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .background(color = Color.Red)
        ) {
            val pullRefreshState = rememberPullRefreshState(
                refreshing = uiState.value.isLoading, onRefresh = { })
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
            ) {
                items(items = uiState.value.news) { model ->
                    NewsItem(
                        newsModel = model,
                        itemClick = { action ->
                            actions.invoke(action)
                        },
                        eventDispatcher = eventDispatcher,
                        forFavorite = true,
                        modifier = Modifier.animateItem(
                            fadeOutSpec = tween(durationMillis = 500), // более плавное исчезновение
                            placementSpec = spring(
                                stiffness = Spring.StiffnessLow,       // мягче перемещение
                                dampingRatio = Spring.DampingRatioMediumBouncy // можно добавить упругости
                            )
                        )
                    )
                }
            }
            PullRefreshIndicator(
                refreshing = uiState.value.isLoading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                backgroundColor = Color.White,
            )
        }
    }
}