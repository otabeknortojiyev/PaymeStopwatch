package uz.payme.otabek.presentation.screens.weather.items

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import uz.payme.otabek.R
import uz.payme.otabek.presentation.screens.weather.WeatherScreenContract.WeatherUiStates
import uz.payme.otabek.utils.kelvinToCelsius

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun WeatherInfoItem(uiState: State<WeatherUiStates>) {
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
            text = currentWeather?.weather?.get(0)?.description ?: stringResource(R.string.no_description),
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
                .background(color = MaterialTheme.colorScheme.onPrimaryContainer, shape = RoundedCornerShape(16.dp))
        ) {
            Row {
                HorizontalPager(
                    count = 2, state = pagerState, modifier = Modifier.fillMaxWidth()
                ) { page ->
                    when (page) {
                        0 -> SunPagerItem(
                            time = currentWeather?.sys?.sunrise,
                            text1 = stringResource(R.string.Dont_miss_the_sunrise),
                            text2 = stringResource(R.string.the_sun_will_rise),
                            icon = R.drawable.sunrise
                        )

                        else -> SunPagerItem(
                            time = currentWeather?.sys?.sunset,
                            text1 = stringResource(R.string.Dont_miss_the_sunset),
                            text2 = stringResource(R.string.the_sun_will_go_down),
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