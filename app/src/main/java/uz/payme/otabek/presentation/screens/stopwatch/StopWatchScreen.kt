package uz.payme.otabek.presentation.screens.stopwatch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import uz.payme.otabek.presentation.screens.stopwatch.StopWatchScreenContract.Intent
import uz.payme.otabek.presentation.screens.stopwatch.StopWatchScreenContract.StopWatchUiStates
import uz.payme.otabek.utils.ActivityLifecycleListener
import uz.payme.otabek.utils.formatCircleTime
import uz.payme.otabek.utils.formatTime

@Composable
fun StopWatchScreen(
    modifier: Modifier = Modifier, startPlayer: () -> Unit, resetPlayer: () -> Unit
) {
    val viewModel: StopWatchViewModel = viewModel()
    val uiState = viewModel.uiState.collectAsState()
    StopWatchScreenContent(
        modifier = modifier,
        uiStates = uiState,
        eventDispatcher = viewModel::eventDispatcher,
        startPlayer = startPlayer,
        resetPlayer = resetPlayer
    )
}

@Composable
private fun StopWatchScreenContent(
    modifier: Modifier = Modifier,
    uiStates: State<StopWatchUiStates>,
    eventDispatcher: (Intent) -> Unit,
    startPlayer: () -> Unit,
    resetPlayer: () -> Unit
) {
    LaunchedEffect(Unit) {
        eventDispatcher(Intent.GetState)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF5999e0)),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatTime(uiStates.value.timeUiState.time),
            color = Color.White,
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 24.dp)
        )
        LazyColumn(modifier = Modifier.weight(1f)) {
            itemsIndexed(
                items = uiStates.value.circlesUiState.list
            ) { index, item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .background(Color(0xFF528fd7), shape = RoundedCornerShape(8.dp))
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = (uiStates.value.circlesUiState.list.size - index).toString(),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                    Text(
                        text = formatCircleTime(item),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
        ) {
            Button(
                onClick = {
                    eventDispatcher(Intent.ClickLeftButton)
                    resetPlayer()
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray, contentColor = Color.White
                ), shape = RoundedCornerShape(12.dp), modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Text(
                    text = uiStates.value.buttonUiState.firstButton,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
            Button(
                onClick = {
                    eventDispatcher(Intent.Start)
                    startPlayer()
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = if (uiStates.value.timeUiState.wasRunning) Color.Red else Color.White,
                    contentColor = Color.White
                ), shape = RoundedCornerShape(12.dp), modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Text(
                    text = uiStates.value.buttonUiState.secondButton,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = Color.Black
                )
            }
        }
    }
    ActivityLifecycleListener {
        when (it) {
            Lifecycle.Event.ON_RESUME -> eventDispatcher(Intent.GetState)
            Lifecycle.Event.ON_STOP -> eventDispatcher(Intent.SaveState)
            else -> Unit
        }
    }
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Color(0xFF5999e0))
    systemUiController.setNavigationBarColor(Color(0xFF5999e0))
}