package uz.payme.otabek.presentation.screens.news

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.payme.otabek.presentation.actions_handler.AppMainScreenAction
import uz.payme.otabek.presentation.screens.news.NewsScreenContract.NewsUiStates
import uz.payme.otabek.presentation.screens.news.items.NewsItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsScreen(
    uiState: State<NewsUiStates>,
    eventDispatcher: (NewsScreenContract.Intent) -> Unit,
    actions: (AppMainScreenAction) -> Unit
) {
    val selectedCategoryIndex = rememberSaveable { mutableIntStateOf(0) }

    val categoryList = listOf(
        NewsCategory(name = "Бизнес", queryName = "business"),
        NewsCategory(name = "Технологии", queryName = "technology"),
        NewsCategory(name = "Спорт", queryName = "sports"),
        NewsCategory(name = "Здоровье", queryName = "health"),
        NewsCategory(name = "Развлечение", queryName = "entertainment"),
        NewsCategory(name = "Наука", queryName = "science"),
    )

    LaunchedEffect(Unit) {
        eventDispatcher(NewsScreenContract.Intent.Init(query = "business"))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier)
            categoryList.forEachIndexed { index, category ->
                Text(
                    text = category.name,
                    color = if (selectedCategoryIndex.intValue == index) Color.Red else Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(12.dp))
                        .clickable {
                            selectedCategoryIndex.intValue = index

                        }
                        .border(
                            width = 1.dp,
                            color = if (selectedCategoryIndex.intValue == index) Color.Red else Color.Black,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(vertical = 8.dp, horizontal = 16.dp))
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            val pullRefreshState = rememberPullRefreshState(
                refreshing = uiState.value.isLoading, onRefresh = {
                    eventDispatcher(NewsScreenContract.Intent.Init(query = categoryList[selectedCategoryIndex.intValue].queryName))
                })
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
                        }
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

data class NewsCategory(val name: String, val queryName: String)