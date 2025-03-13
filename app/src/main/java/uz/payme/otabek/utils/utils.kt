package uz.payme.otabek.utils

import android.annotation.SuppressLint

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