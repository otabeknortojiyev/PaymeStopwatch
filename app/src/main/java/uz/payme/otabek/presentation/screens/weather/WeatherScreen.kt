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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.payme.otabek.R
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import uz.payme.otabek.presentation.screens.weather.WeatherScreenContract.WeatherUiStates
import uz.payme.otabek.presentation.screens.weather.WeatherScreenContract.Intent
import uz.payme.otabek.presentation.screens.weather.items.DotsIndicatorItem
import uz.payme.otabek.presentation.screens.weather.items.FiveDayForecast
import uz.payme.otabek.presentation.screens.weather.items.SmallIcons
import uz.payme.otabek.presentation.screens.weather.items.SunPagerItem
import uz.payme.otabek.utils.getDayOfWeekFromDate
import uz.payme.otabek.utils.kelvinToCelsius

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherScreen(navIconClick: () -> Unit) {
    val weatherViewModel: WeatherViewModel = viewModel()
    val weatherUiState = weatherViewModel.weatherUiState.collectAsState()
    WeatherScreenContent(
        uiState = weatherUiState, eventDispatcher = weatherViewModel::eventDispatcher, navIconClick = navIconClick
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class, ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun WeatherScreenContent(
    uiState: State<WeatherUiStates>,
    eventDispatcher: (Intent) -> Unit,
    navIconClick: () -> Unit
) {

    LaunchedEffect(Unit) { eventDispatcher(Intent.Init) }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.value.isLoading,
        onRefresh = { eventDispatcher(Intent.Init) }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarColors(
                    containerColor = Color(0xFF5999e0),
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
                            contentDescription = "Location",
                            modifier = Modifier
                                .width(16.dp)
                                .height(16.dp)
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
                            contentDescription = "Menu",
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
                    .background(color = Color(0xFF5999e0))
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (uiState.value.errorMessage != null) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Ошибка",
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Ошибка: ${uiState.value.errorMessage}",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            IconButton(
                                onClick = { eventDispatcher(Intent.Init) }, modifier = Modifier.align(Alignment.End)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "Обновить",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                } else if (uiState.value.currentWeather != null && uiState.value.currentForecast != null) {
                    val currentWeather = uiState.value.currentWeather
                    val currentForecast = uiState.value.currentForecast
                    val dayForecast = currentForecast?.list?.subList(0, 5)
                    val temperatures = dayForecast?.mapNotNull { it.main?.temp }
                    val minTemp = temperatures?.minOrNull()?.let { it - 273.15 }?.toInt()
                    Column(
                        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = (kelvinToCelsius(currentWeather?.main?.temp)).toString() + "°C",
                                color = Color.White,
                                fontSize = 72.sp,
                                fontWeight = FontWeight.W600
                            )
                            val imageCode = currentWeather?.weather!![0].icon
                            AsyncImage(
                                modifier = Modifier
                                    .weight(1f)
                                    .size(120.dp),
                                model = "https://openweathermap.org/img/wn/$imageCode@2x.png",
                                contentDescription = null,
                            )
                        }
                        Text(
                            text = currentWeather?.weather?.get(0)?.description ?: "Нету описания",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.White
                        )
                        Text(
                            modifier = Modifier.padding(top = 36.dp),
                            text = "${kelvinToCelsius(currentWeather?.main?.tempMax)}° / $minTemp° Ощущается как ${
                                kelvinToCelsius(
                                    currentWeather?.main?.feelsLike
                                )
                            }°",
                            color = Color.White,
                            fontWeight = FontWeight.Light,
                            fontSize = 20.sp
                        )
                        val pagerState = rememberPagerState(initialPage = 0)
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp)
                                .background(color = Color(0xFF528fd7), shape = RoundedCornerShape(16.dp))
                        ) {
                            Row {
                                HorizontalPager(
                                    count = 2, state = pagerState, modifier = Modifier.fillMaxWidth()
                                ) { page ->
                                    when (page) {
                                        0 -> SunPagerItem(
                                            time = currentWeather?.sys?.sunrise,
                                            text1 = "Не пропустите рассвет",
                                            text2 = "Солнце встанет",
                                            icon = R.drawable.sunrise
                                        )

                                        else -> SunPagerItem(
                                            time = currentWeather?.sys?.sunset,
                                            text1 = "Не пропустите закат",
                                            text2 = "Солнце зайдёт",
                                            icon = R.drawable.sunset
                                        )
                                    }
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                DotsIndicatorItem(pagerState = pagerState.currentPage)
                            }
                        }
                        FiveDayForecast(forecast = currentForecast?.list)
                        SmallIcons(speed = currentWeather?.wind?.speed, humidity = currentWeather?.main?.humidity)
                    }
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