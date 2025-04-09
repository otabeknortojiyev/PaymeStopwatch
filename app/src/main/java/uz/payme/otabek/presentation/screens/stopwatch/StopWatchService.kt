package uz.payme.otabek.presentation.screens.stopwatch

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.payme.otabek.R
import uz.payme.otabek.presentation.screens.stopwatch.StopWatchScreenContract.StopWatchUiStates
import uz.payme.otabek.utils.formatTime

class StopWatchService : Service() {
    private val binder = LocalBinder()
    private var job: Job? = null

    inner class LocalBinder : Binder() {
        fun getService(): StopWatchService = this@StopWatchService
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    fun observeTimeFlow(timeFlow: StateFlow<StopWatchUiStates>) {
        job?.cancel()
        job = CoroutineScope(Dispatchers.Main).launch {
            timeFlow.collect { uiState ->
                updateNotification(formatTime(uiState.timeUiState.time))
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID, getString(R.string.notification_timer), NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun updateNotification(time: String) {
        val stopIntent = PendingIntent.getService(
            this,
            0,
            Intent(this, StopWatchService::class.java).apply { action = STOP_SERVICE },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat
                .Builder(this, CHANNEL_ID)
                .setContentTitle(getString(R.string.timer))
                .setContentText(getString(R.string.gone, time))
                .setSmallIcon(R.drawable.timer_blue)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .addAction(R.drawable.stop, getString(R.string.stop), stopIntent)
                .build()

        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == STOP_SERVICE) {
            stopSelf()
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }

    companion object {
        private const val CHANNEL_ID = "timer_channel"
        private const val STOP_SERVICE = "STOP_SERVICE"
    }
}

