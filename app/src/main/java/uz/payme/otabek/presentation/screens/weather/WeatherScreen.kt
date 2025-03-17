package uz.payme.otabek.presentation.screens.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import uz.payme.otabek.presentation.screens.weather.WeatherModelContract.WeatherUiStates
import uz.payme.otabek.presentation.screens.weather.WeatherModelContract.Intent

@Composable
fun WeatherScreen(modifier: Modifier = Modifier) {
    val viewModel: WeatherViewModel = viewModel()
    val uiState = viewModel.uiState.collectAsState()
    WeatherScreenContent(modifier = modifier, uiStates = uiState, eventDispatcher = viewModel::eventDispatcher)
}

@Composable
private fun WeatherScreenContent(
    modifier: Modifier = Modifier,
    uiStates: State<WeatherUiStates>,
    eventDispatcher: (Intent) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "${uiStates.value.temperature} °C")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = uiStates.value.description.toString())
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Wind: ${uiStates.value.wind} m/s")
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "Humidity: ${uiStates.value.humidity}%")
        }
    }
}
