package uz.payme.otabek.presentation.activity

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import uz.payme.otabek.data.AppDataStoreImpl
import uz.payme.otabek.presentation.screens.main.AppViewModel
import uz.payme.otabek.presentation.screens.main.AppViewModelFactory
import uz.payme.otabek.presentation.screens.main.MainScreen
import uz.payme.otabek.ui.theme.PaymeStopwatchTheme
import kotlin.getValue
import uz.payme.otabek.R

class MainActivity : ComponentActivity() {
    private val viewModel: AppViewModel by viewModels {
        AppViewModelFactory(AppDataStoreImpl(this))
    }
    val startStopPlayer by lazy { MediaPlayer.create(this, R.raw.boop) }
    val resetPlayer by lazy { MediaPlayer.create(this, R.raw.breeze) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val uiStates = viewModel.uiState.collectAsState()
            PaymeStopwatchTheme {
                Scaffold { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding),
                        uiStates = uiStates,
                        eventDispatcher = { intent -> viewModel.eventDispatcher(intent) },
                        startPlayer = { startStopPlayer.start() },
                        resetPlayer = { resetPlayer.start() })
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        startStopPlayer.release()
        resetPlayer.release()
    }
}