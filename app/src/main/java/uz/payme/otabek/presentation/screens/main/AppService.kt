package uz.payme.otabek.presentation.screens.main

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.SystemClock
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.payme.otabek.R
import uz.payme.otabek.presentation.activity.MainActivity
import uz.payme.otabek.utils.formatTime

const val CHANNEL_ID = "timer_channel"
const val STOP_SERVICE = "STOP_SERVICE"

class AppService : Service() {
    private val binder = LocalBinder()
    private var job: Job? = null

    inner class LocalBinder : Binder() {
        fun getService(): AppService = this@AppService
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    fun observeTimeFlow(timeFlow: StateFlow<AppViewModelContract.UiStates>) {
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
                CHANNEL_ID, "Таймер Уведомления", NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun updateNotification(time: String) {
        val stopIntent = PendingIntent.getService(
            this,
            0,
            Intent(this, AppService::class.java).apply { action = STOP_SERVICE },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification =
            NotificationCompat.Builder(this, CHANNEL_ID).setContentTitle("Таймер").setContentText("Прошло: $time")
                .setSmallIcon(R.drawable.timer).setPriority(NotificationCompat.PRIORITY_LOW)
                .addAction(R.drawable.stop, "Стоп", stopIntent).build()

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
}

