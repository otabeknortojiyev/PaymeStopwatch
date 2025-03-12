package uz.payme.otabek.presentation.screens.main


import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import uz.payme.otabek.utils.MyEventListener
import uz.payme.otabek.R
import uz.payme.otabek.data.AppDataStoreImpl
import uz.payme.otabek.presentation.screens.main.uiStates.ButtonUiState
import uz.payme.otabek.presentation.screens.main.uiStates.CirclesUiState
import uz.payme.otabek.presentation.screens.main.uiStates.TimeUiState
import uz.payme.otabek.utils.formatTime
import uz.payme.otabek.ui.theme.PaymeStopwatchTheme


class MainActivity : ComponentActivity() {
    val viewModel: AppViewModel by viewModels {
        AppViewModelFactory(AppDataStoreImpl(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PaymeStopwatchTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val timeState = viewModel.timeUiState.collectAsState()
                    val buttonState = viewModel.buttonUiState.collectAsState()
                    val circlesState = viewModel.circlesUiState.collectAsState()
                    MainScreen(
                        modifier = Modifier.padding(innerPadding),
                        timeState = timeState,
                        buttonState = buttonState,
                        circlesState = circlesState,
                        start = { viewModel.start() },
                        reset = { viewModel.reset() },
                        saveState = { viewModel.saveState() },
                        getState = { viewModel.getState() },
                        setCircles = { viewModel.setCircle() },
                        clearCircles = { viewModel.clearCircles() })
                }
            }
        }
    }
}


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    timeState: State<TimeUiState>,
    buttonState: State<ButtonUiState>,
    circlesState: State<CirclesUiState>,
    start: () -> Unit,
    reset: () -> Unit,
    saveState: () -> Unit,
    getState: () -> Unit,
    setCircles: () -> Unit,
    clearCircles: () -> Unit
) {
    val context = LocalContext.current
    val startStopPlayer = MediaPlayer.create(context, R.raw.boop)
    val resetPlayer = MediaPlayer.create(context, R.raw.breeze)

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(text = formatTime(timeState.value.time), color = Color.Black, fontSize = 64.sp)
        if (circlesState.value.list.isNotEmpty()) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(items = circlesState.value.list.reversed()) { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = formatTime(item), fontSize = 16.sp)
                    }
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(
                onClick = {
                    if (timeState.value.wasRunning) {
                        setCircles()
                    } else {
                        reset()
                        clearCircles()
                    }
                    resetPlayer.start()
                }, colors = ButtonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.DarkGray,
                    disabledContainerColor = Color.LightGray,
                    disabledContentColor = Color.DarkGray
                ), modifier = Modifier.width(screenWidth / 3)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = buttonState.value.firstButton,
                    fontSize = 20.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
            Button(
                onClick = {
                    start()
                    startStopPlayer.start()
                }, colors = ButtonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Red,
                    disabledContentColor = Color.White
                ), modifier = Modifier.width(screenWidth / 3)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = buttonState.value.secondButton,
                    fontSize = 20.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    }
    MyEventListener {
        when (it) {
            Lifecycle.Event.ON_RESUME -> getState()
            Lifecycle.Event.ON_PAUSE -> saveState()

            else -> {

            }
        }
    }
}