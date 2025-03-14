package uz.payme.otabek.presentation.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.app.ServiceCompat.startForeground
import uz.payme.otabek.data.AppDataStoreImpl
import uz.payme.otabek.presentation.screens.main.AppViewModel
import uz.payme.otabek.presentation.screens.main.AppViewModelFactory
import uz.payme.otabek.presentation.screens.main.MainScreen
import uz.payme.otabek.ui.theme.PaymeStopwatchTheme
import kotlin.getValue
import uz.payme.otabek.R
import uz.payme.otabek.presentation.screens.main.AppService

class MainActivity : ComponentActivity() {
    private val viewModel: AppViewModel by viewModels {
        AppViewModelFactory(AppDataStoreImpl(this))
    }
    private var service: AppService? = null
    private var bound = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            val localBinder = binder as? AppService.LocalBinder
            service = localBinder?.getService()
            bound = true
            service?.observeTimeFlow(viewModel.uiState)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            service = null
            bound = false
        }
    }

    private var startStopPlayer: MediaPlayer? = null
    private var resetPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Intent(this, AppService::class.java).also { intent ->
            bindService(intent, serviceConnection, BIND_AUTO_CREATE)
        }

        startStopPlayer = MediaPlayer.create(this, R.raw.boop)
        resetPlayer = MediaPlayer.create(this, R.raw.breeze)
        setContent {
            val uiStates = viewModel.uiState.collectAsState()
            PaymeStopwatchTheme {
                Scaffold { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding),
                        uiStates = uiStates,
                        eventDispatcher = { intent -> viewModel.eventDispatcher(intent) },
                        startPlayer = { startStopPlayer?.start() },
                        resetPlayer = { resetPlayer?.start() })
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        startStopPlayer?.release()
        resetPlayer?.release()
        startStopPlayer = null
        resetPlayer = null
        if (bound) {
            unbindService(serviceConnection)
            bound = false
        }
    }
}