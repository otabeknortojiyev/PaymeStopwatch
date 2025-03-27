package uz.payme.otabek.presentation.screens.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import uz.payme.otabek.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import uz.payme.otabek.presentation.screens.news.NewsScreenContract.Intent
import uz.payme.otabek.presentation.screens.news.NewsScreenContract.NewsUiStates


@Composable
fun NewsMain(navIconClick: () -> Unit) {
    val viewModel: NewsViewModel = viewModel()
    val uiState = viewModel.newsUiState.collectAsState()
    NewsMainContent(uiState = uiState, eventDispatcher = viewModel::eventDispatcher, navIconClick = navIconClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsMainContent(
    uiState: State<NewsUiStates>,
    eventDispatcher: (Intent) -> Unit,
    navIconClick: () -> Unit
) {
    val items = listOf(
        BottomNavItem(
            text = "Новости", selectedIcon = R.drawable.news_item_blue, unselectedIcon = R.drawable.news_item_gray
        ), BottomNavItem(
            text = "Избранное", selectedIcon = R.drawable.favorite_blue, unselectedIcon = R.drawable.favorite_gray
        )
    )

    var selectedItem by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    scrolledContainerColor = Color.Transparent,
                    navigationIconContentColor = Color.White,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                title = {
                    Text(text = stringResource(R.string.news), color = MaterialTheme.colorScheme.onPrimary)
                },
                navigationIcon = {
                    IconButton(
                        onClick = navIconClick, colors = IconButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            disabledContainerColor = Color.Transparent,
                            disabledContentColor = Color.Transparent
                        )
                    ) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = "Menu",
                        )
                    }
                }
            )
        },
        bottomBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(color = MaterialTheme.colorScheme.onPrimary)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.primary),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items.forEachIndexed { index, item ->
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    selectedItem = index
                                },
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = if (index == selectedItem) {
                                    painterResource(item.selectedIcon)
                                } else {
                                    painterResource(item.unselectedIcon)
                                }, contentDescription = item.text
                            )
                            Text(
                                text = item.text,
                                color = if (index == selectedItem) Color(0xFF5999e0) else Color(0xFF999999)
                            )
                        }
                    }
                }
            }
        }) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedItem) {
                0 -> NewsScreen(uiState = uiState)
                1 -> FavoriteScreen(uiState = uiState)
            }
        }
    }
}

data class BottomNavItem(
    val text: String, val selectedIcon: Int, val unselectedIcon: Int
)