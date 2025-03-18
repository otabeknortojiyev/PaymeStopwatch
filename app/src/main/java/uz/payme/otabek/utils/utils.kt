package uz.payme.otabek.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import uz.payme.otabek.data.network.Per3Hour
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale

@SuppressLint("DefaultLocale")
fun formatTime(milliseconds: Long): String {
    val hours = (milliseconds / 3600000) % 24
    val minutes = (milliseconds / 60000) % 60
    val seconds = (milliseconds / 1000) % 60
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

@SuppressLint("DefaultLocale")
fun formatCircleTime(milliseconds: Long): String {
    val hours = (milliseconds / 3600000) % 24
    val minutes = (milliseconds / 60000) % 60
    val seconds = (milliseconds / 1000) % 60
    val millis = milliseconds % 1000 / 10
    return String.format("%02d:%02d:%02d.%02d", hours, minutes, seconds, millis)
}


enum class ButtonNames(val value: String) {
    INTERVAL("Интервал"), START("Начать"), CONTINUE("Продолжить"), STOP("Стоп"), RESET("Сбросить")
}

fun kelvinToCelsius(kelvin: Double?): Int {
    kelvin?.let { return (kelvin - 273.15).toInt() }
    return 0
}

fun convertUnixTimestampToTime(timestamp: Long?): String {
    timestamp?.let {
        val date = Date(timestamp * 1000)
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        return format.format(date)
    }
    return ""
}

@RequiresApi(Build.VERSION_CODES.O)
fun getDayOfWeekFromDate(context: Context, dateString: String): String {
    return try {
        val date = LocalDate.parse(dateString)
        val currentLocale: Locale = context.resources.configuration.locales[0]
        val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.FULL, currentLocale)
        dayOfWeek
    } catch (e: Exception) {
        "Неверный формат даты"
    }
}

