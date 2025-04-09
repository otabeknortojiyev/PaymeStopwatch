package uz.payme.otabek.presentation.screens.weather

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import uz.payme.otabek.R
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import uz.payme.otabek.presentation.screens.weather.WeatherScreenContract.WeatherUiStates
import uz.payme.otabek.presentation.screens.weather.WeatherScreenContract.Intent
import uz.payme.otabek.presentation.screens.weather.items.DotsIndicatorItem
import uz.payme.otabek.presentation.screens.weather.items.ErrorItem
import uz.payme.otabek.presentation.screens.weather.items.FiveDayForecast
import uz.payme.otabek.presentation.screens.weather.items.SmallIcons
import uz.payme.otabek.presentation.screens.weather.items.SunPagerItem
import uz.payme.otabek.presentation.screens.weather.items.WeatherInfoItem
import uz.payme.otabek.utils.kelvinToCelsius

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherScreen(navIconClick: () -> Unit) {
    val weatherViewModel: WeatherViewModel = hiltViewModel()
    val weatherUiState = weatherViewModel.weatherUiState.collectAsState()
    WeatherScreenContent(
        uiState = weatherUiState, eventDispatcher = weatherViewModel::eventDispatcher, navIconClick = navIconClick
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class, ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun WeatherScreenContent(
    uiState: State<WeatherUiStates>, eventDispatcher: (Intent) -> Unit, navIconClick: () -> Unit
) {
    LaunchedEffect(Unit) {
        eventDispatcher(Intent.Init)
    }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.value.isLoading, onRefresh = { eventDispatcher(Intent.Init) })
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.inversePrimary,
                    scrolledContainerColor = Color.Transparent,
                    navigationIconContentColor = Color.White,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),

                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(text = stringResource(R.string.Tashkent), color = Color.White)
                        Image(
                            painter = painterResource(R.drawable.location),
                            contentDescription = stringResource(R.string.location),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                },

                navigationIcon = {
                    IconButton(
                        onClick = navIconClick, colors = IconButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.White,
                            disabledContainerColor = Color.Transparent,
                            disabledContentColor = Color.Transparent
                        )
                    ) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = stringResource(R.string.menu),
                        )
                    }
                })
        }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .pullRefresh(pullRefreshState)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.inversePrimary)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when {
                    uiState.value.errorMessage != null -> ErrorItem(
                        uiState = uiState, eventDispatcher = eventDispatcher
                    )

                    uiState.value.currentWeather != null && uiState.value.currentForecast != null -> WeatherInfoItem(
                        uiState = uiState
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