package uz.payme.otabek


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import uz.payme.otabek.ui.theme.PaymeStopwatchTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PaymeStopwatchTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val viewModel: AppViewModel = viewModel()
    val timeState = viewModel.timeUiState.collectAsState()
    val buttonState = viewModel.buttonUiState.collectAsState()

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(text = formatTime(timeState.value.time), color = Color.Black, fontSize = 64.sp)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(
                onClick = {
                    viewModel.pause()
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
                    viewModel.start()
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
//    MyEventListener {
//        when (it) {
//            Lifecycle.Event.ON_RESUME -> {
//                viewModel.start()
//            }
//
//            Lifecycle.Event.ON_PAUSE -> {
//                viewModel.pause()
//
//            }
//
//            else -> {
//
//            }
//        }
//    }
}