package uz.payme.otabek.presentation.screens.weather.items

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import uz.payme.domain.models.Per3Hour
import uz.payme.otabek.utils.getDayOfWeekFromDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FiveDayForecast(forecast: List<Per3Hour>?) {
    val elementsPerDay = 8
    val daysCount = 5
    if (forecast.isNullOrEmpty()) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = MaterialTheme.colorScheme.inversePrimary,
            strokeWidth = 4.dp
        )
        return
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFF528fd7), shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "5-дневный прогноз", fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        repeat(daysCount) { dayIndex ->
            val initialPosition = dayIndex * elementsPerDay
            val endPosition = (initialPosition + elementsPerDay).coerceAtMost(forecast.size)
            if (initialPosition < forecast.size) {
                val dayForecast = forecast.subList(initialPosition, endPosition)
                val dateText = dayForecast.firstOrNull()?.dtTxt?.split(" ")?.get(0)
                val dayOfWeek = getDayOfWeekFromDate(LocalContext.current, dateText ?: "")
                val temperatures = dayForecast.mapNotNull { it.main?.temp }
                val minTemp = temperatures.minOrNull()?.let { it - 273.15 }?.toInt()
                val maxTemp = temperatures.maxOrNull()?.let { it - 273.15 }?.toInt()
                val minTempIcon =
                    dayForecast.firstOrNull { it.main?.temp == temperatures.minOrNull() }?.weather?.firstOrNull()?.icon
                val maxTempIcon =
                    dayForecast.firstOrNull { it.main?.temp == temperatures.maxOrNull() }?.weather?.firstOrNull()?.icon
                DayForecastRow(dayOfWeek, minTemp, maxTemp, minTempIcon, maxTempIcon)
            }
        }
    }
}

@Composable
fun DayForecastRow(dayOfWeek: String?, minTemp: Int?, maxTemp: Int?, minTempIcon: String?, maxTempIcon: String?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp), contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Text(
                text = dayOfWeek ?: "", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
        ) {
            minTempIcon?.let {
                val iconUrl = "https://openweathermap.org/img/wn/$it.png"
                Row(
                    verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        model = iconUrl, contentDescription = "Weather icon", modifier = Modifier.size(40.dp)
                    )
                }
            }
            val celsiusMinTemp = minTemp ?: "N/A"
            Text(
                text = "$celsiusMinTemp°C", color = Color.White, fontSize = 20.sp
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(end = 4.dp)
                .align(Alignment.CenterEnd)
        ) {
            maxTempIcon?.let {
                val iconUrl = "https://openweathermap.org/img/wn/$it.png"
                Row(
                    verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        model = iconUrl, contentDescription = "Weather icon", modifier = Modifier.size(40.dp)
                    )
                }
            }
            val celsiusMaxTemp = maxTemp ?: "N/A"
            Text(
                text = "$celsiusMaxTemp°C", color = Color.White, fontSize = 20.sp
            )
        }
    }
}